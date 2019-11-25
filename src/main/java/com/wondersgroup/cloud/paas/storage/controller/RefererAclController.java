package com.wondersgroup.cloud.paas.storage.controller;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.exception.BusinessException;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.storage.constant.RefererAclConstant;
import com.wondersgroup.cloud.paas.storage.model.RefererAcl;
import com.wondersgroup.cloud.paas.storage.service.RefererAclService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenlong
 */
@RestController
@RequestMapping(value = "/refererAcl")
public class RefererAclController {
    private static Logger logger = Logger.getLogger(RefererAclController.class);

    @Autowired
    private RefererAclService refererAclService;

    @PostMapping("create")
    public ResultMap create(@RequestBody RefererAcl refererAcl, HttpServletRequest request) {
        try{

            String bucketId = request.getHeader("bucketId");
            refererAclService.create(bucketId, refererAcl);
        }catch (Exception ex){
            logger.error(CommonConstant.RESULT_ERROR, ex);
            if(ex instanceof BusinessException){
                return ((BusinessException)ex).getResultMap();
            }else{
                return new ResultMap(CommonConstant.ERROR, RefererAclConstant.CREATE_FAIL_MSG);
            }
        }

        return new ResultMap(CommonConstant.SUCCESS, RefererAclConstant.CREATE_SUCCESS_MSG);
    }

    @PutMapping("update")
    public ResultMap update(@RequestBody RefererAcl refererAcl, HttpServletRequest request) {
        try{
            String bucketId = request.getHeader("bucketId");
            refererAclService.update(bucketId, refererAcl);
        }catch (Exception ex){
            logger.error(CommonConstant.RESULT_ERROR, ex);
            if(ex instanceof BusinessException){
                return ((BusinessException)ex).getResultMap();
            }else{
                return new ResultMap(CommonConstant.ERROR, RefererAclConstant.UPDATE_FAIL_MSG);
            }
        }

        return new ResultMap(CommonConstant.SUCCESS, RefererAclConstant.UPDATE_SUCCESS_MSG);
    }

    @DeleteMapping("delete")
    public ResultMap delete(HttpServletRequest request) {
        String bucketId = request.getHeader("bucketId");
        refererAclService.delete(bucketId);
        return new ResultMap(CommonConstant.SUCCESS, RefererAclConstant.DELETE_SUCCESS_MSG);
    }

    @GetMapping("queryByBucketId")
    public ResultMap queryByBucketId(HttpServletRequest request) {
        String bucketId = request.getHeader("bucketId");
        RefererAcl refererAcl = refererAclService.queryByBucketId(bucketId);
        return new ResultMap(CommonConstant.SUCCESS, CommonConstant.RESULT_SUCCESS,refererAcl);
    }
}
