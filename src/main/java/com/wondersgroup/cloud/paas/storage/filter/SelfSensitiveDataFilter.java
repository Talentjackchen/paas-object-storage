package com.wondersgroup.cloud.paas.storage.filter;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.enums.RequestFromEnum;
import com.wondersgroup.cloud.paas.common.filter.SensitiveDataFilter;
import com.wondersgroup.cloud.paas.common.properties.ApplicationProperties;
import org.apache.commons.lang.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author chenlong
 * @Comment 解决浏览器端以明文方式传递（头部不传递Explore），不验证通过请求
 */
public class SelfSensitiveDataFilter extends SensitiveDataFilter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (ApplicationProperties.isParamsEncrypt) {
            String from = ((HttpServletRequest) request).getHeader(REQUEST_FROM);
            String urlFrom = request.getParameter(REQUEST_FROM);
            /* TODO 微服务间调用请求头为Internal时是否需要进行控制，一旦浏览器端传递Internal请求头，同样会面临明文问题*/
            if (RequestFromEnum.EXPLORE.value().equals(from)) {
                String privateKey = ((HttpServletRequest) request).getHeader(KEYWORD);
                String iv = ((HttpServletRequest) request).getHeader(IV);
                if (StringUtils.isBlank(privateKey) || StringUtils.isBlank(iv)) {
                    responseOut(CommonConstant.REQUEST_HEADER_ERROR_CODE, CommonConstant.REQUEST_HEADER_ERROR_MSG, response);
                    return;
                }
            } else if (RequestFromEnum.EXPLORE.value().equals(urlFrom)) {
                String fileSensitive = request.getParameter(FILE_SENSITIVE);
                if (StringUtils.isBlank(fileSensitive)) {
                    responseOut(CommonConstant.REQUEST_HEADER_ERROR_CODE, CommonConstant.REQUEST_HEADER_ERROR_MSG, response);
                    return;
                }
            }
        }
        super.doFilter(request, response, chain);
    }
}
