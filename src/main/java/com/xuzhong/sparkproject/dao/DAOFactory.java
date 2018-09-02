package com.xuzhong.sparkproject.dao;

/**
 * DAO工厂类
 * @author Administrator
 *
 */
public class DAOFactory {

	/**
	 * 获取任务管理DAO
	 * @return DAO
	 */
	public static TaskDAO getTaskDAO() {
		return new TaskDAO();
	}
	
}
