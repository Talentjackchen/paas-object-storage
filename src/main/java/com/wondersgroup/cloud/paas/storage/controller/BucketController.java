package com.wondersgroup.cloud.paas.storage.controller;

import com.github.pagehelper.PageInfo;
import com.wondersgroup.cloud.paas.common.annotation.UserInfoValidation;
import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.enums.ParamTypeEnum;
import com.wondersgroup.cloud.paas.common.enums.UserInfoValidationEnum;
import com.wondersgroup.cloud.paas.common.exception.BusinessException;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.common.utils.DateUtils;
import com.wondersgroup.cloud.paas.common.utils.PageUtils;
import com.wondersgroup.cloud.paas.storage.constant.BucketConstant;
import com.wondersgroup.cloud.paas.storage.model.Bucket;
import com.wondersgroup.cloud.paas.storage.pojo.ResourceCharge;
import com.wondersgroup.cloud.paas.storage.service.BucketService;
import com.wondersgroup.cloud.paas.storage.service.ChargeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * bucket
 * zhangyongzhao
 */
@RestController
@RequestMapping(value = "/bucket")
public class BucketController {

    private static Logger logger = Logger.getLogger(BucketController.class);

    @Autowired
    private BucketService bucketService;

    @Autowired
    private ChargeService chargeService;

    /**
     * 创建bucket
     *
     * @param bucket 对象
     * @return ResultMap
     */
    @PostMapping("/create")
    @UserInfoValidation(paramType = ParamTypeEnum.ENTITY, paramNames = {"projectId"}, userInfos = {UserInfoValidationEnum.PROJECT})
    public ResultMap create(@RequestBody Bucket bucket) {
        try {
            bucketService.create(bucket);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (e instanceof BusinessException) {
                return ((BusinessException) e).getResultMap();
            } else {
                return new ResultMap(CommonConstant.ERROR, BucketConstant.MSG_CREATE_BUCKET_ERROR);
            }
        }
        return new ResultMap(CommonConstant.SUCCESS, BucketConstant.MSG_CREATE_BUCKET_SUCCESS);
    }

    /**
     * 删除bucket
     *
     * @param bucketId 主键
     * @return
     */
    @DeleteMapping("/delete")
    public ResultMap delete(@RequestParam(required = false) String bucketId) {
        try {
            bucketService.delete(bucketId);
        } catch (Exception e) {
            logger.error(BucketConstant.MSG_DELETE_BUCKET_FAILURE, e);
            if (e instanceof BusinessException) {
                return ((BusinessException) e).getResultMap();
            } else {
                return new ResultMap(CommonConstant.ERROR, BucketConstant.MSG_DELETE_BUCKET_FAILURE);
            }
        }
        return new ResultMap(CommonConstant.SUCCESS, BucketConstant.MSG_DELETE_BUCKET_SUCCESS);
    }

