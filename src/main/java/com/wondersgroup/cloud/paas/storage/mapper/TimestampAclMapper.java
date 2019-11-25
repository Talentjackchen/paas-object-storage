package com.wondersgroup.cloud.paas.storage.mapper;

import com.wondersgroup.cloud.paas.storage.model.TimestampAcl;
import com.wondersgroup.cloud.paas.storage.model.TimestampAclExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TimestampAclMapper {
    long countByExample(TimestampAclExample example);

    int deleteByExample(TimestampAclExample example);

    int deleteByPrimaryKey(String timestampAclId);

    int insert(TimestampAcl record);

    int insertSelective(TimestampAcl record);

    List<TimestampAcl> selectByExampleWithRowbounds(TimestampAclExample example, RowBounds rowBounds);

    List<TimestampAcl> selectByExample(TimestampAclExample example);

    TimestampAcl selectByPrimaryKey(String timestampAclId);

    int updateByExampleSelective(@Param("record") TimestampAcl record, @Param("example") TimestampAclExample example);

    int updateByExample(@Param("record") TimestampAcl record, @Param("example") TimestampAclExample example);

    int updateByPrimaryKeySelective(TimestampAcl record);

    int updateByPrimaryKey(TimestampAcl record);
}