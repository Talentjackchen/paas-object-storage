package com.wondersgroup.cloud.paas.storage.service;

import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.storage.model.ProjectAuthority;

import java.util.List;

/**
 * @author chenlong
 * 项目权限服接口
 */
public interface ProjectAuthorityService {
    /**
     * 根据空间Id获取项目权限信息
     * @param bucketId
     * @return
     */
    List<ProjectAuthority> getByBucketId(String bucketId);

    /**
     * 新增项目权限信息
     */
    void insert(ProjectAuthority projectAuthority);

    /**
     * 根据项目ID校验是否存在有效数据
     * @param projectId
     * @return 存在返回true,不存在返回false
     */
    boolean checkByProjectId(String projectId);

    /**
     * 为项目生成密钥对
     * @param projectId 项目ID
     * @return
     * @throws Exception
     */
    ResultMap add(String projectId) throws Exception;

    /**
     * 删除
     * @param projectAuthorityId 业务主键
     * @return
     * @throws Exception
     */
    ResultMap delete(String projectAuthorityId) throws Exception;

    /**
     * 停用启用
     * @param projectAuthorityId 业务主键
     * @param isStart true-启用
     * @return
     * @throws Exception
     */
    ResultMap updateStatus(String projectAuthorityId,boolean isStart) throws Exception;

    /**
     * 依据主键获取
     * @param projectAuthorityId 业务主键
     * @return
     */
    ProjectAuthority getById(String projectAuthorityId);

    /**
     * 依据项目ID查询密钥对
     * @param projectId 业务主键
     * @return
     */
    List<ProjectAuthority> getByProjectId(String projectId);

    /**
     * 获取启用密钥
     * @param projectId
     * @return
     */
    List<ProjectAuthority> getStartByProjectId(String projectId);
}
