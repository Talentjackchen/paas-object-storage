package com.wondersgroup.cloud.paas.storage.constant;

/**
 * @author chenlong
 */
public class IpAclConstant {
    public static int WHITE_REFUSED_CODE = 1001;
    public static String WHITE_REFUSED_MSG = "IP不在白名单内";

    public static int IP_NOT_NORM_CODE = 1002;
    public static String IP_NOT_NORM_MSG = "IP地址不符合规范";

    public static String CREATE_SUCCESS_MSG = "IP白名单防盗链配置创建成功";
    public static String UPDATE_SUCCESS_MSG = "IP白名单防盗链配置更新成功";
    public static String DELETE_SUCCESS_MSG = "IP白名单防盗链配置删除成功";

    public static String CREATE_FAIL_MSG = "IP白名单防盗链配置创建失败";
    public static String UPDATE_FAIL_MSG = "IP白名单防盗链配置更新失败";

    /*********************************Redis缓存Field**********************************/
    public static String REDIS_IP = "IP";
}
