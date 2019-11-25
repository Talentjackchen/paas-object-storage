package com.wondersgroup.cloud.paas.storage.service;

import com.wondersgroup.cloud.paas.common.exception.BusinessException;
import com.wondersgroup.cloud.paas.storage.pojo.StatisticsRequestPojo;
import com.wondersgroup.cloud.paas.storage.pojo.StatisticsStoragePojo;

import java.util.HashMap;
import java.util.List;

/**
 * 统计服务
 * zhangyongzhao
 */

public interface StatisticsService {

    /**
     * 获取get请求次数
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @param bucketId 主键
     * @param granularity 时间颗粒度
     * @param domain 空间域名
     * @param storageType 存储类型
     * @param region 存储区域
     * @return
     */
    List<StatisticsRequestPojo> getGETRequestsNumberByDay(String beginDate, String endDate, String bucketId, String granularity,
                                                     String domain, String storageType, String region) throws BusinessException;

    int getGETRequestsNumberByMonth(String beginDate, String endDate, String bucketId, String domain, String storageType, String region) throws BusinessException;

    /**
     * 获取put请求次数
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @param bucketId 主键
     * @param granularity 时间颗粒度
     * @param storageType 存储类型
     * @param region 存储区域
     * @return
     */
    List<StatisticsRequestPojo> getPUTRequestsNumberByDay(String beginDate, String endDate, String bucketId, String granularity,
                                                     String storageType, String region) throws BusinessException;

    int getPUTRequestsNumberByMonth(String beginDate, String endDate, String bucketId, String storageType, String region) throws BusinessException;

    /**
     * 获取本地数据路中文件类型的大小和数量
     * @param fileType
     * @param projectId
     * @param bucketId
     * @return
     */
    List<HashMap<String,Object>> queryCountAndSize(String fileType,String projectId,String bucketId);

    /**
     * 根据bucketId查询文件数量和文件大小
     *
     * @param bucketId 空间ID
     * @return
     */
    HashMap<String, Object> getCountAndSizeByBucketId(String bucketId);

    /**
     * 获取七牛云上标准存储量
     *
     * @param begin    开始时间
     * @param end      结束时间
     * @param bucketId 空间ID
     * @return 存储量
     * @throws Exception
     */
    StatisticsStoragePojo getSpaceStorage(String begin, String end, String bucketId) throws Exception;

    /**
     * 获取七牛云上低频存储量
     *
     * @param begin    开始时间
     * @param end      结束时间
     * @param bucketId 空间ID
     * @return 存储量
     * @throws Exception
     */
    StatisticsStoragePojo getSpaceLineStorage(String begin, String end, String bucketId, String noPreDel, String onlyPreDel) throws Exception;


}
