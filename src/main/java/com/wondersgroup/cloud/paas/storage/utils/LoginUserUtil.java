package com.wondersgroup.cloud.paas.storage.utils;

import com.wondersgroup.cloud.paas.common.constant.AuthConstant;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.common.theadlocal.AuthorizationThreadLocal;
import com.wondersgroup.cloud.paas.common.utils.UserUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * describe : 用户工具类
 * create_by : zhangyongzhao
 * create_time : 2019/9/3
 */
@Component
public class LoginUserUtil {
    /**
     * 越权判断：通过 projectId
     * true 通过 ; false 不通过
     *
     * @param projectId
     * @return
     */
    public static boolean StrideAcrossJudgeByProjectId(String projectId) throws Exception {
        String token = (String) AuthorizationThreadLocal.getCurrent().get();
        if (StringUtils.isBlank(token)) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                token = request.getHeader(AuthConstant.AUTHORIZATION);
            }
        }

        ResultMap resultMap = UserUtils.getLoginUser(token);
        if (resultMap != null && resultMap.isSuccess()) {
            Map userMap = (Map) resultMap.getResult();
            if (userMap != null) {
                List<Map> projects = (List<Map>) userMap.get("projects");
                for (Map projectMap : projects) {
                    String remoteProjectId = (String) projectMap.get("projectId");
                    if (projectId.equals(remoteProjectId)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
