package com.wondersgroup.cloud.paas.storage.mapper.ext;

import com.wondersgroup.cloud.paas.storage.mapper.ProjectAuthorityMapper;
import com.wondersgroup.cloud.paas.storage.model.ProjectAuthority;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenlong
 */
public interface ProjectAuthorityExtendMapper extends ProjectAuthorityMapper {
    /**
     * 根据空间Id获取项目权限信息
     * @param bucketId
     * @return
     */
    List<ProjectAuthority> getByBucketId(@Param("bucketId") String bucketId);
}
