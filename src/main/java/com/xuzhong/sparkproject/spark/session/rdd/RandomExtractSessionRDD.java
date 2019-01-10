package com.xuzhong.sparkproject.spark.session.rdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Row;

import com.xuzhong.sparkproject.domain.SessionAggrStat;
import com.xuzhong.sparkproject.domain.SessionDetail;
import com.xuzhong.sparkproject.domain.SessionRandomExtract;
import com.xuzhong.sparkproject.service.SessionAggrStatService;
import com.xuzhong.sparkproject.service.SessionDetailService;
import com.xuzhong.sparkproject.service.SessionRandomExtractService;
import com.xuzhong.sparkproject.util.ApplicationContextUtils;
import com.xuzhong.sparkproject.util.Constants;
import com.xuzhong.sparkproject.util.DateUtils;
import com.xuzhong.sparkproject.util.NumberUtils;
import com.xuzhong.sparkproject.util.StringUtils;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import scala.Tuple2;

public class RandomExtractSessionRDD {

	
	/**
	 * 随机抽取session
	 * @param sc 
	 * @param sessionid2AggrInfoRDD  
	 */
	public static void randomExtractSession(JavaSparkContext sc, final int taskId,
			JavaPairRDD<String, String> filteredSessionid2AggrInfoRDD,
			JavaPairRDD<String, Row> sessionid2actionRDD) {
		JavaPairRDD<String, String> time2sessionidRDD = filteredSessionid2AggrInfoRDD.mapToPair(tuple2 -> {
			String aggrInfo = tuple2._2;
			String startTime = StringUtils.getFieldFromConcatString(
					aggrInfo, "\\|", Constants.FIELD_START_TIME);
			String dateHour = DateUtils.getDateHour(startTime);
			
			return new Tuple2<String,String>(dateHour,aggrInfo);
		});
		
		/**
		 * 思考一下：这里我们不要着急写大量的代码，做项目的时候，一定要用脑子多思考
		 * 
		 * 每天每小时的session数量，然后计算出每天每小时的session抽取索引，遍历每天每小时session
		 * 首先抽取出的session的聚合数据，写入session_random_extract表
		 * 所以第一个RDD的value，应该是session聚合数据
		 * 
		 */
		
		// 得到每天每小时的session数量
		Map<String, Object> countMap = time2sessionidRDD.countByKey();
		
		// 第二步，使用按时间比例随机抽取算法，计算出每天每小时要抽取session的索引
		
		// 将<yyyy-MM-dd_HH,count>格式的map，转换成<yyyy-MM-dd,<HH,count>>的格式
		Map<String, Map<String, Long>> dateHourCountMap = 
				new HashMap<String, Map<String, Long>>();
		
		for(Map.Entry<String, Object> countEntry : countMap.entrySet()) {
			String dateHour = countEntry.getKey();
			String date = dateHour.split("_")[0];
			String hour = dateHour.split("_")[1];  
			
			long count = Long.valueOf(String.valueOf(countEntry.getValue()));  
			
			Map<String, Long> hourCountMap = dateHourCountMap.get(date);
			if(hourCountMap == null) {
				hourCountMap = new HashMap<String, Long>();
				dateHourCountMap.put(date, hourCountMap);
			}
			
			hourCountMap.put(hour, count);
		}
		
		// 开始实现我们的按时间比例随机抽取算法
		
		// 总共要抽取100个session，先按照天数，进行平分
		int extractNumberPerDay = 100 / dateHourCountMap.size();
		
		// <date,<hour,(3,5,20,102)>>  
		Map<String, Map<String, List<Integer>>> dateHourExtractMap = 
				new HashMap<String, Map<String, List<Integer>>>();
		
		Random random = new Random();
		
		for(Map.Entry<String, Map<String, Long>> dateHourCountEntry : dateHourCountMap.entrySet()) {
			String date = dateHourCountEntry.getKey();
			Map<String, Long> hourCountMap = dateHourCountEntry.getValue();
			
			// 计算出这一天的session总数
			long sessionCount = 0L;
			for(long hourCount : hourCountMap.values()) {
				sessionCount += hourCount;
			}
			
			Map<String, List<Integer>> hourExtractMap = dateHourExtractMap.get(date);
			if(hourExtractMap == null) {
				hourExtractMap = new HashMap<String, List<Integer>>();
				dateHourExtractMap.put(date, hourExtractMap);
			}
			
			// 遍历每个小时
			for(Map.Entry<String, Long> hourCountEntry : hourCountMap.entrySet()) {
				String hour = hourCountEntry.getKey();
				long count = hourCountEntry.getValue();
				
				// 计算每个小时的session数量，占据当天总session数量的比例，直接乘以每天要抽取的数量
				// 就可以计算出，当前小时需要抽取的session数量
				int hourExtractNumber = (int)(((double)count / (double)sessionCount) 
						* extractNumberPerDay);
				if(hourExtractNumber > count) {
					hourExtractNumber = (int) count;
				}
				
				// 先获取当前小时的存放随机数的list
				List<Integer> extractIndexList = hourExtractMap.get(hour);
				if(extractIndexList == null) {
					extractIndexList = new ArrayList<Integer>();
					hourExtractMap.put(hour, extractIndexList);
				}
				
				// 生成上面计算出来的数量的随机数
				for(int i = 0; i < hourExtractNumber; i++) {
					int extractIndex = random.nextInt((int) count);
					while(extractIndexList.contains(extractIndex)) {
						extractIndex = random.nextInt((int) count);
					}
					extractIndexList.add(extractIndex);
				}
			}
		}
		/**
		 * fastutil的使用，很简单，比如List<Integer>的list，对应到fastutil，就是IntList
		 * 重构上面的dateHourExtractMap
		 */
		// <date,<hour,(3,5,20,102)>>  
		Map<String, Map<String, IntList>> fastutilDateHourExtractMap = 
				new HashMap<String, Map<String, IntList>>();
		
		
		for(Entry<String, Map<String, List<Integer>>> dateHourExtractEntry : dateHourExtractMap.entrySet()) {
			String date = dateHourExtractEntry.getKey();
			Map<String, List<Integer>> hourExtractMap = dateHourExtractEntry.getValue();
			
			Map<String, IntList> fastutilHourExtractMap = new HashMap<String, IntList>();
			
			for(Map.Entry<String, List<Integer>> hourExtractEntry : hourExtractMap.entrySet()) {
				String hour = hourExtractEntry.getKey();
				List<Integer> extractList = hourExtractEntry.getValue();
				
				IntList fastutilExtractList = new IntArrayList();
				
				for(int i = 0; i < extractList.size(); i++) {
					fastutilExtractList.add(extractList.get(i));  
				}
				
				fastutilHourExtractMap.put(hour, fastutilExtractList);
			}
			
			fastutilDateHourExtractMap.put(date, fastutilHourExtractMap);
		}
		
		
		/**
		 * 广播变量，很简单
		 * 其实就是SparkContext的broadcast()方法，传入你要广播的变量，即可
		 */		
		
		final Broadcast<Map<String, Map<String, IntList>>> dateHourExtractMapBroadcast = 
				sc.broadcast(fastutilDateHourExtractMap);

		/**
		 * 第三步：遍历每天每小时的session，然后根据随机索引进行抽取
		 */
		
		// 执行groupByKey算子，得到<dateHour,(session aggrInfo)>  
		JavaPairRDD<String, Iterable<String>> time2sessionsRDD = time2sessionidRDD.groupByKey();
		
		// 我们用flatMap算子，遍历所有的<dateHour,(session aggrInfo)>格式的数据
		// 然后呢，会遍历每天每小时的session
		// 如果发现某个session恰巧在我们指定的这天这小时的随机抽取索引上
		// 那么抽取该session，直接写入MySQL的random_extract_session表
		// 将抽取出来的session id返回回来，形成一个新的JavaRDD<String>
		// 然后最后一步，是用抽取出来的sessionid，去join它们的访问行为明细数据，写入session表
		JavaPairRDD<String, String> extractSessionidsRDD = time2sessionsRDD.flatMapToPair(
				
			new PairFlatMapFunction<Tuple2<String,Iterable<String>>, String, String>() {

				private static final long serialVersionUID = 1L;

				@Override
				public Iterable<Tuple2<String, String>> call(
						Tuple2<String, Iterable<String>> tuple)
						throws Exception {
					List<Tuple2<String, String>> extractSessionids = 
							new ArrayList<Tuple2<String, String>>();
					
					String dateHour = tuple._1;
					String date = dateHour.split("_")[0];
					String hour = dateHour.split("_")[1];
					Iterator<String> iterator = tuple._2.iterator();
					
					/**
					 * 使用广播变量的时候
					 * 直接调用广播变量（Broadcast类型）的value() / getValue() 
					 * 可以获取到之前封装的广播变量
					 */
					Map<String, Map<String, IntList>> dateHourExtractMap = dateHourExtractMapBroadcast.value();
					
					List<Integer> extractIndexList = dateHourExtractMap.get(date).get(hour);  
					
					int index = 0;
					SessionRandomExtractService sessionRandomExtractService = ApplicationContextUtils.getBean(SessionRandomExtractService.class);
					while(iterator.hasNext()) {
						String sessionAggrInfo = iterator.next();
						
						if(extractIndexList.contains(index)) {
							String sessionid = StringUtils.getFieldFromConcatString(
									sessionAggrInfo, "\\|", Constants.FIELD_SESSION_ID);
							
							// 将数据写入MySQL
							SessionRandomExtract sessionRandomExtract = new SessionRandomExtract();
							sessionRandomExtract.setTaskId(taskId);
							sessionRandomExtract.setSessionId(sessionid);  
							sessionRandomExtract.setStartTime(StringUtils.getFieldFromConcatString(
									sessionAggrInfo, "\\|", Constants.FIELD_START_TIME));  
							sessionRandomExtract.setSearchKeywords(StringUtils.getFieldFromConcatString(
									sessionAggrInfo, "\\|", Constants.FIELD_SEARCH_KEYWORDS));
							sessionRandomExtract.setClickCategoryIds(StringUtils.getFieldFromConcatString(
									sessionAggrInfo, "\\|", Constants.FIELD_CLICK_CATEGORY_IDS));
							
							sessionRandomExtractService.insert(sessionRandomExtract);  
							
							// 将sessionid加入list
							extractSessionids.add(new Tuple2<String, String>(sessionid, sessionid));  
						}
						
						index++;
					}
					
					return extractSessionids;
				}
				
			});
		
		/**
		 * 第四步：获取抽取出来的session的明细数据
		 */
		JavaPairRDD<String, Tuple2<String, Row>> extractSessionDetailRDD =
				extractSessionidsRDD.join(sessionid2actionRDD);
		
		
			extractSessionDetailRDD.foreach(new VoidFunction<Tuple2<String,Tuple2<String,Row>>>() {  
			
			private static final long serialVersionUID = 1L;

			@Override
			public void call(Tuple2<String, Tuple2<String, Row>> tuple) throws Exception {
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
			}
		});
		
	}
	
	

