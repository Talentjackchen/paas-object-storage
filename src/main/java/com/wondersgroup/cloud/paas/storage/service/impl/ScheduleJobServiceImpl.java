package com.wondersgroup.cloud.paas.storage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wondersgroup.cloud.paas.storage.config.scheduler.ScheduleUtils;
import com.wondersgroup.cloud.paas.storage.mapper.ScheduleJobMapper;
import com.wondersgroup.cloud.paas.storage.mapper.ext.ScheduleJobExtandMapper;
import com.wondersgroup.cloud.paas.storage.model.ScheduleJob;
import com.wondersgroup.cloud.paas.storage.model.ScheduleJobExample;
import com.wondersgroup.cloud.paas.storage.service.ScheduleJobService;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 定时任务
 * author : zhangyongzhao
 * createTime : 2019/5/29
 */
@Service("scheduleJobService")
@Transactional(rollbackFor = Exception.class)
public class ScheduleJobServiceImpl implements ScheduleJobService {

    @Autowired
    private Scheduler scheduler;

    @Autowired(required = false)
    private ScheduleJobMapper scheduleJobMapper;

    @Autowired(required = false)
    private ScheduleJobExtandMapper scheduleJobExtandMapper;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        ScheduleJobExample scheduleJobExample = new ScheduleJobExample();

        List<ScheduleJob> scheduleJobList = scheduleJobMapper.selectByExample(scheduleJobExample);


        for (ScheduleJob scheduleJob : scheduleJobList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            //如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    @Override
    public PageInfo page(String beanName, Integer pageNum, Integer pageSize, String orderByClause) {
        PageHelper.startPage(pageNum, pageSize);
        ScheduleJobExample example = new ScheduleJobExample();
        ScheduleJobExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(beanName)) {
            criteria.andBeanNameLike(beanName);
        }

        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        List<ScheduleJob> scheduleJobs = scheduleJobMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo<>(scheduleJobs);
        return pageInfo;
    }



    @Override
    public void update(ScheduleJob scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
        scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
    }


    @Override
    public int updateBatch(List<String> jobIds, String status) {
        return scheduleJobExtandMapper.updateBatch(jobIds, status);
    }

}


