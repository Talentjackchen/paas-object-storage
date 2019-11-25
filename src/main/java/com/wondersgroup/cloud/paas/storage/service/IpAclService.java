package com.wondersgroup.cloud.paas.storage.service;

import com.wondersgroup.cloud.paas.storage.model.IpAcl;

import java.util.List;

/**
 * @author chenlong
 */
public interface IpAclService {

    /**
     * 加载缓存
     */
    void load();

    /**
     * 创建Ip防盗链
     * @param bucketId 空间Id
     * @param ipList 白名单地址
     */
    void create(String bucketId, List<String> ipList) throws Exception;

    /**
     * 更新Ip防盗链
     * @param bucketId 空间Id
     * @param ipList 白名单地址
     */
    void update(String bucketId, List<String> ipList) throws Exception;

    /**
     * 删除Ip防盗链
     * @param bucketId 空间Id
     */
    void delete(String bucketId);

    /**
     * 根据空间Id查询Ip防盗链
     * @param bucketId 空间Id
     * @return
     */
    IpAcl queryByBucketId(String bucketId);
}
