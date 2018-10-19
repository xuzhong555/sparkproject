package com.xuzhong.sparkproject.spark.session.rdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Row;

import com.xuzhong.sparkproject.dao.Top10CategorySessionMapper;
import com.xuzhong.sparkproject.domain.SessionDetail;
import com.xuzhong.sparkproject.domain.Top10CategorySession;
import com.xuzhong.sparkproject.service.SessionDetailService;
import com.xuzhong.sparkproject.spark.session.CategorySortKey;
import com.xuzhong.sparkproject.util.ApplicationContextUtils;
import com.xuzhong.sparkproject.util.Constants;
import com.xuzhong.sparkproject.util.StringUtils;

import scala.Tuple2;

public class Top10SessionRDD {

	/**
	 * 获取top10热门品类
	 * 
	 * @param top10CategaryList
	 * @param sc
	 * @param filteredSessionid2AggrInfoRDD
	 * @param sessionid2actionRDD
	 */
	public static void getTop10Session(List<Tuple2<CategorySortKey, String>> top10CategaryList,
			JavaPairRDD<String, Row> sessionid2detailRDD, int taskId, JavaSparkContext sc) {

		/**
		 * 第一步，将top10热门品类的id，生成一份RDD
		 */
		List<Tuple2<Long, Long>> top10CategaryIdList = new ArrayList<>();
		for (Tuple2<CategorySortKey, String> tuple : top10CategaryList) {

			long categoryId = Long
					.valueOf(StringUtils.getFieldFromConcatString(tuple._2, "\\|", Constants.FIELD_CATEGORY_ID));
			top10CategaryIdList.add(new Tuple2<Long, Long>(categoryId, categoryId));
		}

		JavaPairRDD<Long, Long> top10CategaryIdRDD = sc.parallelizePairs(top10CategaryIdList);

		/**
		 * 第二步，计算top10品类被各session点击的次数
		 */
		JavaPairRDD<String, Iterable<Row>> sessionid2detailsRDD = sessionid2detailRDD.groupByKey();

		JavaPairRDD<Long, String> categaryid2sessionCountRDD = sessionid2detailsRDD.flatMapToPair(tuple -> {
			String sessionid = tuple._1;
			Iterator<Row> iterator = tuple._2.iterator();

			Map<Long, Long> categaryCountMap = new HashMap<Long, Long>();

			while (iterator.hasNext()) {
				Row row = iterator.next(); 
				if (row.get(6) != null) {
					long categaryid = row.getLong(6);
					Long count = categaryCountMap.get(categaryid);
					if (count == null) {
						count = 0L;
					}
					count++;

					categaryCountMap.put(categaryid, count);
				}
			}
			// 返回结果 <categaryid,sessionid:count>
			List<Tuple2<Long, String>> list = new ArrayList<>();
			for (Entry<Long, Long> categaryCountEntry : categaryCountMap.entrySet()) {
				Long categaryid = categaryCountEntry.getKey();
				Long count = categaryCountEntry.getValue();

				String info = sessionid + "," + count;
				list.add(new Tuple2<Long, String>(categaryid, info));
			}
			return list;
		});
		// top10热门品类被各个session点击的次数
		JavaPairRDD<Long, String> top10CategarySessionCountRDD = top10CategaryIdRDD
				.join(categaryid2sessionCountRDD).mapToPair(tuple -> {
					
				return new Tuple2<Long, String>(tuple._1,tuple._2._2); 
		});

		/**
		 * 第三步，分组取TopN算法获取top10活跃session
		 */
		JavaPairRDD<Long, Iterable<String>> top10CategarySessionCountsRDD = top10CategarySessionCountRDD.groupByKey();
		
		JavaPairRDD<String, String> top10SessionRDD = top10CategarySessionCountsRDD.flatMapToPair(tuple -> {
			long categaryid = tuple._1;
			Iterator<String> iterator = tuple._2.iterator();
			
			String[] top10Sessions = new String[10];
			while(iterator.hasNext()){
				String sessionCount = iterator.next();
				long count = Long.valueOf(sessionCount.split(",")[1]);
				
				for (int i = 0; i < top10Sessions.length; i++) {
					if(top10Sessions[i] == null){
						top10Sessions[i] = sessionCount;
						break;
					}else{
						long _count = Long.valueOf(top10Sessions[i].split(",")[1]);
						if(count > _count){
							for (int j = 9; j > i; j--) {
								top10Sessions[j] = top10Sessions[j - 1];
							}
							top10Sessions[i] = sessionCount;
							break;
						}
					}
				}
			}
			
			Top10CategorySessionMapper top10CategorySessionMapper = ApplicationContextUtils.getBean(Top10CategorySessionMapper.class);
			List<Tuple2<String, String>> list = new ArrayList<>();
			
			for (String sessionCount : top10Sessions) {
				if(sessionCount != null){
					
					String sessionid = sessionCount.split(",")[0];
					long count = Long.valueOf(sessionCount.split(",")[1]);
					
					Top10CategorySession top10CategorySession = new Top10CategorySession();
					top10CategorySession.setCategoryId((int)categaryid);
					top10CategorySession.setClickCount((int)count);
					top10CategorySession.setSessionId(sessionid);
					top10CategorySession.setTaskId(taskId);
					top10CategorySessionMapper.insert(top10CategorySession);
					
					list.add(new Tuple2<String, String>(sessionid, sessionid));
				}
			}
			
 			
			return list;
		});
		
		/**
		 * 第四步，获取top10活跃session的明细数据，并写入mysql
		 */
		JavaPairRDD<String, Tuple2<String, Row>> sessionDetailRDD =
				top10SessionRDD.join(sessionid2detailRDD);
		
		
		sessionDetailRDD.foreach(tuple -> {
			Row row = tuple._2._2;
			
			SessionDetail sessionDetail = new SessionDetail();
			sessionDetail.setTaskId(taskId);  
			sessionDetail.setUserId((int)row.getLong(1));  
			sessionDetail.setSessionId(row.getString(2));  
			sessionDetail.setPageId((int)row.getLong(3));  
			sessionDetail.setActionTime(row.getString(4));
			sessionDetail.setSearchKeyword(row.getString(5));  
			sessionDetail.setClickCategoryId((int)row.getLong(6));  
			sessionDetail.setClickProductId((int)row.getLong(7));   
			sessionDetail.setOrderCategoryIds(row.getString(8));  
			sessionDetail.setOrderProductIds(row.getString(9));  
			sessionDetail.setPayCategoryIds(row.getString(10)); 
			sessionDetail.setPayProductIds(row.getString(11));  
			
			SessionDetailService sessionDetailService = ApplicationContextUtils.getBean(SessionDetailService.class);
			sessionDetailService.insert(sessionDetail);  
		});
		
	}
}
