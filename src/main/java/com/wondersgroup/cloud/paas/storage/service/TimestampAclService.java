package com.wondersgroup.cloud.paas.storage.service;


import com.wondersgroup.cloud.paas.storage.model.TimestampAcl;

/**
 * @author chenlong
 */
public interface TimestampAclService {

    /**
     * 加载缓存
     */
    void load();

    /**
     * 创建时间戳防盗链
     * @param bucketId 空间Id
     * @param mainKey 主要key
     * @param spareKey 备用key
     */
    void create(String bucketId, String mainKey, String spareKey);

    /**
     * 更新时间戳防盗链
     * @param bucketId 空间Id
     * @param mainKey 主要key
     * @param spareKey 备用key
     */
    void update(String bucketId, String mainKey, String spareKey);

    /**
     * 删除时间戳防盗链
     * @param bucketId 空间Id
     */
    void delete(String bucketId);

    /**
     * 根据空间Id查询时间戳防盗链
     * @param bucketId
     * @return
     */
    TimestampAcl queryByBucketId(String bucketId);
}
