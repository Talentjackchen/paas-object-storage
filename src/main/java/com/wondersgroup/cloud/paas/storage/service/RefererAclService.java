package com.wondersgroup.cloud.paas.storage.service;

import com.wondersgroup.cloud.paas.storage.model.RefererAcl;

/**
 * @author chenlong
 * Refere服务接口
 */
public interface RefererAclService {
    /**
     * 加载缓存
     */
    void load();

    /**
     * 创建referer防盗链
     * @param bucketId 空间Id
     * @param refererAcl 白名单referer对象
     */
    void create(String bucketId, RefererAcl refererAcl) throws Exception;

    /**
     * 更新referer防盗链
     * @param bucketId 空间Id
     * @param refererAcl 白名单referer对象
     */
    void update(String bucketId, RefererAcl refererAcl) throws Exception;

    /**
     * 删除referer防盗链
     * @param bucketId 空间Id
     */
    void delete(String bucketId);

    /**
     * 根据空间Id查询referer防盗链
     * @param bucketId 空间Id
     * @return
     */
    RefererAcl queryByBucketId(String bucketId);
}
