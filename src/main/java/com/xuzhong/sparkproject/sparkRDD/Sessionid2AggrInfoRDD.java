package com.xuzhong.sparkproject.sparkRDD;

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
	 * @param actionRDD 行为数据RDD
	 * @return session粒度聚合数据
	 */
	public static  JavaPairRDD<String, String> aggregateBySession(
			SQLContext sqlContext, JavaRDD<Row> actionRDD) {
		// 现在actionRDD中的元素是Row，一个Row就是一行用户访问行为记录，比如一次点击或者搜索
		// 我们现在需要将这个Row映射成<sessionid,Row>的格式
		JavaPairRDD<String, Row> sessionid2ActionRDD = actionRDD.mapToPair(row -> {
				return new Tuple2<String, Row>(row.getString(2), row);
		});
		
		// 对行为数据按session粒度进行分组
		JavaPairRDD<String, Iterable<Row>> sessionid2ActionsRDD = 
				sessionid2ActionRDD.groupByKey();
		
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
		
		return sessionid2FullAggrInfoRDD;
	}
}
