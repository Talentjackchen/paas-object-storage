package com.wondersgroup.cloud.paas.storage.config.scheduler;

import com.wondersgroup.cloud.paas.common.utils.CommonUtils;
import com.wondersgroup.cloud.paas.common.utils.IpUtils;
import com.wondersgroup.cloud.paas.storage.constant.ScheduleConstant;
import com.wondersgroup.cloud.paas.storage.mapper.ScheduleJobLogMapper;
import com.wondersgroup.cloud.paas.storage.model.ScheduleJob;
import com.wondersgroup.cloud.paas.storage.model.ScheduleJobLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * 定时任务工具
 * author : zhangyongzhao
 * createTime : 2019/5/29
 */
@Component
public class ScheduleJobUtils extends QuartzJobBean {

    private ExecutorService service = Executors.newSingleThreadExecutor();
    private static Logger logger = Logger.getLogger(ScheduleJobUtils.class);

    @Value("${spring.application.name}")
    private String projectName;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired(required = false)
    private ScheduleJobLogMapper scheduleJobLogMapper;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap()
                .get(ScheduleConstant.JOB_PARAM_KEY);
        ScheduleJobLog jobLog = new ScheduleJobLog();
        String lockKey = "job_" + projectName + "_" + scheduleJob.getJobId();
        int time = 60 * 1000;
        if (scheduleJob.getInvalidTime() != null && scheduleJob.getInvalidTime() != 0) {
            time = scheduleJob.getInvalidTime();
        }
        boolean isLock = RedisTool.tryGetDistributedLock(jedisCluster, lockKey, lockKey, time);
        if (!isLock) {
            return;
        }

        //数据库保存执行记录
        jobLog.setLogId(CommonUtils.generateId());
        jobLog.setJobId(scheduleJob.getJobId());
        jobLog.setBeanName(scheduleJob.getBeanName());
        jobLog.setMethodName(scheduleJob.getMethodName());
        jobLog.setParams(scheduleJob.getParams());
        jobLog.setIpAddress(IpUtils.getHostIP());
        jobLog.setCreateTime(new Date());
        //任务开始时间
        long startTime = System.currentTimeMillis();
        try {
            //执行任务
            logger.info("任务准备执行，任务ID：" + scheduleJob.getJobId());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(),
                    scheduleJob.getMethodName(), scheduleJob.getParams());
            Future<?> future = service.submit(task);

            future.get();

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            jobLog.setTimes((int) times);
            //任务状态    0：成功    1：失败
            jobLog.setStatus(ScheduleConstant.PAUSE);

            logger.info("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times + "毫秒");
        } catch (Exception e) {
            logger.info("任务执行失败，任务ID：" + scheduleJob.getJobId());

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            jobLog.setTimes((int) times);

            //任务状态    0：成功    1：失败
            jobLog.setStatus(ScheduleConstant.NORMAL);
            jobLog.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            if (jobLog != null) {
                scheduleJobLogMapper.insertSelective(jobLog);
            }
            RedisTool.releaseDistributedLock(jedisCluster, lockKey, lockKey);
        }
    }
}



