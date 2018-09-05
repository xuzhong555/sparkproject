package com.xuzhong.sparkproject.dao.factory;

import com.xuzhong.sparkproject.dao.SessionAggrStatDAO;
import com.xuzhong.sparkproject.dao.TaskDAO;

/**
 * DAO工厂类
 * @author Administrator
 *
 */
public class DAOFactory {


	public static TaskDAO getTaskDAO() {
		return new TaskDAO();
	}

	public static SessionAggrStatDAO getSessionAggrStatDAO() {
		return new SessionAggrStatDAO();
	}
	
	
	
	
	
}
