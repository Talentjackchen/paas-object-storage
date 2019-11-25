package com.wondersgroup.cloud.paas.storage.controller;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.storage.constant.ProjectAuthorityConstant;
import com.wondersgroup.cloud.paas.storage.enums.AclStatusEnum;
import com.wondersgroup.cloud.paas.storage.model.ProjectAuthority;
import com.wondersgroup.cloud.paas.storage.service.ProjectAuthorityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 密钥对维护
 */
@RestController
@RequestMapping(value = "/projectAuthority")
public class ProjectAuthorityController {

    private Logger logger = Logger.getLogger(ProjectAuthorityController.class);

    @Autowired(required = false)
    private ProjectAuthorityService projectAuthorityService;

    /**
     * 依据项目ID获取密钥对
     * @param projectId
     * @return
     */
    @GetMapping("/get")
    public ResultMap get(@RequestParam String projectId){
        List<ProjectAuthority> list =  projectAuthorityService.getByProjectId(projectId);
        if(!CollectionUtils.isEmpty(list)){
            return new ResultMap(CommonConstant.SUCCESS,CommonConstant.RESULT_SUCCESS,list);
        }else{
            return new ResultMap(CommonConstant.ERROR,CommonConstant.RESULT_ERROR);
        }
    }

    /**
     * 添加密钥对
     * @param projectId 项目ID
     * @return
     */
    @PostMapping("/add")
    public ResultMap add(@RequestParam String projectId){

        logger.info("添加密钥对");
        try{
            return projectAuthorityService.add(projectId);
        }catch (Exception e){
            logger.error("新增密钥对异常！",e);
            return new ResultMap(CommonConstant.ERROR,CommonConstant.RESULT_ERROR);
        }
    }

    /**
     *  密钥对的删除
     * @param projectAuthorityId 业务主键
     * @return
     */
    @DeleteMapping("/delete")
    public ResultMap delete(@RequestParam String projectAuthorityId) {

        logger.info("删除密钥对");
        try{
            ProjectAuthority projectAuthority = projectAuthorityService.getById(projectAuthorityId);
            if(projectAuthority != null){
                if(AclStatusEnum.START.value().equals(projectAuthority.getStatus())){
                    return new ResultMap(CommonConstant.ERROR,ProjectAuthorityConstant.KEY_START_USING);
                }else{
                    return projectAuthorityService.delete(projectAuthorityId);
                }
            }else{
                return new ResultMap(CommonConstant.ERROR,CommonConstant.DATA_NOT_EXISTS);
            }
        }catch (Exception e){
            logger.error("删除密钥对异常！",e);
            return new ResultMap(CommonConstant.ERROR,CommonConstant.RESULT_ERROR);
        }
    }

    /**
     * 停用密钥对
     * @param projectAuthorityId 业务主键
     * @return
     */
    @PutMapping("/stop")
    public ResultMap stop(@RequestParam String projectAuthorityId) {

        logger.info("停用密钥对");
        try{

            return projectAuthorityService.updateStatus(projectAuthorityId,false);
        }catch(Exception e){
            logger.error("停用密钥对异常！",e);
            return new ResultMap(CommonConstant.ERROR,CommonConstant.RESULT_ERROR);
        }
    }

    /**
     * 启用密钥对
     * @param projectAuthorityId 业务主键
     * @return
     */
    @PutMapping("/start")
    public ResultMap start(@RequestParam String projectAuthorityId) {

        logger.info("启用密钥对");
        try{

            return projectAuthorityService.updateStatus(projectAuthorityId,true);
        }catch(Exception e){
            logger.error("启用密钥对异常！",e);
            return new ResultMap(CommonConstant.ERROR,CommonConstant.RESULT_ERROR);
        }
    }

}
