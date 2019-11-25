package com.wondersgroup.cloud.paas.storage.controller;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.exception.BusinessException;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.storage.constant.IpAclConstant;
import com.wondersgroup.cloud.paas.storage.model.IpAcl;
import com.wondersgroup.cloud.paas.storage.service.IpAclService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author chenlong
 */
@RestController
@RequestMapping(value = "/ipAcl")
public class IpAclController {
    private static Logger logger = Logger.getLogger(IpAclController.class);

    @Autowired
    private IpAclService ipAclService;

    @PostMapping("create")
    public ResultMap create(@RequestBody List<String> ips, HttpServletRequest request) {
        try {
            String bucketId = request.getHeader("bucketId");
            ipAclService.create(bucketId, ips);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            if (ex instanceof BusinessException) {
                return ((BusinessException) ex).getResultMap();
            } else {
                return new ResultMap(CommonConstant.ERROR, IpAclConstant.CREATE_FAIL_MSG);
            }
        }

        return new ResultMap(CommonConstant.SUCCESS, IpAclConstant.CREATE_SUCCESS_MSG);
    }

    @PutMapping("update")
    public ResultMap update(@RequestBody List<String> ips, HttpServletRequest request) {
        try {
            String bucketId = request.getHeader("bucketId");
            ipAclService.update(bucketId, ips);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            if (ex instanceof BusinessException) {
                return ((BusinessException) ex).getResultMap();
            } else {
                return new ResultMap(CommonConstant.ERROR, IpAclConstant.UPDATE_FAIL_MSG);
            }
        }
        return new ResultMap(CommonConstant.SUCCESS, IpAclConstant.UPDATE_SUCCESS_MSG);
    }

    @DeleteMapping("delete")
    public ResultMap delete(HttpServletRequest request) {
        String bucketId = request.getHeader("bucketId");
        ipAclService.delete(bucketId);
        return new ResultMap(CommonConstant.SUCCESS, IpAclConstant.DELETE_SUCCESS_MSG);
    }

    @GetMapping("queryByBucketId")
    public ResultMap queryByBucketId(HttpServletRequest request) {
        String bucketId = request.getHeader("bucketId");
        IpAcl ipAcl = ipAclService.queryByBucketId(bucketId);
        return new ResultMap(CommonConstant.SUCCESS, CommonConstant.RESULT_SUCCESS,ipAcl);
    }
}
