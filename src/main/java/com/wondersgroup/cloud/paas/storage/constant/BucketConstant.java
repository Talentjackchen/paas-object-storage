package com.wondersgroup.cloud.paas.storage.constant;

public class BucketConstant {

    //bucketId  空间主键
    public static String BUCKET_ID = "bucketId";

    public static String MSG_QUERY_BUCKET_SUCCESS = "获取bucket数据成功";

    public static String MSG_CREATE_BUCKET_SUCCESS = "bucket创建成功";
    public static String MSG_CREATE_BUCKET_ERROR = "bucket创建失败";
    public static String MSG_DELETE_BUCKET_SUCCESS = "bucket删除成功";
    public static String MSG_DELETE_BUCKET_FAILURE = "bucket删除失败";
    public static String EXIT_RESOURCE = "当前空间下存在资源，不可删除";

    public static int BUCKET_ERROR_CODE = 614;
    public static String BUCKET_NAME_ERROR = "空间名称必填";
    public static String BUCKET_NAME_TOO_LONG_ERROR = "空间名称超出长度";
    public static String BUCKET_REGION_ERROR = "存储区域必填";
    public static String BUCKET_PROJECT_ERROR = "项目代码不可为空";
    public static String BUCKET_TYPE_ERROR = "权限不可为空";
    public static String BUCKET_ORG_ERROR = "机构代码不可为空";
    public static String BUCKET_ALIAS_NAME_ERROR = "本地名称已重复";
    public static String BUCKET_NOT_EXIST_ERROR = "数据不存在";


    public static int BUCKET_PRIVATE_REJECT_CODE = 1000;
    public static String BUCKET_PRIVATE_REJECT_MSG = "私有空间拒绝访问";

    public static int ID_EMPTY_CODE = 1001;
    public static String ID_EMPTY_MSG = "空间ID不可为空";
    public static String SUCCESS_PERMISSION = "设置权限成功";
    public static String ERROR_PERMISSION = "设置权限失败";
    public static String REPEAT_PERMISSION = "重复设置权限";

    public static String NOT_FREE_DOMAIN = "没有空闲的域名";
    public static String REPEAT_FREE_DOMAIN = "重复转正";

    public static String TRANSFORMATION_FORMAL = "转换成功";


    public static int BUCKET_NOT_EXIST_CODE = 1002;
    public static String BUCKET_NOT_EXIST_MSG = "资源空间不存在";

    public static String TRANSFORM_PUBLIC_SUCCESS = "空间转换成功";
    public static String TRANSFORM_PUBLIC_ERROR = "空间转换失败";
    public static String TRANSFORM_PRIVATE_SUCCESS = "转换公开空间成功";
}
