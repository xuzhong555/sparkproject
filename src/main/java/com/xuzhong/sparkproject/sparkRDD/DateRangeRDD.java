package com.xuzhong.sparkproject.sparkRDD;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

import com.alibaba.fastjson.JSONObject;
import com.xuzhong.sparkproject.util.Constants;
import com.xuzhong.sparkproject.util.ParamUtils;

import scala.Tuple2;

public class DateRangeRDD {

	
	/**
	 * 获取指定日期范围内的用户访问行为数据
	 * @param sqlContext SQLContext
	 * @param taskParam 任务参数
	 * @return 行为数据RDD
	 */
	public static JavaRDD<Row> getActionRDDByDateRange(
			SQLContext sqlContext, JSONObject taskParam) {
		String startDate = ParamUtils.getParam(taskParam, Constants.PARAM_START_DATE);
		String endDate = ParamUtils.getParam(taskParam, Constants.PARAM_END_DATE);
		
		String sql = 
				"select * "
				+ "from user_visit_action "
				+ "where date>='" + startDate + "' "
				+ "and date<='" + endDate + "'";  
		
		DataFrame actionDF = sqlContext.sql(sql);
		
		return actionDF.javaRDD();
	}
	
	/**
	 * 获取sessionid2到访问行为数据的映射的RDD
	 * @param actionRDD 
	 * @return
	 */
	public static JavaPairRDD<String, Row> getSessionid2ActionRDD(JavaRDD<Row> actionRDD) {
		return actionRDD.mapToPair(row ->{
			return new Tuple2<String, Row>(row.getString(2), row);  
		});
	}
}
