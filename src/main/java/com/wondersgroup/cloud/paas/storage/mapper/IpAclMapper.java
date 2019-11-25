package com.wondersgroup.cloud.paas.storage.mapper;

import com.wondersgroup.cloud.paas.storage.model.IpAcl;
import com.wondersgroup.cloud.paas.storage.model.IpAclExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface IpAclMapper {
    long countByExample(IpAclExample example);

    int deleteByExample(IpAclExample example);

    int deleteByPrimaryKey(String ipAclId);

    int insert(IpAcl record);

    int insertSelective(IpAcl record);

    List<IpAcl> selectByExampleWithRowbounds(IpAclExample example, RowBounds rowBounds);

    List<IpAcl> selectByExample(IpAclExample example);

    IpAcl selectByPrimaryKey(String ipAclId);

    int updateByExampleSelective(@Param("record") IpAcl record, @Param("example") IpAclExample example);

    int updateByExample(@Param("record") IpAcl record, @Param("example") IpAclExample example);

    int updateByPrimaryKeySelective(IpAcl record);

    int updateByPrimaryKey(IpAcl record);
}