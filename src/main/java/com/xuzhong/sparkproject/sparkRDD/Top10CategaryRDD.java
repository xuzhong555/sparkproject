package com.xuzhong.sparkproject.sparkRDD;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.sql.Row;

import com.google.common.base.Optional;
import com.xuzhong.sparkproject.domain.Top10Category;
import com.xuzhong.sparkproject.service.Top10CategoryService;
import com.xuzhong.sparkproject.spark.session.CategorySortKey;
import com.xuzhong.sparkproject.util.ApplicationContextUtils;
import com.xuzhong.sparkproject.util.Constants;
import com.xuzhong.sparkproject.util.StringUtils;

import scala.Tuple2;

public class Top10CategaryRDD {

	
	/**
	 * 获取top10热门品类
	 * @param filteredSessionid2AggrInfoRDD
	 * @param sessionid2actionRDD
	 * @return 
	 */
	public static List<Tuple2<CategorySortKey, String>> getTop10Categary(JavaPairRDD<String, Row> sessionid2detailRDD,int taskId) {
		
		/**
		 * 第一步，获取符合条件的session访问过的所有品类
		 */
		
		//session访问过的所有品类id
		JavaPairRDD<Long, Long> categoryIdRDD = sessionid2detailRDD.flatMapToPair(tuple -> {
				Row row = tuple._2;
				List<Tuple2<Long, Long>> list = new ArrayList<>();
			
				Long clickCategoryId = row.getLong(6);
				if(clickCategoryId != null){
					list.add(new Tuple2<Long, Long>(clickCategoryId, clickCategoryId));
				}
				String orderCategoryIds = row.getString(8);
				if(orderCategoryIds != null){
					String[] orderCategoryIdsSplited = orderCategoryIds.split(",");
					for (String orderCategoryId : orderCategoryIdsSplited) {
						list.add(new Tuple2<Long, Long>(Long.valueOf(orderCategoryId), Long.valueOf(orderCategoryId)));
					}
				}
				String payCategoryIds = row.getString(10);
				if(payCategoryIds != null){
					String[] payCategoryIdsSplited = payCategoryIds.split(",");
					for (String payCategoryId : payCategoryIdsSplited) {
						list.add(new Tuple2<Long, Long>(Long.valueOf(payCategoryId), Long.valueOf(payCategoryId)));
					}
				}
				
				return list;
		});
		//必须要进行去重
		categoryIdRDD = categoryIdRDD.distinct();
		/**
		 * 第二步，计算各品类的点击，支付，下单次数
		 */
		
		//访问明细中，其中三种访问行为是 点击，下单，支付， 分别计算其次数
		
		//计算各个品类的点击次数
		JavaPairRDD<Long, Long> clickCategaryId2CountRDD = getClickCategaryId2CountRDD(sessionid2detailRDD);
		//计算各个品类的下单次数
		JavaPairRDD<Long, Long> orderCategaryId2CountRDD = getCategaryId2CountRDD(sessionid2detailRDD,8);
		//计算各个品类的支付次数
		JavaPairRDD<Long, Long> payCategaryId2CountRDD = getCategaryId2CountRDD(sessionid2detailRDD,10);
		
		/**
		 * 第三步，join各品类与他们的点击，下单，支付的次数
		 */
		JavaPairRDD<Long, String> categaryId2CountRDD = joinCategoryAndData(categoryIdRDD,clickCategaryId2CountRDD,orderCategaryId2CountRDD,payCategaryId2CountRDD);
		
		/**
		 * 第四步，自定义二次排序key
		 */
		
		/**
		 * 第五步，将数据映射成<CategorySortKey,info>格式的RDD，然后进行二次排序（降序）
		 */
		JavaPairRDD<CategorySortKey, String> sortKey2CountRDD = categaryId2CountRDD.mapToPair(tuple ->{
			String info = tuple._2;
			
			long clickCount = Long.valueOf(StringUtils.getFieldFromConcatString(
					info, "\\|", Constants.FIELD_CLICK_COUNT));  
			long orderCount = Long.valueOf(StringUtils.getFieldFromConcatString(
					info, "\\|", Constants.FIELD_ORDER_COUNT));  
			long payCount = Long.valueOf(StringUtils.getFieldFromConcatString(
					info, "\\|", Constants.FIELD_PAY_COUNT));
			
			CategorySortKey categorySortKey = new CategorySortKey(clickCount, orderCount, payCount);
			
			return new Tuple2<CategorySortKey, String>(categorySortKey, info);
		});
		JavaPairRDD<CategorySortKey,String> sortCategaryCountRDD = sortKey2CountRDD.sortByKey(false);
		
		/**
		 * 第六步，取前10条数据,写入mysql
		 */
		Top10CategoryService top10CategoryService = ApplicationContextUtils.getBean(Top10CategoryService.class);
		
		List<Tuple2<CategorySortKey, String>> top10CategaryList = sortCategaryCountRDD.take(10);
		for (Tuple2<CategorySortKey, String> tuple : top10CategaryList) {
			String countInfo = tuple._2;
			long categoryId = Long.valueOf(StringUtils.getFieldFromConcatString(
					countInfo, "\\|", Constants.FIELD_CATEGORY_ID));  
			long clickCount = Long.valueOf(StringUtils.getFieldFromConcatString(
					countInfo, "\\|", Constants.FIELD_CLICK_COUNT));  
			long orderCount = Long.valueOf(StringUtils.getFieldFromConcatString(
					countInfo, "\\|", Constants.FIELD_ORDER_COUNT));  
			long payCount = Long.valueOf(StringUtils.getFieldFromConcatString(
					countInfo, "\\|", Constants.FIELD_PAY_COUNT));
			
			Top10Category top10Category = new Top10Category();
			top10Category.setClickCount((int)clickCount);
			top10Category.setOrderCount((int)orderCount);
			top10Category.setPayCount((int)payCount);
			top10Category.setTaskId(taskId);
			top10Category.setCategoryId((int)categoryId);
			
			top10CategoryService.insert(top10Category);
		}
		return top10CategaryList;
	}

