package com.xuzhong.sparkproject.dao;

import com.xuzhong.sparkproject.domain.SessionRandomExtract;
import com.xuzhong.sparkproject.domain.SessionRandomExtractExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SessionRandomExtractMapper {
    int countByExample(SessionRandomExtractExample example);

    int deleteByExample(SessionRandomExtractExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SessionRandomExtract record);

    int insertSelective(SessionRandomExtract record);

    List<SessionRandomExtract> selectByExample(SessionRandomExtractExample example);

    SessionRandomExtract selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SessionRandomExtract record, @Param("example") SessionRandomExtractExample example);

    int updateByExample(@Param("record") SessionRandomExtract record, @Param("example") SessionRandomExtractExample example);

    int updateByPrimaryKeySelective(SessionRandomExtract record);

    int updateByPrimaryKey(SessionRandomExtract record);
}