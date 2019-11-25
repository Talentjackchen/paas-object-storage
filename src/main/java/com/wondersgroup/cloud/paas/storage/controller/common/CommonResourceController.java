package com.wondersgroup.cloud.paas.storage.controller.common;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.enums.status.ValidStatusEnum;
import com.wondersgroup.cloud.paas.common.exception.BusinessException;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.common.utils.JsonUtils;
import com.wondersgroup.cloud.paas.common.utils.MD5Utils;
import com.wondersgroup.cloud.paas.storage.constant.BucketConstant;
import com.wondersgroup.cloud.paas.storage.constant.ResourceConstant;
import com.wondersgroup.cloud.paas.storage.controller.ResourceController;
import com.wondersgroup.cloud.paas.storage.enums.ResourceOperationTypeEnum;
import com.wondersgroup.cloud.paas.storage.enums.StorageTypeEnum;
import com.wondersgroup.cloud.paas.storage.model.Bucket;
import com.wondersgroup.cloud.paas.storage.model.Resource;
import com.wondersgroup.cloud.paas.storage.model.ResourceRecord;
import com.wondersgroup.cloud.paas.storage.pojo.BlockRecord;
import com.wondersgroup.cloud.paas.storage.service.BucketService;
import com.wondersgroup.cloud.paas.storage.service.ResourceRecordService;
import com.wondersgroup.cloud.paas.storage.service.ResourceService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * @author chenlong
 * 三方和本地公用的接口方法
 */
public class CommonResourceController {

    private Logger logger = Logger.getLogger(ResourceController.class);

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private BucketService bucketService;

    @Autowired
    private ResourceRecordService resourceRecordService;

    /**
     * 资源上传接口
     *
     * @param file        上传文件
     * @param key         文件名
     * @param storageType 存储类型 0-标准 1-低频
     * @param force       强制覆盖 false-不覆盖
     * @return 执行结果
     */
    @PostMapping("/upload")
    public ResultMap upload(@RequestParam MultipartFile file,
                            @RequestParam String key,
                            @RequestParam(required = false) String storageType,
                            @RequestParam(required = false) Boolean force,
                            HttpServletRequest request) {
        String bucketId = request.getHeader(BucketConstant.BUCKET_ID);//获取bucketId

        //临时空间检验
        if (!resourceService.checkBucketOver(bucketId, true, file.getSize())) {
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.BUCKET_SIZE_CHECK_ERROR);
        }

