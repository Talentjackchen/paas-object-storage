package com.wondersgroup.cloud.paas.storage.mapper;

import com.wondersgroup.cloud.paas.storage.model.ResourceRecord;
import com.wondersgroup.cloud.paas.storage.model.ResourceRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ResourceRecordMapper {
    long countByExample(ResourceRecordExample example);

    int deleteByExample(ResourceRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(ResourceRecord record);

    int insertSelective(ResourceRecord record);

    List<ResourceRecord> selectByExampleWithRowbounds(ResourceRecordExample example, RowBounds rowBounds);

    List<ResourceRecord> selectByExample(ResourceRecordExample example);

    ResourceRecord selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ResourceRecord record, @Param("example") ResourceRecordExample example);

    int updateByExample(@Param("record") ResourceRecord record, @Param("example") ResourceRecordExample example);

    int updateByPrimaryKeySelective(ResourceRecord record);

    int updateByPrimaryKey(ResourceRecord record);
}