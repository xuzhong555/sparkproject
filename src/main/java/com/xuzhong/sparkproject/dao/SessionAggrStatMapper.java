package com.xuzhong.sparkproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xuzhong.sparkproject.domain.SessionAggrStat;
import com.xuzhong.sparkproject.domain.SessionAggrStatExample;

@Mapper
public interface SessionAggrStatMapper {
    int countByExample(SessionAggrStatExample example);

    int deleteByExample(SessionAggrStatExample example);

    int deleteByPrimaryKey(Integer taskId);

    int insert(SessionAggrStat record);

    int insertSelective(SessionAggrStat record);

    List<SessionAggrStat> selectByExample(SessionAggrStatExample example);

    SessionAggrStat selectByPrimaryKey(Integer taskId);

    int updateByExampleSelective(@Param("record") SessionAggrStat record, @Param("example") SessionAggrStatExample example);

    int updateByExample(@Param("record") SessionAggrStat record, @Param("example") SessionAggrStatExample example);

    int updateByPrimaryKeySelective(SessionAggrStat record);

    int updateByPrimaryKey(SessionAggrStat record);
}