	/**
	 * 连接品类RDD和数据RDD
	 * @return
	 */
	public static JavaPairRDD<Long, String> joinCategoryAndData(JavaPairRDD<Long, Long> categoryIdRDD,
			JavaPairRDD<Long, Long> clickCategaryId2CountRDD, JavaPairRDD<Long, Long> orderCategaryId2CountRDD,
			JavaPairRDD<Long, Long> payCategaryId2CountRDD) {
		
		JavaPairRDD<Long, Tuple2<Long, Optional<Long>>> tmpJoinRDD = categoryIdRDD.leftOuterJoin(clickCategaryId2CountRDD);
		
		JavaPairRDD<Long, String> tmpMapRDD = tmpJoinRDD.mapToPair(tuple ->{
			Long categoryId = tuple._1;
			Optional<Long> optional = tuple._2._2;
			long clickCount = 0L;
			if(optional.isPresent()){
				clickCount = optional.get();
			}
			
			String value = Constants.FIELD_CATEGORY_ID + "=" + categoryId + "|" +
					Constants.FIELD_CLICK_COUNT + "=" + clickCount;
			
			return new Tuple2<Long,String>(categoryId,value);
		});
		
		tmpMapRDD = tmpMapRDD.leftOuterJoin(orderCategaryId2CountRDD).mapToPair(tuple ->{
			Long categoryId = tuple._1;
			String value = tuple._2._1;
			Optional<Long> optional = tuple._2._2;
			long orderCount = 0L;
			if(optional.isPresent()){
				orderCount = optional.get();
			}
			
			value = value + "|" + Constants.FIELD_ORDER_COUNT + "=" + orderCount;
			
			return new Tuple2<Long,String>(categoryId,value);
		});
		
		tmpMapRDD = tmpMapRDD.leftOuterJoin(payCategaryId2CountRDD).mapToPair(tuple ->{
			Long categoryId = tuple._1;
			String value = tuple._2._1;
			Optional<Long> optional = tuple._2._2;	
			long payCount = 0L;
			if(optional.isPresent()){
				payCount = optional.get();
			}
			
			value = value + "|" + Constants.FIELD_PAY_COUNT + "=" + payCount;
			
			return new Tuple2<Long,String>(categoryId,value);
		});
		
		return tmpMapRDD;
	}


	/**
	 * 获取下单或支付次数RDD
	 * @param sessionid2detailRDD
	 * @return 
	 * @return
	 */
	public static JavaPairRDD<Long, Long> getCategaryId2CountRDD(JavaPairRDD<String, Row> sessionid2detailRDD,int index) {
		JavaPairRDD<String, Row> actionRDD = sessionid2detailRDD.filter(tuple -> {
			Row row = tuple._2;
			return row.getString(index) != null ? true : false;
		});
		JavaPairRDD<Long, Long> categaryIdRDD = actionRDD.flatMapToPair(tuple ->{
			Row row = tuple._2;
			String[] categaryIds = row.getString(index).split(",");
			List<Tuple2<Long, Long>> list = new ArrayList<>();
			for (String categaryId : categaryIds) {
				list.add(new Tuple2<Long, Long>(Long.valueOf(categaryId),1L));
			}
			return list;
		});
		JavaPairRDD<Long, Long> categaryId2CountRDD = categaryIdRDD.reduceByKey((v1 , v2) ->{
				return v1 + v2;
		});
		return categaryId2CountRDD;
	}


	/**
	 * 获取点击次数RDD
	 * @param sessionid2detailRDD
	 * @return
	 */
	public static JavaPairRDD<Long, Long> getClickCategaryId2CountRDD(JavaPairRDD<String, Row> sessionid2detailRDD) {
		JavaPairRDD<String, Row> clickActionRDD = sessionid2detailRDD.filter(tuple -> {
			Row row = tuple._2;
			return Long.valueOf(row.getLong(6)) != null ? true : false;
		});
		JavaPairRDD<Long, Long> clickCategaryIdRDD = clickActionRDD.mapToPair(tuple ->{
			Row row = tuple._2;
			Long clickCategaryId = row.getLong(6);
			return new Tuple2<Long, Long>(clickCategaryId,1L);
		});
		JavaPairRDD<Long, Long> clickCategaryId2CountRDD = clickCategaryIdRDD.reduceByKey((v1 , v2) ->{
			return v1 + v2;
		});
		return clickCategaryId2CountRDD;
	}
}
