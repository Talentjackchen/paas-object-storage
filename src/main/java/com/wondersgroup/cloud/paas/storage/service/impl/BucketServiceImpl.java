package com.wondersgroup.cloud.paas.storage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.model.AclType;
import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.constant.UserInfoValidationConstant;
import com.wondersgroup.cloud.paas.common.enums.status.ValidStatusEnum;
import com.wondersgroup.cloud.paas.common.exception.BusinessException;
import com.wondersgroup.cloud.paas.common.pojo.ResultMap;
import com.wondersgroup.cloud.paas.common.utils.CollectionUtils;
import com.wondersgroup.cloud.paas.common.utils.CommonUtils;
import com.wondersgroup.cloud.paas.common.utils.QueryUtils;
import com.wondersgroup.cloud.paas.storage.constant.BucketConstant;
import com.wondersgroup.cloud.paas.storage.enums.AclStatusEnum;
import com.wondersgroup.cloud.paas.storage.enums.BucketStatusEnum;
import com.wondersgroup.cloud.paas.storage.enums.BucketTypeEnum;
import com.wondersgroup.cloud.paas.storage.mapper.BucketMapper;
import com.wondersgroup.cloud.paas.storage.mapper.RequestStatisticMapper;
import com.wondersgroup.cloud.paas.storage.model.*;
import com.wondersgroup.cloud.paas.storage.pojo.StatisticsRequestPojo;
import com.wondersgroup.cloud.paas.storage.service.*;
import com.wondersgroup.cloud.paas.storage.tools.CertificateTool;
import com.wondersgroup.cloud.paas.storage.tools.ResourceMangerTool;
import com.wondersgroup.cloud.paas.storage.utils.LoginUserUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * author :zhangyongzhao
 * Bucket服务
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BucketServiceImpl implements BucketService {

    private static Logger logger = Logger.getLogger(BucketServiceImpl.class);

    @Autowired(required = false)
    private BucketMapper bucketMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProjectAuthorityService projectAuthorityService;

    @Autowired
    private DomainService domainService;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired(required = false)
    private ResourceService resourceService;

    @Autowired(required = false)
    private RequestStatisticMapper requestStatisticMapper;

    @Autowired
    private IpAclService ipAclService;

    @Autowired
    private RefererAclService refererAclService;

    @Autowired
    private TimestampAclService timestampAclService;

    @Autowired
    private RequestStatisticService requestStatisticService;

    /**
     * 查询所有有效的空间数据
     *
     * @param projectId 项目ID
     * @return
     */
    @Override
    public List<Bucket> list(String projectId) {
        BucketExample bucketExample = new BucketExample();
        BucketExample.Criteria criteria = bucketExample.createCriteria();
        criteria.andProjectIdEqualTo(projectId);
        criteria.andValidFlagEqualTo(ValidStatusEnum.VALID.value());
        return bucketMapper.selectByExample(bucketExample);
    }

    /**
     * 根据ID查询数据
     *
     * @param bucketId
     * @return
     */
    @Override
    public Bucket getById(String bucketId) {
        if (StringUtils.isBlank(bucketId)) {
            return null;
        }
        Bucket bucket = bucketMapper.selectByPrimaryKey(bucketId);
        if (bucket != null && (bucket.getValidFlag().equals(ValidStatusEnum.VALID.value()))) {
            return bucket;
        } else {
            return null;
        }
    }

    /**
     * 根据bucketId并且七牛云上未删除的数据
     *
     * @param bucketId
     * @return
     */
    @Override
    public Bucket getByIdAndRemoteFlag(String bucketId) {
        BucketExample example = new BucketExample();
        BucketExample.Criteria criteria = example.createCriteria();
        criteria.andBucketIdEqualTo(bucketId);
        criteria.andRemoteFlagEqualTo(ValidStatusEnum.VALID.value());
        List<Bucket> bucketList = bucketMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(bucketList)) {
            return bucketList.get(0);
        } else {
            return null;
        }
    }


    @Override
    public PageInfo<Bucket> page(String name, String projectId, String accountId, String type,
                                 Integer pageNum, Integer pageSize, String orderByClause) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        BucketExample example = new BucketExample();
        BucketExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(QueryUtils.generateLikeString(name));
        }
        if (StringUtils.isBlank(projectId)) {
            throw new BusinessException(CommonConstant.ERROR, BucketConstant.BUCKET_PROJECT_ERROR);
        }
        criteria.andProjectIdEqualTo(projectId);
        if (StringUtils.isNotBlank(accountId)) {
            criteria.andAccountIdEqualTo(accountId);
        }

        if (StringUtils.isNotBlank(type)) {
            criteria.andTypeEqualTo(type);
        }

        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }

        //只查询有效的数据
        criteria.andValidFlagEqualTo(ValidStatusEnum.VALID.value());
        List<Bucket> bucketList = bucketMapper.selectByExample(example);

        //查询所有有效的域名
        Map<String, Domain> domainMap = domainService.getMap();
        //替换domain and 显示临时空间或者正式空间
        if (CollectionUtils.isNotEmpty(bucketList)) {
            bucketList.stream().forEach(bucket -> {
                if (bucket.getDomainId() != null && !"".equals(bucket.getDomainId())) {
                    bucket.setDomain(domainMap.get(bucket.getDomainId()).getName());
                    bucket.setStatus(BucketStatusEnum.FORMAL.value());
                } else {
                    bucket.setStatus(BucketStatusEnum.TEMPORARY.value());
                }
            });
        }
        PageInfo<Bucket> pageInfo = new PageInfo<>(bucketList);
        return pageInfo;

    }


    /**
     * 创建bucket
     *
     * @param bucket 对象
     */
    @Override
    public void create(Bucket bucket) throws Exception {

        Account account = accountService.getAccount();
        BucketManager bucketManager = ResourceMangerTool.getInstance().buildBucketManger(account.getAccessKey(),
                account.getSecretKey(), bucket.getRegion());
        checkParameter(bucket);
        try {
            //构建bucket数据
            buildBucket(bucket);
            //创建bucket
            bucketManager.createBucket(bucket.getAliasName(), bucket.getRegion());
            //生成空间域名
            bucket.setDomain(CertificateTool.getDomainOfBucket(account.getAccessKey(),
                    account.getSecretKey(), bucket.getAliasName()));
            AclType aclType = AclType.PUBLIC;
            if (BucketTypeEnum.PRIVATE.value().equals(bucket.getType())) {
                aclType = AclType.PRIVATE;
            } else {
                bucket.setType(BucketTypeEnum.PUBLIC.value());
            }
            bucketManager.setBucketAcl(bucket.getAliasName(), aclType);
            bucket.setStatus(BucketStatusEnum.TEMPORARY.value());
            bucketMapper.insert(bucket);
            /**
             * 插入project_authority表
             */
            //校验当前projectId是否存在有效的密钥
            boolean isExistProject = projectAuthorityService.checkByProjectId(bucket.getProjectId());
            //不存在添加数据
            if (!isExistProject) {
                //构建ProjectAuthority
                ProjectAuthority projectAuthority = buildProjectAuthority(bucket.getProjectId());
                projectAuthorityService.insert(projectAuthority);
            }
        } catch (Exception e) {
            if (e instanceof QiniuException) {
                logger.error(e.getMessage(), e);
                throw new BusinessException(CommonConstant.ERROR, BucketConstant.MSG_CREATE_BUCKET_ERROR);
            } else {
                logger.error(e.getMessage(), e);
                throw new BusinessException(CommonConstant.ERROR, BucketConstant.MSG_CREATE_BUCKET_ERROR);
            }
        }
    }

    /**
     * 删除bucket
     *
     * @param bucketId 数据ID
     * @throws Exception
     */
    @Override
    public void delete(String bucketId) throws Exception {
        Bucket bucket = getById(bucketId);
        if (bucket == null) {
            throw new BusinessException(BucketConstant.BUCKET_ERROR_CODE, BucketConstant.BUCKET_NOT_EXIST_ERROR);
        }
        //越权判断
        boolean isStrideAcross = LoginUserUtil.StrideAcrossJudgeByProjectId(bucket.getProjectId());
        if (!isStrideAcross) {
            throw new BusinessException(UserInfoValidationConstant.OPERATE_OUT_CONTROL_CODE, UserInfoValidationConstant.OPERATE_OUT_CONTROL_MSG);
        }
        //删除之前判断是否当前空间是否存在文件
        List<Resource> resourceList = resourceService.getByBucketId(bucketId);
        if (!CollectionUtils.isEmpty(resourceList)) {
            throw new BusinessException(CommonConstant.ERROR, BucketConstant.EXIT_RESOURCE);
        }
        try {

            //删除本地数据成功以后删除云数据
            deleteBucket(bucket);
            bucket.setValidFlag(ValidStatusEnum.INVALID.value());
            bucket.setRemoteFlag(ValidStatusEnum.INVALID.value());
            bucket.setUpdateTime(new Date());
            //逻辑删除
            bucketMapper.updateByPrimaryKey(bucket);
            //释放域名
            domainService.releaseDomain(bucket.getBucketId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(CommonConstant.ERROR, BucketConstant.MSG_DELETE_BUCKET_FAILURE, e.getCause());
        }
//        }
    }

    /**
     * 七牛云上删除bucket
     *
     * @param bucket
     */
    @Override
    public void deleteBucket(Bucket bucket) throws QiniuException, BusinessException {
        Account account = accountService.getAccount();
        BucketManager bucketManager = ResourceMangerTool.getInstance().buildBucketManger(account.getAccessKey(),
                account.getSecretKey());

        // 将七牛云上的数据存入本地数据库
        saveData(bucket);

        // 删除存储空间
        bucketManager.deleteBucket(bucket.getAliasName());
    }

    /**
     * 根据bucketId查询详细数据
     *
     * @param bucketId 空间ID
     * @return
     * @throws BusinessException
     */
    @Override
    public HashMap<String, Object> getBucketInfo(String bucketId) throws Exception {
        Bucket bucket = getById(bucketId);
        //格式化开始时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String createTime = sdf.format(bucket.getCreateTime());
        String nowTime = sdf.format(new Date());
        if (bucket == null) {
            throw new BusinessException(BucketConstant.BUCKET_ERROR_CODE, BucketConstant.BUCKET_NOT_EXIST_ERROR);
        }

        //越权判断
        boolean isStrideAcross = LoginUserUtil.StrideAcrossJudgeByProjectId(bucket.getProjectId());
        if (!isStrideAcross) {
            throw new BusinessException(UserInfoValidationConstant.OPERATE_OUT_CONTROL_CODE, UserInfoValidationConstant.OPERATE_OUT_CONTROL_MSG);
        }

        //获取存储量大小和数量
        HashMap<String, Object> resultMap = statisticsService.getCountAndSizeByBucketId(bucketId);
        if (resultMap == null) {
            resultMap = new HashMap<>();
        }
        //获取请求次数
        int getNumberList = statisticsService.getGETRequestsNumberByMonth(createTime, nowTime, bucketId, "", "", "");
        int putNumberList = statisticsService.getPUTRequestsNumberByMonth(createTime, nowTime, bucketId, "", "");
        resultMap.put("requestNumber", getNumberList + putNumberList);
        //bucket详细信息
        resultMap.put("bucket", bucket);
        //ip黑白名单
        IpAcl ipAcl = ipAclService.queryByBucketId(bucketId);
        resultMap.put("ipAcl", ipAcl);
        //refer防盗链
        RefererAcl refererAcl = refererAclService.queryByBucketId(bucketId);
        resultMap.put("refererAcl", refererAcl);
        //时间防盗链
        TimestampAcl timestampAcl = timestampAclService.queryByBucketId(bucketId);
        resultMap.put("timestampAcl", timestampAcl);
        return resultMap;
    }

    @Override
    public boolean isExistBucket(String projectId) {
        BucketExample example = new BucketExample();
        BucketExample.Criteria criteria = example.createCriteria();
        criteria.andValidFlagEqualTo(ValidStatusEnum.VALID.value());

        if (StringUtils.isNotBlank(projectId)) {
            criteria.andProjectIdEqualTo(projectId);
        }
        List<Bucket> bucketList = bucketMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(bucketList);
    }

    /**
     * 将七牛云上的数据存入本地数据库
     *
     * @param bucket 空间实例
     * @throws BusinessException
     */
    private void saveData(Bucket bucket) throws BusinessException {
        String bucketId = bucket.getBucketId();
        Date date = new Date();
        String firstDayOfMonth = getFirstDayOfMonth(date);
        String lastDayOfMonth = getLastDayOfMonth(date);
        List<StatisticsRequestPojo> list = new ArrayList<>();

        // 在删除bucket之前从七牛云获取当前用户的操作次数,包括get和put/delete
        // 获取标准存储的put请求次数
        int putCommonNum = 0;
        RequestStatistic requestStatisticCommon = new RequestStatistic();
        list = statisticsService.getPUTRequestsNumberByDay(firstDayOfMonth, lastDayOfMonth, bucketId, null, "0", null);
        if (list.isEmpty()) {
            throw new BusinessException(CommonConstant.ERROR, CommonConstant.DATA_NOT_EXISTS);
        }

        for (StatisticsRequestPojo statisticsRequestPojo : list) {
            if (statisticsRequestPojo.getValues() != null) {
                putCommonNum += (int) (statisticsRequestPojo.getValues().get("hits"));
            }
        }
        // 将数据写入公司数据库
        if (putCommonNum > 0) {
            requestStatisticCommon.setAmount(putCommonNum);
            requestStatisticCommon.setBucketId(bucketId);
            requestStatisticCommon.setRecordTime(date);
            requestStatisticCommon.setStorageType("0");
            requestStatisticCommon.setType("1");
            requestStatisticCommon.setRequestStatisticId(UUID.randomUUID().toString().replaceAll("-", ""));
            requestStatisticMapper.insert(requestStatisticCommon);
        }

        // 获取低频存储的put请求次数
        int putFrequencyNum = 0;
        RequestStatistic requestStatisticFrequency = new RequestStatistic();
        list = statisticsService.getPUTRequestsNumberByDay(firstDayOfMonth, lastDayOfMonth, bucketId, null, "1", null);
        if (list.isEmpty()) {
            throw new BusinessException(CommonConstant.ERROR, CommonConstant.DATA_NOT_EXISTS);
        }
        for (StatisticsRequestPojo statisticsRequestPojo : list) {
            if (statisticsRequestPojo.getValues() != null) {
                putFrequencyNum += (int) (statisticsRequestPojo.getValues().get("hits"));
            }

        }
        // 将数据写入公司数据库
        if (putFrequencyNum > 0) {
            requestStatisticFrequency.setAmount(putFrequencyNum);
            requestStatisticFrequency.setBucketId(bucketId);
            requestStatisticFrequency.setRecordTime(date);
            requestStatisticFrequency.setStorageType("1");
            requestStatisticFrequency.setType("1");
            requestStatisticFrequency.setRequestStatisticId(UUID.randomUUID().toString().replaceAll("-", ""));
            requestStatisticMapper.insert(requestStatisticFrequency);
        }

        // 获取标准存储的get/delete请求次数
        int getCommonNum = 0;
        RequestStatistic requestStatisticGetCommon = new RequestStatistic();
        list = statisticsService.getGETRequestsNumberByDay(firstDayOfMonth, lastDayOfMonth, bucket.getBucketId(), null, null, "0", null);
        if (list.isEmpty()) {
            throw new BusinessException(CommonConstant.ERROR, CommonConstant.DATA_NOT_EXISTS);
        }
        for (StatisticsRequestPojo statisticsRequestPojo : list) {
            if (statisticsRequestPojo.getValues() != null) {
                getCommonNum += (int) (statisticsRequestPojo.getValues().get("hits"));
            }
        }
        // 将数据写入公司数据库
        if (getCommonNum > 0) {
            requestStatisticGetCommon.setAmount(getCommonNum);
            requestStatisticGetCommon.setBucketId(bucketId);
            requestStatisticGetCommon.setRecordTime(date);
            requestStatisticGetCommon.setStorageType("0");
            requestStatisticGetCommon.setType("2");
            requestStatisticGetCommon.setRequestStatisticId(UUID.randomUUID().toString().replaceAll("-", ""));
            requestStatisticMapper.insert(requestStatisticGetCommon);
        }

        // 获取低频存储的get/delete请求次数
        int getFrequencyNum = 0;
        RequestStatistic requestStatisticGetFrequency = new RequestStatistic();
        list = statisticsService.getGETRequestsNumberByDay(firstDayOfMonth, lastDayOfMonth, bucket.getBucketId(), null, null, "1", null);
        if (list.isEmpty()) {
            throw new BusinessException(CommonConstant.ERROR, CommonConstant.DATA_NOT_EXISTS);
        }
        for (StatisticsRequestPojo statisticsRequestPojo : list) {
            if (statisticsRequestPojo.getValues() != null) {
                getFrequencyNum += (int) (statisticsRequestPojo.getValues().get("hits"));
            }
        }
        // 将数据写入公司数据库
        if (getFrequencyNum > 0) {
            requestStatisticGetFrequency.setAmount(getFrequencyNum);
            requestStatisticGetFrequency.setBucketId(bucketId);
            requestStatisticGetFrequency.setRecordTime(date);
            requestStatisticGetFrequency.setStorageType("0");
            requestStatisticGetFrequency.setType("2");
            requestStatisticGetFrequency.setRequestStatisticId(UUID.randomUUID().toString().replaceAll("-", ""));
            requestStatisticMapper.insert(requestStatisticGetFrequency);
        }

        /* 保存当月低频与标频转换次数 */
        requestStatisticService.saveExchangeAmount(bucket);
    }

    /**
     * 校验名称是否重复
     *
     * @param name
     * @param projectId
     * @return
     */
    @Override
    public boolean checkName(String name, String projectId) {
        BucketExample example = new BucketExample();
        BucketExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        criteria.andProjectIdEqualTo(projectId);
        criteria.andValidFlagEqualTo(ValidStatusEnum.VALID.value());
        List<Bucket> bucketList = bucketMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(bucketList);
    }


    /**
     * 根据条件修改bucket数据
     *
     * @param bucket
     */
    @Override
    public void editByExample(Bucket bucket) {
        String domainId = bucket.getDomainId();
        BucketExample example = new BucketExample();
        BucketExample.Criteria criteria = example.createCriteria();
        criteria.andDomainIdEqualTo(domainId);
        bucket.setDomainId("");
        bucketMapper.updateByExampleSelective(bucket, example);
    }

    /**
     * 设置空间权限
     *
     * @param bucketId 空间ID
     * @param acl      权限 1 私有  0公开
     */
    @Override
    public void setPermission(String bucketId, int acl) throws Exception {
        Bucket bucket = getById(bucketId);
        if (bucket == null) {
            throw new BusinessException(CommonConstant.ERROR, BucketConstant.BUCKET_NOT_EXIST_ERROR);
        }

        if (bucket.getType().equals(String.valueOf(acl))) {
            throw new BusinessException(CommonConstant.ERROR, BucketConstant.REPEAT_PERMISSION);
        }

        //越权判断
        boolean isStrideAcross = LoginUserUtil.StrideAcrossJudgeByProjectId(bucket.getProjectId());
        if (!isStrideAcross) {
            throw new BusinessException(UserInfoValidationConstant.OPERATE_OUT_CONTROL_CODE, UserInfoValidationConstant.OPERATE_OUT_CONTROL_MSG);
        }
        Account account = accountService.getAccount();
        BucketManager bucketManager = ResourceMangerTool.getInstance().buildBucketManger(account.getAccessKey(),
                account.getSecretKey(), bucket.getRegion());
        AclType aclType = acl == 1 ? AclType.PRIVATE : AclType.PUBLIC;
        bucket.setType(String.valueOf(aclType.getType()));
        bucket.setUpdateTime(new Date());
        bucketMapper.updateByPrimaryKey(bucket);
        bucketManager.setBucketAcl(bucket.getAliasName(), aclType);
    }


    /**
     * 临时转正式空间
     *
     * @param bucketId 空间ID
     */
    @Override
    public void setFreeDomain(String bucketId) throws Exception {
        //获取当前空闲域名
        Domain domain = domainService.getFreeDomain();
        if (domain == null) {
            throw new BusinessException(CommonConstant.ERROR, BucketConstant.NOT_FREE_DOMAIN);
        }
        Bucket bucketData = getById(bucketId);

        if (StringUtils.isNotBlank(bucketData.getDomainId())) {
            throw new BusinessException(CommonConstant.ERROR, BucketConstant.REPEAT_FREE_DOMAIN);
        }

        //越权判断
        boolean isStrideAcross = LoginUserUtil.StrideAcrossJudgeByProjectId(bucketData.getProjectId());
        if (!isStrideAcross) {
            throw new BusinessException(UserInfoValidationConstant.OPERATE_OUT_CONTROL_CODE, UserInfoValidationConstant.OPERATE_OUT_CONTROL_MSG);
        }

        Bucket bucket = new Bucket();
        bucket.setDomainId(domain.getDomainId());
        bucket.setUpdateTime(new Date());
        //正式空间的标志
        bucket.setStatus(BucketStatusEnum.FORMAL.value());
        BucketExample example = new BucketExample();
        BucketExample.Criteria criteria = example.createCriteria();
        criteria.andBucketIdEqualTo(bucketId);
        bucketMapper.updateByExampleSelective(bucket, example);
        //创建域名
        domainService.create(domain.getName(), domain.getProtocol(), domain.getPlatform(), bucketId);

    }

    /**
     * 根据项目ID查询以存储区域分组的数据
     *
     * @param projectId 项目ID
     * @return
     */
    @Override
    public Map<String, List<Bucket>> getByProjectId(String projectId) {
        Map<String, List<Bucket>> bucketMap = new HashMap<>();
        BucketExample example = new BucketExample();
        BucketExample.Criteria criteria = example.createCriteria();
        criteria.andProjectIdEqualTo(projectId);
        criteria.andValidFlagEqualTo(ValidStatusEnum.VALID.value());
        example.setOrderByClause(" region,create_time desc ");
        List<Bucket> bucketList = bucketMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(bucketList)) {
            bucketList.stream().forEach(bucket -> {
                if (bucketMap.containsKey(bucket.getRegion())) {
                    bucketMap.get(bucket.getRegion()).add(bucket);
                } else {
                    List<Bucket> buckets = new ArrayList<>();
                    buckets.add(bucket);
                    bucketMap.put(bucket.getRegion(), buckets);
                }
            });
        }
        return bucketMap;
    }

    /**
     * 校验参数是否符合规范
     *
     * @param bucket
     */
    private void checkParameter(Bucket bucket) throws BusinessException {
        //判断名称是否为空
        if (StringUtils.isBlank(bucket.getName())) {
            throw new BusinessException(BucketConstant.BUCKET_ERROR_CODE, BucketConstant.BUCKET_NAME_ERROR);
        } else {
            int length = bucket.getName().length();
            if (length > 30) {
                throw new BusinessException(BucketConstant.BUCKET_ERROR_CODE, BucketConstant.BUCKET_NAME_TOO_LONG_ERROR);
            }
        }


        if (StringUtils.isBlank(bucket.getProjectId())) {
            throw new BusinessException(BucketConstant.BUCKET_ERROR_CODE, BucketConstant.BUCKET_PROJECT_ERROR);
        } else {
            //不为空校验名称是否存在
            if (checkName(bucket.getName(), bucket.getProjectId())) {
                throw new BusinessException(BucketConstant.BUCKET_ERROR_CODE,
                        BucketConstant.BUCKET_ALIAS_NAME_ERROR);
            }
        }

        //判断存储区域是否为空
        if (StringUtils.isBlank(bucket.getRegion())) {
            throw new BusinessException(BucketConstant.BUCKET_ERROR_CODE, BucketConstant.BUCKET_REGION_ERROR);
        }
        //判断空间权限是否为空
        if (StringUtils.isBlank(bucket.getType())) {
            throw new BusinessException(BucketConstant.BUCKET_ERROR_CODE, BucketConstant.BUCKET_TYPE_ERROR);
        }

        //判断机构ID是否为空
        if (StringUtils.isBlank(bucket.getAccountId())) {
            throw new BusinessException(BucketConstant.BUCKET_ERROR_CODE, BucketConstant.BUCKET_ORG_ERROR);
        }
    }


    /**
     * 构建bucket数据
     *
     * @param bucket
     */
    private void buildBucket(Bucket bucket) {
        //七牛云空间名称生成   name + '_' + projectId
        bucket.setAliasName(bucket.getName() + CommonConstant.UNDER_LINE_SYMBOL + bucket.getProjectId());
        //七牛云上创建成功以后添加表数据
        bucket.setBucketId(CommonUtils.generateId());
        //控制是否删除七牛云上数据
        bucket.setRemoteFlag(ValidStatusEnum.VALID.value());
        bucket.setCreateTime(new Date());
        bucket.setValidFlag(ValidStatusEnum.VALID.value());
        bucket.setStatus(BucketStatusEnum.TEMPORARY.value());
    }

    /**
     * 构建ProjectAuthority对象
     *
     * @param projectId 项目ID
     * @return
     */
    private ProjectAuthority buildProjectAuthority(String projectId) {
        ProjectAuthority projectAuthority = new ProjectAuthority();
        projectAuthority.setProjectId(projectId);
        projectAuthority.setAccessKey(CommonUtils.generateCapitalUUID());
        projectAuthority.setSecretKey(CommonUtils.generateCapitalUUID());
        projectAuthority.setProjectAuthorityId(CommonUtils.generateId());
        projectAuthority.setValidFlag(ValidStatusEnum.VALID.value());
        projectAuthority.setStatus(AclStatusEnum.START.value());
        projectAuthority.setCreateTime(new Date());
        return projectAuthority;
    }

    @Override
    public List<Bucket> getListByProjectId(String projectId) {
        BucketExample example = new BucketExample();
        example.createCriteria().andProjectIdEqualTo(projectId);
        List<Bucket> list = bucketMapper.selectByExample(example);
        return list;
    }

    /**
     * 逻辑删除时间大于某个时间未绑定域名的空间
     */
    @Override
    public void deleteByCreateDate(Date date) {
        Bucket bucket = new Bucket();
        bucket.setValidFlag(ValidStatusEnum.INVALID.value());
        BucketExample example = new BucketExample();
        BucketExample.Criteria criteria = example.createCriteria();
        BucketExample.Criteria criteria2 = example.createCriteria();
        criteria.andDomainIdIsNull();
        criteria2.andDomainIdEqualTo("");
        criteria.andCreateTimeLessThan(date);
        example.or(criteria2);
        bucketMapper.updateByExampleSelective(bucket, example);
    }

    /**
     * 获取当月第一天
     *
     * @param date 日期
     * @return
     */
    public String getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date firstDay = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(firstDay);
    }

    /**
     * 获取当月最后一天
     *
     * @param date 日期
     * @return
     */
    public String getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDay = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(lastDay);
    }

    /**
     * 根据创建时间查询bucket数据(已被逻辑删除的数据)
     *
     * @return
     */
    @Override
    public List<Bucket> getCompareCreateTime(Date date) {
        BucketExample example = new BucketExample();
        BucketExample.Criteria criteria = example.createCriteria();
        BucketExample.Criteria criteria2 = example.createCriteria();
        criteria.andDomainIdIsNull();
        criteria2.andDomainIdEqualTo("");
        criteria.andCreateTimeLessThanOrEqualTo(date);
        criteria.andValidFlagEqualTo(ValidStatusEnum.INVALID.value());
        example.or(criteria2);
        return bucketMapper.selectByExample(example);
    }


    /**
     * 获取所有项目Id
     *
     * @return
     */
    @Override
    public List<String> getProjectIds() {
        BucketExample example = new BucketExample();
        example.createCriteria().andValidFlagEqualTo(ValidStatusEnum.VALID.value());
        List<Bucket> bucketList = bucketMapper.selectByExample(example);
        List<String> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bucketList)) {
            bucketList.stream().forEach(bucket -> {
                if (!list.contains(bucket.getProjectId())) {
                    list.add(bucket.getProjectId());
                }
            });
        }
        return list;
    }

}
