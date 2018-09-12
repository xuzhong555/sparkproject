package com.xuzhong.sparkproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuzhong.sparkproject.dao.SessionRandomRxtractMapper;
import com.xuzhong.sparkproject.domain.SessionRandomRxtract;
import com.xuzhong.sparkproject.service.SessionRandomRxtractService;

@Service
public class SessionRandomRxtractServiceImpl implements SessionRandomRxtractService {

	@Autowired
	private SessionRandomRxtractMapper sessionRandomRxtractMapper;
	
    public int insert(SessionRandomRxtract record){
    	return sessionRandomRxtractMapper.insert(record);
    }

}