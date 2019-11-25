package com.wondersgroup.cloud.paas.storage.mapper;

import com.wondersgroup.cloud.paas.storage.model.RefererAcl;
import com.wondersgroup.cloud.paas.storage.model.RefererAclExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface RefererAclMapper {
    long countByExample(RefererAclExample example);

    int deleteByExample(RefererAclExample example);

    int deleteByPrimaryKey(String refererAclId);

    int insert(RefererAcl record);

    int insertSelective(RefererAcl record);

    List<RefererAcl> selectByExampleWithRowbounds(RefererAclExample example, RowBounds rowBounds);

    List<RefererAcl> selectByExample(RefererAclExample example);

    RefererAcl selectByPrimaryKey(String refererAclId);

    int updateByExampleSelective(@Param("record") RefererAcl record, @Param("example") RefererAclExample example);

    int updateByExample(@Param("record") RefererAcl record, @Param("example") RefererAclExample example);

    int updateByPrimaryKeySelective(RefererAcl record);

    int updateByPrimaryKey(RefererAcl record);
}