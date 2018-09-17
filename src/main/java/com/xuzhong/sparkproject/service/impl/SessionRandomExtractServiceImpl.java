package com.xuzhong.sparkproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuzhong.sparkproject.dao.SessionRandomExtractMapper;
import com.xuzhong.sparkproject.domain.SessionRandomExtract;
import com.xuzhong.sparkproject.service.SessionRandomExtractService;

@Service
public class SessionRandomExtractServiceImpl implements SessionRandomExtractService {

	@Autowired
	private SessionRandomExtractMapper sessionRandomExtractMapper;
	
    public int insert(SessionRandomExtract record){
    	return sessionRandomExtractMapper.insert(record);
    }

}