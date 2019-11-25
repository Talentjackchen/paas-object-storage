package com.wondersgroup.cloud.paas.storage.mapper;

import com.wondersgroup.cloud.paas.storage.model.RequestStatistic;
import com.wondersgroup.cloud.paas.storage.model.RequestStatisticExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface RequestStatisticMapper {
    long countByExample(RequestStatisticExample example);

    int deleteByExample(RequestStatisticExample example);

    int deleteByPrimaryKey(String requestStatisticId);

    int insert(RequestStatistic record);

    int insertSelective(RequestStatistic record);

    List<RequestStatistic> selectByExampleWithRowbounds(RequestStatisticExample example, RowBounds rowBounds);

    List<RequestStatistic> selectByExample(RequestStatisticExample example);

    RequestStatistic selectByPrimaryKey(String requestStatisticId);

    int updateByExampleSelective(@Param("record") RequestStatistic record, @Param("example") RequestStatisticExample example);

    int updateByExample(@Param("record") RequestStatistic record, @Param("example") RequestStatisticExample example);

    int updateByPrimaryKeySelective(RequestStatistic record);

    int updateByPrimaryKey(RequestStatistic record);
}