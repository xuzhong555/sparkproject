package com.xuzhong.sparkproject.service;

import com.xuzhong.sparkproject.domain.Task;

public interface TaskService {
	
	
	public Task selectByPrimaryKey(Integer taskId);

}
