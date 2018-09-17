package com.xuzhong.sparkproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuzhong.sparkproject.dao.SessionDetailMapper;
import com.xuzhong.sparkproject.domain.SessionDetail;
import com.xuzhong.sparkproject.service.SessionDetailService;

@Service
public class SessionDetailServiceImpl implements SessionDetailService {

	@Autowired
	private SessionDetailMapper sessionDetailMapper;
	
    public int insert(SessionDetail record){
    
    	return sessionDetailMapper.insert(record);
    }

}