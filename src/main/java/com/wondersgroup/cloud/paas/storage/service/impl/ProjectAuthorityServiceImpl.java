package com.wondersgroup.cloud.paas.storage.service.impl;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.enums.status.ValidStatusEnum;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.common.utils.CommonUtils;
import com.wondersgroup.cloud.paas.storage.constant.ProjectAuthorityConstant;
import com.wondersgroup.cloud.paas.storage.enums.AclStatusEnum;
import com.wondersgroup.cloud.paas.storage.mapper.ProjectAuthorityMapper;
import com.wondersgroup.cloud.paas.storage.mapper.ext.ProjectAuthorityExtendMapper;
import com.wondersgroup.cloud.paas.storage.model.ProjectAuthority;
import com.wondersgroup.cloud.paas.storage.model.ProjectAuthorityExample;
import com.wondersgroup.cloud.paas.storage.service.ProjectAuthorityService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author chenlong
 * 项目权限服务实现
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProjectAuthorityServiceImpl implements ProjectAuthorityService {
    @Autowired(required = false)
    private ProjectAuthorityExtendMapper projectAuthorityExtendMapper;

    @Autowired(required = false)
    private ProjectAuthorityMapper projectAuthorityMapper;

    @Override
    public List<ProjectAuthority> getByBucketId(String bucketId) {
        return projectAuthorityExtendMapper.getByBucketId(bucketId);
    }

    @Override
    public void insert(ProjectAuthority projectAuthority) {
        projectAuthorityMapper.insert(projectAuthority);
    }

    @Override
    public boolean checkByProjectId(String projectId) {
        ProjectAuthorityExample example = new ProjectAuthorityExample();
        ProjectAuthorityExample.Criteria criteria = example.createCriteria();
        criteria.andProjectIdEqualTo(projectId);
        criteria.andValidFlagEqualTo(ValidStatusEnum.VALID.value());
        List<ProjectAuthority> projectAuthorities = projectAuthorityMapper.selectByExample(example);
        return !CollectionUtils.isEmpty(projectAuthorities);
    }

    /**
     * 为项目生成密钥对
     * @param projectId 项目ID
     * @return
     */
    @Override
    public ResultMap add(String projectId) {
        List<ProjectAuthority> authorityList = getByProjectId(projectId);
        if(!CollectionUtils.isEmpty(authorityList) && authorityList.size() > 1){
            return new ResultMap(CommonConstant.ERROR,ProjectAuthorityConstant.MAX_NUM_ERROR);
        }else{
            ProjectAuthority projectAuthority = new ProjectAuthority();
            projectAuthority.setProjectAuthorityId(CommonUtils.generateId());
            projectAuthority.setProjectId(projectId);
            projectAuthority.setAccessKey(CommonUtils.generateCapitalUUID());
            projectAuthority.setSecretKey(CommonUtils.generateCapitalUUID());
            projectAuthority.setValidFlag(ValidStatusEnum.VALID.value());
            projectAuthority.setStatus(AclStatusEnum.START.value());
            projectAuthority.setCreateTime(new Date());
            projectAuthority.setUpdateTime(new Date());
            int returnCode = projectAuthorityMapper.insert(projectAuthority);
            if(returnCode > 0){
                return new ResultMap(CommonConstant.SUCCESS,CommonConstant.ADD_SUCCESS);
            }else{
                return new ResultMap(CommonConstant.ERROR,CommonConstant.ADD_ERROR);
            }
        }
    }

    /**
     * 删除
     * @param projectAuthorityId 业务主键
     * @return
     */
    @Override
    public ResultMap delete(String projectAuthorityId) {
        ProjectAuthority projectAuthority = getById(projectAuthorityId);
        if(projectAuthority != null){
            if(AclStatusEnum.START.value().equals(projectAuthority.getStatus())){
                return new ResultMap(CommonConstant.ERROR,ProjectAuthorityConstant.KEY_START_USING);
            }else{
                List<ProjectAuthority> authorityList = getStartByProjectId(projectAuthority.getProjectId());
                if(CollectionUtils.isEmpty(authorityList)){//没有启用状态，不允许删除
                    return new ResultMap(CommonConstant.ERROR,CommonConstant.STOP_ERROR_KEEP_ONE);
                }
                projectAuthority.setUpdateTime(new Date());
                projectAuthority.setValidFlag(ValidStatusEnum.INVALID.value());
                int returnCode = projectAuthorityMapper.updateByPrimaryKey(projectAuthority);
                if(returnCode > 0){
                    return new ResultMap(CommonConstant.SUCCESS,CommonConstant.DELETE_SUCCESS);
                }else{
                    return new ResultMap(CommonConstant.ERROR,CommonConstant.DELETE_ERROR);
                }
            }
        }else{
            return new ResultMap(CommonConstant.ERROR,CommonConstant.DATA_NOT_EXISTS);
        }
    }

    /**
     * 停用、启用
     * @param projectAuthorityId 业务主键
     * @param isStart true-启用
     * @return
     */
    @Override
    public ResultMap updateStatus(String projectAuthorityId, boolean isStart) {
        ProjectAuthority projectAuthority = getById(projectAuthorityId);
        if(projectAuthority != null){
            //启用操作
            if(isStart){
                if(AclStatusEnum.START.value().equals(projectAuthority.getStatus())){
                    return new ResultMap(CommonConstant.SUCCESS,CommonConstant.START_SUCCESS);
                }else{
                    projectAuthority.setStatus(AclStatusEnum.START.value());
                }
            }else{
                if(AclStatusEnum.STOP.value().equals(projectAuthority.getStatus())){
                    return new ResultMap(CommonConstant.SUCCESS,CommonConstant.STOP_SUCCESS);
                }else{
                    List<ProjectAuthority> authorityList = getStartByProjectId(projectAuthority.getProjectId());
                    if(authorityList ==null || authorityList.size() < 2){//启用数量小于2
                        return new ResultMap(CommonConstant.ERROR,CommonConstant.STOP_ERROR_KEEP_ONE);
                    }
                    projectAuthority.setStatus(AclStatusEnum.STOP.value());
                }
            }
            projectAuthority.setUpdateTime(new Date());
            int returnCode =projectAuthorityMapper.updateByPrimaryKey(projectAuthority);
            if(returnCode > 0){
                if(isStart){
                    return new ResultMap(CommonConstant.SUCCESS,CommonConstant.START_SUCCESS);
                }else{
                    return new ResultMap(CommonConstant.SUCCESS,CommonConstant.STOP_SUCCESS);
                }
            }else{
                if(isStart){
                    return new ResultMap(CommonConstant.ERROR,CommonConstant.START_ERROR);
                }else{
                    return new ResultMap(CommonConstant.ERROR,CommonConstant.STOP_ERROR);
                }
            }
        }else{
            return new ResultMap(CommonConstant.ERROR,CommonConstant.DATA_NOT_EXISTS);
        }
    }

    /**
     * 依据主键获取
     * @param projectAuthorityId 业务主键
     * @return
     */
    @Override
    public ProjectAuthority getById(String projectAuthorityId) {
        if(StringUtils.isBlank(projectAuthorityId)){
            return null;
        }
        ProjectAuthority projectAuthority = projectAuthorityMapper.selectByPrimaryKey(projectAuthorityId);
        if(projectAuthority != null && ValidStatusEnum.VALID.value().equals(projectAuthority.getValidFlag())){
            return projectAuthority;
        }else{
            return null;
        }
    }

    /**
     * 依据项目ID查询密钥对
     * @param projectId 业务主键
     * @return
     */
    @Override
    public List<ProjectAuthority> getByProjectId(String projectId) {
        if(StringUtils.isBlank(projectId)){
            return null;
        }
        ProjectAuthorityExample example = new ProjectAuthorityExample();
        example.createCriteria().andValidFlagEqualTo(ValidStatusEnum.VALID.value()).andProjectIdEqualTo(projectId);
        return  projectAuthorityMapper.selectByExample(example);
    }

    @Override
    public List<ProjectAuthority> getStartByProjectId(String projectId) {
        if(StringUtils.isBlank(projectId)){
            return null;
        }
        ProjectAuthorityExample example = new ProjectAuthorityExample();
        example.createCriteria().andValidFlagEqualTo(ValidStatusEnum.VALID.value()).andStatusEqualTo(AclStatusEnum.START.value()).andProjectIdEqualTo(projectId);
        return  projectAuthorityMapper.selectByExample(example);
    }
}
