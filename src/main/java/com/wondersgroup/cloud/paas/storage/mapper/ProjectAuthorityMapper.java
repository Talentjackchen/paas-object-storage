package com.wondersgroup.cloud.paas.storage.mapper;

import com.wondersgroup.cloud.paas.storage.model.ProjectAuthority;
import com.wondersgroup.cloud.paas.storage.model.ProjectAuthorityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ProjectAuthorityMapper {
    long countByExample(ProjectAuthorityExample example);

    int deleteByExample(ProjectAuthorityExample example);

    int deleteByPrimaryKey(String projectAuthorityId);

    int insert(ProjectAuthority record);

    int insertSelective(ProjectAuthority record);

    List<ProjectAuthority> selectByExampleWithRowbounds(ProjectAuthorityExample example, RowBounds rowBounds);

    List<ProjectAuthority> selectByExample(ProjectAuthorityExample example);

    ProjectAuthority selectByPrimaryKey(String projectAuthorityId);

    int updateByExampleSelective(@Param("record") ProjectAuthority record, @Param("example") ProjectAuthorityExample example);

    int updateByExample(@Param("record") ProjectAuthority record, @Param("example") ProjectAuthorityExample example);

    int updateByPrimaryKeySelective(ProjectAuthority record);

    int updateByPrimaryKey(ProjectAuthority record);
}