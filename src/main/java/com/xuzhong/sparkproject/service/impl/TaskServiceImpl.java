package com.xuzhong.sparkproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuzhong.sparkproject.dao.TaskMapper;
import com.xuzhong.sparkproject.domain.Task;
import com.xuzhong.sparkproject.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private TaskMapper taskMapper;
	
	public Task selectByPrimaryKey(Integer taskId) {
		return taskMapper.selectByPrimaryKey(taskId);
	}

}
