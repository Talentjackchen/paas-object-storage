package com.wondersgroup.cloud.paas.storage.config.scheduler;

import com.wondersgroup.cloud.paas.common.context.ApplicationContextProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 执行定时任务实现
 * author : zhangyongzhao
 * createTime : 2019/5/29
 */
public class ScheduleRunnable implements Runnable {

    private static Logger logger = Logger.getLogger(ScheduleRunnable.class);

    private Object target;
    private Method method;
    private String params;

    public ScheduleRunnable(String beanName, String methodName, String params) throws NoSuchMethodException, SecurityException {
        this.target = ApplicationContextProvider.getBeanByName(beanName);
        this.params = params;

        if (StringUtils.isNotBlank(params)) {
            this.method = target.getClass().getDeclaredMethod(methodName, String.class);
        } else {
            this.method = target.getClass().getDeclaredMethod(methodName);
        }
    }

    @Override
    public void run() {
        try {
            ReflectionUtils.makeAccessible(method);
                if (StringUtils.isNotBlank(params)) {
                    method.invoke(target, params);
                } else {
                    method.invoke(target);
                }
        } catch (Exception e) {
            logger.error("执行定时任务失败", e);
            throw new RuntimeException(e);
        }
    }
}


