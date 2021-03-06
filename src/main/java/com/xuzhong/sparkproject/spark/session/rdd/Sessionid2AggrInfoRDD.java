package com.xuzhong.sparkproject.spark.session.rdd;

import java.util.Date;
import java.util.Iterator;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

import com.xuzhong.sparkproject.util.Constants;
import com.xuzhong.sparkproject.util.DateUtils;
import com.xuzhong.sparkproject.util.StringUtils;

import scala.Tuple2;

public class Sessionid2AggrInfoRDD {

	
	/**
	 * 对行为数据按session粒度进行聚合
	 * @param sessionid2actionRDD2 行为数据RDD
	 * @return session粒度聚合数据
	 */
	public static  JavaPairRDD<String, String> aggregateBySession(
			SQLContext sqlContext, JavaPairRDD<String, Row> sessionid2actionRDD2) {
		
		// 对行为数据按session粒度进行分组
		JavaPairRDD<String, Iterable<Row>> sessionid2ActionsRDD = 
				sessionid2actionRDD2.groupByKey();
		
		// 对每一个session分组进行聚合，将session中所有的搜索词和点击品类都聚合起来
		// 到此为止，获取的数据格式，如下：<userid,partAggrInfo(sessionid,searchKeywords,clickCategoryIds)>
		JavaPairRDD<Long, String> userid2PartAggrInfoRDD = sessionid2ActionsRDD.mapToPair(tuple -> {
				String sessionid = tuple._1;
				Iterator<Row> iterator = tuple._2.iterator();
				
				StringBuffer searchKeywordsBuffer = new StringBuffer("");
				StringBuffer clickCategoryIdsBuffer = new StringBuffer("");
				
				Long userid = null;
				
				// session的起始和结束时间
				Date startTime = null;
				Date endTime = null;
				// session的访问步长
				int stepLength = 0;
				
				// 遍历session所有的访问行为
				while(iterator.hasNext()) {
					// 提取每个访问行为的搜索词字段和点击品类字段
					Row row = iterator.next();
					if(userid == null) {
						userid = row.getLong(1);
					}
					String searchKeyword = row.getString(5);
					Long clickCategoryId = row.getLong(6);
					
					// 实际上这里要对数据说明一下
					// 并不是每一行访问行为都有searchKeyword何clickCategoryId两个字段的
					// 其实，只有搜索行为，是有searchKeyword字段的
					// 只有点击品类的行为，是有clickCategoryId字段的
					// 所以，任何一行行为数据，都不可能两个字段都有，所以数据是可能出现null值的
					
					// 我们决定是否将搜索词或点击品类id拼接到字符串中去
					// 首先要满足：不能是null值
					// 其次，之前的字符串中还没有搜索词或者点击品类id
					
					if(StringUtils.isNotEmpty(searchKeyword)) {
						if(!searchKeywordsBuffer.toString().contains(searchKeyword)) {
							searchKeywordsBuffer.append(searchKeyword + ",");  
						}
					}
					if(clickCategoryId != null) {
						if(!clickCategoryIdsBuffer.toString().contains(
								String.valueOf(clickCategoryId))) {   
							clickCategoryIdsBuffer.append(clickCategoryId + ",");  
						}
					}
					
					// 计算session开始和结束时间
					Date actionTime = DateUtils.parseTime(row.getString(4));
					
					if(startTime == null) {
						startTime = actionTime;
					}
					if(endTime == null) {
						endTime = actionTime;
					}
					
					if(actionTime.before(startTime)) {
						startTime = actionTime;
					}
					if(actionTime.after(endTime)) {
						endTime = actionTime;
					}
					
					// 计算session访问步长
					stepLength++;
				}
				
				String searchKeywords = StringUtils.trimComma(searchKeywordsBuffer.toString());
				String clickCategoryIds = StringUtils.trimComma(clickCategoryIdsBuffer.toString());
				
				// 计算session访问时长（秒）
				long visitLength = (endTime.getTime() - startTime.getTime()) / 1000; 
				
				// 大家思考一下
				// 我们返回的数据格式，即使<sessionid,partAggrInfo>
				// 但是，这一步聚合完了以后，其实，我们是还需要将每一行数据，跟对应的用户信息进行聚合
				// 问题就来了，如果是跟用户信息进行聚合的话，那么key，就不应该是sessionid
				// 就应该是userid，才能够跟<userid,Row>格式的用户信息进行聚合
				// 如果我们这里直接返回<sessionid,partAggrInfo>，还得再做一次mapToPair算子
				// 将RDD映射成<userid,partAggrInfo>的格式，那么就多此一举
				
				// 所以，我们这里其实可以直接，返回的数据格式，就是<userid,partAggrInfo>
				// 然后跟用户信息join的时候，将partAggrInfo关联上userInfo
				// 然后再直接将返回的Tuple的key设置成sessionid
				// 最后的数据格式，还是<sessionid,fullAggrInfo>
				
				// 聚合数据，用什么样的格式进行拼接？
				// 我们这里统一定义，使用key=value|key=value
				String partAggrInfo = Constants.FIELD_SESSION_ID + "=" + sessionid + "|"
						+ Constants.FIELD_SEARCH_KEYWORDS + "=" + searchKeywords + "|"
						+ Constants.FIELD_CLICK_CATEGORY_IDS + "=" + clickCategoryIds + "|"
						+ Constants.FIELD_VISIT_LENGTH + "=" + visitLength + "|"
						+ Constants.FIELD_STEP_LENGTH + "=" + stepLength + "|"
						+ Constants.FIELD_START_TIME + "=" + DateUtils.formatTime(startTime);  
				
				return new Tuple2<Long, String>(userid, partAggrInfo);
			
		});
		
		// 查询所有用户数据，并映射成<userid,Row>的格式
		String sql = "select * from user_info";  
		JavaRDD<Row> userInfoRDD = sqlContext.sql(sql).javaRDD();
		
		JavaPairRDD<Long, Row> userid2InfoRDD = userInfoRDD.mapToPair(row ->{
			return new Tuple2<Long, Row>(row.getLong(0), row);
		});
		
		/**
		 * 这里就可以说一下，比较适合采用reduce join转换为map join的方式
		 * 
		 * userid2PartAggrInfoRDD，可能数据量还是比较大，比如，可能在1千万数据
		 * userid2InfoRDD，可能数据量还是比较小的，你的用户数量才10万用户
		 * 
		 */
		// 将session粒度聚合数据，与用户信息进行join
		JavaPairRDD<Long, Tuple2<String, Row>> userid2FullInfoRDD = 
				userid2PartAggrInfoRDD.join(userid2InfoRDD);
		
		// 对join起来的数据进行拼接，并且返回<sessionid,fullAggrInfo>格式的数据
		JavaPairRDD<String, String> sessionid2FullAggrInfoRDD = userid2FullInfoRDD.mapToPair(tuple ->{
				String partAggrInfo = tuple._2._1;
				Row userInfoRow = tuple._2._2;
				
				String sessionid = StringUtils.getFieldFromConcatString(
						partAggrInfo, "\\|", Constants.FIELD_SESSION_ID);
				
				int age = userInfoRow.getInt(3);
				String professional = userInfoRow.getString(4);
				String city = userInfoRow.getString(5);
				String sex = userInfoRow.getString(6);
				
				String fullAggrInfo = partAggrInfo + "|"
						+ Constants.FIELD_AGE + "=" + age + "|"
						+ Constants.FIELD_PROFESSIONAL + "=" + professional + "|"
						+ Constants.FIELD_CITY + "=" + city + "|"
						+ Constants.FIELD_SEX + "=" + sex;
				
				return new Tuple2<String, String>(sessionid, fullAggrInfo);
			
		});
		
		/**
		 * reduce join转换为map join
		 */
		
//		List<Tuple2<Long, Row>> userInfos = userid2InfoRDD.collect();
//		final Broadcast<List<Tuple2<Long, Row>>> userInfosBroadcast = sc.broadcast(userInfos);
//		
//		JavaPairRDD<String, String> sessionid2FullAggrInfoRDD = userid2PartAggrInfoRDD.mapToPair(
//				
//				new PairFunction<Tuple2<Long,String>, String, String>() {
//
//					private static final long serialVersionUID = 1L;
//
//					@Override
//					public Tuple2<String, String> call(Tuple2<Long, String> tuple)
//							throws Exception {
//						// 得到用户信息map
//						List<Tuple2<Long, Row>> userInfos = userInfosBroadcast.value();
//						
//						Map<Long, Row> userInfoMap = new HashMap<Long, Row>();
//						for(Tuple2<Long, Row> userInfo : userInfos) {
//							userInfoMap.put(userInfo._1, userInfo._2);
//						}
//						
//						// 获取到当前用户对应的信息
//						String partAggrInfo = tuple._2;
//						Row userInfoRow = userInfoMap.get(tuple._1);
//						
//						String sessionid = StringUtils.getFieldFromConcatString(
//								partAggrInfo, "\\|", Constants.FIELD_SESSION_ID);
//						
//						int age = userInfoRow.getInt(3);
//						String professional = userInfoRow.getString(4);
//						String city = userInfoRow.getString(5);
//						String sex = userInfoRow.getString(6);
//						
//						String fullAggrInfo = partAggrInfo + "|"
//								+ Constants.FIELD_AGE + "=" + age + "|"
//								+ Constants.FIELD_PROFESSIONAL + "=" + professional + "|"
//								+ Constants.FIELD_CITY + "=" + city + "|"
//								+ Constants.FIELD_SEX + "=" + sex;
//						
//						return new Tuple2<String, String>(sessionid, fullAggrInfo);
//					}
//					
//				});
		
		/**
		 * sample采样倾斜key单独进行join
		 */
		
//		JavaPairRDD<Long, String> sampledRDD = userid2PartAggrInfoRDD.sample(false, 0.1, 9);
//		
//		JavaPairRDD<Long, Long> mappedSampledRDD = sampledRDD.mapToPair(
//				
//				new PairFunction<Tuple2<Long,String>, Long, Long>() {
//
//					private static final long serialVersionUID = 1L;
//
//					@Override
//					public Tuple2<Long, Long> call(Tuple2<Long, String> tuple)
//							throws Exception {
//						return new Tuple2<Long, Long>(tuple._1, 1L);
//					}
//					
//				});
//		
//		JavaPairRDD<Long, Long> computedSampledRDD = mappedSampledRDD.reduceByKey(
//				
//				new Function2<Long, Long, Long>() {
//
//					private static final long serialVersionUID = 1L;
//		
//					@Override
//					public Long call(Long v1, Long v2) throws Exception {
//						return v1 + v2;
//					}
//					
//				});
//		
//		JavaPairRDD<Long, Long> reversedSampledRDD = computedSampledRDD.mapToPair(
//				
//				new PairFunction<Tuple2<Long,Long>, Long, Long>() {
//
//					private static final long serialVersionUID = 1L;
//
//					@Override
//					public Tuple2<Long, Long> call(Tuple2<Long, Long> tuple)
//							throws Exception {
//						return new Tuple2<Long, Long>(tuple._2, tuple._1);
//					}
//					
//				});
//		
//		final Long skewedUserid = reversedSampledRDD.sortByKey(false).take(1).get(0)._2;  
//		
//		JavaPairRDD<Long, String> skewedRDD = userid2PartAggrInfoRDD.filter(
//				
//				new Function<Tuple2<Long,String>, Boolean>() {
//
//					private static final long serialVersionUID = 1L;
//
//					@Override
//					public Boolean call(Tuple2<Long, String> tuple) throws Exception {
//						return tuple._1.equals(skewedUserid);
//					}
//					
//				});
//			
//		JavaPairRDD<Long, String> commonRDD = userid2PartAggrInfoRDD.filter(
//				
//				new Function<Tuple2<Long,String>, Boolean>() {
//
//					private static final long serialVersionUID = 1L;
//
//					@Override
//					public Boolean call(Tuple2<Long, String> tuple) throws Exception {
//						return !tuple._1.equals(skewedUserid);
//					}
//					
//				});
//		
//		JavaPairRDD<String, Row> skewedUserid2infoRDD = userid2InfoRDD.filter(
//				
//				new Function<Tuple2<Long,Row>, Boolean>() {
//
//					private static final long serialVersionUID = 1L;
//		
//					@Override
//					public Boolean call(Tuple2<Long, Row> tuple) throws Exception {
//						return tuple._1.equals(skewedUserid);
//					}
//					
//				}).flatMapToPair(new PairFlatMapFunction<Tuple2<Long,Row>, String, Row>() {
//
//					private static final long serialVersionUID = 1L;
//
//					@Override
//					public Iterable<Tuple2<String, Row>> call(
//							Tuple2<Long, Row> tuple) throws Exception {
//						Random random = new Random();
//						List<Tuple2<String, Row>> list = new ArrayList<Tuple2<String, Row>>();
//						
//						for(int i = 0; i < 100; i++) {
//							int prefix = random.nextInt(100);
//							list.add(new Tuple2<String, Row>(prefix + "_" + tuple._1, tuple._2));
//						}
//						
//						return list;
//					}
//					
//				});
//		
//		JavaPairRDD<Long, Tuple2<String, Row>> joinedRDD1 = skewedRDD.mapToPair(
//				
//				new PairFunction<Tuple2<Long,String>, String, String>() {
//
//					private static final long serialVersionUID = 1L;
//
//					@Override
//					public Tuple2<String, String> call(Tuple2<Long, String> tuple)
//							throws Exception {
//						Random random = new Random();
//						int prefix = random.nextInt(100);
//						return new Tuple2<String, String>(prefix + "_" + tuple._1, tuple._2);
//					}
//					
//				}).join(skewedUserid2infoRDD).mapToPair(
//						
//						new PairFunction<Tuple2<String,Tuple2<String,Row>>, Long, Tuple2<String, Row>>() {
//
//							private static final long serialVersionUID = 1L;
//		
//							@Override
//							public Tuple2<Long, Tuple2<String, Row>> call(
//									Tuple2<String, Tuple2<String, Row>> tuple)
//									throws Exception {
//								long userid = Long.valueOf(tuple._1.split("_")[1]);  
//								return new Tuple2<Long, Tuple2<String, Row>>(userid, tuple._2);  
//							}
//							
//						});
//		
//		JavaPairRDD<Long, Tuple2<String, Row>> joinedRDD2 = commonRDD.join(userid2InfoRDD);
//		
//		JavaPairRDD<Long, Tuple2<String, Row>> joinedRDD = joinedRDD1.union(joinedRDD2);
//		
//		JavaPairRDD<String, String> sessionid2FullAggrInfoRDD = joinedRDD.mapToPair(
//				
//				new PairFunction<Tuple2<Long,Tuple2<String,Row>>, String, String>() {
//
//					private static final long serialVersionUID = 1L;
//
//					@Override
//					public Tuple2<String, String> call(
//							Tuple2<Long, Tuple2<String, Row>> tuple)
//							throws Exception {
//						String partAggrInfo = tuple._2._1;
//						Row userInfoRow = tuple._2._2;
//						
//						String sessionid = StringUtils.getFieldFromConcatString(
//								partAggrInfo, "\\|", Constants.FIELD_SESSION_ID);
//						
//						int age = userInfoRow.getInt(3);
//						String professional = userInfoRow.getString(4);
//						String city = userInfoRow.getString(5);
//						String sex = userInfoRow.getString(6);
//						
//						String fullAggrInfo = partAggrInfo + "|"
//								+ Constants.FIELD_AGE + "=" + age + "|"
//								+ Constants.FIELD_PROFESSIONAL + "=" + professional + "|"
//								+ Constants.FIELD_CITY + "=" + city + "|"
//								+ Constants.FIELD_SEX + "=" + sex;
//						
//						return new Tuple2<String, String>(sessionid, fullAggrInfo);
//					}
//					
//				});
		
		/**
		 * 使用随机数和扩容表进行join
		 */
		
//		JavaPairRDD<String, Row> expandedRDD = userid2InfoRDD.flatMapToPair(
//				
//				new PairFlatMapFunction<Tuple2<Long,Row>, String, Row>() {
//
//					private static final long serialVersionUID = 1L;
//
//					@Override
//					public Iterable<Tuple2<String, Row>> call(Tuple2<Long, Row> tuple)
//							throws Exception {
//						List<Tuple2<String, Row>> list = new ArrayList<Tuple2<String, Row>>();
//						
//						for(int i = 0; i < 10; i++) {
//							list.add(new Tuple2<String, Row>(0 + "_" + tuple._1, tuple._2));
//						}
//						
//						return list;
//					}
//					
//				});
//		
//		JavaPairRDD<String, String> mappedRDD = userid2PartAggrInfoRDD.mapToPair(
//				
//				new PairFunction<Tuple2<Long,String>, String, String>() {
//
//					private static final long serialVersionUID = 1L;
//
//					@Override
//					public Tuple2<String, String> call(Tuple2<Long, String> tuple)
//							throws Exception {
//						Random random = new Random();
//						int prefix = random.nextInt(10);
//						return new Tuple2<String, String>(prefix + "_" + tuple._1, tuple._2);  
//					}
//					
//				});
//		
//		JavaPairRDD<String, Tuple2<String, Row>> joinedRDD = mappedRDD.join(expandedRDD);
//		
//		JavaPairRDD<String, String> finalRDD = joinedRDD.mapToPair(
//				
//				new PairFunction<Tuple2<String,Tuple2<String,Row>>, String, String>() {
//
//					private static final long serialVersionUID = 1L;
//
//					@Override
//					public Tuple2<String, String> call(
//							Tuple2<String, Tuple2<String, Row>> tuple)
//							throws Exception {
//						String partAggrInfo = tuple._2._1;
//						Row userInfoRow = tuple._2._2;
//						
//						String sessionid = StringUtils.getFieldFromConcatString(
//								partAggrInfo, "\\|", Constants.FIELD_SESSION_ID);
//						
//						int age = userInfoRow.getInt(3);
//						String professional = userInfoRow.getString(4);
//						String city = userInfoRow.getString(5);
//						String sex = userInfoRow.getString(6);
//						
//						String fullAggrInfo = partAggrInfo + "|"
//								+ Constants.FIELD_AGE + "=" + age + "|"
//								+ Constants.FIELD_PROFESSIONAL + "=" + professional + "|"
//								+ Constants.FIELD_CITY + "=" + city + "|"
//								+ Constants.FIELD_SEX + "=" + sex;
//						
//						return new Tuple2<String, String>(sessionid, fullAggrInfo);
//					}
//					
//				});
		
		return sessionid2FullAggrInfoRDD;
	}
}
