package com.wondersgroup.cloud.paas.storage.mapper;

import com.wondersgroup.cloud.paas.storage.model.Domain;
import com.wondersgroup.cloud.paas.storage.model.DomainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DomainMapper {
    long countByExample(DomainExample example);

    int deleteByExample(DomainExample example);

    int deleteByPrimaryKey(String domainId);

    int insert(Domain record);

    int insertSelective(Domain record);

    List<Domain> selectByExampleWithRowbounds(DomainExample example, RowBounds rowBounds);

    List<Domain> selectByExample(DomainExample example);

    Domain selectByPrimaryKey(String domainId);

    int updateByExampleSelective(@Param("record") Domain record, @Param("example") DomainExample example);

    int updateByExample(@Param("record") Domain record, @Param("example") DomainExample example);

    int updateByPrimaryKeySelective(Domain record);

    int updateByPrimaryKey(Domain record);
}