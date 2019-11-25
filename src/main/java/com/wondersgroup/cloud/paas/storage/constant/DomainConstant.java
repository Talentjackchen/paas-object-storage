package com.wondersgroup.cloud.paas.storage.constant;/**

/**
 * describe : 域名
 * create_by : zhangyongzhao
 * create_time : 2019/5/6
 */
public class DomainConstant {

    //获取域名信息成功的标志
    public static String DOMAIN_SUCCESS = "success";
    public static String DOMAIN_FAILED = "failed";

    //域名访问地址前缀
    public static String DOMAIN_PATH = "/domain/";
    //启用域名
    public static String DOMAIN_ONLINE = "/online";
    //停用域名
    public static String DOMAIN_OFFLINE = "/offline";
    public static String OFFLINE = "offlined";
    //http升级为https
    public static String DOMAIN_SSLIZE = "/sslize";
    //https降级为http
    public static String DOMAIN_UNSSLIZE = "/unsslize";

    //source路径
    public static String DOMAIN_SOURCE_URL = "/source";


    //域名类型
    public static String DOMAIN_TYPE = "type";
    public static String DOMAIN_TYPE_NORMAL = "normal";
    //通信协议
    public static String DOMAIN_PROTOCOL = "protocol";
    //https协议
    public static String DOMAIN_PROTOCOL_HTTPS = "https";
    public static String DOMAIN_PROTOCOL_HTTP = "http";

    //地域
    public static String DOMAIN_GEO_COVER = "geoCover";
    //地域默认为中国大陆
    public static String DOMAIN_GEO_COVER_CHINA = "china";
    //平台类型
    public static String DOMAIN_PLATFORM = "platform";
    //源站类型
    public static String DOMAIN_SOURCE_TYPE = "sourceType";
    //源站类型为云存储
    public static String DOMAIN_SOURCE_TYPE_BUCKET = "qiniuBucket";
    //bucket名称
    public static String DOMAIN_SOURCE_QINIU_BUCKET = "sourceQiniuBucket";
    //回源参数
    public static String DOMAIN_SOURCE = "source";

    //缓存时间
    public static String DOMAIN_CACHE_TIME = "time";
    //缓存时间单位
    public static String DOMAIN_CACHE_TIME_UNIT = "timeunit";
    //缓存类型
    public static String DOMAIN_CACHE_TYPE = "type";
    public static String DOMAIN_CACHE_TYPE_ALL = "all";
    //缓存路径规则
    public static String DOMAIN_CACHE_RULE = "rule";
    public static String DOMAIN_CACHE_RULE_ALL = "*";
    //是否开启问号缓存
    public static String DOMAIN_CACHE_IGNORE_PARAM = "ignoreParam";

    //缓存数组
    public static String DOMAIN_CACHE_CONTROLS = "cacheControls";

    //缓存
    public static String DOMAIN_CACHE = "cache";

    //证书ID
    public static String CERT_ID = "certId";
    //是否开启强制https访问
    public static String FORCE_HTTPS = "forceHttps";

    //访问成功的标志
    public static String SUCCESS = "\"code\":200";

    public static String QUESTION_SYMBOL = "?" ;
    public static String EQUAL_SYMBOL = "=";
    public static String AND_SYMBOL = "&";

    public static String SUCCESS_CREATE = "创建成功";
    public static String SUCCESS_DELETE = "删除成功";
    public static String FAIL_DELETE = "删除失败";
    public static String SUCCESS_ENABLE = "启用成功";
    public static String SUCCESS_STOP = "停用成功";
    public static String SUCCESS_STOP_STATE = "暂时不可修改";
    public static String NOT_EXIST_BUCKET = "不存在的空间";
    public static String DOMAIN_CERT_ERROR = "未匹配到有效的证书ID";
    public static String DOMAIN_NOT_EXIST = "当前域名不存在";
    public static String SUCCESS_DOWNGRADE = "降级成功";
    public static String ERROR_DOWNGRADE = "降级失败";
    public static String REPEAT_DOWNGRADE = "降级成功";
    public static String SUCCESS_UPGRADE = "升级成功";
    public static String ERROR_UPGRADE = "升级失败";
    public static String REPEAT_UPGRADE = "重复升级";
    public static String SUCCESS_DATA = "数据获取成功";
    public static String FAIL_EDIT = "数据修改失败";
    public static String SUCCESS_EDIT = "数据修改成功";
    public static String FAIL = "失败";
    public static String NOT_DISCONTINUED = "未停用";
    public static String SUCCESS_BIND = "域名绑定成功";



}
