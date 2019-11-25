package com.wondersgroup.cloud.paas.storage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiniu.cdn.CdnManager;
import com.qiniu.cdn.CdnResult;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Client;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.ResumeBlockInfo;
import com.qiniu.storage.model.StorageType;
import com.qiniu.util.Auth;
import com.qiniu.util.Crc32;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.enums.status.ValidStatusEnum;
import com.wondersgroup.cloud.paas.common.exception.BusinessException;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.common.utils.CommonUtils;
import com.wondersgroup.cloud.paas.common.utils.DateUtils;
import com.wondersgroup.cloud.paas.common.utils.MD5Utils;
import com.wondersgroup.cloud.paas.common.utils.QueryUtils;
import com.wondersgroup.cloud.paas.storage.constant.ResourceConstant;
import com.wondersgroup.cloud.paas.storage.enums.BucketTypeEnum;
import com.wondersgroup.cloud.paas.storage.enums.GranularityEnum;
import com.wondersgroup.cloud.paas.storage.enums.ResourceOperationTypeEnum;
import com.wondersgroup.cloud.paas.storage.enums.StorageTypeEnum;
import com.wondersgroup.cloud.paas.storage.mapper.ResourceMapper;
import com.wondersgroup.cloud.paas.storage.mapper.ext.ResourceExtendMapper;
import com.wondersgroup.cloud.paas.storage.model.*;
import com.wondersgroup.cloud.paas.storage.pojo.BlockRecord;
import com.wondersgroup.cloud.paas.storage.pojo.ResourcePojo;
import com.wondersgroup.cloud.paas.storage.service.*;
import com.wondersgroup.cloud.paas.storage.tools.CertificateTool;
import com.wondersgroup.cloud.paas.storage.tools.ResourceMangerTool;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 七牛文件上传实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceServiceImpl implements ResourceService {

    private Logger logger = Logger.getLogger(ResourceServiceImpl.class);

    @Autowired(required = false)
    private ResourceMapper resourceMapper;

    @Autowired(required = false)
    private AccountService accountService;

    @Autowired(required = false)
    private BucketService bucketService;

    @Autowired(required = false)
    private JedisCluster jedisCluster;

    @Autowired(required = false)
    private ResourceStatisticService resourceStatisticService;

    @Autowired(required = false)
    private ResourceExtendMapper resourceExtendMapper;

    @Autowired(required = false)
    private ResourceRecordService resourceRecordService;

    /**
     * 分页加载数据
     * @param bucketId 目录ID
     * @param fileType 文件类型
     * @param storageType 存储类型 0-标准 1-低频
     * @param pageNum 页数
     * @param pageSize 数量
     * @param orderByClause 排序
     * @return 有效集合
     */
    @Override
    public PageInfo<Resource> page(String bucketId,String fileName, String fileType, String storageType, int pageNum, int pageSize, String orderByClause) {
        if(StringUtils.isBlank(bucketId)){
            return new PageInfo<>(new ArrayList<>());
        }
        PageHelper.startPage(pageNum, pageSize);
        ResourceExample example = new ResourceExample();
        ResourceExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(bucketId)){
            criteria.andBucketIdEqualTo(bucketId);
        }

        if (StringUtils.isNotBlank(fileName)){
            criteria.andKeyLike(QueryUtils.generateLikeString(fileName));
        }

        if (StringUtils.isNotBlank(fileType)){
            criteria.andTypeEqualTo(fileType);
        }

        if (StringUtils.isNotBlank(storageType)){
            criteria.andStorageTypeEqualTo(storageType);
        }

        criteria.andValidFlagEqualTo(ValidStatusEnum.VALID.value());

        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        List<Resource> resourceList = resourceMapper.selectByExample(example);
        return new PageInfo<>(resourceList);
    }

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
    @Override
    public ResultMap upload(Resource resource,String bucketId,InputStream stream, String fileName,Boolean force) throws Exception{

        if(StringUtils.isBlank(bucketId) || StringUtils.isBlank(fileName)){
            return new ResultMap(CommonConstant.DATA_CHECK_FAILURE_CODE, CommonConstant.DATA_CHECK_FAILURE_MSG,fileName);
        }
        Bucket bucket = bucketService.getById(bucketId);
        if(bucket == null || StringUtils.isBlank(bucket.getAliasName())){
            return new ResultMap(ResourceConstant.BUCKET_NOT_FOUND_CODE, ResourceConstant.FROM_BUCKET_NOT_FOUND_MSG,fileName);
        }
        if(force == null){
            force = false;
        }
        Resource existResource = getResourceByBucketIdAndName(bucketId,fileName);
        if(existResource !=null && !force){//资源存在且不覆盖
            return new ResultMap(ResourceConstant.RESOURCE_EXISTS_CODE, ResourceConstant.RESOURCE_EXISTS_MSG,fileName);
        }

        Account account = accountService.getAccount();
        if(account == null){
            return new ResultMap(CommonConstant.ERROR_ACCESSKEY_CODE, CommonConstant.ERROR_ACCESSKEY_MSG);
        }

        Auth auth = Auth.create(account.getAccessKey(), account.getSecretKey());
        Configuration configuration = ResourceMangerTool.getInstance().buildConfiguration(bucket.getRegion());
        ResourceRecord resourceRecord = new ResourceRecord();
        resourceRecord.setProjectId(bucket.getProjectId());
        resourceRecord.setBucketId(bucket.getBucketId());
        resourceRecord.setKey(resource.getKey());
        resourceRecord.setOperationType(ResourceOperationTypeEnum.ADD.value());

        String returnMsg = null;
        try{
            String upToken;
            StringMap putPolicy = new StringMap();
            if(StorageTypeEnum.INFREQUENCY.value().equals(resource.getStorageType())){
                 putPolicy.put("fileType", 1);
            }
            if (force){
                upToken = auth.uploadToken(bucket.getAliasName(),fileName,ResourceConstant.UPLOAD_EXPIRES,putPolicy);//强制覆盖凭证
            }else {
                upToken = auth.uploadToken(bucket.getAliasName(),null,ResourceConstant.UPLOAD_EXPIRES,putPolicy);//普通上传凭证
            }

            UploadManager uploadManager = new UploadManager(configuration);
            Response response = uploadManager.put(stream,fileName,upToken,null,null);
            resourceRecord.setReturnCode(response.statusCode);
        }catch(QiniuException qex){
            if(qex.response != null){
                returnMsg =  qex.response.error;
                resourceRecord.setReturnCode(qex.response.statusCode);
            }else{
                returnMsg = CommonConstant.RESULT_ERROR;
            }
        }

        if(StringUtils.isBlank(returnMsg)){
            BucketManager bucketManager = new BucketManager(auth, configuration);
            //获取7牛存储信息
            ResourcePojo resourcePojo = getFileInfo(bucketManager,bucket.getAliasName(),fileName);
            if(existResource != null){//存在资源,且覆盖
                existResource.setUpdateTime(resourcePojo.getPutTime());
                int insertDelCode = resourceStatisticService.add(existResource,false);
                resourceRecord.setResourceId(existResource.getResourceId());
                if(insertDelCode < 1){
                    return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR,resourceRecord);
                }
                existResource.setFileSize(resourcePojo.getFsize());
                existResource.setCreateUser(resource.getCreateUser());
                existResource.setLifeCycle(resource.getLifeCycle());
                existResource.setStorageType(resource.getStorageType());
                existResource.setType(resourcePojo.getMimeType());
                existResource.setCreateTime(resourcePojo.getPutTime());
                existResource.setValidFlag(ValidStatusEnum.VALID.value());
                int resCode = resourceMapper.updateByPrimaryKey(existResource);
                if(resCode > 0 ){
                    int insertAddCode = resourceStatisticService.add(existResource,true);
                    if(insertAddCode < 1){
                        return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR,resourceRecord);
                    }
                    return new ResultMap(CommonConstant.SUCCESS, ResourceConstant.RESOURCE_COVER_SUCCESS_MSG,resourceRecord);
                }else{
                    return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_COVER_ERROR_MSG,resourceRecord);
                }
            }else{
                resource.setResourceId(CommonUtils.generateId());
                resource.setCreateTime(resourcePojo.getPutTime());
                resource.setUpdateTime(resourcePojo.getPutTime());
                resource.setFileSize(resourcePojo.getFsize());
                resource.setType(resourcePojo.getMimeType());
                int resCode = resourceMapper.insert(resource);
                resourceRecord.setResourceId(resource.getResourceId());
                if(resCode > 0){
                    int insertAddCode = resourceStatisticService.add(resource,true);
                    if(insertAddCode < 1){
                        return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR,resourceRecord);
                    }
                    return new ResultMap(CommonConstant.SUCCESS, ResourceConstant.RESOURCE_UPLOAD_SUCCESS_CN_MSG, resourceRecord);
                }else{
                    return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_UPLOAD_ERROR_MSG,resourceRecord);
                }
            }
        }else{
            logger.error(returnMsg);
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_UPLOAD_ERROR_MSG, resourceRecord);
        }

    }

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
    @Override
    public ResultMap copyOrMove(Bucket fromBucket,Resource resource,Bucket toBucket, String toKey, Boolean force,boolean ifCopy) throws Exception{
        if(force == null){
            force = false; //默认不覆盖
        }

        Resource existResource = getResourceByBucketIdAndName(toBucket.getBucketId(),toKey);
        if(existResource != null && !force){//目标资源已存在且不覆盖
            return new ResultMap(ResourceConstant.RESOURCE_EXISTS_CODE, ResourceConstant.RESOURCE_EXISTS_MSG);
        }

        Account account = accountService.getAccount();
        if(account == null){
            return new ResultMap(CommonConstant.ERROR_ACCESSKEY_CODE, CommonConstant.ERROR_ACCESSKEY_MSG);
        }

        Auth auth = Auth.create(account.getAccessKey(), account.getSecretKey());
        Configuration configuration = ResourceMangerTool.getInstance().buildConfiguration(fromBucket.getRegion());
        BucketManager bucketManager = new BucketManager(auth, configuration);

        ResourceRecord resourceRecord = new ResourceRecord();
        resourceRecord.setProjectId(fromBucket.getProjectId());
        resourceRecord.setBucketId(fromBucket.getBucketId());
        resourceRecord.setResourceId(resource.getResourceId());
        resourceRecord.setKey(resource.getKey());

        String returnMsg = null;
        try {
            Response response;
            if(ifCopy){
                resourceRecord.setOperationType(ResourceOperationTypeEnum.COPY.value());
                response = bucketManager.copy(fromBucket.getAliasName(), resource.getKey(), toBucket.getAliasName(), toKey, force);
            }else{
                resourceRecord.setOperationType(ResourceOperationTypeEnum.MOVE.value());
                response = bucketManager.move(fromBucket.getAliasName(), resource.getKey(), toBucket.getAliasName(), toKey, force);
            }
            resourceRecord.setReturnCode(response.statusCode);
        } catch (QiniuException ex) {
            if(ex.response != null){
                returnMsg =  ex.response.error;
                resourceRecord.setReturnCode(ex.response.statusCode);
            }else{
                returnMsg = CommonConstant.RESULT_ERROR;
            }
        }

        if(StringUtils.isBlank(returnMsg)){
            //获取目标空间区域信息等
            Configuration cfg = ResourceMangerTool.getInstance().buildConfiguration(toBucket.getRegion());
            BucketManager toBucketManager = new BucketManager(auth, cfg);
            //获取7牛存储信息
            ResourcePojo resourcePojo = getFileInfo(toBucketManager,toBucket.getAliasName(),toKey);
            int resCode;
            if(existResource != null){//资源覆盖
                existResource.setUpdateTime(resourcePojo.getPutTime());
                int insertDelCode = resourceStatisticService.add(existResource,false);

                if(insertDelCode < 1){
                    return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR,resourceRecord);
                }

                existResource.setStorageType(resource.getStorageType());
                existResource.setLifeCycle(resource.getLifeCycle());
                existResource.setCreateTime(resourcePojo.getPutTime());
                existResource.setValidFlag(ValidStatusEnum.VALID.value());
                existResource.setFileSize(resourcePojo.getFsize());
                existResource.setType(resourcePojo.getMimeType());
                resCode = resourceMapper.updateByPrimaryKey(existResource);
                if(resCode > 0){
                    if(!ifCopy){
                        resource.setUpdateTime(resourcePojo.getPutTime());
                        resource.setValidFlag(ValidStatusEnum.INVALID.value());
                        int delCode = resourceMapper.updateByPrimaryKey(resource);
                        if(delCode < 1){
                            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_MOVE_ERROR_MSG,resourceRecord);
                        }
                        int insertFromDelCode = resourceStatisticService.add(resource,false);

                        if(insertFromDelCode < 1){
                            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR,resourceRecord);
                        }
                    }
                    int insertAddCode = resourceStatisticService.add(existResource,true);
                    if(insertAddCode < 1){
                        return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR,resourceRecord);
                    }
                    return new ResultMap(CommonConstant.SUCCESS, ResourceConstant.RESOURCE_COVER_SUCCESS_MSG,resourceRecord);
                }else{
                    return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_COVER_ERROR_MSG,resourceRecord);
                }
            }else{
                if(ifCopy){
                    Resource resourceInsert = new Resource();
                    resourceInsert.setResourceId(CommonUtils.generateId());
                    resourceInsert.setBucketId(toBucket.getBucketId());
                    resourceInsert.setKey(toKey);
                    resourceInsert.setValidFlag(ValidStatusEnum.VALID.value());
                    resourceInsert.setStorageType(resource.getStorageType());
                    resourceInsert.setType(resourcePojo.getMimeType());
                    resourceInsert.setLifeCycle(ResourceConstant.DEFAULT_LIFE_CYCLE);
                    resourceInsert.setFileSize(resourcePojo.getFsize());
                    resourceInsert.setCreateTime(resourcePojo.getPutTime());
                    resourceInsert.setUpdateTime(resourcePojo.getPutTime());
                    resCode = resourceMapper.insert(resourceInsert);
                    if(resCode > 0 ){
                        int insertAddCode = resourceStatisticService.add(resourceInsert,true);
                        if(insertAddCode < 1){
                            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR,resourceRecord);
                        }
                        return new ResultMap(CommonConstant.SUCCESS, ResourceConstant.RESOURCE_COPY_SUCCESS_MSG,resourceRecord);
                    }else{
                        return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_COPY_ERROR_MSG,resourceRecord);
                    }
                }else{
                    resource.setUpdateTime(resourcePojo.getPutTime());
                    int insertDelCode = resourceStatisticService.add(resource,false);

                    if(insertDelCode < 1){
                        return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR,resourceRecord);
                    }

                    resource.setBucketId(toBucket.getBucketId());
                    resource.setKey(toKey);
                    resource.setCreateTime(resourcePojo.getPutTime());
                    resCode = resourceMapper.updateByPrimaryKey(resource);
                    if(resCode > 0 ){
                        int insertAddCode = resourceStatisticService.add(resource,true);
                        if(insertAddCode < 1){
                            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR,resourceRecord);
                        }
                        return new ResultMap(CommonConstant.SUCCESS, ResourceConstant.RESOURCE_MOVE_SUCCESS_MSG,resourceRecord);
                    }else{
                        return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_MOVE_ERROR_MSG,resourceRecord);
                    }
                }

            }
        }else{
            return new ResultMap(CommonConstant.ERROR, returnMsg, resourceRecord);
        }
    }

    /**
     * 文件删除
     * @param bucket 空间
     * @param resource 资源
     * @return 运行结果，无异常表示成功
     */
    @Override
    public ResultMap delete(Bucket bucket,Resource resource) throws Exception{

        Account account = accountService.getAccount();
        if(account == null){
            return new ResultMap(CommonConstant.ERROR_ACCESSKEY_CODE, CommonConstant.ERROR_ACCESSKEY_MSG);
        }

        Auth auth = Auth.create(account.getAccessKey(), account.getSecretKey());
        Configuration configuration = ResourceMangerTool.getInstance().buildConfiguration(bucket.getRegion());
        BucketManager bucketManager = new BucketManager(auth, configuration);

        ResourceRecord resourceRecord = new ResourceRecord();
        resourceRecord.setProjectId(bucket.getProjectId());
        resourceRecord.setBucketId(bucket.getBucketId());
        resourceRecord.setResourceId(resource.getResourceId());
        resourceRecord.setKey(resource.getKey());
        resourceRecord.setOperationType(ResourceOperationTypeEnum.DELETE.value());

        String returnMsg = null;
        try {
            Response response = bucketManager.delete(bucket.getAliasName(),resource.getKey());
            resourceRecord.setReturnCode(response.statusCode);
        } catch (QiniuException ex) {
            if(ex.response != null){
                returnMsg =  ex.response.error;
                resourceRecord.setReturnCode(ex.response.statusCode);
            }else{
                returnMsg = CommonConstant.RESULT_ERROR;
            }
        }

        if(StringUtils.isBlank(returnMsg)){
            resource.setValidFlag(ValidStatusEnum.INVALID.value());
            resource.setUpdateTime(new Date());
            int resCode = resourceMapper.updateByPrimaryKey(resource);
            if(resCode > 0 ){
                int insertDelCode = resourceStatisticService.add(resource,false);

                if(insertDelCode < 1){
                    return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR,resourceRecord);
                }
                return new ResultMap(CommonConstant.SUCCESS, ResourceConstant.RESOURCE_DELETE_SUCCESS_MSG,resourceRecord);
            }else{
                return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_DELETE_ERROR_MSG,resourceRecord);
            }
        }else{
            logger.error(returnMsg);
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_DELETE_ERROR_MSG, resourceRecord);
        }
    }

    /**
     * 根据主键Id获取资源
     * @param resourceId
     * @return
     */
    @Override
    public Resource getById(String resourceId){
        if(StringUtils.isBlank(resourceId)){
            return null;
        }
        Resource resource = resourceMapper.selectByPrimaryKey(resourceId);
        if(resource != null && ValidStatusEnum.VALID.value().equals(resource.getValidFlag())){
            return resource;
        }
        return null;
    }

    /**
     * 修改资源文件类型
     * @param bucketId 空间ID
     * @param key 资源名称
     * @param mimeType 资源类型
     */
    @Override
    public ResultMap updateFileType(String bucketId, String key, String mimeType) throws Exception {
        if(StringUtils.isBlank(key) || StringUtils.isBlank(mimeType)){
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.PARAMETER_MSG_ERROR);
        }
        Resource resource = getResourceByBucketIdAndName(bucketId,key);
        if(resource == null || ValidStatusEnum.INVALID.value().equals(resource.getValidFlag()) || StringUtils.isBlank(resource.getBucketId()) || StringUtils.isBlank(resource.getKey())) {
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_NOT_FOUND_MSG);
        }
        Bucket bucket = bucketService.getById(resource.getBucketId());
        if(bucket==null || ValidStatusEnum.INVALID.value().equals(bucket.getValidFlag())) {
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.FROM_BUCKET_NOT_FOUND_MSG);
        }

        Account account = accountService.getAccount();
        BucketManager bm= ResourceMangerTool.getInstance().buildBucketManger(account.getAccessKey(),account.getSecretKey(),bucket.getRegion());

        ResourceRecord resourceRecord = new ResourceRecord();
        resourceRecord.setProjectId(bucket.getProjectId());
        resourceRecord.setBucketId(bucket.getBucketId());
        resourceRecord.setResourceId(resource.getResourceId());
        resourceRecord.setKey(key);
        resourceRecord.setOperationType(ResourceOperationTypeEnum.UPDATE_FILE_TYPE.value());

        String returnMsg = null;
        try {
            Response response =  bm.changeMime(bucket.getAliasName(),resource.getKey(),mimeType);
            resourceRecord.setReturnCode(response.statusCode);
        } catch (QiniuException ex) {
            logger.error(ex.getMessage(), ex);
            if(ex.response != null){
                returnMsg =  ex.response.error;
                resourceRecord.setReturnCode(ex.response.statusCode);
            }else{
                returnMsg = CommonConstant.RESULT_ERROR;
            }
        }

        if(StringUtils.isBlank(returnMsg)){
            resource.setType(mimeType);
            resource.setUpdateTime(new Date());
            int updateCode = resourceMapper.updateByPrimaryKey(resource);
            if(updateCode < 1){
                return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_UPDATE_ERROR_MSG,resourceRecord);
            }
            return new ResultMap(CommonConstant.SUCCESS, ResourceConstant.RESOURCE_UPDATE_SUCCESS_MSG,resourceRecord);
        }else{
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_UPDATE_ERROR_MSG,resourceRecord);
        }
    }

    /**
     * 依据资源空间Id和文件名检索数据
     * @param bucketId 资源空间Id
     * @param key 文件名
     * @return 一个有效资源
     */
    @Override
    public Resource getResourceByBucketIdAndName(String bucketId, String key) {
        if(StringUtils.isBlank(bucketId) || StringUtils.isBlank(key)){
            return null;
        }
        ResourceExample example = new ResourceExample();
        example.createCriteria().andBucketIdEqualTo(bucketId).andKeyEqualTo(key).andValidFlagEqualTo(ValidStatusEnum.VALID.value());
        List<Resource> resourceList = resourceMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(resourceList)){
            return null;
        }else{
            return resourceList.get(0);
        }
    }

    /**
     * 批量复制文件
     * @param formBucket 文件空间(样本，所有操作资源必须在这个空间下)
     * @param keys 资源名称数组
     * @param toBucketIds 目标文件空间
     * @param toKeys 目标文件
     * @param isCopy true-复制（默认）  false-移动
     * @return 运行结果，无异常表示成功
     */
    @Override
    public ResultMap batchCopyOrMove(Bucket formBucket,String[] keys, String[] toBucketIds,String[] toKeys,boolean isCopy) {

        List<String> doCopyResourceIds = new ArrayList<>();
        List<String> doFromKeys = new ArrayList<>();
        List<String> doToBucketIds = new ArrayList<>();
        List<String> doToBucketNames = new ArrayList<>();
        List<String> doToKeys = new ArrayList<>();

        for(int i=0;i<keys.length;i++){
            if(!doCopyResourceIds.contains(keys[i])){
                Resource resource = getResourceByBucketIdAndName(formBucket.getBucketId(),keys[i]);
                if(resource != null){
                    if(toBucketIds == null || toBucketIds.length == 0){//目标空间与源空间一致
                        if(!keys[i].equals(toKeys[i])){//检查文件是否相同和检验临时空间有效性
                            doCopyResourceIds.add(resource.getResourceId());
                            doFromKeys.add(resource.getKey());
                            doToBucketNames.add(formBucket.getAliasName());
                            doToBucketIds.add(resource.getBucketId());
                            doToKeys.add(toKeys[i]);
                        }
                    }else{
                        Bucket toBucket = bucketService.getById(toBucketIds[i]);
                        if(toBucket != null  && formBucket.getProjectId().equals(toBucket.getProjectId())
                                && StringUtils.isNotBlank(toBucket.getAliasName())
                                && checkBucketOver(toBucketIds[i],true,resource.getFileSize())){
                            doCopyResourceIds.add(resource.getResourceId());
                            doFromKeys.add(resource.getKey());
                            doToBucketNames.add(toBucket.getAliasName());
                            doToBucketIds.add(toBucketIds[i]);
                            doToKeys.add(toKeys[i]);
                        }
                    }
                }
            }
        }

        if(CollectionUtils.isEmpty(doCopyResourceIds)){
            return new ResultMap(ResourceConstant.RESOURCE_NOT_FOUND_CODE, ResourceConstant.RESOURCE_NOT_FOUND_MSG);
        }

        Account account = accountService.getAccount();
        if(account == null){
            return new ResultMap(CommonConstant.ERROR_ACCESSKEY_CODE, CommonConstant.ERROR_ACCESSKEY_MSG);
        }

        Auth auth = Auth.create(account.getAccessKey(), account.getSecretKey());
        Configuration configuration = ResourceMangerTool.getInstance().buildConfiguration(formBucket.getRegion());

        BucketManager bucketManager = new BucketManager(auth, configuration);
        BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();

        for (int i = 0; i < doCopyResourceIds.size(); i++) {
            if(isCopy) {
                batchOperations.addCopyOp(formBucket.getAliasName(), doFromKeys.get(i), doToBucketNames.get(i), doToKeys.get(i));
            }else{
                batchOperations.addMoveOp(formBucket.getAliasName(), doFromKeys.get(i), doToBucketNames.get(i), doToKeys.get(i));
            }
        }

        StringBuilder errorData = new StringBuilder();
        boolean existsSuccess = false;
        try{
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);

            for (int i = 0; i < doCopyResourceIds.size(); i++) {
                BatchStatus status = batchStatusList[i];
                if (status.code == 200) {
                    try{
                        existsSuccess = true;
                        ResourcePojo resourcePojo;//存放文件信息
                        if (doToBucketIds.get(i).equals(formBucket.getBucketId())) {//相同空间内
                            resourcePojo = getFileInfo(bucketManager, formBucket.getAliasName(), doToKeys.get(i));
                        } else {
                            Bucket toBucket = bucketService.getById(doToBucketIds.get(i));
                            Configuration cfg = ResourceMangerTool.getInstance().buildConfiguration(toBucket.getRegion());
                            BucketManager toBucketManager = new BucketManager(auth, cfg);
                            resourcePojo = getFileInfo(toBucketManager, toBucket.getAliasName(), doToKeys.get(i));
                        }

                        Resource resource = getById(doCopyResourceIds.get(i));
                        if(resource != null){
                            if(isCopy){
                                resource.setResourceId(CommonUtils.generateId());
                                resource.setCreateTime(resourcePojo.getPutTime());
                                resource.setUpdateTime(resourcePojo.getPutTime());
                                resource.setBucketId(doToBucketIds.get(i));
                                resource.setKey(doToKeys.get(i));
                                int resCode = resourceMapper.insert(resource);
                                if(resCode < 1){
                                    errorData.append("资源(").append(doToKeys.get(i)).append(")复制成功,但数据库执行失败;");
                                }
                                int insertAddCode = resourceStatisticService.add(resource, true);

                                if (insertAddCode < 1) {
                                    errorData.append("资源(").append(doToKeys.get(i)).append(")").append(ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR).append(";");
                                }
                            }else{
                                resource.setUpdateTime(resourcePojo.getPutTime());
                                int insertDelCode = resourceStatisticService.add(resource, false);

                                if (insertDelCode < 1) {
                                    errorData.append("资源(").append(doToKeys.get(i)).append(")").append(ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR).append(";");
                                }

                                resource.setBucketId(doToBucketIds.get(i));
                                resource.setKey(doToKeys.get(i));
                                resource.setCreateTime(resourcePojo.getPutTime());

                                int resCode = resourceMapper.updateByPrimaryKey(resource);
                                if(resCode < 1){
                                    errorData.append("资源(").append(doToKeys.get(i)).append(")移动失败,但数据库执行失败;");
                                }
                                int insertAddCode = resourceStatisticService.add(resource, true);

                                if (insertAddCode < 1) {
                                    errorData.append("资源(").append(doToKeys.get(i)).append(")").append(ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR).append(";");
                                }
                            }
                        }else{
                            errorData.append("资源(").append(doToKeys.get(i)).append(")不存在或已被删除;");
                        }
                    }catch(Exception e){
                        if(isCopy){
                            errorData.append("资源(").append(doFromKeys.get(i)).append(")复制成功,数据库执行异常;");
                        }else{
                            errorData.append("资源(").append(doFromKeys.get(i)).append(")移动失败,数据库执行异常;");
                        }
                    }
                } else {
                    String errorMsg = status.data.error;
                    if (ResourceConstant.BATCH_EXISTS_FILE_ERROR.equals(errorMsg)) {
                        errorMsg = ResourceConstant.EXISTS_FILE_ERROR;
                    }
                    if(isCopy){
                        errorData.append("资源(").append(doFromKeys.get(i)).append(")复制失败:").append(errorMsg).append(";");
                    }else{
                        errorData.append("资源(").append(doFromKeys.get(i)).append(")移动失败:").append(errorMsg).append(";");
                    }
                }
                try{
                    ResourceRecord resourceRecord = new ResourceRecord();
                    resourceRecord.setProjectId(formBucket.getProjectId());
                    resourceRecord.setBucketId(formBucket.getBucketId());
                    resourceRecord.setResourceId(doCopyResourceIds.get(i));
                    if(isCopy){
                        resourceRecord.setOperationType(ResourceOperationTypeEnum.BATCH_COPY.value());
                    }else{
                        resourceRecord.setOperationType(ResourceOperationTypeEnum.BATCH_MOVE.value());
                    }
                    resourceRecord.setKey(doFromKeys.get(i));
                    resourceRecord.setReturnCode(status.code);
                    resourceRecordService.insert(resourceRecord);
                }catch (Exception ex) {
                    logger.error(ResourceConstant.RESOURCE_RECORD_ERROR,ex);
                }
            }
        } catch (QiniuException e) {
            if(e.response != null){
                errorData.append("批量操作失败：").append(e.response.error);
                try{
                    ResourceRecord resourceRecord = new ResourceRecord();
                    resourceRecord.setProjectId(formBucket.getProjectId());
                    resourceRecord.setBucketId(formBucket.getBucketId());
                    resourceRecord.setResourceId("");
                    if(isCopy){
                        resourceRecord.setOperationType(ResourceOperationTypeEnum.BATCH_COPY.value());
                    }else{
                        resourceRecord.setOperationType(ResourceOperationTypeEnum.BATCH_MOVE.value());
                    }
                    resourceRecord.setKey(keys[0]);
                    resourceRecord.setReturnCode(e.response.statusCode);
                    resourceRecordService.insert(resourceRecord);
                }catch (Exception ex) {
                    logger.error(ResourceConstant.RESOURCE_RECORD_ERROR,ex);
                }
            }else{
                errorData.append("批量操作失败：").append(CommonConstant.RESULT_ERROR);
            }
        }

        if(StringUtils.isBlank(errorData.toString())){
            if(isCopy){
                return new ResultMap(CommonConstant.SUCCESS, ResourceConstant.RESOURCE_BATCH_COPY_SUCCESS_MSG);
            }else{
                return new ResultMap(CommonConstant.SUCCESS, ResourceConstant.RESOURCE_BATCH_MOVE_SUCCESS_MSG);
            }
        }else{
            if(isCopy){
                if(existsSuccess){
                    return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_BATCH_COPY_HALF_ERROR_MSG,errorData.toString());
                }else{
                    return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_BATCH_COPY_ERROR_MSG,errorData.toString());
                }
            }else{
                if(existsSuccess){
                    return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_BATCH_MOVE_HALF_ERROR_MSG,errorData.toString());
                }else{
                    return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_BATCH_MOVE_ERROR_MSG,errorData.toString());
                }
            }
        }
    }

    /**
     * 批量删除（同一个bucket下）
     * @param keys  资源数组
     * @param bucket 文件空间(样本，所有资源必须在这个空间下)
     * @return 运行结果
     */
    @Override
    public ResultMap batchDelete(String[] keys,Bucket bucket){
        List<String> doDelResourceIds = new ArrayList<>();
        List<String> doDelKeys = new ArrayList<>();

        Arrays.asList(keys).stream().forEach(key -> {
            if(!doDelKeys.contains(key)){
                Resource resource = getResourceByBucketIdAndName(bucket.getBucketId(),key);
                if(resource != null){
                    doDelResourceIds.add(resource.getResourceId());
                    doDelKeys.add(resource.getKey());
                }
            }
        });

        if(CollectionUtils.isEmpty(doDelResourceIds) || CollectionUtils.isEmpty(doDelKeys)){
            return new ResultMap(ResourceConstant.RESOURCE_NOT_FOUND_CODE, ResourceConstant.RESOURCE_NOT_FOUND_MSG);
        }

        Account account = accountService.getAccount();
        if(account == null){
            return new ResultMap(CommonConstant.ERROR_ACCESSKEY_CODE, CommonConstant.ERROR_ACCESSKEY_MSG);
        }

        Auth auth = Auth.create(account.getAccessKey(), account.getSecretKey());
        Configuration configuration = ResourceMangerTool.getInstance().buildConfiguration(bucket.getRegion());

        BucketManager bucketManager = new BucketManager(auth, configuration);
        BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
        batchOperations.addDeleteOp(bucket.getAliasName(), doDelKeys.toArray(new String[doDelKeys.size()]));

        StringBuilder errorData = new StringBuilder();
        boolean existsSuccess = false;
        try{
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);

            for (int i = 0; i < doDelResourceIds.size(); i++) {
                BatchStatus status = batchStatusList[i];
                if (status.code == 200) {
                    existsSuccess = true;
                    Resource resource = getById(doDelResourceIds.get(i));
                    if(resource != null){
                        resource.setValidFlag(ValidStatusEnum.INVALID.value());
                        resource.setUpdateTime(new Date());
                        try{
                            int resCode = resourceMapper.updateByPrimaryKey(resource);
                            if(resCode < 1){
                                errorData.append("资源(").append(doDelKeys.get(i)).append(")删除成功,但数据库执行失败;");
                            }
                            int insertDelCode = resourceStatisticService.add(resource, false);

                            if (insertDelCode < 1) {
                                errorData.append("资源(").append(doDelKeys.get(i)).append(")").append(ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR).append(";");
                            }
                        }catch(Exception e){
                            errorData.append("资源(").append(doDelKeys.get(i)).append(")删除成功,但数据库执行异常;");
                        }
                    }
                } else {
                    errorData.append("资源(").append(doDelKeys.get(i)).append(")删除失败:").append(status.data.error).append(";");
                }
                try{
                    ResourceRecord resourceRecord = new ResourceRecord();
                    resourceRecord.setProjectId(bucket.getProjectId());
                    resourceRecord.setBucketId(bucket.getBucketId());
                    resourceRecord.setResourceId(doDelResourceIds.get(i));
                    resourceRecord.setOperationType(ResourceOperationTypeEnum.BATCH_DELETE.value());
                    resourceRecord.setReturnCode(status.code);
                    resourceRecord.setKey(doDelKeys.get(i));
                    resourceRecordService.insert(resourceRecord);
                }catch (Exception e) {
                    logger.error(ResourceConstant.RESOURCE_RECORD_ERROR,e);
                }

            }

        } catch (QiniuException e) {
            if(e.response != null){
                errorData.append("批量删除操作失败：").append(e.response.error);
                try{
                    ResourceRecord resourceRecord = new ResourceRecord();
                    resourceRecord.setProjectId(bucket.getProjectId());
                    resourceRecord.setBucketId(bucket.getBucketId());
                    resourceRecord.setResourceId("");
                    resourceRecord.setOperationType(ResourceOperationTypeEnum.BATCH_DELETE.value());
                    resourceRecord.setReturnCode(e.response.statusCode);
                    resourceRecord.setKey(keys[0]);
                    resourceRecordService.insert(resourceRecord);
                }catch (Exception ex) {
                    logger.error(ResourceConstant.RESOURCE_RECORD_ERROR,ex);
                }

            }else{
                errorData.append("批量删除操作失败：").append(CommonConstant.RESULT_ERROR);
            }
        }
        if(StringUtils.isBlank(errorData.toString())){
            return new ResultMap(CommonConstant.SUCCESS, ResourceConstant.RESOURCE_BATCH_DELETE_SUCCESS_MSG);
        }else{
            if(existsSuccess){
                return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_BATCH_DELETE_HALF_ERROR_MSG,errorData.toString());
            }
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_BATCH_DELETE_ERROR_MSG,errorData.toString());
        }
    }

    /**
     * 下载链接
     * @param resource
     * @return
     */
    @Override
    public String generateDownloadUrl(Resource resource,String expire) throws Exception{
        String url = "";
        if(resource != null){
            Account account = accountService.getAccount();
            Bucket bucket = bucketService.getById(resource.getBucketId());
            if(bucket != null){
                String publicUrl = CommonConstant.HTTP_PROTOCOL + CertificateTool.generatePublicDownloadUrl(resource.getKey(),bucket.getDomain());
                if(BucketTypeEnum.PUBLIC.value().equals(bucket.getType())){
                    return publicUrl;
                }

                if(BucketTypeEnum.PRIVATE.value().equals(bucket.getType())){
                    if(StringUtils.isNotEmpty(expire)){
                        url = CertificateTool.generatePrivateDownloadUrl(account.getAccessKey(), account.getSecretKey(), publicUrl,Long.valueOf(expire));
                    }else{
                        url = CertificateTool.generatePrivateDownloadUrl(account.getAccessKey(), account.getSecretKey(), publicUrl);
                    }
                }
            }
        }else{
            throw new BusinessException(ResourceConstant.FIND_CODE_FAIL,ResourceConstant.FIND_MSG_FAIL);
        }
        return url;
    }

    /**
     * 低频存储-普通存储切换
     * @param bucket 空间信息
     * @param resource 资源信息
     * @param storageType 修改后的存储类型
     * @return
     */
    @Override
    public ResultMap changeStorageType(Bucket bucket,Resource resource,String storageType) throws Exception{

        Account account = accountService.getAccount();
        if(account == null){
            return new ResultMap(CommonConstant.ERROR_ACCESSKEY_CODE, CommonConstant.ERROR_ACCESSKEY_MSG);
        }

        Auth auth = Auth.create(account.getAccessKey(), account.getSecretKey());
        Configuration configuration = ResourceMangerTool.getInstance().buildConfiguration(bucket.getRegion());
        BucketManager bucketManager = new BucketManager(auth, configuration);

        ResourceRecord resourceRecord = new ResourceRecord();
        resourceRecord.setProjectId(bucket.getProjectId());
        resourceRecord.setBucketId(bucket.getBucketId());
        resourceRecord.setResourceId(resource.getResourceId());
        resourceRecord.setOperationType(ResourceOperationTypeEnum.UPDATE_STORAGE_TYPE.value());
        resourceRecord.setKey(resource.getKey());
        String returnMsg = null;
        try {
            Response response;
            if(StorageTypeEnum.COMMON.value().equals(storageType)){
                response = bucketManager.changeType(bucket.getAliasName(),resource.getKey(), StorageType.COMMON);
            }else{
                response = bucketManager.changeType(bucket.getAliasName(),resource.getKey(), StorageType.INFREQUENCY);
            }
            resourceRecord.setReturnCode(response.statusCode);
        } catch (QiniuException ex) {
            if(ex.response != null){
                returnMsg =  ex.response.error;
                resourceRecord.setReturnCode(ex.response.statusCode);
            }else{
                returnMsg = CommonConstant.RESULT_ERROR;
            }
        }

        if(StringUtils.isBlank(returnMsg)){
            Date date = new Date();
            resource.setUpdateTime(date);
            int insertDelCode = resourceStatisticService.add(resource,false);
            if(insertDelCode < 1){
                return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR,resourceRecord);
            }
            resource.setStorageType(storageType);
            int resCode = resourceMapper.updateByPrimaryKey(resource);
            if(resCode > 0){
                resource.setCreateTime(date);
                int insertAddCode = resourceStatisticService.add(resource,true);
                if(insertAddCode < 1){
                    return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR,resourceRecord);
                }
                return new ResultMap(CommonConstant.SUCCESS, ResourceConstant.RESOURCE_UPDATE_SUCCESS_MSG,resourceRecord);
            }else{
                return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_UPDATE_ERROR_MSG,resourceRecord);
            }
        }else{
            logger.error(returnMsg);
            return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_UPDATE_ERROR_MSG, resourceRecord);
        }
    }

    /**
     * 检查文件
     * @param fileName 文件名称
     * @param key 目标文件名称
     * @param totalSize 文件总大小
     * @param lastModifiedTime 最后修改时间
     * @return
     */
    @Override
    public ResultMap checkFile(String fileName, String key, long totalSize, String lastModifiedTime) {

        String redisStr = fileName + "_" + key + "_" + totalSize + "_" + lastModifiedTime;
        String redisHashKey =  MD5Utils.encode("H"+redisStr);//哈希key 存放文件名称、总大小、已上传大小、存储文件名、已传入快数量
        String redisListKey =  MD5Utils.encode("L"+redisStr);//列表key 存放七牛返回块索引
        String redisIndexKey =  MD5Utils.encode("I"+redisStr);//存放上传块数量
        Boolean existsHashKey = jedisCluster.exists(redisHashKey);
        Boolean existsListKey = jedisCluster.exists(redisListKey);
        Boolean existsIndexKey = jedisCluster.exists(redisIndexKey);
        //计算总block数量
        int blockIndex = 1;

        if (totalSize != 0) {
            blockIndex = ((int) (totalSize / ResourceConstant.BLOCK_MAX_SIZE)) + (totalSize % ResourceConstant.BLOCK_MAX_SIZE == 0 ? 0 : 1);
        }

        if(existsHashKey != null && existsHashKey && existsListKey != null && existsListKey && existsIndexKey != null && existsIndexKey){
            //获取redis数据
            String redisRecordLastModifiedTime = jedisCluster.hget(redisHashKey,ResourceConstant.REDIS_HASH_LAST_MODIFIED_TIME);
            String redisRecordKey = jedisCluster.hget(redisHashKey,ResourceConstant.REDIS_HASH_TO_KEY);
            String redisRecordFileSize = jedisCluster.hget(redisHashKey,ResourceConstant.REDIS_HASH_FILE_SIZE);
            String redisRecordFileName = jedisCluster.hget(redisHashKey,ResourceConstant.REDIS_HASH_FILE_NAME);

            //校验文件是否完整或被修改
            if(StringUtils.isNotBlank(redisRecordLastModifiedTime) && StringUtils.isNotBlank(redisRecordKey)
                    && StringUtils.isNotBlank(redisRecordFileSize) && StringUtils.isNotBlank(redisRecordFileName)
                    && redisRecordLastModifiedTime.equals(lastModifiedTime) && redisRecordKey.equals(key)
                    && Long.valueOf(redisRecordFileSize) == totalSize && redisRecordFileName.equals(fileName)){
                List<Integer> existsResumeIds = new ArrayList<>();
                for(int i=0;i<blockIndex;i++){
                    if(!(i+"").equals(jedisCluster.lindex(redisListKey,i))){
                        existsResumeIds.add(i);
                    }
                }
                return new ResultMap(CommonConstant.SUCCESS,ResourceConstant.RESOURCE_RESUME_UPLOAD_EXISTS_FILE_MSG,existsResumeIds);
            }else{
                try{
                    for(int i=0;i<blockIndex;i++){
                        jedisCluster.rpush(redisListKey,i+"");
                    }
                    jedisCluster.expire(redisListKey,ResourceConstant.REDIS_FILE_RECORD_MAX_TIME);
                    return new ResultMap(CommonConstant.SUCCESS,ResourceConstant.RESOURCE_RESUME_UPLOAD_ERROR_FILE_MSG);
                }catch(Exception e){
                    return new ResultMap(CommonConstant.ERROR,CommonConstant.RESULT_ERROR);
                }
            }
        }else{
            try{
                for(int i=0;i<blockIndex;i++){
                    jedisCluster.rpush(redisListKey,i+"");
                }
                jedisCluster.expire(redisListKey,ResourceConstant.REDIS_FILE_RECORD_MAX_TIME);
                return new ResultMap(CommonConstant.SUCCESS,ResourceConstant.RESOURCE_RESUME_UPLOAD_RELOAD_FILE_MSG);
            }catch(Exception e){
                return new ResultMap(CommonConstant.ERROR,CommonConstant.RESULT_ERROR);
            }
        }
    }

    /**
     * 断点续传
     * @param blockRecord 块信息
     * @return
     */
    @Override
    public ResultMap resumeUpload(BlockRecord blockRecord) {
        try{
            Account account = accountService.getAccount();
            if(account == null){
                return new ResultMap(CommonConstant.ERROR_ACCESSKEY_CODE, CommonConstant.ERROR_ACCESSKEY_MSG);
            }

            Auth auth = Auth.create(account.getAccessKey(), account.getSecretKey());
            Configuration configuration = ResourceMangerTool.getInstance().buildConfiguration(blockRecord.getBucket().getRegion());

            StringMap putPolicy = new StringMap();
            if(StorageTypeEnum.INFREQUENCY.value().equals(blockRecord.getStorageType())){
                putPolicy.put("fileType", 1);//设置低频存储
            }

            String upToken = auth.uploadToken(blockRecord.getBucket().getAliasName(),null,ResourceConstant.UPLOAD_EXPIRES,putPolicy);//普通上传凭证
            String host = configuration.upHost(upToken);
            Client client = new Client(configuration);

            byte[] blockBuffer = new byte[blockRecord.getBlockSize()];
            try {
                blockRecord.getFile().read(blockBuffer, 0, blockRecord.getBlockSize());
            } catch (IOException var22) {
                return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_RESUME_UPLOAD_READ_FILE_ERROR);
            }
            boolean retry = false;
            long crc = Crc32.bytes(blockBuffer, 0, blockRecord.getBlockSize());
            Response response = null;
            ResourceRecord resourceRecord = new ResourceRecord();
            resourceRecord.setProjectId(blockRecord.getBucket().getProjectId());
            resourceRecord.setBucketId(blockRecord.getBucket().getBucketId());
            resourceRecord.setResourceId("");
            resourceRecord.setOperationType(ResourceOperationTypeEnum.MAKE_BLOCK.value());
            resourceRecord.setKey(blockRecord.getKey());
            try {
                response = makeBlock(client,upToken,host,blockBuffer, blockRecord.getBlockSize());
                resourceRecord.setReturnCode(response.statusCode);
                try{
                    resourceRecordService.insert(resourceRecord);
                }catch (Exception e) {
                    logger.error(ResourceConstant.RESOURCE_RECORD_ERROR,e);
                }
            } catch (QiniuException var27) {
                if (var27.code() < 0) {
                    host = configuration.upHostBackup(upToken);
                }
                if (var27.response != null && !var27.response.needRetry()) {
                    resourceRecord.setReturnCode(var27.response.statusCode);
                    try{
                        resourceRecordService.insert(resourceRecord);
                    }catch (Exception e) {
                        logger.error(ResourceConstant.RESOURCE_RECORD_ERROR,e);
                    }
                    logger.error(var27.response.toString());
                    return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_RESUME_UPLOAD_MAKE_BLOCK_ERROR);
                }
                retry = true;
            }

            ResumeBlockInfo blockInfo;
            if (!retry) {
                blockInfo = response.jsonToObject(ResumeBlockInfo.class);
                if (blockInfo.crc32 != crc) {
                    retry = true;
                }
            }

            if (retry && configuration.retryMax > 0) {
                response = makeBlock(client,upToken,host,blockBuffer, blockRecord.getBlockSize());
                resourceRecord.setReturnCode(response.statusCode);
                try{
                    resourceRecordService.insert(resourceRecord);
                }catch (Exception e) {
                    logger.error(ResourceConstant.RESOURCE_RECORD_ERROR,e);
                }
            }

            blockInfo = response.jsonToObject(ResumeBlockInfo.class);
            if (blockInfo.crc32 != crc) {
                return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_RESUME_UPLOAD_READ_BLOCK_ERROR);
            }

            int blockIndexNow = -1;
            if(!blockRecord.isMakeFile()){
                Long blockIndexLong =  jedisCluster.incr(blockRecord.getRedisIndexKey());
                blockIndexNow = blockIndexLong.intValue();//当前第几块
            }

            jedisCluster.lset(blockRecord.getRedisListKey(),blockRecord.getResumeId(),blockInfo.ctx);//记录分片返回信息

            //计算总block数量
            int blockIndex = 1;
            if (blockRecord.getTotalSize() != 0) {
                blockIndex = ((int) (blockRecord.getTotalSize() / ResourceConstant.BLOCK_MAX_SIZE)) + (blockRecord.getTotalSize() % ResourceConstant.BLOCK_MAX_SIZE == 0 ? 0 : 1);
            }

            if (blockIndexNow == blockIndex || blockRecord.isMakeFile()) {
                //先检查是否存在该文件
                Resource existsResource = getResourceByBucketIdAndName(blockRecord.getBucket().getBucketId(), blockRecord.getKey());

                //查询缓存数据
                List<String> contextsList = jedisCluster.lrange(blockRecord.getRedisListKey(),0,blockIndex-1);
                String[] contexts = contextsList.toArray(new String[contextsList.size()]);
                resourceRecord.setOperationType(ResourceOperationTypeEnum.MAKE_FILE.value());
                Response makeResponse = null;
                try {
                    //执行合并
                    makeResponse = makeFile(client,upToken,contexts,host,blockRecord.getTotalSize(),ResourceConstant.FILE_UPLOAD_MIME,blockRecord.getFileName(),blockRecord.getKey(),null);
                    resourceRecord.setReturnCode(makeResponse.statusCode);
                    try{
                        resourceRecordService.insert(resourceRecord);
                    }catch (Exception e) {
                        logger.error(ResourceConstant.RESOURCE_RECORD_ERROR,e);
                    }
                } catch (QiniuException var25) {
                    resourceRecord.setReturnCode(var25.response.statusCode);
                    try{
                        resourceRecordService.insert(resourceRecord);
                    }catch (Exception e) {
                        logger.error(ResourceConstant.RESOURCE_RECORD_ERROR,e);
                    }
                    try {
                        makeResponse = makeFile(client,upToken,contexts,host,blockRecord.getTotalSize(),ResourceConstant.FILE_UPLOAD_MIME,blockRecord.getFileName(),blockRecord.getKey(),null);
                        resourceRecord.setReturnCode(makeResponse.statusCode);
                        try{
                            resourceRecordService.insert(resourceRecord);
                        }catch (Exception e) {
                            logger.error(ResourceConstant.RESOURCE_RECORD_ERROR,e);
                        }
                    } catch (QiniuException var20) {
                        resourceRecord.setReturnCode(var20.response.statusCode);
                        try{
                            resourceRecordService.insert(resourceRecord);
                        }catch (Exception e) {
                            logger.error(ResourceConstant.RESOURCE_RECORD_ERROR,e);
                        }
                        logger.error(var20.response.toString());
                        return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_RESUME_UPLOAD_MAKE_FILE_ERROR);
                    }
                }

                jedisCluster.del(blockRecord.getRedisHashKey());
                jedisCluster.del(blockRecord.getRedisListKey());
                jedisCluster.del(blockRecord.getRedisIndexKey());

                BucketManager bucketManager = new BucketManager(auth, configuration);
                ResourcePojo resourcePojo  = getFileInfo(bucketManager,blockRecord.getBucket().getAliasName(),blockRecord.getKey());
                int insertAddCode;
                if (existsResource != null) {
                    existsResource.setUpdateTime(resourcePojo.getPutTime());
                    int insertDelCode = resourceStatisticService.add(existsResource, false);
                    if (insertDelCode < 1) {
                        return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR);
                    }
                    existsResource.setStorageType(blockRecord.getStorageType());
                    existsResource.setValidFlag(ValidStatusEnum.VALID.value());
                    existsResource.setFileSize(resourcePojo.getFsize());
                    existsResource.setLifeCycle(-1);
                    existsResource.setKey(blockRecord.getKey());
                    existsResource.setBucketId(blockRecord.getBucket().getBucketId());
                    existsResource.setType(resourcePojo.getMimeType());
                    existsResource.setCreateTime(resourcePojo.getPutTime());
                    existsResource.setUpdateTime(resourcePojo.getPutTime());
                    int resCode = resourceMapper.updateByPrimaryKeySelective(existsResource);
                    if(resCode > 0){
                        insertAddCode = resourceStatisticService.add(existsResource,true);
                    }else{
                        return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_UPLOAD_ERROR_MSG);
                    }
                }else{
                    Resource resource = new Resource();
                    resource.setResourceId(CommonUtils.generateId());
                    resource.setStorageType(blockRecord.getStorageType());
                    resource.setValidFlag(ValidStatusEnum.VALID.value());
                    resource.setFileSize(resourcePojo.getFsize());
                    resource.setLifeCycle(-1);
                    resource.setKey(blockRecord.getKey());
                    resource.setBucketId(blockRecord.getBucket().getBucketId());
                    resource.setType(resourcePojo.getMimeType());
                    resource.setCreateTime(resourcePojo.getPutTime());
                    resource.setUpdateTime(resourcePojo.getPutTime());
                    int resCode = resourceMapper.insert(resource);
                    if(resCode > 0){
                        insertAddCode = resourceStatisticService.add(resource,true);
                    }else{
                        return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_UPLOAD_ERROR_MSG);
                    }
                }

                if(insertAddCode < 1){
                    return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR);
                }
                return new ResultMap(CommonConstant.SUCCESS, ResourceConstant.RESOURCE_UPLOAD_SUCCESS_MSG);
            }else{
                jedisCluster.hset(blockRecord.getRedisHashKey(),ResourceConstant.REDIS_HASH_FILE_NAME,blockRecord.getFileName());
                jedisCluster.hset(blockRecord.getRedisHashKey(),ResourceConstant.REDIS_HASH_TO_KEY,blockRecord.getKey());
                jedisCluster.hset(blockRecord.getRedisHashKey(),ResourceConstant.REDIS_HASH_FILE_SIZE,blockRecord.getTotalSize()+"");
                jedisCluster.hset(blockRecord.getRedisHashKey(),ResourceConstant.REDIS_HASH_LAST_MODIFIED_TIME,blockRecord.getLastModifiedTime());
                if(blockIndexNow == 1){
                    jedisCluster.expire(blockRecord.getRedisHashKey(),ResourceConstant.REDIS_FILE_RECORD_MAX_TIME);
                    jedisCluster.expire(blockRecord.getRedisIndexKey(),ResourceConstant.REDIS_FILE_RECORD_MAX_TIME);
                }
                return new ResultMap(CommonConstant.SUCCESS, ResourceConstant.RESUME_UPLOAD_SUCCESS_MSG);
            }

        } catch (Exception e) {
            return new ResultMap(CommonConstant.ERROR, CommonConstant.ADD_ERROR);
        }
    }

    private Response makeBlock(Client client,String upToken,String host, byte[] block, int blockSize) throws QiniuException {
        String url = host + "/mkblk/" + blockSize;
        return post(client,upToken,url, block, 0, blockSize);
    }

    private Response post(Client client,String upToken,String url, byte[] data, int offset, int size) throws QiniuException {
        return client.post(url, data, offset, size, (new StringMap()).put("Authorization", "UpToken " + upToken), "application/octet-stream");
    }

    private String fileUrl(String host,long size,String mime,String fileName,String key,StringMap params) {
        String url = host + "/mkfile/" + size + "/mimeType/" + UrlSafeBase64.encodeToString(mime) + "/fname/" + UrlSafeBase64.encodeToString(fileName);
        final StringBuilder b = new StringBuilder(url);
        if (key != null) {
            b.append("/key/");
            b.append(UrlSafeBase64.encodeToString(key));
        }

        if (params != null) {
            params.forEach(new StringMap.Consumer() {
                public void accept(String key, Object value) {
                    b.append("/");
                    b.append(key);
                    b.append("/");
                    b.append(UrlSafeBase64.encodeToString("" + value));
                }
            });
        }

        return b.toString();
    }

    private Response makeFile(Client client,String upToken,String[] contexts,String host,long size,String mime,String fileName,String key,StringMap params) throws QiniuException {
        String url = this.fileUrl(host,size,mime,fileName,key,params);
        String s = com.qiniu.util.StringUtils.join(contexts, ",");
        return this.post(client,upToken,url, com.qiniu.util.StringUtils.utf8Bytes(s));
    }

    private Response post(Client client,String upToken,String url, byte[] data) throws QiniuException {
        return client.post(url, data, (new StringMap()).put("Authorization", "UpToken " + upToken));
    }

    /**
     * 获取7牛文件存储信息
     * @param bucketManager bucket操作对象
     * @param bucket 存储空间
     * @param key 文件名
     * @return
     * @throws QiniuException
     */
    @Override
    public ResourcePojo getFileInfo(BucketManager bucketManager,String bucket, String key){
        ResourcePojo resourcePojo = new ResourcePojo();
        try{
            FileInfo fileInfo =  bucketManager.stat(bucket, key);
            long createTime = fileInfo.putTime / 10000;//处理七牛17位时间戳
            resourcePojo.setPutTime(DateUtils.longToDate(createTime,"yyyy-MM-dd HH:mm:ss"));
            resourcePojo.setFsize(fileInfo.fsize);
            resourcePojo.setKey(fileInfo.key);
            resourcePojo.setType(fileInfo.type);
            resourcePojo.setMimeType(fileInfo.mimeType);
            resourcePojo.setStatus(fileInfo.status);
        }catch(Exception e){
            resourcePojo.setPutTime(new Date());
        }
        return resourcePojo;
    }

    /**
     * 依据空间删除资源
     * @param bucketId
     * @return
     */
    @Override
    public ResultMap deleteByBucketId(String bucketId) throws Exception{
        if(StringUtils.isBlank(bucketId)){
            return new ResultMap(CommonConstant.DATA_CHECK_FAILURE_CODE, CommonConstant.DATA_CHECK_FAILURE_MSG);
        }
        //检查空间有效性
        Bucket bucket = bucketService.getById(bucketId);
        if(bucket == null || StringUtils.isBlank(bucket.getAliasName())){
            return new ResultMap(ResourceConstant.BUCKET_NOT_FOUND_CODE, ResourceConstant.FROM_BUCKET_NOT_FOUND_MSG);
        }
        Date updateDate = new Date();
        Long totalSize = resourceExtendMapper.getTotalSizeByBucketId(bucketId,StorageTypeEnum.COMMON.value());
        if(totalSize != null){
            //标准资源统计
            Resource commonResource = new Resource();
            commonResource.setBucketId(bucketId);
            commonResource.setResourceId(bucketId);
            commonResource.setFileSize(totalSize);
            commonResource.setUpdateTime(updateDate);
            commonResource.setStorageType(StorageTypeEnum.COMMON.value());
            int insertDelCode = resourceStatisticService.add(commonResource,false);
            if(insertDelCode < 1){
                return new ResultMap(CommonConstant.ERROR, ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR);
            }
        }

        //查询空间下有效低频存储资源
        ResourceExample commonExample = new ResourceExample();
        commonExample.createCriteria().andBucketIdEqualTo(bucketId).andValidFlagEqualTo(ValidStatusEnum.VALID.value()).andStorageTypeEqualTo(StorageTypeEnum.INFREQUENCY.value());
        List<Resource> resourceList = resourceMapper.selectByExample(commonExample);
        if(!CollectionUtils.isEmpty(resourceList)){
            resourceList.stream().forEach(resource->{
                try{
                    resource.setUpdateTime(updateDate);
                    resourceStatisticService.add(resource,false);
                }catch(Exception e){
                    logger.error(ResourceConstant.RESOURCE_STATISTIC_INSERT_ERROR,e);
                }
            });
        }

        //空间下无任何文件
        if((totalSize == null || totalSize == 0) && CollectionUtils.isEmpty(resourceList)){
            return new ResultMap(CommonConstant.SUCCESS, CommonConstant.RESULT_SUCCESS);
        }

        //执行删除
        Resource delResource = new Resource();
        delResource.setBucketId(bucketId);
        delResource.setUpdateTime(updateDate);
        resourceExtendMapper.deleteByResource(delResource);
        return new ResultMap(CommonConstant.SUCCESS, CommonConstant.RESULT_SUCCESS);
    }

    /**
     * 检查临时空间的
     * @param bucketId 空间ID
     * @param ifSize true-大小（10G） false-流量（5G）
     * @return
     */
    @Override
    public boolean checkBucketOver(String bucketId,boolean ifSize,long upSize) {
        if (StringUtils.isBlank(bucketId)) {
            return false;
        }
        Bucket bucket = bucketService.getById(bucketId);
        if (bucket == null) {
            return true;
        }
        if (StringUtils.isNotBlank(bucket.getDomainId())) {//已绑定域名
            return true;
        }
        if (ifSize) {
            Long totalSize = resourceExtendMapper.getTotalSizeByBucketId(bucketId, null);
            if (totalSize == null) {
                totalSize = upSize;
            } else {
                totalSize += upSize;
            }
            return totalSize <= ResourceConstant.BUCKET_MAX_SIZE;
        } else {
            DateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            String date = dt.format(new Date());
            Account account = accountService.getAccount();
            if (account == null) {
                return true;
            }
            Auth auth = Auth.create(account.getAccessKey(), account.getSecretKey());
            Configuration cfg = ResourceMangerTool.getInstance().buildConfiguration(bucket.getRegion());
            BucketManager bucketManager = new BucketManager(auth, cfg);
            try {
                String[] domains = bucketManager.domainList(bucket.getAliasName());
                if (domains == null || domains.length < 1) {
                    return true;
                }
                CdnManager c = new CdnManager(auth);
                CdnResult.FluxResult fluxResult = c.getFluxData(domains, date, date, GranularityEnum.DAY.value());

                if (CollectionUtils.isEmpty(fluxResult.data)) {
                    return true;
                } else {
                    Map<String, CdnResult.FluxData> fluxMap = fluxResult.data;
                    long allFlux = 0l;
                    for (int i = 0; i < domains.length; i++) {
                        if (fluxMap.get(domains[i]).china.length > 0) {
                            allFlux += fluxMap.get(domains[i]).china[0];//按天最多一个
                        }
                    }
                    if (allFlux > ResourceConstant.BUCKET_MAX_FLUX) {
                        return false;
                    }
                }
            } catch (QiniuException e) {
                logger.error(e.getMessage(), e);
                return true;
            }
        }
        return true;
    }

    /**
     * 判断当前bucket资源
     *
     * @param bucketId
     * @return
     */
    @Override
    public List<Resource> getByBucketId(String bucketId) {
        ResourceExample example = new ResourceExample();
        example.createCriteria().andBucketIdEqualTo(bucketId).andValidFlagEqualTo(ValidStatusEnum.VALID.value());
        return resourceMapper.selectByExample(example);
    }
}
