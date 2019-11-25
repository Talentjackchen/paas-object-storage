package com.wondersgroup.cloud.paas.storage.constant;


public class StatisticsConstant {

    public static String MSG_QUERY_GET_SUCCESS = "获取get请求次数成功";
    public static String MSG_QUERY_PUT_SUCCESS = "获取put请求次数成功";
    public static String MSG_ERROR = "获取数据失败";
    public static String MSG_QUERY_FILE_SIZE_AND_NUM_SUCCESS = "获取文件类型存储量成功";
    public static String MSG_COMMON_SUCCESS = "获取标准存储的存储量统计成功";
    public static String MSG_INFREQUENCY_SUCCESS = "获取低频存储的存储量统计成功";


    //默认时间颗粒度 5min hour day month
    public static String DAY = "day";

    //url
    public static String API_URL = "/v6";
    //API PUT请求URL
    public static String API_PUT_URL = "/rs_put";
    //API GET请求URL
    public static String API_GET_URL = "/blob_io";
    //标准存储量统计
    public static String API_SPACE_URL = "/space";
    //低频存储量统计
    public static String API_SPACE_LINE_URL = "/space_line";

    //起始时间
    public static String BEGIN_DATE = "begin";
    //结束时间
    public static String END_DATE = "end";
    //时间颗粒度
    public static String GRANULARITY = "g";
    //值字段
    public static String SELECT = "select";
    //GET 请求次数
    public static String SELECT_HITS = "hits";
    //流量来源
    public static String SRC = "$src";
    //流量来源 `origin` 通过外⽹访问  `inner` 通过内⽹访问
    public static String SRC_ORIGIN = "origin";
    public static String SRC_INNER = "inner";

    //空间名称
    public static String BUCKET_NAME = "$bucket";
    public static String BUCKET = "bucket";
    //空间访问域名
    public static String DOMAIN = "$domain";
    //存储类型
    public static String STORAGE_TYPE = "$ftype";
    //存储区域
    public static String REGION = "$region";
    //除去低频存储提前删除，剩余的存储量，值为1
    public static String NO_PREDEL = "no_predel";
    //只显示低频存储提前删除的存储量，值为1
    public static String ONLY_PREDEL = "only_predel";

    //头部参数Authorization
    public static String AUTHORIZATION = "Authorization";

    public static String SYMBOL_QUESTION = "?";
    public static String SYMBOL_EQUAL = "=";
    public static String SYMBOL_AND = "&";


}
