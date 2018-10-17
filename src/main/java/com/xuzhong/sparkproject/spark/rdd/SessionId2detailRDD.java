package com.xuzhong.sparkproject.spark.rdd;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.sql.Row;

import scala.Tuple2;

public class SessionId2detailRDD {

	
	public static JavaPairRDD<String, Row> getSessionId2detailRDD(JavaPairRDD<String, String> filteredSessionid2AggrInfoRDD,
			JavaPairRDD<String, Row> sessionid2actionRDD) {
		
		//获取符合条件的session访问明细
		JavaPairRDD<String, Row> sessionid2detailRDD = filteredSessionid2AggrInfoRDD.join(sessionid2actionRDD).mapToPair(tuple -> {
				return new Tuple2<String, Row>(tuple._1, tuple._2._2);
		});
		return sessionid2detailRDD;
	}
}
