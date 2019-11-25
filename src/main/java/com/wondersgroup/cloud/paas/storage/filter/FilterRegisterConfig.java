package com.wondersgroup.cloud.paas.storage.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondersgroup.cloud.paas.common.context.ApplicationContextProvider;
import com.wondersgroup.cloud.paas.common.filter.AuthorizationFilter;
import com.wondersgroup.cloud.paas.common.filter.SensitiveDataFilter;
import com.wondersgroup.cloud.paas.common.filter.XssFilter;
import com.wondersgroup.cloud.paas.common.properties.ApplicationProperties;
import com.wondersgroup.cloud.paas.common.utils.UserUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author 过滤器配置
 */
@Component
public class FilterRegisterConfig {

    @Bean(name = "CorsFilter")
    public FilterRegistrationBean corsFilterRegister() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 注入过滤器
        registration.setFilter(new CorsFilter());
        // 拦截规则
        registration.addUrlPatterns("/*");
        // 过滤器名称
        registration.setName("CorsFilter");
        // 是否自动注册 false 取消Filter的自动注册
        registration.setEnabled(true);
        // 过滤器顺序
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    @Bean(name = "XssFilter")
    public FilterRegistrationBean xssFilterRegister() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 注入过滤器
        registration.setFilter(new XssFilter());
        // 拦截规则
        registration.addUrlPatterns("/*");
        // 过滤器名称
        registration.setName("XssFilter");
        // 是否自动注册 false 取消Filter的自动注册
        registration.setEnabled(true);
        // 过滤器顺序
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registration;
    }

    @Bean(name = "SensitiveDataFilter")
    public FilterRegistrationBean sensitiveDataFilterRegister() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 注入过滤器
        registration.setFilter(new SensitiveDataFilter());
        // 拦截规则
        registration.addUrlPatterns("/*");
        // 过滤器名称
        registration.setName("SensitiveDataFilter");
        // 是否自动注册 false 取消Filter的自动注册
        registration.setEnabled(true);
        // 过滤器顺序
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 3);
        return registration;
    }

    @Bean(name = "AuthenticationFilter")
    public FilterRegistrationBean authenticationFilter(){
        FilterRegistrationBean filterRegistrationBean= new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new AuthenticationFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("AuthenticationFilter");
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 2);
        return filterRegistrationBean;
    }

    @Bean(name = "AuthorizationFilter")
    public FilterRegistrationBean authorizationFilter(){
        FilterRegistrationBean filterRegistrationBean= new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new AuthorizationFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("AuthorizationFilter");
        filterRegistrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);
        return filterRegistrationBean;
    }

    @Bean
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        //设置日期格式
        ObjectMapper objectMapper = new ObjectMapper();
        DateFormat smt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(smt);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        return mappingJackson2HttpMessageConverter;
    }

    @Bean
    public ApplicationProperties registerApplicationProperties() {
        return new ApplicationProperties();
    }

    @Bean
    public ApplicationContextProvider registerApplicationContextProvider() {
        return new ApplicationContextProvider();
    }

    @Bean
    public UserUtils registerUserUtils() {
        return new UserUtils();
    }
}
