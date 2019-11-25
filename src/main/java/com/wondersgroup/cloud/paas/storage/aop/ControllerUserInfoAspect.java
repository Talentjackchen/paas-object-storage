package com.wondersgroup.cloud.paas.storage.aop;

import com.wondersgroup.cloud.paas.common.annotation.UserInfoValidation;
import com.wondersgroup.cloud.paas.common.constant.AuthConstant;
import com.wondersgroup.cloud.paas.common.constant.UserInfoValidationConstant;
import com.wondersgroup.cloud.paas.common.enums.ParamTypeEnum;
import com.wondersgroup.cloud.paas.common.enums.UserInfoValidationEnum;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.common.utils.UserUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author chenlong
 */
@Aspect
@Component
public class ControllerUserInfoAspect {

    private static Logger logger = Logger.getLogger(ControllerUserInfoAspect.class);

    private String thirdAccessFlag = "openapi";

    @Pointcut("execution(public * com.wondersgroup.cloud.paas.storage.controller..*.*(..))")
    public void controllerUserInfoValidation() {
    }

    @Around("controllerUserInfoValidation()")
    public ResultMap doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        /* OpenAPI不进行内部越权控制 */
        if (request.getRequestURI().contains(thirdAccessFlag)) {
            return (ResultMap) joinPoint.proceed();
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        UserInfoValidation userInfoValidation = method.getDeclaredAnnotation(UserInfoValidation.class);
        if (userInfoValidation != null) {
            String token = request.getHeader(AuthConstant.AUTHORIZATION);
            ResultMap resultMap = UserUtils.getLoginUser(token);
            if (resultMap == null) {
                logger.error(UserInfoValidationConstant.GET_CURRENT_USER_INFO_FAIL_MSG);
                return new ResultMap(UserInfoValidationConstant.OPERATE_OUT_CONTROL_CODE, UserInfoValidationConstant.OPERATE_OUT_CONTROL_MSG);
            }
            Map userMap = (Map) resultMap.getResult();
            ParamTypeEnum paramType = userInfoValidation.paramType();
            String[] paramNames = userInfoValidation.paramNames();
            UserInfoValidationEnum[] userInfoValidationEnums = userInfoValidation.userInfos();
            boolean pass = true;
            for (int i = 0; i < paramNames.length; i++) {
                String paramValue = "";
                String paramName = paramNames[i];
                if (paramType.equals(ParamTypeEnum.STRING)) {
                    paramValue = request.getParameter(paramName);
                } else if (paramType.equals(ParamTypeEnum.MAP)) {
                    Map map = (Map) joinPoint.getArgs()[0];
                    paramValue = (String) map.get(paramName);
                } else if (paramType.equals(ParamTypeEnum.ENTITY)) {
                    try {
                        Object object = joinPoint.getArgs()[0];
                        Field[] fields = object.getClass().getDeclaredFields();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            if (paramName.equals(field.getName())) {
                                paramValue = (String) field.get(object);
                                break;
                            }
                        }
                    } catch (Exception ex) {
                        logger.error(UserInfoValidationConstant.OBJECT_PROPERTIES_FAIL_MSG);
                        pass = false;
                    }
                }

                if (StringUtils.isNotBlank(paramValue)) {
                    if (userInfoValidationEnums[i].equals(UserInfoValidationEnum.ACCOUNT)) {
                        /* 验证用户账号 */
                        if (!paramValue.equals(userMap.get("userId"))) {
                            logger.error(UserInfoValidationConstant.ACCOUNT_VALID_FAIL_MSG);
                            pass = false;
                            break;
                        }
                    } else if (userInfoValidationEnums[i].equals(UserInfoValidationEnum.PROJECT)) {
                        /* 验证项目 */
                        List<Map> projects = (List<Map>) userMap.get("projects");
                        pass = false;
                        for (Map projectMap : projects) {
                            String projectId = (String) projectMap.get("projectId");
                            if (paramValue.equals(projectId)) {
                                pass = true;
                            }
                        }
                        if (pass == false) {
                            logger.error(UserInfoValidationConstant.PROJECT_VALID_FAIL_MSG);
                            break;
                        }
                    }
                } else {
                    logger.error(UserInfoValidationConstant.USER_ACCESS_FAIL_MSG);
                    pass = false;
                    break;
                }
            }

            if (pass == false) {
                return new ResultMap(UserInfoValidationConstant.OPERATE_OUT_CONTROL_CODE, UserInfoValidationConstant.OPERATE_OUT_CONTROL_MSG);
            }
        }
        return (ResultMap) joinPoint.proceed();
    }
}
