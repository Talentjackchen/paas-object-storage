package com.wondersgroup.cloud.paas.storage.service;

import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.storage.model.SslCertificate;

public interface SslCertService {

    /**
     * SSL证书分页查询
     * @param remarkName 备注名
     * @param genericName 通用名称
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param pageNum 页
     * @param pageSize 页大小
     * @param orderByClause 排序
     * @return
     */
    ResultMap page(String remarkName,String genericName, String beginTime, String endTime, int pageNum, int pageSize, String orderByClause);

    /**
     * 上传证书
     * @param name 备注名称
     * @param ca 证书内容
     * @param pri 证书私钥
     * @return
     * @throws Exception
     */
    ResultMap add(String name, String ca, String pri) throws Exception;

    /**
     * 删除证书
     * @param sslCertificateId 业务主键
     * @return
     * @throws Exception
     */
    ResultMap delete(String sslCertificateId) throws Exception;

    /**
     * 获取证书记录信息
     * @param sslCertificateId
     * @return
     */
    SslCertificate getById(String sslCertificateId);

    /**
     * 依据通用名称加载7天后不过期的证书
     * @param genericName 通用名称
     * @return
     */
    SslCertificate getByGenericName(String genericName);
}
