package com.wondersgroup.cloud.paas.storage.controller;

import com.github.pagehelper.PageInfo;
import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.exception.BusinessException;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.common.utils.PageUtils;
import com.wondersgroup.cloud.paas.storage.constant.DomainConstant;
import com.wondersgroup.cloud.paas.storage.model.Domain;
import com.wondersgroup.cloud.paas.storage.pojo.DomainInfo;
import com.wondersgroup.cloud.paas.storage.service.DomainService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * describe : 域名控制类
 * create_by : zhangyongzhao
 * create_time : 2019/5/5
 */
@RestController
@RequestMapping(value = "/domain")
public class DomainController {

    private static Logger logger = Logger.getLogger(DomainController.class);
    @Autowired
    private DomainService domainService;

    /**
     * 分页查询
     *
     * @param name     域名名称
     * @param protocol 协议
     * @param platform 平台类型
     * @param bucketId bucketId
     * @param page     当前页
     * @param rows     显示的条数
     * @param sidx     排序字段
     * @param sord     排序规则：ASC/DESC缺省为：DESC
     * @return
     */
    @GetMapping("/page")
    public ResultMap page(@RequestParam(required = false) String name,
                          @RequestParam(required = false) String protocol,
                          @RequestParam(required = false) String platform,
                          @RequestParam(required = false) String bucketId,
                          @RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer rows,
                          @RequestParam(required = false) String sidx,
                          @RequestParam(required = false) String sord) {
        int pageNum = PageUtils.getParamToInt(page, CommonConstant.PAGENUM);
        int pageSize = PageUtils.getParamToInt(rows, CommonConstant.PAGESIZE);
        String orderByClause = PageUtils.getOrderByClause(sidx, sord, CommonConstant.ORDERBYCLAUSE);
        PageInfo<Domain> pageInfo = domainService.page(name, protocol, platform, bucketId, pageNum, pageSize, orderByClause);
        return new ResultMap(CommonConstant.SUCCESS, DomainConstant.SUCCESS_DATA, pageInfo);
    }


    /**
     * 创建域名
     *
     * @param name     域名名称
     * @param protocol 协议
     * @param platform 平台类型
     * @param bucketId bucketId
     */
    @PostMapping("/create")
    public ResultMap create(@RequestParam String name, @RequestParam String protocol,
                            @RequestParam String platform, @RequestParam String bucketId) {
        try {
            domainService.create(name, protocol, platform, bucketId);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            if (ex instanceof BusinessException) {
                return ((BusinessException) ex).getResultMap();
            } else {
                return new ResultMap(CommonConstant.ERROR, CommonConstant.RESULT_ERROR);
            }
        }
        return new ResultMap(CommonConstant.SUCCESS, DomainConstant.SUCCESS_CREATE);
    }

    /**
     * 删除域名
     *
     * @param domainId 域名ID
     */
    @DeleteMapping("/delete")
    public ResultMap delete(@RequestParam String domainId) {
        try {
            domainService.delete(domainId);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            if (ex instanceof BusinessException) {
                return ((BusinessException) ex).getResultMap();
            } else {
                return new ResultMap(CommonConstant.ERROR, CommonConstant.RESULT_ERROR);
            }
        }
        return new ResultMap(CommonConstant.SUCCESS, DomainConstant.SUCCESS_DELETE);
    }

    /**
     * 启用域名
     *
     * @param domainId 域名ID
     * @return
     */
    @PostMapping("/enable")
    public ResultMap enable(@RequestParam String domainId) {
        try {
            domainService.enable(domainId);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            if (ex instanceof BusinessException) {
                return ((BusinessException) ex).getResultMap();
            } else {
                return new ResultMap(CommonConstant.ERROR, CommonConstant.RESULT_ERROR);
            }
        }
        return new ResultMap(CommonConstant.SUCCESS, DomainConstant.SUCCESS_ENABLE);
    }

    /**
     * 停用域名
     *
     * @param domainId 域名ID
     * @return
     */
    @PostMapping("/stop")
    public ResultMap stop(@RequestParam String domainId) {
        try {
            domainService.stop(domainId);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            if (ex instanceof BusinessException) {
                return ((BusinessException) ex).getResultMap();
            } else {
                return new ResultMap(CommonConstant.ERROR, CommonConstant.RESULT_ERROR);
            }
        }
        return new ResultMap(CommonConstant.SUCCESS, DomainConstant.SUCCESS_STOP);
    }

    /**
     * 升级为https
     *
     * @param domainId 域名id
     * @return
     */
    @PutMapping("/upgrade")
    public ResultMap upgrade(@RequestParam String domainId) {
        try {
            domainService.upgrade(domainId);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            if (ex instanceof BusinessException) {
                return ((BusinessException) ex).getResultMap();
            } else {
                return new ResultMap(CommonConstant.ERROR, CommonConstant.RESULT_ERROR);
            }
        }
        return new ResultMap(CommonConstant.SUCCESS, DomainConstant.SUCCESS_UPGRADE);
    }

    /**
     * 降级为HTTP
     *
     * @param domainId 域名ID
     * @return
     */
    @PutMapping("/downgrade")
    public ResultMap downgrade(@RequestParam String domainId) {
        try {
            domainService.downgrade(domainId);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            if (ex instanceof BusinessException) {
                return ((BusinessException) ex).getResultMap();
            } else {
                return new ResultMap(CommonConstant.ERROR, CommonConstant.RESULT_ERROR);
            }
        }
        return new ResultMap(CommonConstant.SUCCESS, DomainConstant.SUCCESS_DOWNGRADE);
    }

    /**
     * 详细查询
     *
     * @param domainId 域名ID
     * @return
     */
    @GetMapping("/detail")
    public ResultMap getById(@RequestParam String domainId) {
        DomainInfo domainInfo = null;
        try {
            domainInfo = domainService.getById(domainId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ((BusinessException) e).getResultMap();
        }
        return new ResultMap(CommonConstant.SUCCESS, DomainConstant.SUCCESS_DATA, domainInfo);
    }

    /**
     * 修改
     *
     * @param domainId 域名ID
     * @param bucketId 空间ID
     * @return
     */
    @PutMapping("/edit")
    public ResultMap edit(@RequestParam String domainId, @RequestParam String bucketId) {
        try {
            domainService.edit(domainId, bucketId);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            if (ex instanceof BusinessException) {
                return ((BusinessException) ex).getResultMap();
            } else {
                return new ResultMap(CommonConstant.ERROR, CommonConstant.RESULT_ERROR);
            }
        }
        return new ResultMap(CommonConstant.SUCCESS, DomainConstant.SUCCESS_EDIT);
    }

    /**
     * 绑定空闲域名
     *
     * @param name     域名
     * @param bucketId 空间ID
     * @return
     */
    @PostMapping("/bindFreeDomain")
    public ResultMap bindFreeDomain(@RequestParam String name, @RequestParam String bucketId) {
        try {
            domainService.bindFreeDomain(name, bucketId);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            if (ex instanceof BusinessException) {
                return ((BusinessException) ex).getResultMap();
            } else {
                return new ResultMap(CommonConstant.ERROR, CommonConstant.RESULT_ERROR);
            }
        }
        return new ResultMap(CommonConstant.SUCCESS, DomainConstant.SUCCESS_BIND);
    }

}
