package com.xuzhong.sparkproject.dao;

import com.xuzhong.sparkproject.domain.SessionDetail;
import com.xuzhong.sparkproject.domain.SessionDetailExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SessionDetailMapper {
    int countByExample(SessionDetailExample example);

    int deleteByExample(SessionDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SessionDetail record);

    int insertSelective(SessionDetail record);

    List<SessionDetail> selectByExample(SessionDetailExample example);

    SessionDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SessionDetail record, @Param("example") SessionDetailExample example);

    int updateByExample(@Param("record") SessionDetail record, @Param("example") SessionDetailExample example);

    int updateByPrimaryKeySelective(SessionDetail record);

    int updateByPrimaryKey(SessionDetail record);
}