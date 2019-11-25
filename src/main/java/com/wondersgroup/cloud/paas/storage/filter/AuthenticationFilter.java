package com.wondersgroup.cloud.paas.storage.filter;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.exception.BusinessException;
import com.wondersgroup.cloud.paas.common.filter.CommonFilter;
import com.wondersgroup.cloud.paas.common.utils.CollectionUtils;
import com.wondersgroup.cloud.paas.common.utils.DateUtils;
import com.wondersgroup.cloud.paas.common.utils.MD5Utils;
import com.wondersgroup.cloud.paas.storage.config.PropertiesConfig;
import com.wondersgroup.cloud.paas.storage.constant.BucketConstant;
import com.wondersgroup.cloud.paas.storage.constant.IpAclConstant;
import com.wondersgroup.cloud.paas.storage.constant.ProjectAuthorityConstant;
import com.wondersgroup.cloud.paas.storage.constant.RefererAclConstant;
import com.wondersgroup.cloud.paas.storage.enums.BucketTypeEnum;
import com.wondersgroup.cloud.paas.storage.model.Bucket;
import com.wondersgroup.cloud.paas.storage.model.ProjectAuthority;
import com.wondersgroup.cloud.paas.storage.service.AclService;
import com.wondersgroup.cloud.paas.storage.service.BucketService;
import com.wondersgroup.cloud.paas.storage.service.ProjectAuthorityService;
import com.wondersgroup.cloud.paas.storage.utils.AuthUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chenlong
 * 认证过滤器
 */
public class AuthenticationFilter extends CommonFilter implements Filter {

    private static Logger logger = Logger.getLogger(AuthenticationFilter.class);

    private List<String> defaultAddress = Stream.of("127.0.0.1","0:0:0:0:0:0:0:1").collect(Collectors.toList());

    private static final String  UNKNOWN = "unknown";

    private ProjectAuthorityService projectAuthorityService;

    private BucketService bucketService;

    private AclService aclService;

    private final String OPEN_API_URL = "openapi";
    private final String HEALTH_CHECK_URL = "/actuator/info";
    private final String LINK_DOWNLOAD_URL = "link/resource/download";
    private final String LINK_PREVIEW_URL = "link/resource/preview";

