package com.xuzhong.sparkproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xuzhong.sparkproject.domain.DessionDetail;
import com.xuzhong.sparkproject.domain.DessionDetailExample;
@Mapper
public interface DessionDetailMapper {
    int countByExample(DessionDetailExample example);

    int deleteByExample(DessionDetailExample example);

    int deleteByPrimaryKey(Integer taskId);

    int insert(DessionDetail record);

    int insertSelective(DessionDetail record);

    List<DessionDetail> selectByExample(DessionDetailExample example);

    DessionDetail selectByPrimaryKey(Integer taskId);

    int updateByExampleSelective(@Param("record") DessionDetail record, @Param("example") DessionDetailExample example);

    int updateByExample(@Param("record") DessionDetail record, @Param("example") DessionDetailExample example);

    int updateByPrimaryKeySelective(DessionDetail record);

    int updateByPrimaryKey(DessionDetail record);
}