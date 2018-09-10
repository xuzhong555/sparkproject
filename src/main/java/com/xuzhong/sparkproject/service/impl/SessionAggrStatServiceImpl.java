package com.xuzhong.sparkproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuzhong.sparkproject.dao.SessionAggrStatMapper;
import com.xuzhong.sparkproject.domain.SessionAggrStat;
import com.xuzhong.sparkproject.service.SessionAggrStatService;

@Service
public class SessionAggrStatServiceImpl implements SessionAggrStatService {

	@Autowired
	private SessionAggrStatMapper sessionAggrStatMapper;
	
	
    public int insert(SessionAggrStat record){
    	return sessionAggrStatMapper.insert(record);
    }

}