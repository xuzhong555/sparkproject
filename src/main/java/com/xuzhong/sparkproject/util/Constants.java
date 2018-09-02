package com.xuzhong.sparkproject.util;

/**
 * 常量接口
 * @author Administrator
 *
 */
public interface Constants {
	
	/**
	 * 数据库相关的常量
	 */
	String JDBC_DRIVER = "jdbc.driver";
	String JDBC_DATASOURCE_SIZE = "jdbc.datasource.size";
	String JDBC_URL = "jdbc.url";
	String JDBC_USER = "jdbc.user";
	String JDBC_PASSWORD = "jdbc.password";

	String SPARK_APP_NAME_SESSION = "UserVisitSessionAnalyzeSpark";
	String SPARK_LOCAL = "spark.local";

	String PARAM_START_DATE = "startDate";
	String PARAM_END_DATE = "endDate";
	String FIELD_SESSION_ID = "sessionid";
	String FIELD_CLICK_CATEGORY_IDS = "clickcategoryids";
	String FIELD_SEARCH_KEYWORDS = "searchkeywords";
	String FIELD_AGE = "age";
	String FIELD_PROFESSIONAL = "professional";
	String FIELD_SEX = "sex";
	String FIELD_CITY = "city";
	
	String PARAM_START_AGE = "startAge";
	String PARAM_END_AGE = "endAge";
	String PARAM_PROFESSIONALS = "professionals";
	String PARAM_CITIES = "citys";
	String PARAM_SEX = "sex";
	String PARAM_KEYWORDS = "keywords";
	String PARAM_CATEGORY_IDS = "categoryids";

	
	
	
	
}
