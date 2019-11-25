package com.wondersgroup.cloud.paas.storage.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Created by shimingjie on 2019/7/29.
 */
@Aspect
@Component
public class ControllerLogAspect {
    private Logger logger = Logger.getLogger(getClass());

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Value("${spring.aop.log}")
    private boolean enable;

    @Pointcut("execution(public * com.wondersgroup.cloud.paas.storage.controller..*.*(..))")
    public void controllerLog(){}

    @Before("controllerLog()")
    public void doBefore(JoinPoint joinPoint)  {
        if(!enable){
            return;
        }
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        StringBuilder headerInfo = new StringBuilder("HEADERS : ");
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String header = headerNames.nextElement();
            headerInfo.append(header).append("=").append(request.getHeader(header)).append(";");
        }
        logger.info(headerInfo.toString());
        startTime.set(System.currentTimeMillis());

    }

    @AfterReturning(returning = "ret", pointcut = "controllerLog()")
    public void doAfterReturning(Object ret)  {
        if(!enable){
            return;
        }
        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()) + "ms");
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
    }
}
