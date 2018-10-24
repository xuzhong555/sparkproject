package com.xuzhong.sparkproject.dao;

import com.xuzhong.sparkproject.domain.AreaTop3Product;
import com.xuzhong.sparkproject.domain.AreaTop3ProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AreaTop3ProductMapper {
    int countByExample(AreaTop3ProductExample example);

    int deleteByExample(AreaTop3ProductExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AreaTop3Product record);

    int insertSelective(AreaTop3Product record);

    List<AreaTop3Product> selectByExample(AreaTop3ProductExample example);

    AreaTop3Product selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AreaTop3Product record, @Param("example") AreaTop3ProductExample example);

    int updateByExample(@Param("record") AreaTop3Product record, @Param("example") AreaTop3ProductExample example);

    int updateByPrimaryKeySelective(AreaTop3Product record);

    int updateByPrimaryKey(AreaTop3Product record);
}