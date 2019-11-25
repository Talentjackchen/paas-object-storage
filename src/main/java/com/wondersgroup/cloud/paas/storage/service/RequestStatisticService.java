package com.wondersgroup.cloud.paas.storage.service;

import com.wondersgroup.cloud.paas.storage.model.Bucket;

import java.util.Date;

/**
 * @author chenlong
 */
public interface RequestStatisticService {
    /**
     * 获取空间Get请求或者Put/Delete请求数量
     * @param bucketId
     * @param beginDate
     * @param endDate
     * @param type get put/delete
     * @param storageType 低频/标频
     * @return
     */
    int getRequestStatisticAmount(String bucketId, Date beginDate, Date endDate, String type, String storageType);

    /**
     * 获取删除空间的低频与标频转换次数
     *
     * @param bucketId
     * @param beginDate
     * @param endDate
     * @return
     */
    int getExchangeStatisticAmount(String bucketId, Date beginDate, Date endDate);

    /**
     * 保存删除空间的低频与标频转换的次数
     *
     * @param bucket
     */
    void saveExchangeAmount(Bucket bucket);

    /**
     * 实时获取空间低频与标频转换次数
     *
     * @param buckteName
     * @param beginDate
     * @param endDate
     * @return
     */
    int getRealTimeExchangeStatisticAmount(String buckteName, Date beginDate, Date endDate);
}
