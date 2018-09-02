package com.xuzhong.sparkproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xuzhong.sparkproject.domain.SessionRandomRxtract;
import com.xuzhong.sparkproject.domain.SessionRandomRxtractExample;

@Mapper
public interface SessionRandomRxtractMapper {
    int countByExample(SessionRandomRxtractExample example);

    int deleteByExample(SessionRandomRxtractExample example);

    int deleteByPrimaryKey(Integer taskId);

    int insert(SessionRandomRxtract record);

    int insertSelective(SessionRandomRxtract record);

    List<SessionRandomRxtract> selectByExample(SessionRandomRxtractExample example);

    SessionRandomRxtract selectByPrimaryKey(Integer taskId);

    int updateByExampleSelective(@Param("record") SessionRandomRxtract record, @Param("example") SessionRandomRxtractExample example);

    int updateByExample(@Param("record") SessionRandomRxtract record, @Param("example") SessionRandomRxtractExample example);

    int updateByPrimaryKeySelective(SessionRandomRxtract record);

    int updateByPrimaryKey(SessionRandomRxtract record);
}