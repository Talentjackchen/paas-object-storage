package com.wondersgroup.cloud.paas.storage.service;

import com.github.pagehelper.PageInfo;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.storage.model.Bucket;
import com.wondersgroup.cloud.paas.storage.model.Resource;
import com.wondersgroup.cloud.paas.storage.pojo.BlockRecord;
import com.wondersgroup.cloud.paas.storage.pojo.ResourcePojo;

import java.io.InputStream;
import java.util.List;

/**
 * 七牛文件上传接口
 */
public interface ResourceService {
    /**
     * 分页加载数据
     * @param bucketId 目录ID
     * @param fileType 文件类型
     * @param storageType 存储类型 00-标准 01-低频
     * @param pageNum 页数
     * @param pageSize 数量
     * @param orderByClause 排序
     * @return 有效集合
     */
    PageInfo<Resource> page(String bucketId, String fileName, String fileType, String storageType, int pageNum, int pageSize, String orderByClause);

    /**
     * 上传接口
     * @param resource resource 资源
     * @param bucketId bucketId
     * @param stream 上传文件流
     * @param fileName 保存文件路径
     * @param force 是否强制覆盖
     * @return 处理结果
     * @throws Exception 七牛异常处理
     */
    ResultMap upload(Resource resource,String bucketId,InputStream stream, String fileName,Boolean force) throws Exception;

    /**
     * 文件复制或移动
     * @param fromBucket 源空间信息
     * @param resource   资源信息
     * @param toBucket   目标文件空间信息
     * @param toKey      目标文件名称（全路径）
     * @param force      强制覆盖 false-否 （默认）
     * @param ifCopy     true-复制（默认）
     * @return 运行结果，无异常表示成功
     */
    ResultMap copyOrMove(Bucket fromBucket,Resource resource,Bucket toBucket, String toKey, Boolean force,boolean ifCopy) throws Exception;

    /**
     * 文件删除
     * @param bucket 空间
     * @param resource 资源
     * @return 运行结果，无异常表示成功
     */
    ResultMap delete(Bucket bucket, Resource resource) throws Exception;

    /**
     * 根据主键Id获取资源
     * @param resourceId
     * @return
     */
    Resource getById(String resourceId);

    /**
     * 修改资源文件类型
     * @param bucketId 空间ID
     * @param key 资源名称
     * @param mimeType 资源类型
     */
    ResultMap updateFileType(String bucketId,String key, String mimeType) throws Exception;

    /**
     * 依据资源空间Id和文件名检索数据
     * @param bucketId 资源空间Id
     * @param key 文件名
     * @return 一个有效资源
     */
    Resource getResourceByBucketIdAndName(String bucketId,String key);

    /**
     * 批量复制文件
     * @param formBucket 文件空间(样本，所有操作资源必须在这个空间下)
     * @param keys 资源名称数组
     * @param toBucketIds 目标文件空间
     * @param toKeys 目标文件
     * @param isCopy true-复制（默认）  false-移动
     * @return 运行结果，无异常表示成功
     */
    ResultMap batchCopyOrMove(Bucket formBucket,String[] keys, String[] toBucketIds,String[] toKeys,boolean isCopy);

    /**
     * 批量删除（同一个bucket下）
     * @param keys  资源数组
     * @param bucket 文件空间(样本，所有资源必须在这个空间下)
     * @return 运行结果
     * @throws Exception 抛出意外异常
     */
    ResultMap batchDelete(String[] keys, Bucket bucket);

    /**
     * 生成下载链接
     * @param resource 资源对象
     * @param expire 失效时间
     * @return
     */
    String generateDownloadUrl(Resource resource,String expire) throws Exception;

    /**
     * 低频存储-普通存储切换
     * @param bucket 空间信息
     * @param resource 资源信息
     * @param storageType 修改后的存储类型
     * @return
     */
    ResultMap changeStorageType(Bucket bucket,Resource resource,String storageType) throws Exception;

    /**
     * 断点续传
     * @param blockRecord 块信息
     * @return
     */
    ResultMap resumeUpload(BlockRecord blockRecord);

    /**
     * 检查文件
     * @param fileName 文件名称
     * @param key 目标文件名称
     * @param totalSize 文件总大小
     * @param lastModifiedTime 最后修改时间
     * @return
     */
    ResultMap checkFile(String fileName,String key,long totalSize,String lastModifiedTime);

    /**
     * 获取7牛文件存储信息
     * @param bucketManager bucket操作对象
     * @param bucket 存储空间
     * @param key 文件名
     * @return
     * @throws QiniuException
     */
    ResourcePojo getFileInfo(BucketManager bucketManager, String bucket , String key);

    /**
     * 依据空间删除资源
     * @param bucketId
     * @return
     */
    ResultMap deleteByBucketId(String bucketId) throws Exception;

    /**
     * 检查临时空间的
     * @param bucketId 空间ID
     * @param ifSize true-大小（10G） false-流量（5G）
     * @param upSize 上传文件大小
     * @return
     */
    boolean checkBucketOver(String bucketId,boolean ifSize,long upSize);

    /**
     * 获取当前bucket资源
     *
     * @param bucketId
     * @return
     */
    List<Resource> getByBucketId(String bucketId);



}
