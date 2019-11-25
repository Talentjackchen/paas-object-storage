package com.wondersgroup.cloud.paas.storage.mapper;

import com.wondersgroup.cloud.paas.storage.model.MdConfig;
import com.wondersgroup.cloud.paas.storage.model.MdConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MdConfigMapper {
    long countByExample(MdConfigExample example);

    int deleteByExample(MdConfigExample example);

    int deleteByPrimaryKey(String configId);

    int insert(MdConfig record);

    int insertSelective(MdConfig record);

    List<MdConfig> selectByExampleWithRowbounds(MdConfigExample example, RowBounds rowBounds);

    List<MdConfig> selectByExample(MdConfigExample example);

    MdConfig selectByPrimaryKey(String configId);

    int updateByExampleSelective(@Param("record") MdConfig record, @Param("example") MdConfigExample example);

    int updateByExample(@Param("record") MdConfig record, @Param("example") MdConfigExample example);

    int updateByPrimaryKeySelective(MdConfig record);

    int updateByPrimaryKey(MdConfig record);
}