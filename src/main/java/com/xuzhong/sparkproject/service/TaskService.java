package com.xuzhong.sparkproject.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.xuzhong.sparkproject.dao.TaskMapper;
import com.xuzhong.sparkproject.domain.Task;

public class TaskService {
	
	@Autowired
	private TaskMapper taskMapper;
	
	public Task selectByPrimaryKey(Integer taskId) {
		return taskMapper.selectByPrimaryKey(taskId);
	}

}
