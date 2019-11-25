package com.wondersgroup.cloud.paas.storage.service;

import com.github.pagehelper.PageInfo;
import com.wondersgroup.cloud.paas.storage.model.ScheduleJob;

import java.util.List;

/**
 * 定时任务
 * author : zhangyongzhao
 * createTime : 2019/5/29
 */
public interface ScheduleJobService {

    PageInfo page(String jobId, Integer pageNum, Integer pageSize, String orderByClause);


    /**
     * 更新定时任务
     */
    void update(ScheduleJob scheduleJob);


    /**
     * 批量更新定时任务状态
     */
    int updateBatch(List<String> jobIds, String status);

}


