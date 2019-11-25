package com.wondersgroup.cloud.paas.storage.service;

/**
 * @author chenlong
 * 防盗链服务
 */
public interface AclService {
    /**
     * 时间戳防盗链
     * @param domainId 域名Id
     * @param path 访问路径
     * @param expire 失效时间
     * @param sign 签名
     * @return
     */
    boolean validTimestamp(String domainId,String path,String expire,String sign) throws Exception;

    /**
     * IP防盗链
     * @param domainId 域名Id
     * @param clientIp 客户机Ip地址
     * @return
     */
    boolean validIp(String domainId,String clientIp);

    /**
     * Referer(来源引用)防盗链
     * @param domainId 域名Id
     * @param referer 客户机地址来源
     * @return
     */
    boolean validReferer(String domainId,String referer);
}
