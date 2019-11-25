package com.wondersgroup.cloud.paas.storage.mapper;

import com.wondersgroup.cloud.paas.storage.model.IpControl;
import com.wondersgroup.cloud.paas.storage.model.IpControlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface IpControlMapper {
    long countByExample(IpControlExample example);

    int deleteByExample(IpControlExample example);

    int deleteByPrimaryKey(String ipControlId);

    int insert(IpControl record);

    int insertSelective(IpControl record);

    List<IpControl> selectByExampleWithRowbounds(IpControlExample example, RowBounds rowBounds);

    List<IpControl> selectByExample(IpControlExample example);

    IpControl selectByPrimaryKey(String ipControlId);

    int updateByExampleSelective(@Param("record") IpControl record, @Param("example") IpControlExample example);

    int updateByExample(@Param("record") IpControl record, @Param("example") IpControlExample example);

    int updateByPrimaryKeySelective(IpControl record);

    int updateByPrimaryKey(IpControl record);
}