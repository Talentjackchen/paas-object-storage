package com.wondersgroup.cloud.paas.storage.constant;

public class SslCertConstant {

    /**
     * SSL cert请求路径
     */
    public static String API_SSLCERT_URL = "/sslcert";//上传

    public static String GET_ERROR_MSG = "获取证书失败";

    public static String GET_DEL_DATA_MSG = "获取证书不存在或已被删除";

    public static String INSERT_ERROR_MSG = "添加异常";

    public static String CAN_NOT_DELETE = "该证书已绑定域名，不能直接删除！";

    public static String DELETE_ERROR_MSG = "删除异常";

    public static String INSERT_SUCCESS_MSG = "添加证书成功";

    public static String DELETE_SUCCESS_MSG = "删除证书成功";

    public static int EXPIRE_DAY = 7;//过期前天数
}
