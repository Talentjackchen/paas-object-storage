package com.wondersgroup.cloud.paas.storage.controller;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.common.utils.PageUtils;
import com.wondersgroup.cloud.paas.storage.constant.SslCertConstant;
import com.wondersgroup.cloud.paas.storage.model.Domain;
import com.wondersgroup.cloud.paas.storage.model.SslCertificate;
import com.wondersgroup.cloud.paas.storage.service.DomainService;
import com.wondersgroup.cloud.paas.storage.service.SslCertService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SLL证书相关操作
 */
@RestController
@RequestMapping(value = "/sslCert")
public class SslCertController {

    private Logger logger = Logger.getLogger(SslCertController.class);

    @Autowired(required = false)
    private SslCertService sslCertService;

    @Autowired(required = false)
    private DomainService domainService;

    /**
     * SSL证书分页查询
     * @param remarkName 备注名
     * @param genericName 通用名称
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param page 页
     * @param rows 码
     * @param sidx 排序字段
     * @param sord 排序方式
     * @return
     */
    @GetMapping("/page")
    public ResultMap page(@RequestParam(required = false) String remarkName,
                          @RequestParam(required = false) String genericName,
                          @RequestParam(required = false) String beginTime,
                          @RequestParam(required = false) String endTime,
                          @RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer rows,
                          @RequestParam(required = false) String sidx,
                          @RequestParam(required = false) String sord){

        logger.info("分页加载资源数据");

        int pageNum = PageUtils.getParamToInt(page, CommonConstant.PAGENUM);
        int pageSize = PageUtils.getParamToInt(rows, CommonConstant.PAGESIZE);
        String orderByClause = PageUtils.getOrderByClause(sidx, sord, CommonConstant.ORDERBYCLAUSE);

        return sslCertService.page(remarkName,genericName,beginTime,endTime,pageNum,pageSize,orderByClause);
    }

    /**
     * 上传证书
     * @param name 备注名称
     * @param certificate 证书内容
     * @param privateKey 证书私钥
     * @return
     */
    @PostMapping("/add")
    public ResultMap add(@RequestParam String name,
                         @RequestParam String certificate,
                         @RequestParam String privateKey){

        logger.info("添加证书");
        try{
            return sslCertService.add(name,certificate,privateKey);
        }catch (Exception e){
            logger.error("添加证书异常！",e);
            return new ResultMap(CommonConstant.ERROR,CommonConstant.RESULT_ERROR);
        }
    }

    /**
     * 删除证书
     * @param sslCertificateId 业务主键
     * @return
     */
    @DeleteMapping("/delete")
    public ResultMap delete(@RequestParam String sslCertificateId) {

        logger.info("删除证书");
        try{
            SslCertificate sslCertificate = sslCertService.getById(sslCertificateId);
            if(sslCertificate != null){
                List<Domain> domainList = domainService.getBySslCertificateId(sslCertificateId);
                if(!CollectionUtils.isEmpty(domainList)){
                    return new ResultMap(CommonConstant.ERROR,SslCertConstant.CAN_NOT_DELETE);
                }
                return sslCertService.delete(sslCertificateId);
            }else{
                return new ResultMap(CommonConstant.ERROR,CommonConstant.DATA_NOT_EXISTS);
            }
        }catch (Exception e){
            logger.error("删除证书异常！",e);
            return new ResultMap(CommonConstant.ERROR,CommonConstant.RESULT_ERROR);
        }
    }

    /**
     * 获取证书信息
     * @param sslCertificateId 业务主键
     * @return
     */
    @GetMapping("/get")
    public SslCertificate get(@RequestParam String sslCertificateId){
        logger.info("根据证书编号获取证书");
        return sslCertService.getById(sslCertificateId);
    }
}
