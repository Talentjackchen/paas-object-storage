package com.wondersgroup.cloud.paas.storage.exception;

import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.common.utils.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Created by shimingjie on 2019/7/16.
 * 统一的异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultMap defaultErrorHandler(Exception e) {
        String msg = ExceptionUtils.getExceptionStackTrace(e, null);
        logger.error("", msg);
        ResultMap resultMap = new ResultMap();
        resultMap.setMsg(msg);
        if (e instanceof NoHandlerFoundException) {
            resultMap.setCode(404);
        } else {
            resultMap.setCode(500);
        }
        return resultMap;
    }
}