    @Override
    public void init(FilterConfig filterConfig) {
        ServletContext sc = filterConfig.getServletContext();
        AbstractApplicationContext cxt = (AbstractApplicationContext) WebApplicationContextUtils.getWebApplicationContext(sc);
        if(cxt != null && cxt.getBean(ProjectAuthorityService.class) != null && projectAuthorityService == null){
            projectAuthorityService = cxt.getBean(ProjectAuthorityService.class);
        }

        if(cxt != null && cxt.getBean(BucketService.class) != null && bucketService == null){
            bucketService = cxt.getBean(BucketService.class);
        }

        if(cxt != null && cxt.getBean(AclService.class) != null && aclService == null){
            aclService = cxt.getBean(AclService.class);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        //忽略健康检查的请求
        if(httpRequest.getRequestURI().endsWith(HEALTH_CHECK_URL)){
            chain.doFilter(request, response);
            return;
        }

        /* 三方访问openapi开头的API */
        if (httpRequest.getRequestURI().contains(OPEN_API_URL)) {
            String bucketId = httpRequest.getHeader("bucketId");
            String timestamp = httpRequest.getHeader("timestamp");
            String clientAuthentication = httpRequest.getHeader("authentication");

            /* 空间Id不可为空 */
            if (bucketId == null) {
                responseOut(BucketConstant.ID_EMPTY_CODE, BucketConstant.ID_EMPTY_MSG, response);
                return;
            }

            if(DateUtils.compareLongTime(Long.valueOf(timestamp),System.currentTimeMillis() - CommonConstant.REQUEST_EXPIRE_TIME)){
                List<ProjectAuthority> authorityList = projectAuthorityService.getByBucketId(bucketId);
                if (CollectionUtils.isNotEmpty(authorityList)) {
                    boolean flag = false;
                    /* 保证切换密钥时不影响使用 */
                    for (ProjectAuthority authority : authorityList) {
                        /**
                         * 待加密格式[accessKey|bucketId|timeStamp|secretKey]
                         */
                        String headerStr = "[" + authority.getAccessKey() + "|" + bucketId + "|" + timestamp + "|" + authority.getSecretKey() + "]";
                        String serverAuthentication;
                        try {
                            serverAuthentication = MD5Utils.encode(headerStr);
                        } catch (Exception ex) {
                            responseOut(CommonConstant.MD5_CODE_FAIL, CommonConstant.MD5_MSG_FAIL, response);
                            return;
                        }

                        if (serverAuthentication.equals(clientAuthentication)) {
                            flag = true;
                            break;
                        }
                    }

                    if (flag) {
                        /* Auth2.0认证机制开启 */
                        if (PropertiesConfig.AuthEnable) {
                            doClientCredentialFilter(chain, httpRequest, response);
                        } else {
                            chain.doFilter(request, response);
                        }
                    } else {
                        responseOut(CommonConstant.AUTHENTICATION_CODE_FAIL, CommonConstant.AUTHENTICATION_MSG_FAIL, response);
                    }
                }else{
                    responseOut(ProjectAuthorityConstant.NOT_FOUND_CODE_FAIL ,ProjectAuthorityConstant.NOT_FOUND_MSG_FAIL,response);
                }
            }else{
                responseOut(CommonConstant.REQUEST_EXPIRE_CODE_FAIL,CommonConstant.REQUEST_EXPIRE_MSG_FAIL,response);
            }
        } else if (httpRequest.getRequestURI().contains(LINK_DOWNLOAD_URL) || httpRequest.getRequestURI().contains(LINK_PREVIEW_URL)) {
            /* 外链接访问路径 link/resource/download link/resource/preview 需要进行防盗链验证*/
            if (downloadAndPreviewOpenBucketResource(httpRequest, response)) {
                /* Auth2.0认证机制开启 */
                if (PropertiesConfig.AuthEnable) {
                    doClientCredentialFilter(chain, httpRequest, response);
                } else {
                    chain.doFilter(request, response);
                }
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 公有空间对外下载或预览不需要进行authorization验证
     * @param request
     * @param response
     * @return
     */
    private boolean downloadAndPreviewOpenBucketResource(HttpServletRequest request,ServletResponse response)throws IOException{
        String bucketId = request.getParameter("bucketId");
        Bucket bucket = bucketService.getById(bucketId);
        if (bucket != null) {
            if (bucket.getType().equals(BucketTypeEnum.PUBLIC.value())) {
                /* IP防盗链 */
                if (!validIpACL(bucketId, request)) {
                    responseOut(IpAclConstant.WHITE_REFUSED_CODE, IpAclConstant.WHITE_REFUSED_MSG, response);
                    return false;
                }

                /* Referer防盗链*/
                if (!validRefererACL(bucketId, request)) {
                    responseOut(RefererAclConstant.WHITE_REFUSED_CODE, RefererAclConstant.WHITE_REFUSED_MSG, response);
                    return false;
                }
                /* 时间戳防盗链*/
                try {
                    if (!validTimeACL(bucketId, request)) {
                        return false;
                    }
                } catch (Exception ex) {
                    if (ex instanceof BusinessException) {
                        int code = ((BusinessException) ex).getResultMap().getCode();
                        String msg = ((BusinessException) ex).getResultMap().getMsg();
                        responseOut(code, msg, response);
                    } else {
                        responseOut(CommonConstant.ERROR, CommonConstant.RESULT_ERROR, response);
                    }
                    logger.error(CommonConstant.RESULT_ERROR, ex);
                    return false;
                }
                return true;
            } else {
                responseOut(BucketConstant.BUCKET_PRIVATE_REJECT_CODE, BucketConstant.BUCKET_PRIVATE_REJECT_MSG, response);
                return false;
            }
        } else {
            responseOut(BucketConstant.BUCKET_NOT_EXIST_CODE, BucketConstant.BUCKET_NOT_EXIST_MSG, response);
            return false;
        }
    }

    /**
     * IP防盗链
     * @param bucketId
     * @param servletRequest
     * @return
     * @throws UnknownHostException
     */
    private boolean validIpACL(String bucketId, ServletRequest servletRequest) throws UnknownHostException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String clientIp = request.getHeader("X-Forwarded-For");
        if (org.apache.commons.lang.StringUtils.isEmpty(clientIp) || UNKNOWN.equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }

        if (org.apache.commons.lang.StringUtils.isEmpty(clientIp) || UNKNOWN.equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }

        if (org.apache.commons.lang.StringUtils.isEmpty(clientIp) || UNKNOWN.equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("HTTP_CLIENT_IP");
        }

        if (org.apache.commons.lang.StringUtils.isEmpty(clientIp) || UNKNOWN.equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (StringUtils.isEmpty(clientIp) || UNKNOWN.equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }

        if (defaultAddress.contains(clientIp)) {
            clientIp = InetAddress.getLocalHost().getHostAddress();
        }

        return aclService.validIp(bucketId, clientIp);
    }

    /**
     * Referer防盗链
     * @param bucketId
     * @param servletRequest
     * @return
     */
    private boolean validRefererACL(String bucketId, ServletRequest servletRequest) {
        String referer = ((HttpServletRequest) servletRequest).getHeader("referer");
        return aclService.validReferer(bucketId, referer);
    }

    /**
     * 时间戳防盗链
     * @param bucketId
     * @param servletRequest
     * @return
     * @throws Exception
     */
    private boolean validTimeACL(String bucketId, ServletRequest servletRequest) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getParameter("token");
        String expire = request.getParameter("expire");
        String resourceName = request.getParameter("key");
        String path = request.getRequestURI();
        path = String.format("%s?expire=%s&bucketId=%s&key=%s", path, expire, bucketId, resourceName);
        return aclService.validTimestamp(bucketId, path, expire, token);
    }

    /**
     * 客户端凭证跳转
     *
     * @param chain
     * @param httpRequest
     * @param response
     * @throws Exception
     */
    private void doClientCredentialFilter(FilterChain chain, HttpServletRequest httpRequest, ServletResponse response) throws IOException, ServletException {
        AuthenticationHttpServletRequest authenticationHttpServletRequest = new AuthenticationHttpServletRequest(httpRequest);
        String authorization = AuthUtils.getClientCredentialAccessTokenFromCache();
        authenticationHttpServletRequest.addHeader("Authorization", authorization);
        chain.doFilter(authenticationHttpServletRequest, response);
    }
}

