package com.xuzhong.sparkproject.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuzhong.sparkproject.dao.Top10CategoryMapper;
import com.xuzhong.sparkproject.domain.Top10Category;
import com.xuzhong.sparkproject.domain.Top10CategoryExample;
import com.xuzhong.sparkproject.service.Top10CategoryService;

@Service
public class Top10CategoryServiceImpl implements Top10CategoryService{
	
	@Autowired 
	public Top10CategoryMapper top10CategoryMapper;
	
	public int countByExample(Top10CategoryExample example){
		return top10CategoryMapper.countByExample(example);
	}

	public int deleteByExample(Top10CategoryExample example){
		return top10CategoryMapper.deleteByExample(example);
	}

	public int deleteByPrimaryKey(Integer taskId){
		return top10CategoryMapper.deleteByPrimaryKey(taskId);
	}

	public int insert(Top10Category record){
		return top10CategoryMapper.insert(record);
	}

	public int insertSelective(Top10Category record){
		return top10CategoryMapper.insertSelective(record);
	}

	public List<Top10Category> selectByExample(Top10CategoryExample example){
		return top10CategoryMapper.selectByExample(example);
	}

	public Top10Category selectByPrimaryKey(Integer taskId){
		return top10CategoryMapper.selectByPrimaryKey(taskId);
	}

	public int updateByExampleSelective(@Param("record") Top10Category record, @Param("example") Top10CategoryExample example){
		return top10CategoryMapper.updateByExampleSelective(record, example);
	}

	public int updateByExample(@Param("record") Top10Category record, @Param("example") Top10CategoryExample example){
		return top10CategoryMapper.updateByExample(record, example);
	}

	public int updateByPrimaryKeySelective(Top10Category record){
		return top10CategoryMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(Top10Category record){
		return top10CategoryMapper.updateByPrimaryKey(record);
	}
}