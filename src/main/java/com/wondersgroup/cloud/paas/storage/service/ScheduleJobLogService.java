package com.wondersgroup.cloud.paas.storage.service;

import com.github.pagehelper.PageInfo;


/**
 * 定时任务日志
 * author : zhangyongzhao
 * createTime : 2019/5/29
 */
public interface ScheduleJobLogService {

    /**
     * 分页查询
     *
     * @return
     */
    PageInfo page(String jobId, Integer pageNum, Integer pageSize, String orderByClause);

}