        InputStream stream = null;
        try {
            if (file == null || file.getSize() == 0) {
                return new ResultMap(CommonConstant.ERROR, CommonConstant.RESULT_ERROR, ResourceConstant.FIND_MSG_FAIL);
            }

            stream = file.getInputStream();
            Resource resource = new Resource();
            resource.setBucketId(bucketId);
            resource.setKey(key);
            resource.setCreateUser("");//当前登录用户
            resource.setValidFlag(ValidStatusEnum.VALID.value());
            if (StringUtils.isBlank(storageType) || !StorageTypeEnum.INFREQUENCY.value().equals(storageType)) {
                resource.setStorageType(StorageTypeEnum.COMMON.value());
            } else {
                resource.setStorageType(storageType);
            }
            resource.setLifeCycle(ResourceConstant.DEFAULT_LIFE_CYCLE);
            ResultMap resultMap = resourceService.upload(resource, bucketId, stream, key, force);
            if (resultMap.getResult() != null && resultMap.isSuccess()) {
                ResourceRecord resourceRecord = (ResourceRecord) resultMap.getResult();
                resourceRecordService.insert(resourceRecord);//增加记录
            }
            return resultMap;
        } catch (Exception e) {
            logger.error(ResourceConstant.RESOURCE_UPLOAD_ERROR_MSG, e);
            return new ResultMap(CommonConstant.ERROR, CommonConstant.RESULT_ERROR, ResourceConstant.RESOURCE_UPLOAD_ERROR_MSG);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception ex) {
                    logger.error(ResourceConstant.RESOURCE_UPLOAD_ERROR_MSG, ex);
                    return new ResultMap(CommonConstant.ERROR, CommonConstant.RESULT_ERROR, ResourceConstant.RESOURCE_UPLOAD_ERROR_MSG);
                }
            }
        }
    }

    /**
     * 文件断点续传
     *
     * @param file             分片
     * @param resumeId         第几个分片
     * @param key              目标文件名称
     * @param totalSize        总大小
     * @param fileName         上传文件名
     * @param lastModifiedTime 最后修改时间
     * @param storageType      存储类型
     * @param isMakeFile       是否合并
     * @param request
     * @return
     */
    @PostMapping("/resumeUpload")
    public ResultMap resumeUpload(@RequestParam MultipartFile file,
                                  @RequestParam int resumeId,
                                  @RequestParam String key,
                                  @RequestParam long totalSize,
                                  @RequestParam String fileName,
                                  @RequestParam String lastModifiedTime,
                                  @RequestParam(required = false) String storageType,
                                  @RequestParam(required = false) Boolean isMakeFile,
                                  HttpServletRequest request) {
        String bucketId = request.getHeader(BucketConstant.BUCKET_ID);//获取bucketId
        if (StringUtils.isBlank(bucketId) || file == null || StringUtils.isBlank(fileName) || StringUtils.isBlank(lastModifiedTime)) {
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_RESUME_UPLOAD_ERROR_DATA_MSG);
        }

        String redisStr = fileName + "_" + key + "_" + totalSize + "_" + lastModifiedTime;//缓存唯一key
        String redisListKey = MD5Utils.encode("L" + redisStr);//列表key 存放七牛返回块索引
        Boolean existsListKey = jedisCluster.exists(redisListKey);
        if (existsListKey == null || !existsListKey) {
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_RESUME_UPLOAD_CONNECT_REDIS_ERROR);
        }

        Bucket bucket = bucketService.getById(bucketId);
        if (bucket == null || StringUtils.isBlank(bucket.getAliasName())) {
            return new ResultMap(ResourceConstant.BUCKET_NOT_FOUND_CODE, ResourceConstant.FROM_BUCKET_NOT_FOUND_MSG);
        }

        if (StringUtils.isBlank(storageType) || !StorageTypeEnum.INFREQUENCY.value().equals(storageType)) {
            storageType = StorageTypeEnum.COMMON.value();
        }

        BlockRecord blockRecord = new BlockRecord();
        Long blockSize = file.getSize();
        blockRecord.setResumeId(resumeId);
        blockRecord.setFileName(fileName);

        if (blockSize.intValue() > ResourceConstant.BLOCK_MAX_SIZE) {
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_RESUME_UPLOAD_SIZE_OVER);
        }
        blockRecord.setBlockSize(blockSize.intValue());
        blockRecord.setKey(key);
        blockRecord.setTotalSize(totalSize);
        blockRecord.setLastModifiedTime(lastModifiedTime);
        blockRecord.setStorageType(storageType);
        blockRecord.setMakeFile(isMakeFile == null ? false : isMakeFile);
        blockRecord.setBucket(bucket);
        blockRecord.setRedisHashKey(MD5Utils.encode("H" + redisStr));//哈希key 存放文件名称、总大小、已上传大小、存储文件名、已传入快数量
        blockRecord.setRedisListKey(redisListKey);//列表key 存放七牛返回块索引
        blockRecord.setRedisIndexKey(MD5Utils.encode("I" + redisStr));//存放上传块数量
        InputStream stream = null;
        try {
            stream = file.getInputStream();
            blockRecord.setFile(stream);
            return resourceService.resumeUpload(blockRecord);
        } catch (Exception e) {
            logger.error(ResourceConstant.RESOURCE_UPLOAD_ERROR_MSG, e);
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_UPLOAD_ERROR_MSG);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception ex) {
                    logger.error(ResourceConstant.RESOURCE_UPLOAD_ERROR_MSG, ex);
                    return new ResultMap(CommonConstant.ERROR, CommonConstant.RESULT_ERROR);
                }
            }
        }
    }

    /**
     * 文件移动
     *
     * @param key        资源名称
     * @param toBucketId 目标文件空间
     * @param toKey      目标文件名
     * @param force      是否覆盖 true-是
     * @return 执行结果
     */
    @PutMapping("/move")
    public ResultMap move(@RequestParam String key,
                          @RequestParam(required = false) String toBucketId,
                          @RequestParam(required = false) String toKey,
                          @RequestParam(required = false) Boolean force,
                          HttpServletRequest request) {
        String bucketId = request.getHeader(BucketConstant.BUCKET_ID);//获取bucketId

        try {
            ResultMap resultMap = copyOrMove(bucketId, key, toBucketId, toKey, force, false);
            if (resultMap.getResult() != null) {
                ResourceRecord resourceRecord = (ResourceRecord) resultMap.getResult();
                resourceRecordService.insert(resourceRecord);//增加记录
            }
            return resultMap;
        } catch (Exception e) {
            logger.error(ResourceConstant.RESOURCE_MOVE_ERROR_MSG, e);
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_MOVE_ERROR_MSG);
        }
    }

    /**
     * 文件复制
     *
     * @param key        资源名称
     * @param toBucketId 目标文件空间
     * @param toKey      目标文件名
     * @param force      是否覆盖 true-是
     * @return 执行结果
     */
    @PutMapping("/copy")
    public ResultMap copy(@RequestParam String key,
                          @RequestParam(required = false) String toBucketId,
                          @RequestParam(required = false) String toKey,
                          @RequestParam(required = false) Boolean force,
                          HttpServletRequest request) {

        String bucketId = request.getHeader(BucketConstant.BUCKET_ID);//获取bucketId
        try {
            ResultMap resultMap = copyOrMove(bucketId, key, toBucketId, toKey, force, true);
            if (resultMap.getResult() != null) {
                ResourceRecord resourceRecord = (ResourceRecord) resultMap.getResult();
                resourceRecordService.insert(resourceRecord);//增加记录
            }
            return resultMap;
        } catch (Exception e) {
            logger.error(ResourceConstant.RESOURCE_COPY_ERROR_MSG, e);
            return new ResultMap(CommonConstant.ERROR, CommonConstant.RESULT_ERROR, ResourceConstant.RESOURCE_COPY_ERROR_MSG);
        }
    }

    /**
     * 文件删除
     *
     * @param key 资源名称
     * @return 执行结果
     */
    @DeleteMapping("/delete")
    public ResultMap delete(@RequestParam String key, HttpServletRequest request) {

        String bucketId = request.getHeader(BucketConstant.BUCKET_ID);//获取bucketId
        try {
            if (StringUtils.isBlank(key) || StringUtils.isBlank(bucketId)) {
                return new ResultMap(CommonConstant.DATA_CHECK_FAILURE_CODE, CommonConstant.DATA_CHECK_FAILURE_MSG);
            }

            Bucket bucket = bucketService.getById(bucketId);
            if (bucket == null || StringUtils.isBlank(bucket.getAliasName())) {
                return new ResultMap(ResourceConstant.BUCKET_NOT_FOUND_CODE, ResourceConstant.FROM_BUCKET_NOT_FOUND_MSG);
            }

            Resource resource = resourceService.getResourceByBucketIdAndName(bucketId, key);
            if (resource == null) {
                return new ResultMap(ResourceConstant.RESOURCE_NOT_FOUND_CODE, ResourceConstant.RESOURCE_NOT_FOUND_MSG);
            }
            ResultMap resultMap = resourceService.delete(bucket, resource);
            if (resultMap.getResult() != null) {
                ResourceRecord resourceRecord = (ResourceRecord) resultMap.getResult();
                resourceRecordService.insert(resourceRecord);//增加记录
            }
            return resultMap;
        } catch (Exception e) {
            logger.error(ResourceConstant.RESOURCE_DELETE_ERROR_MSG, e);
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_DELETE_ERROR_MSG);
        }
    }

    /**
     * 文件批量移动（不会覆盖）
     *
     * @param keys        资源名称数组
     * @param toBucketIds 目标空间ID数组
     * @param toKeys      目标文件数组（存在则失败）
     * @param request     request
     * @return 执行结果
     */
    @PutMapping("/batchMove")
    public ResultMap batchMove(@RequestParam String[] keys,
                               @RequestParam(required = false) String[] toBucketIds,
                               @RequestParam String[] toKeys,
                               HttpServletRequest request) {
        String bucketId = request.getHeader(BucketConstant.BUCKET_ID);//获取bucketId
        try {
            if (keys == null || keys.length == 0 || StringUtils.isBlank(bucketId) || toKeys == null || toKeys.length == 0) {
                return new ResultMap(CommonConstant.DATA_CHECK_FAILURE_CODE, CommonConstant.DATA_CHECK_FAILURE_MSG);
            }
            if (keys.length > ResourceConstant.DEFAULT_MAX_BATCH_NUM) {
                return new ResultMap(CommonConstant.DATA_CHECK_FAILURE_CODE, ResourceConstant.RESOURCE_BATCH_TOO_MUCH);
            }
            if (keys.length != toKeys.length) {
                return new ResultMap(CommonConstant.DATA_CHECK_FAILURE_CODE, CommonConstant.DATA_CHECK_FAILURE_MSG);
            }
            if (toBucketIds != null && toBucketIds.length > 0) {
                if (keys.length != toBucketIds.length) {
                    return new ResultMap(CommonConstant.DATA_CHECK_FAILURE_CODE, CommonConstant.DATA_CHECK_FAILURE_MSG);
                }
            }
            Bucket formBucket = bucketService.getById(bucketId);
            if (formBucket == null || StringUtils.isBlank(formBucket.getAliasName())) {
                return new ResultMap(ResourceConstant.BUCKET_NOT_FOUND_CODE, ResourceConstant.FROM_BUCKET_NOT_FOUND_MSG);
            }
            return resourceService.batchCopyOrMove(formBucket, keys, toBucketIds, toKeys, false);
        } catch (Exception e) {
            logger.error(ResourceConstant.RESOURCE_BATCH_MOVE_HALF_ERROR_MSG, e);
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_BATCH_MOVE_HALF_ERROR_MSG);
        }
    }

    /**
     * 文件批量复制（不会覆盖）
     *
     * @param keys        资源名称数组
     * @param toBucketIds 目标空间ID数组
     * @param toKeys      目标文件数组（存在则失败）
     * @param request     request
     * @return 执行结果
     */
    @PutMapping("/batchCopy")
    public ResultMap batchCopy(@RequestParam String[] keys,
                               @RequestParam(required = false) String[] toBucketIds,
                               @RequestParam String[] toKeys,
                               HttpServletRequest request) {
        String bucketId = request.getHeader(BucketConstant.BUCKET_ID);//获取bucketId
        try {
            if (keys == null || keys.length == 0 || StringUtils.isBlank(bucketId) || toKeys == null || toKeys.length == 0) {
                return new ResultMap(CommonConstant.DATA_CHECK_FAILURE_CODE, CommonConstant.DATA_CHECK_FAILURE_MSG);
            }
            if (keys.length > ResourceConstant.DEFAULT_MAX_BATCH_NUM) {
                return new ResultMap(CommonConstant.DATA_CHECK_FAILURE_CODE, ResourceConstant.RESOURCE_BATCH_TOO_MUCH);
            }
            if (keys.length != toKeys.length) {
                return new ResultMap(CommonConstant.DATA_CHECK_FAILURE_CODE, CommonConstant.DATA_CHECK_FAILURE_MSG);
            }
            if (toBucketIds != null && toBucketIds.length > 0) {
                if (keys.length != toBucketIds.length) {
                    return new ResultMap(CommonConstant.DATA_CHECK_FAILURE_CODE, CommonConstant.DATA_CHECK_FAILURE_MSG);
                }
            }
            Bucket formBucket = bucketService.getById(bucketId);
            if (formBucket == null || StringUtils.isBlank(formBucket.getAliasName())) {
                return new ResultMap(ResourceConstant.BUCKET_NOT_FOUND_CODE, ResourceConstant.FROM_BUCKET_NOT_FOUND_MSG);
            }
            return resourceService.batchCopyOrMove(formBucket, keys, toBucketIds, toKeys, true);
        } catch (Exception e) {
            logger.error(ResourceConstant.RESOURCE_BATCH_COPY_HALF_ERROR_MSG, e);
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_BATCH_COPY_HALF_ERROR_MSG);
        }
    }

    /**
     * 批量文件删除
     *
     * @param keys    资源名称数组
     * @param request request
     * @return 执行结果
     */
    @DeleteMapping("/batchDelete")
    public ResultMap batchDelete(@RequestParam String[] keys, HttpServletRequest request) {

        String bucketId = request.getHeader(BucketConstant.BUCKET_ID);//获取bucketId
        try {
            if (keys == null || keys.length == 0 || StringUtils.isBlank(bucketId)) {
                return new ResultMap(CommonConstant.DATA_CHECK_FAILURE_CODE, CommonConstant.DATA_CHECK_FAILURE_MSG);
            }
            if (keys.length > ResourceConstant.DEFAULT_MAX_BATCH_NUM) {
                return new ResultMap(CommonConstant.DATA_CHECK_FAILURE_CODE, ResourceConstant.RESOURCE_BATCH_TOO_MUCH);
            }
            Bucket bucket = bucketService.getById(bucketId);
            if (bucket == null || StringUtils.isBlank(bucket.getAliasName())) {
                return new ResultMap(ResourceConstant.BUCKET_NOT_FOUND_CODE, ResourceConstant.FROM_BUCKET_NOT_FOUND_MSG);
            }
            return resourceService.batchDelete(keys, bucket);
        } catch (Exception e) {
            logger.error(ResourceConstant.RESOURCE_BATCH_DELETE_ERROR_MSG, e);
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_BATCH_DELETE_ERROR_MSG);
        }
    }

    /**
     * 修改存储类型
     *
     * @param key         资源名称
     * @param storageType 不设置则低频
     * @param request     request
     * @return
     */
    @PutMapping("/changeStorageType")
    public ResultMap changeStorageType(@RequestParam String key,
                                       @RequestParam(required = false) String storageType,
                                       HttpServletRequest request) {

        String bucketId = request.getHeader(BucketConstant.BUCKET_ID);//获取bucketId
        try {
            if (StringUtils.isBlank(key) || StringUtils.isBlank(bucketId)) {
                return new ResultMap(CommonConstant.DATA_CHECK_FAILURE_CODE, CommonConstant.DATA_CHECK_FAILURE_MSG);
            }

            Bucket bucket = bucketService.getById(bucketId);
            if (bucket == null || StringUtils.isBlank(bucket.getAliasName())) {
                return new ResultMap(ResourceConstant.BUCKET_NOT_FOUND_CODE, ResourceConstant.FROM_BUCKET_NOT_FOUND_MSG);
            }

            Resource resource = resourceService.getResourceByBucketIdAndName(bucketId, key);
            if (resource == null) {
                return new ResultMap(ResourceConstant.RESOURCE_NOT_FOUND_CODE, ResourceConstant.RESOURCE_NOT_FOUND_MSG);
            }
            //不存在或者不合法
            if (StringUtils.isBlank(storageType) || (!StorageTypeEnum.INFREQUENCY.value().equals(storageType) && !StorageTypeEnum.COMMON.value().equals(storageType))) {
                storageType = StorageTypeEnum.INFREQUENCY.value();
            }

            //不需要转化
            if (storageType.equals(resource.getStorageType())) {
                return new ResultMap(CommonConstant.SUCCESS, ResourceConstant.RESOURCE_NOT_NEED_UPDATE_MSG);
            }
            ResultMap resultMap = resourceService.changeStorageType(bucket, resource, storageType);
            if (resultMap.getResult() != null) {
                ResourceRecord resourceRecord = (ResourceRecord) resultMap.getResult();
                resourceRecordService.insert(resourceRecord);//增加记录
            }
            return resultMap;
        } catch (Exception e) {
            logger.error(ResourceConstant.RESOURCE_UPDATE_ERROR_MSG, e);
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_UPDATE_ERROR_MSG);
        }
    }

    /**
     * 修改资源文件类型
     *
     * @param key      资源名称
     * @param mimeType 资源类型
     * @return
     */
    @PutMapping(value = "/updateMimeType")
    public ResultMap updateFileType(@RequestParam String key, @RequestParam String mimeType, HttpServletRequest request) {
        String bucketId = request.getHeader(BucketConstant.BUCKET_ID);//获取bucketId
        try {
            ResultMap resultMap = resourceService.updateFileType(bucketId, key, mimeType);
            if (resultMap.getResult() != null) {
                ResourceRecord resourceRecord = (ResourceRecord) resultMap.getResult();
                resourceRecordService.insert(resourceRecord);//增加记录
            }
            return resultMap;
        } catch (Exception e) {
            logger.error(ResourceConstant.RESOURCE_UPDATE_ERROR_MSG, e);
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_UPDATE_ERROR_MSG);
        }
    }

    /**
     * 修改资源文件名
     *
     * @param key   源资源名称
     * @param toKey 修改后资源文件名
     * @return
     */
    @PutMapping(value = "/updateName")
    public ResultMap updateFileName(@RequestParam String key,
                                    @RequestParam String toKey,
                                    HttpServletRequest request) {
        String bucketId = request.getHeader(BucketConstant.BUCKET_ID);//获取bucketId
        ResultMap resultMap;
        try {
            Resource existResource = resourceService.getResourceByBucketIdAndName(bucketId, toKey);
            if (existResource == null) {
                resultMap = copyOrMove(bucketId, key, null, toKey, false, false);
                if (resultMap.getResult() != null) {
                    ResourceRecord resourceRecord = (ResourceRecord) resultMap.getResult();
                    resourceRecordService.insert(resourceRecord);//增加记录
                }
                if (resultMap.isSuccess()) {
                    resultMap.setMsg(ResourceConstant.RESOURCE_UPDATE_SUCCESS_MSG);
                }
                return resultMap;

            } else {
                return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_EXISTS_MSG);
            }
        } catch (Exception e) {
            logger.error(ResourceConstant.RESOURCE_UPDATE_ERROR_MSG, e);
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_UPDATE_ERROR_MSG);
        }
    }

    /**
     * 预览资源
     *
     * @param key      资源文件全路径
     * @param response
     * @throws IOException
     */
    @GetMapping("/preview")
    public ResultMap preview(@RequestParam String key, @RequestParam(required = false) String expire, String bucketId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        try {
            Resource resource = resourceService.getResourceByBucketIdAndName(bucketId, key);
            if (resource == null) {
                return new ResultMap(ResourceConstant.RESOURCE_NOT_FOUND_CODE, ResourceConstant.RESOURCE_NOT_FOUND_MSG) ;
            } else {
                String headBucketId = request.getHeader("bucketId");
                if (StringUtils.isNotBlank(headBucketId)) {
                    if (!headBucketId.equals(bucketId)) {
                        return new ResultMap(ResourceConstant.BUCKET_NOT_MATCH_CODE, ResourceConstant.BUCKET_NOT_MATCH_MSG);
                    }
                }
            }

            //临时空间检验
            if (!resourceService.checkBucketOver(bucketId, false, 0l)) {
                response.setStatus(ResourceConstant.PREVIEW_CODE_FAIL);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.getWriter().write(JsonUtils.getJsonString(new ResultMap(CommonConstant.SUCCESS, ResourceConstant.BUCKET_FLUX_CHECK_ERROR)));
                response.getWriter().flush();
                response.getWriter().close();
            }
            String url = resourceService.generateDownloadUrl(resource, expire);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();

            resourceReturnCodeRecord(bucketId, resource.getResourceId(), resource.getKey(), ResourceOperationTypeEnum.PREVIEW.value(), response.getStatus());

            entity.writeTo(response.getOutputStream());
            response.setStatus(CommonConstant.SUCCESS);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            return new ResultMap(CommonConstant.SUCCESS,CommonConstant.RESULT_SUCCESS);
        } catch (Exception ex) {
            ResultMap resultMap;
            if (ex instanceof BusinessException) {
                logger.error(ex.getMessage(), ex);
                resultMap = ((BusinessException) ex).getResultMap();
            } else {
                logger.error(ResourceConstant.PREVIEW_MSG_FAIL, ex);
                resultMap = new ResultMap(ResourceConstant.PREVIEW_CODE_FAIL, ResourceConstant.PREVIEW_MSG_FAIL);
            }

            response.setStatus(ResourceConstant.PREVIEW_CODE_FAIL);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(JsonUtils.getJsonString(resultMap));
            response.getWriter().flush();
            response.getWriter().close();
            return resultMap;
        }
    }

    /**
     * 下载资源
     *
     * @param key      资源文件全路径
     * @param response
     * @throws IOException
     */
    @GetMapping("/download")
    public ResultMap download(@RequestParam String key, @RequestParam(required = false) String expire, String bucketId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        try {
            Resource resource = resourceService.getResourceByBucketIdAndName(bucketId, key);
            if (resource == null) {
                return  new ResultMap(ResourceConstant.RESOURCE_NOT_FOUND_CODE, ResourceConstant.RESOURCE_NOT_FOUND_MSG);
            } else {
                String headBucketId = request.getHeader("bucketId");
                if (StringUtils.isNotBlank(headBucketId)) {
                    if (!headBucketId.equals(bucketId)) {
                        return new ResultMap(ResourceConstant.BUCKET_NOT_MATCH_CODE, ResourceConstant.BUCKET_NOT_MATCH_MSG);
                    }
                }
            }
            //临时空间检验
            if (!resourceService.checkBucketOver(bucketId, false, 0l)) {
                response.setStatus(ResourceConstant.DOWNLOAD_CODE_FAIL);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.getWriter().write(JsonUtils.getJsonString(new ResultMap(CommonConstant.SUCCESS, ResourceConstant.BUCKET_FLUX_CHECK_ERROR)));
                response.getWriter().flush();
                response.getWriter().close();
            }

            String fileName = resource.getKey();
            int pos = fileName.lastIndexOf("/");
            if (pos > -1) {
                fileName = fileName.substring(pos + 1);
            }

            String url = resourceService.generateDownloadUrl(resource, expire);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();

            resourceReturnCodeRecord(bucketId, resource.getResourceId(), resource.getKey(), ResourceOperationTypeEnum.PREVIEW.value(), response.getStatus());

            response.setContentType("multipart/form-data");
            fileName = URLEncoder.encode(fileName, "utf-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            entity.writeTo(response.getOutputStream());
            response.setStatus(CommonConstant.SUCCESS);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            return new ResultMap(CommonConstant.SUCCESS,CommonConstant.RESULT_SUCCESS);
        } catch (Exception ex) {
            ResultMap resultMap;
            if (ex instanceof BusinessException) {
                logger.error(ex.getMessage(), ex);
                resultMap = ((BusinessException) ex).getResultMap();
            } else {
                logger.error(ResourceConstant.DOWNLOAD_MSG_FAIL, ex);
                resultMap = new ResultMap(ResourceConstant.DOWNLOAD_CODE_FAIL, ResourceConstant.DOWNLOAD_MSG_FAIL);
            }

            response.setStatus(ResourceConstant.DOWNLOAD_CODE_FAIL);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(JsonUtils.getJsonString(resultMap));
            response.getWriter().flush();
            response.getWriter().close();
            return resultMap;
        }
    }

    /**
     * 检查文件
     *
     * @param fileName         文件名称
     * @param key              目标文件名称
     * @param totalSize        文件总大小
     * @param lastModifiedTime 最后修改时间
     * @return
     */
    @GetMapping("/checkFile")
    public ResultMap checkFile(@RequestParam String fileName,
                               @RequestParam String key,
                               @RequestParam long totalSize,
                               @RequestParam String lastModifiedTime,
                               HttpServletRequest request) {
        String bucketId = request.getHeader(BucketConstant.BUCKET_ID);//获取bucketId

        if (StringUtils.isBlank(fileName) || StringUtils.isBlank(lastModifiedTime) || StringUtils.isBlank(bucketId)) {
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_RESUME_UPLOAD_ERROR_DATA_MSG);
        }
        //临时空间检验
        if (!resourceService.checkBucketOver(bucketId, true, totalSize)) {
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.BUCKET_SIZE_CHECK_ERROR);
        }
        Resource Resource = resourceService.getResourceByBucketIdAndName(bucketId, key);
        if (Resource != null) {
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_RESUME_UPLOAD_EXISTS_KEY_MSG);
        }
        return resourceService.checkFile(fileName, key, totalSize, lastModifiedTime);
    }

    /**
     * 复制或移动基础校验
     *
     * @param bucketId   源空间ID
     * @param key        源文件名
     * @param toBucketId 目标空间ID
     * @param toKey      目标文件名
     * @param force      是否覆盖
     * @param ifCopy     true-复制
     * @return
     * @throws Exception
     */
    private ResultMap copyOrMove(String bucketId, String key, String toBucketId, String toKey, Boolean force, boolean ifCopy) throws Exception {

        if (StringUtils.isBlank(key) || StringUtils.isBlank(bucketId)) {//参数校验
            return new ResultMap(CommonConstant.DATA_CHECK_FAILURE_CODE, CommonConstant.DATA_CHECK_FAILURE_MSG);
        }
        if (StringUtils.isBlank(toBucketId) && StringUtils.isBlank(toKey)) {//无需移动的操作
            if (ifCopy) {
                return new ResultMap(CommonConstant.SUCCESS, ResourceConstant.RESOURCE_COPY_SUCCESS_MSG);
            } else {
                return new ResultMap(CommonConstant.SUCCESS, ResourceConstant.RESOURCE_MOVE_SUCCESS_MSG);
            }
        }
        //原空间与目标空间一致，源文件名与目标文件名一致 不需要操作
        if (bucketId.equals(toBucketId) && key.equals(toKey)) {
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_EXISTS_MSG);
        }

        Bucket fromBucket = bucketService.getById(bucketId);
        if (fromBucket == null || StringUtils.isBlank(fromBucket.getAliasName())) {//源空间有效性校验
            return new ResultMap(ResourceConstant.BUCKET_NOT_FOUND_CODE, ResourceConstant.FROM_BUCKET_NOT_FOUND_MSG);
        }
        Bucket toBucket;
        if (StringUtils.isBlank(toBucketId)) {
            toBucket = fromBucket;//默认值
        } else {
            toBucket = bucketService.getById(toBucketId);
            if (toBucket == null || StringUtils.isBlank(toBucket.getAliasName())) {//目标空间有效性校验
                return new ResultMap(ResourceConstant.BUCKET_NOT_FOUND_CODE, ResourceConstant.TO_BUCKET_NOT_FOUND_MSG);
            }

            if (!fromBucket.getProjectId().equals(toBucket.getProjectId())) {//权限校验
                return new ResultMap(ResourceConstant.BUCKET_NOT_FOUND_CODE, CommonConstant.NO_AUTHORITY);
            }
        }
        if (StringUtils.isBlank(toKey)) {
            toKey = key;//默认值
        }

        Resource resource = resourceService.getResourceByBucketIdAndName(bucketId, key);
        if (resource == null) {//操作文件有效性校验
            return new ResultMap(ResourceConstant.RESOURCE_NOT_FOUND_CODE, ResourceConstant.RESOURCE_NOT_FOUND_MSG);
        }

        if (StringUtils.isNotBlank(toBucketId) && !resourceService.checkBucketOver(toBucketId, true, resource.getFileSize())) {//跨空间
            //临时空间检验
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.TO_BUCKET_SIZE_CHECK_ERROR);
        }
        return resourceService.copyOrMove(fromBucket, resource, toBucket, toKey, force, ifCopy);

    }

    /**
     * 增加资源操作记录
     *
     * @param bucketId      空间ID
     * @param resourceId    资源ID
     * @param key           key
     * @param operationType 操作类型
     * @param returnCode    返回码
     * @throws Exception
     */
    private void resourceReturnCodeRecord(String bucketId, String resourceId, String key, String operationType, int returnCode) throws Exception {
        Bucket bucket = bucketService.getById(bucketId);
        if (bucket != null) {
            ResourceRecord resourceRecord = new ResourceRecord();
            resourceRecord.setProjectId(bucket.getProjectId());
            resourceRecord.setBucketId(bucketId);
            resourceRecord.setResourceId(resourceId);
            resourceRecord.setOperationType(ResourceOperationTypeEnum.PREVIEW.value());
            resourceRecord.setKey(key);
            resourceRecord.setReturnCode(returnCode);
            resourceRecordService.insert(resourceRecord);
        }
    }
}
