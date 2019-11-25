package com.wondersgroup.cloud.paas.storage.mapper;

import com.wondersgroup.cloud.paas.storage.model.Price;
import com.wondersgroup.cloud.paas.storage.model.PriceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PriceMapper {
    long countByExample(PriceExample example);

    int deleteByExample(PriceExample example);

    int deleteByPrimaryKey(String priceId);

    int insert(Price record);

    int insertSelective(Price record);

    List<Price> selectByExampleWithRowbounds(PriceExample example, RowBounds rowBounds);

    List<Price> selectByExample(PriceExample example);

    Price selectByPrimaryKey(String priceId);

    int updateByExampleSelective(@Param("record") Price record, @Param("example") PriceExample example);

    int updateByExample(@Param("record") Price record, @Param("example") PriceExample example);

    int updateByPrimaryKeySelective(Price record);

    int updateByPrimaryKey(Price record);
}