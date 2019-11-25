package com.wondersgroup.cloud.paas.storage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wondersgroup.cloud.paas.storage.mapper.ScheduleJobLogMapper;
import com.wondersgroup.cloud.paas.storage.model.ScheduleJobLog;
import com.wondersgroup.cloud.paas.storage.model.ScheduleJobLogExample;
import com.wondersgroup.cloud.paas.storage.service.ScheduleJobLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 日志查询
 * author : zhangyongzhao
 * createTime : 2019/5/29
 */
@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {
    @Autowired(required = false)
    private ScheduleJobLogMapper scheduleJobLogMapper;

    @Override
    public PageInfo page(String jobId, Integer pageNum, Integer pageSize, String orderByClause) {
        PageHelper.startPage(pageNum, pageSize);
        ScheduleJobLogExample example = new ScheduleJobLogExample();
        ScheduleJobLogExample.Criteria criteria = example.createCriteria();
        criteria.andJobIdEqualTo(jobId);
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        List<ScheduleJobLog> scheduleJobLogs = scheduleJobLogMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo<>(scheduleJobLogs);
        return pageInfo;
    }

}