	/**
	 * 计算各session范围占比，并写入MySQL
	 * @param value
	 */
	public static void calculateAndPersistAggrStat(String value, int taskId) {
		// 从Accumulator统计串中获取值
		int session_count = Integer.valueOf(StringUtils.getFieldFromConcatString(
				value, "\\|", Constants.SESSION_COUNT));  
		
		long visit_length_1s_3s = Long.valueOf(StringUtils.getFieldFromConcatString(
				value, "\\|", Constants.TIME_PERIOD_1s_3s));  
		long visit_length_4s_6s = Long.valueOf(StringUtils.getFieldFromConcatString(
				value, "\\|", Constants.TIME_PERIOD_4s_6s));
		long visit_length_7s_9s = Long.valueOf(StringUtils.getFieldFromConcatString(
				value, "\\|", Constants.TIME_PERIOD_7s_9s));
		long visit_length_10s_30s = Long.valueOf(StringUtils.getFieldFromConcatString(
				value, "\\|", Constants.TIME_PERIOD_10s_30s));
		long visit_length_30s_60s = Long.valueOf(StringUtils.getFieldFromConcatString(
				value, "\\|", Constants.TIME_PERIOD_30s_60s));
		long visit_length_1m_3m = Long.valueOf(StringUtils.getFieldFromConcatString(
				value, "\\|", Constants.TIME_PERIOD_1m_3m));
		long visit_length_3m_10m = Long.valueOf(StringUtils.getFieldFromConcatString(
				value, "\\|", Constants.TIME_PERIOD_3m_10m));
		long visit_length_10m_30m = Long.valueOf(StringUtils.getFieldFromConcatString(
				value, "\\|", Constants.TIME_PERIOD_10m_30m));
		long visit_length_30m = Long.valueOf(StringUtils.getFieldFromConcatString(
				value, "\\|", Constants.TIME_PERIOD_30m));
		
		long step_length_1_3 = Long.valueOf(StringUtils.getFieldFromConcatString(
				value, "\\|", Constants.STEP_PERIOD_1_3));
		long step_length_4_6 = Long.valueOf(StringUtils.getFieldFromConcatString(
				value, "\\|", Constants.STEP_PERIOD_4_6));
		long step_length_7_9 = Long.valueOf(StringUtils.getFieldFromConcatString(
				value, "\\|", Constants.STEP_PERIOD_7_9));
		long step_length_10_30 = Long.valueOf(StringUtils.getFieldFromConcatString(
				value, "\\|", Constants.STEP_PERIOD_10_30));
		long step_length_30_60 = Long.valueOf(StringUtils.getFieldFromConcatString(
				value, "\\|", Constants.STEP_PERIOD_30_60));
		long step_length_60 = Long.valueOf(StringUtils.getFieldFromConcatString(
				value, "\\|", Constants.STEP_PERIOD_60));
		
		// 计算各个访问时长和访问步长的范围
		double visit_length_1s_3s_ratio = NumberUtils.formatDouble(
				(double)visit_length_1s_3s / (double)session_count, 2);  
		double visit_length_4s_6s_ratio = NumberUtils.formatDouble(
				(double)visit_length_4s_6s / (double)session_count, 2);  
		double visit_length_7s_9s_ratio = NumberUtils.formatDouble(
				(double)visit_length_7s_9s / (double)session_count, 2);  
		double visit_length_10s_30s_ratio = NumberUtils.formatDouble(
				(double)visit_length_10s_30s / (double)session_count, 2);  
		double visit_length_30s_60s_ratio = NumberUtils.formatDouble(
				(double)visit_length_30s_60s / (double)session_count, 2);  
		double visit_length_1m_3m_ratio = NumberUtils.formatDouble(
				(double)visit_length_1m_3m / (double)session_count, 2);
		double visit_length_3m_10m_ratio = NumberUtils.formatDouble(
				(double)visit_length_3m_10m / (double)session_count, 2);  
		double visit_length_10m_30m_ratio = NumberUtils.formatDouble(
				(double)visit_length_10m_30m / (double)session_count, 2);
		double visit_length_30m_ratio = NumberUtils.formatDouble(
				(double)visit_length_30m / (double)session_count, 2);  
		
		double step_length_1_3_ratio = NumberUtils.formatDouble(
				(double)step_length_1_3 / (double)session_count, 2);  
		double step_length_4_6_ratio = NumberUtils.formatDouble(
				(double)step_length_4_6 / (double)session_count, 2);  
		double step_length_7_9_ratio = NumberUtils.formatDouble(
				(double)step_length_7_9 / (double)session_count, 2);  
		double step_length_10_30_ratio = NumberUtils.formatDouble(
				(double)step_length_10_30 / (double)session_count, 2);  
		double step_length_30_60_ratio = NumberUtils.formatDouble(
				(double)step_length_30_60 / (double)session_count, 2);  
		double step_length_60_ratio = NumberUtils.formatDouble(
				(double)step_length_60 / (double)session_count, 2);  
		
		// 将统计结果封装为Domain对象
		SessionAggrStat sessionAggrStat = new SessionAggrStat();
		sessionAggrStat.setTaskId(taskId);
		sessionAggrStat.setSessionCount(session_count);
		sessionAggrStat.setC1s3s(visit_length_1s_3s_ratio);
		sessionAggrStat.setC4s6s(visit_length_4s_6s_ratio);
		sessionAggrStat.setC7s9s(visit_length_7s_9s_ratio);
		sessionAggrStat.setC1030(visit_length_10s_30s_ratio);
		sessionAggrStat.setC3060(visit_length_30s_60s_ratio);
		sessionAggrStat.setC1m3m(visit_length_1m_3m_ratio);
		sessionAggrStat.setC3m10m(visit_length_3m_10m_ratio);
		sessionAggrStat.setC10m30m(visit_length_10m_30m_ratio);
		sessionAggrStat.setC30m(visit_length_30m_ratio);
		sessionAggrStat.setC13(step_length_1_3_ratio);
		sessionAggrStat.setC46(step_length_4_6_ratio);
		sessionAggrStat.setC79(step_length_7_9_ratio);
		sessionAggrStat.setC1030(step_length_10_30_ratio);
		sessionAggrStat.setC3060(step_length_30_60_ratio);
		sessionAggrStat.setC60(step_length_60_ratio);
		
		//调用对应的DAO插入统计结果
		SessionAggrStatService sessionAggrStatService = ApplicationContextUtils.getBean(SessionAggrStatService.class);
		sessionAggrStatService.insert(sessionAggrStat);
	}
}