    /**
     * 设置空间权限
     *
     * @param bucketId 空间ID
     * @param acl      权限代码
     * @return
     */
    @PostMapping("setPermission")
    public ResultMap setPermission(@RequestParam String bucketId, @RequestParam int acl) {
        try {
            bucketService.setPermission(bucketId, acl);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                logger.error(e.getMessage(), e);
                return ((BusinessException) e).getResultMap();
            } else {
                logger.error(BucketConstant.TRANSFORM_PUBLIC_ERROR, e);
                return new ResultMap(CommonConstant.ERROR, BucketConstant.TRANSFORM_PUBLIC_ERROR);
            }
        }
        return new ResultMap(CommonConstant.SUCCESS, BucketConstant.TRANSFORM_PUBLIC_SUCCESS);
    }

    /**
     * 临时转正式
     *
     * @param bucketId 空间ID
     * @return
     */
    @PutMapping("setFreeDomain")
    public ResultMap setFreeDomain(@RequestParam String bucketId) {
        try {
            bucketService.setFreeDomain(bucketId);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                logger.error(e.getMessage(), e);
                return ((BusinessException) e).getResultMap();
            } else {
                logger.error(BucketConstant.ERROR_PERMISSION, e);
                return new ResultMap(CommonConstant.ERROR, CommonConstant.RESULT_ERROR);
            }
        }
        return new ResultMap(CommonConstant.SUCCESS, BucketConstant.TRANSFORMATION_FORMAL);
    }

    /**
     * 查询所有有效的空间数据
     *
     * @param projectId 项目ID
     * @return
     */
    @GetMapping("list")
    @UserInfoValidation(paramType = ParamTypeEnum.STRING, paramNames = {"projectId"}, userInfos = {UserInfoValidationEnum.PROJECT})
    public ResultMap list(@RequestParam String projectId) {
        List<Bucket> list = bucketService.list(projectId);
        return new ResultMap(CommonConstant.SUCCESS, BucketConstant.MSG_QUERY_BUCKET_SUCCESS, list);
    }

    /**
     * 根据项目ID查询以存储区域分组的数据
     *
     * @param projectId 项目ID
     * @return
     */
    @GetMapping("getByProjectId")
    @UserInfoValidation(paramType = ParamTypeEnum.STRING, paramNames = {"projectId"}, userInfos = {UserInfoValidationEnum.PROJECT})
    public ResultMap getByProjectId(@RequestParam String projectId) {
        Map<String, List<Bucket>> bucketMap = bucketService.getByProjectId(projectId);
        return new ResultMap(CommonConstant.SUCCESS, BucketConstant.MSG_QUERY_BUCKET_SUCCESS, bucketMap);
    }

    /**
     * 分页查询
     *
     * @param name           空间名称
     * @param projectId      项目ID
     * @param accountId 账号
     * @param type 空间类型
     * @param page           当前页
     * @param rows           显示的条数
     * @param sidx           排序字段
     * @param sord           排序规则：ASC/DESC缺省为：DESC
     * @return
     */
    @GetMapping("/page")
    public ResultMap page(@RequestParam(required = false) String name,
                          @RequestParam String projectId,
                          @RequestParam(required = false) String accountId,
                          @RequestParam(required = false) String type,
                          @RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer rows,
                          @RequestParam(required = false) String sidx,
                          @RequestParam(required = false) String sord
    ) {
        PageInfo<Bucket> pageInfo;
        try {
            int pageNum = PageUtils.getParamToInt(page, CommonConstant.PAGENUM);
            int pageSize = PageUtils.getParamToInt(rows, CommonConstant.PAGESIZE);
            String orderByClause = PageUtils.getOrderByClause(sidx, sord, CommonConstant.ORDERBYCLAUSE);
            pageInfo = bucketService.page(name, projectId, accountId, type,
                    pageNum, pageSize, orderByClause);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            if (ex instanceof BusinessException) {
                return ((BusinessException) ex).getResultMap();
            } else {
                return new ResultMap(CommonConstant.ERROR, CommonConstant.RESULT_ERROR, ex.getMessage());
            }
        }

        return new ResultMap(CommonConstant.SUCCESS, BucketConstant.MSG_QUERY_BUCKET_SUCCESS, pageInfo);
    }

    /**
     * 查询bucket详细数据
     *
     * @param bucketId
     * @return
     */
    @GetMapping("info")
    public ResultMap getBucketInfo(@RequestParam String bucketId) {
        HashMap<String, Object> bucketInfo;
        try {
            bucketInfo = bucketService.getBucketInfo(bucketId);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                logger.error(e.getMessage(), e);
                return ((BusinessException) e).getResultMap();
            } else {
                logger.error(BucketConstant.ERROR_PERMISSION, e);
                return new ResultMap(CommonConstant.ERROR, e.getMessage());
            }
        }
        return new ResultMap(CommonConstant.SUCCESS, BucketConstant.MSG_QUERY_BUCKET_SUCCESS, bucketInfo);
    }

    /**
     * 判断是否存在空间（存在返回true,不存在返回false）
     *
     * @param projectId
     * @return
     */
    @GetMapping("isExistResource")
    public ResultMap isExistBucket(@RequestParam String projectId) {
        try {
            boolean existBucket = bucketService.isExistBucket(projectId);
            return new ResultMap(CommonConstant.SUCCESS, CommonConstant.RESULT_SUCCESS, existBucket);
        } catch (Exception e) {
            logger.error(CommonConstant.RESULT_ERROR, e);
            return new ResultMap(CommonConstant.ERROR, CommonConstant.RESULT_ERROR, e.getMessage());
        }
    }


    /**
     * @return
     */
    @GetMapping("calculateResourceCharge")
    public ResultMap calculateResourceCharge() {
        logger.info("ChargeTask beginning!");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        logger.info("ChargeTask currentTime: " + calendar.getTime());
        Date beginDate = DateUtils.getDateOfMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        Date endDate = DateUtils.getDateOfMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1);
        logger.info("ChargeTask beginDate: " + beginDate);
        logger.info("ChargeTask endDate: " + endDate);
        List<ResourceCharge> list = chargeService.calculateResourceCharge(beginDate, endDate);
        return new ResultMap(CommonConstant.SUCCESS, CommonConstant.RESULT_SUCCESS, list);
    }
}
