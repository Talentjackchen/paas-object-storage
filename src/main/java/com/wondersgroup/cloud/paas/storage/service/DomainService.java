package com.wondersgroup.cloud.paas.storage.service;

import com.github.pagehelper.PageInfo;
import com.wondersgroup.cloud.paas.storage.model.Domain;
import com.wondersgroup.cloud.paas.storage.pojo.DomainInfo;

import java.util.List;
import java.util.Map;

/**
 * description : 域名管理
 * create by : zhangyongzhao
 * create time : 2019/5/5
 */
public interface DomainService {

    /**
     * 创建域名
     * @param name     域名名称
     * @param protocol 协议
     * @param platform 平台类型
     * @param bucketId bucketId
     */
    void create(String name,String protocol,String platform,String bucketId)throws Exception;

    /**
     * 删除域名
     * @param domainId 域名ID
     * @throws Exception
     */
    void delete(String domainId) throws Exception;

    /**
     * 启用域名
     *
     * @param domainId 域名ID
     */
    void enable(String domainId) throws Exception ;

    /**
     * 停用域名
     *
     * @param domainId 域名ID
     */
    void stop(String domainId) throws Exception ;

    //获取七牛云上域名信息
    DomainInfo getQiuNiuDomain(String name);

    /**
     * 分页查询
     * @param name 域名名称
     * @param protocol 协议
     * @param platform 平台类型
     * @param bucketId bucketID
     * @param pageNum 当前页
     * @param pageSize 显示条数
     * @param orderByClause 排序字段
     * @return
     */
    PageInfo<Domain> page(String name,String protocol,String platform,String bucketId,
                          Integer pageNum, Integer pageSize, String orderByClause);

    /**
     * http升级为https
     *
     * @param domainId 域名ID
     */
    void upgrade(String domainId) throws Exception;

    /**
     * https降级为http
     *
     * @param domainId 域名ID
     * @throws Exception
     */
    void downgrade(String domainId) throws Exception;

    /**
     * 详细查询数据
     * @param domainId
     * @return
     */
    DomainInfo getById(String domainId) throws Exception;

    /**
     * 修改配置
     * @param domainId 域名ID
     * @param bucketId 空间Id
     */
    void edit(String domainId,String bucketId) throws Exception;

    /**
     * 查询证书使用情况
     * @param sslCertificateId
     * @return
     */
    List<Domain> getBySslCertificateId(String sslCertificateId);

    /**
     * 绑定空闲域名
     *
     * @param name     域名
     * @param bucketId 空间ID
     * @throws Exception
     */
    void bindFreeDomain(String name, String bucketId) throws Exception;

    /**
     * 释放绑定的域名
     *
     * @param bucketId
     * @throws Exception
     */
    void releaseDomain(String bucketId);

    /**
     * 获取空闲的域名（多条取第一条）
     * @return
     */
    Domain getFreeDomain();

    /**
     * 获取所有有效的域名信息
     *
     * @return 键是域名ID，值为对象
     */
    Map<String, Domain> getMap();

}
