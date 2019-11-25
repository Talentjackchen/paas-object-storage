package com.wondersgroup.cloud.paas.storage.constant;

/**
 * @author chenlong
 */
public class ResourceConstant {

    public static int DOWNLOAD_CODE_FAIL = 1001;
    public static String DOWNLOAD_MSG_FAIL = "下载资源失败";

    public static int FIND_CODE_FAIL = 1002;
    public static String FIND_MSG_FAIL = "资源文件未发现";

    public static String PARAMETER_MSG_ERROR = "参数错误";

    public static int PREVIEW_CODE_FAIL = 1005;
    public static String PREVIEW_MSG_FAIL = "预览资源失败";

    //文件类型大小
    public static String FILE_TYPE_SIZE = "size";

    public static long UPLOAD_EXPIRES = 3600;//七牛上传时间

    /**
     * 资源生命周期默认值(-1代表永久)
     */
    public static int DEFAULT_LIFE_CYCLE = -1;


    /**
     * 批量操作上限
     */
    public static int DEFAULT_MAX_BATCH_NUM = 1000;

    public static String RESOURCE_RECORD_ERROR = "资源批量操作记录失败";

    /**
     * 文件检查返回信息
     */
    public static String RESOURCE_RESUME_UPLOAD_ERROR_DATA_MSG = "errorData";
    public static String RESOURCE_RESUME_UPLOAD_EXISTS_FILE_MSG = "existsFile";
    public static String RESOURCE_RESUME_UPLOAD_ERROR_FILE_MSG = "errorFile";
    public static String RESOURCE_RESUME_UPLOAD_RELOAD_FILE_MSG = "reLoadFile";
    public static String RESOURCE_RESUME_UPLOAD_EXISTS_KEY_MSG = "existsKey";

    public static String RESOURCE_RESUME_UPLOAD_READ_FILE_ERROR = "读取文件流异常";
    public static String RESOURCE_RESUME_UPLOAD_MAKE_BLOCK_ERROR = "创建块异常";
    public static String RESOURCE_RESUME_UPLOAD_READ_BLOCK_ERROR = "该分块的CRC32不匹配";
    public static String RESOURCE_RESUME_UPLOAD_MAKE_FILE_ERROR = "创建文件失败";

    public static String RESOURCE_RESUME_UPLOAD_CONNECT_REDIS_ERROR = "连接缓存服务器失败，或无文件上传记录";

    public static String RESOURCE_RESUME_UPLOAD_SIZE_OVER = "块大小最高4M";

    /**
     * 资源操作结果
     */
    public static String RESOURCE_UPLOAD_SUCCESS_MSG = "resourceUploadSuccess";
    public static String RESUME_UPLOAD_SUCCESS_MSG = "resumeUploadSuccess";
    public static String RESOURCE_UPLOAD_SUCCESS_CN_MSG = "资源上传成功";
    public static String RESOURCE_COPY_SUCCESS_MSG = "资源复制成功";
    public static String RESOURCE_MOVE_SUCCESS_MSG = "资源移动成功";
    public static String RESOURCE_DELETE_SUCCESS_MSG = "资源删除成功";
    public static String RESOURCE_UPDATE_SUCCESS_MSG = "资源修改成功";
    public static String RESOURCE_COVER_SUCCESS_MSG = "资源覆盖成功";
    public static String RESOURCE_BATCH_COPY_SUCCESS_MSG = "资源批量复制成功";
    public static String RESOURCE_BATCH_MOVE_SUCCESS_MSG = "资源批量移动成功";
    public static String RESOURCE_BATCH_DELETE_SUCCESS_MSG = "资源批量删除成功";

    public static String RESOURCE_UPLOAD_ERROR_MSG = "资源上传失败";
    public static String RESOURCE_COPY_ERROR_MSG = "资源复制失败";
    public static String RESOURCE_MOVE_ERROR_MSG = "资源移动失败";
    public static String RESOURCE_DELETE_ERROR_MSG = "资源删除失败";
    public static String RESOURCE_UPDATE_ERROR_MSG = "资源修改失败";
    public static String RESOURCE_COVER_ERROR_MSG = "资源覆盖失败";
    public static String RESOURCE_BATCH_COPY_ERROR_MSG = "资源批量复制失败";
    public static String RESOURCE_BATCH_MOVE_ERROR_MSG = "资源批量移动失败";
    public static String RESOURCE_BATCH_COPY_HALF_ERROR_MSG = "资源批量复制部分失败";
    public static String RESOURCE_BATCH_MOVE_HALF_ERROR_MSG = "资源批量移动部分失败";
    public static String RESOURCE_BATCH_DELETE_ERROR_MSG = "资源批量删除失败";
    public static String RESOURCE_BATCH_DELETE_HALF_ERROR_MSG = "资源批量删除部分失败";
    public static String RESOURCE_BATCH_TOO_MUCH = "批量数量不超过1000条";

    public static String RESOURCE_STATISTIC_INSERT_ERROR = "计费失败";

    public static String BATCH_EXISTS_FILE_ERROR = "file exists";
    public static String EXISTS_FILE_ERROR = "文件已存在";
    public static String RESOURCE_NOT_NEED_UPDATE_MSG = "资源无需修改";


    /**
     * 资源空间不存在
     */
    public static int BUCKET_NOT_FOUND_CODE = 2005;
    public static String FROM_BUCKET_NOT_FOUND_MSG = "源文资源空间未找到";
    public static String TO_BUCKET_NOT_FOUND_MSG = "目标资源空间未找到";

    /**
     * 资源未找到
     */
    public static int RESOURCE_NOT_FOUND_CODE = 2006;
    public static String RESOURCE_NOT_FOUND_MSG = "资源未找到";

    /**
     * 目标资源已存在
     */
    public static int RESOURCE_EXISTS_CODE = 2007;
    public static String RESOURCE_EXISTS_MSG = "目标资源已存在";


    public static int BUCKET_NOT_MATCH_CODE = 2008;
    public static String BUCKET_NOT_MATCH_MSG = "资源空间Id不一致";
    /**
     * 文件上传redis记录常量
     */
    public static String REDIS_HASH_FILE_NAME = "fileName";//文件名称
    public static String REDIS_HASH_FILE_SIZE = "fileSize";//文件总大小
    public static String REDIS_HASH_TO_KEY = "toKey";//目标文件名称
    public static String REDIS_HASH_LAST_MODIFIED_TIME = "lastModifiedTime";//文件最后修改时间
    public static int REDIS_FILE_RECORD_MAX_TIME = 172800;//redis 生命周期 2天 2*24*60*60

    /**
     * 文件上传固定参数
     */
    public static String FILE_UPLOAD_MIME = "application/octet-stream";//文件名称
    public static int BLOCK_MAX_SIZE = 4194304;// 块最大大小  4M  4*1024*1024

    /**
     * 空间检验
     */
    public static long BUCKET_MAX_SIZE = 10737418240l;//临时空间文件存储最大值10G 10*1024*1024*1024
    public static long BUCKET_MAX_FLUX = 5368709120l;//临时空间每日流量最大值5G 5*1024*1024*1024
    public static String BUCKET_SIZE_CHECK_ERROR = "存储量达到上限（10G）";
    public static String BUCKET_FLUX_CHECK_ERROR = "访问流量达到上限（5G）";
    public static String TO_BUCKET_SIZE_CHECK_ERROR = "目标空间储量达到上限（10G）";

    public static String GET_FLUX_DATA_ERROR = "获取域名流量失败";
}
