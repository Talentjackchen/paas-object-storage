package com.wondersgroup.cloud.paas.storage.mapper;

import com.wondersgroup.cloud.paas.storage.model.Cost;
import com.wondersgroup.cloud.paas.storage.model.CostExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CostMapper {
    long countByExample(CostExample example);

    int deleteByExample(CostExample example);

    int deleteByPrimaryKey(String costId);

    int insert(Cost record);

    int insertSelective(Cost record);

    List<Cost> selectByExampleWithRowbounds(CostExample example, RowBounds rowBounds);

    List<Cost> selectByExample(CostExample example);

    Cost selectByPrimaryKey(String costId);

    int updateByExampleSelective(@Param("record") Cost record, @Param("example") CostExample example);

    int updateByExample(@Param("record") Cost record, @Param("example") CostExample example);

    int updateByPrimaryKeySelective(Cost record);

    int updateByPrimaryKey(Cost record);
}