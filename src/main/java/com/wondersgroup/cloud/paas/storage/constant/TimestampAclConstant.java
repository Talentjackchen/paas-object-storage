package com.wondersgroup.cloud.paas.storage.constant;

/**
 * @author chenlong
 */
public class TimestampAclConstant {
    public static String UPDATE_SUCCESS_MSG = "时间戳防盗链配置更新成功";
    public static String DELETE_SUCCESS_MSG = "时间戳防盗链配置删除成功";

    public static int URL_TIME_EXPIRE_CODE = 1000;
    public static  String URL_TIME_EXPIRE_MSG = "资源访问链接时间失效";

    public static int URL_MODIFIED_CODE = 1001;
    public static  String URL_MODIFIED_MSG = "资源访问链接被篡改";

    public static int CHECK_FAIL_CODE = 1002;
    public static String CHECK_FAIL_MSG = "时间戳防盗链检查不可用";

    /*********************************Redis缓存Field**********************************/
    public static String REDIS_TIMESTAMP = "TIMESTAMP";
}
