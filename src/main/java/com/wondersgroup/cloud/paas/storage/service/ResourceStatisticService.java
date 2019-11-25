package com.wondersgroup.cloud.paas.storage.service;

import com.wondersgroup.cloud.paas.storage.model.Resource;
import com.wondersgroup.cloud.paas.storage.model.ResourceStatistic;

import java.util.Date;
import java.util.List;

public interface ResourceStatisticService {
    /**
     * 资源计费项
     * @param resource 资源
     * @param ifAdd true-增加  false-删除
     * @return
     * @throws Exception
     */
    int add(Resource resource, boolean ifAdd) throws Exception;

    /**
     * 根据起止时间查询资源统计数据
     * @param projectId
     * @param beginDate
     * @param endDate
     * @param type
     * @return
     */
    List<ResourceStatistic> getListByDate(String projectId, Date beginDate, Date endDate, String type);

    /**
     * 获取60天内上传并且删除的资源
     *
     * @param projectId
     * @param date
     * @param type     资源类型（低频，标频）
     * @return
     */
    List<ResourceStatistic> getDeleteListForSixtyDate(String projectId, Date date, String type);

    /**
     * 统计截至日期项目空间总存储量
     *
     * @param projectId 项目Id
     * @param date      截至日期
     * @param type      资源类型（低频，标频）
     * @return
     */
    long getPreStorageByDate(String projectId, Date date, String type);
}
