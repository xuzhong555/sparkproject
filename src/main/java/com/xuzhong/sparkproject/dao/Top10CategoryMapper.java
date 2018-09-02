package com.xuzhong.sparkproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xuzhong.sparkproject.domain.Top10Category;
import com.xuzhong.sparkproject.domain.Top10CategoryExample;

@Mapper
public interface Top10CategoryMapper {
    int countByExample(Top10CategoryExample example);

    int deleteByExample(Top10CategoryExample example);

    int deleteByPrimaryKey(Integer taskId);

    int insert(Top10Category record);

    int insertSelective(Top10Category record);

    List<Top10Category> selectByExample(Top10CategoryExample example);

    Top10Category selectByPrimaryKey(Integer taskId);

    int updateByExampleSelective(@Param("record") Top10Category record, @Param("example") Top10CategoryExample example);

    int updateByExample(@Param("record") Top10Category record, @Param("example") Top10CategoryExample example);

    int updateByPrimaryKeySelective(Top10Category record);

    int updateByPrimaryKey(Top10Category record);
}