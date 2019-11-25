package com.wondersgroup.cloud.paas.storage.mapper;

import com.wondersgroup.cloud.paas.storage.model.ResourceStatistic;
import com.wondersgroup.cloud.paas.storage.model.ResourceStatisticExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ResourceStatisticMapper {
    long countByExample(ResourceStatisticExample example);

    int deleteByExample(ResourceStatisticExample example);

    int deleteByPrimaryKey(String resourceStatisticId);

    int insert(ResourceStatistic record);

    int insertSelective(ResourceStatistic record);

    List<ResourceStatistic> selectByExampleWithRowbounds(ResourceStatisticExample example, RowBounds rowBounds);

    List<ResourceStatistic> selectByExample(ResourceStatisticExample example);

    ResourceStatistic selectByPrimaryKey(String resourceStatisticId);

    int updateByExampleSelective(@Param("record") ResourceStatistic record, @Param("example") ResourceStatisticExample example);

    int updateByExample(@Param("record") ResourceStatistic record, @Param("example") ResourceStatisticExample example);

    int updateByPrimaryKeySelective(ResourceStatistic record);

    int updateByPrimaryKey(ResourceStatistic record);
}