package com.wondersgroup.cloud.paas.storage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiniu.common.Zone;
import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.enums.status.ValidStatusEnum;
import com.wondersgroup.cloud.paas.common.exception.BusinessException;
import com.wondersgroup.cloud.paas.common.utils.CollectionUtils;
import com.wondersgroup.cloud.paas.common.utils.CommonUtils;
import com.wondersgroup.cloud.paas.common.utils.QueryUtils;
import com.wondersgroup.cloud.paas.storage.constant.DomainConstant;
import com.wondersgroup.cloud.paas.storage.enums.BucketStatusEnum;
import com.wondersgroup.cloud.paas.storage.mapper.DomainMapper;
import com.wondersgroup.cloud.paas.storage.model.*;
import com.wondersgroup.cloud.paas.storage.pojo.DomainInfo;
import com.wondersgroup.cloud.paas.storage.pojo.ResponsePojo;
import com.wondersgroup.cloud.paas.storage.service.AccountService;
import com.wondersgroup.cloud.paas.storage.service.BucketService;
import com.wondersgroup.cloud.paas.storage.service.DomainService;
import com.wondersgroup.cloud.paas.storage.service.SslCertService;
import com.wondersgroup.cloud.paas.storage.utils.HttpClientUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * describe : 域名管理
 * create_by : zhangyongzhao
 * create_time : 2019/5/5
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DomainServiceImpl implements DomainService {

    private static Logger logger = Logger.getLogger(DomainServiceImpl.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private BucketService bucketService;

    @Autowired
    private SslCertService sslCertService;

    @Autowired(required = false)
    private DomainMapper domainMapper;


    /**
     * 创建域名
     *
     * @param name     域名名称
     * @param protocol 协议
     * @param platform 平台类型
     * @param bucketId bucketId
     */
    @Override
    public void create(String name, String protocol, String platform, String bucketId) throws Exception {
        //获取bucket信息
        Bucket bucket = bucketService.getById(bucketId);
        if (bucket == null) {
            throw new BusinessException(CommonConstant.ERROR, DomainConstant.NOT_EXIST_BUCKET);
        }

        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        //对导入库的数据进行处理
        //判断当前域名是否是有效且关联bucket字段为空
        Domain domain = getByName(name);
        if (domain == null) {
            domain = new Domain();
            //插入本地数据
            params = buildParams(name, platform, protocol, bucket.getAliasName(), domain);
            buildDomain(domain, name, protocol, platform, bucketId);
            domainMapper.insert(domain);
        } else {
            if (ValidStatusEnum.VALID.value().equals(domain.getValidFlag())) {
                params = buildParams(name, platform, protocol, bucket.getAliasName(), domain);
                domain.setPlatform(platform);
                domain.setProtocol(protocol);
                domain.setValidFlag(ValidStatusEnum.VALID.value());
            }
            domain.setUpdateTime(new Date());
            domain.setBelongBucketId(bucketId);
            domainMapper.updateByPrimaryKey(domain);
        }

        //获取当前域名是否存在与七牛云上
        DomainInfo qiuNiuDomain = getQiuNiuDomain(name);
        if (qiuNiuDomain.getOperatingState() != null) {
            //存在则不再创建，仅修改当前绑定的空间即可
            edit(domain.getDomainId(), bucketId);
            return;
        }
        String url = new Zone().getApiHttp() + DomainConstant.DOMAIN_PATH + name;
        Account account = accountService.getAccount();
        ResponsePojo responsePojo = HttpClientUtils.doPost(url, params, account, "", ResponsePojo.class);

        if (responsePojo.getCode() != CommonConstant.SUCCESS) {
            logger.error(responsePojo.getError());
            throw new BusinessException(CommonConstant.ERROR, responsePojo.getError());
        }

    }

    /**
     * 删除域名
     *
     * @param domainId 域名ID
     * @throws Exception
     */
    @Override
    public void delete(String domainId) throws Exception {
        try {
            //判断当前域名是否已停用
            DomainInfo domainInfo = getById(domainId);
            if (domainInfo == null) {
                throw new BusinessException(CommonConstant.ERROR, DomainConstant.DOMAIN_NOT_EXIST);
            }
            Domain domain = domainInfo.getDomain();
            if (DomainConstant.OFFLINE.equals(domainInfo.getOperatingState())) {
                //删除本地数据成功以后删除云域名 逻辑删除
                domain.setUpdateTime(new Date());
                domain.setValidFlag(ValidStatusEnum.INVALID.value());
                domainMapper.updateByPrimaryKey(domain);
                Bucket bucket = new Bucket();
                bucket.setDomainId(domain.getDomainId());
                bucket.setStatus(BucketStatusEnum.TEMPORARY.value());
                //域名删除，修改bucket表中绑定的字段
                bucketService.editByExample(bucket);
                String url = new Zone().getApiHttp() + DomainConstant.DOMAIN_PATH + domain.getName();
                Account account = accountService.getAccount();
                ResponsePojo responsePojo = HttpClientUtils.doDelete(url, account);
                if (CommonConstant.SUCCESS != responsePojo.getCode()) {
                    logger.error(responsePojo.getError());
                    throw new BusinessException(CommonConstant.ERROR, DomainConstant.NOT_DISCONTINUED);
                }
            } else {
                throw new BusinessException(CommonConstant.ERROR, DomainConstant.NOT_DISCONTINUED);
            }
        } catch (Exception e) {
            if (!(e instanceof BusinessException)) {
                throw new BusinessException(CommonConstant.ERROR, DomainConstant.FAIL_DELETE, e.getCause());
            } else {
                throw e;
            }
        }
    }

    /**
     * 启用域名
     *
     * @param domainId 域名ID
     */
    @Override
    public void enable(String domainId) throws Exception {
        DomainInfo domainInfo = getById(domainId);
        Domain domain;
        if (domainInfo == null) {
            throw new BusinessException(CommonConstant.ERROR, DomainConstant.DOMAIN_NOT_EXIST);
        } else {
            domain = domainInfo.getDomain();
        }
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        String url = new Zone().getApiHttp() + DomainConstant.DOMAIN_PATH + domain.getName() + DomainConstant.DOMAIN_ONLINE;
        Account account = accountService.getAccount();
        executeUrlPost(url, params, account);

    }

    /**
     * 停用域名
     *
     * @param domainId 域名ID
     */
    @Override
    public void stop(String domainId) throws Exception {
        DomainInfo domainInfo = getById(domainId);
        if (domainInfo == null) {
            throw new BusinessException(CommonConstant.ERROR, DomainConstant.DOMAIN_NOT_EXIST);
        }
        Domain domain = domainInfo.getDomain();
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        String url = new Zone().getApiHttp() + DomainConstant.DOMAIN_PATH + domain.getName() + DomainConstant.DOMAIN_OFFLINE;
        Account account = accountService.getAccount();
        executeUrlPost(url, params, account);
    }

    /**
     * 获取七牛云域名信息
     *
     * @param name 域名
     * @return 域名信息对象
     */
    public DomainInfo getQiuNiuDomain(String name) {
        String url = new Zone().getApiHttp() + DomainConstant.DOMAIN_PATH + name;
        Account account = accountService.getAccount();
        DomainInfo domainInfo = HttpClientUtils.doGet(url, account, DomainInfo.class);
        return domainInfo;
    }

    /**
     * 分页查询
     *
     * @param name          域名名称
     * @param protocol      协议
     * @param platform      平台类型
     * @param bucketId      bucketID
     * @param pageNum       当前页
     * @param pageSize      显示条数
     * @param orderByClause 排序字段
     * @return pageInfo
     */
    @Override
    public PageInfo<Domain> page(String name, String protocol, String platform, String bucketId,
                                 Integer pageNum, Integer pageSize, String orderByClause) {
        PageHelper.startPage(pageNum, pageSize);
        DomainExample example = new DomainExample();
        DomainExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(QueryUtils.generateLikeString(name));
        }

        if (StringUtils.isNotBlank(protocol)) {
            criteria.andProtocolEqualTo(protocol);
        }

        if (StringUtils.isNotBlank(platform)) {
            criteria.andPlatformEqualTo(platform);
        }

        if (StringUtils.isNotBlank(bucketId)) {
            criteria.andBelongBucketIdEqualTo(bucketId);
        }

        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        criteria.andValidFlagEqualTo(ValidStatusEnum.VALID.value());

        List<Domain> domains = domainMapper.selectByExample(example);
        return new PageInfo<>(domains);
    }

    /**
     * http升级为https
     *
     * @param domainId 域名ID
     */
    @Override
    public void upgrade(String domainId) throws Exception {
        DomainInfo domainInfo = getById(domainId);
        if (domainInfo == null) {
            throw new BusinessException(CommonConstant.ERROR, DomainConstant.DOMAIN_NOT_EXIST);
        }
        Domain domain = domainInfo.getDomain();
        if (domain.getProtocol().equals(DomainConstant.DOMAIN_PROTOCOL_HTTPS)) {
            throw new BusinessException(CommonConstant.ERROR, DomainConstant.REPEAT_UPGRADE);
        }
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        //构建请求参数
        SslCertificate sslCertificate = sslCertService.getByGenericName(domain.getName());
        if (sslCertificate == null) {
            throw new BusinessException(CommonConstant.ERROR, DomainConstant.DOMAIN_CERT_ERROR);
        }
        domain.setUpdateTime(new Date());
        domain.setProtocol(DomainConstant.DOMAIN_PROTOCOL_HTTPS);
        domain.setSslCertificateId(sslCertificate.getSslCertificateId());
        domainMapper.updateByPrimaryKey(domain);
        params.put(DomainConstant.CERT_ID, sslCertificate.getCertificateId());
        params.put(DomainConstant.FORCE_HTTPS, false);
        String url = new Zone().getApiHttp() + DomainConstant.DOMAIN_PATH + domain.getName()
                + DomainConstant.DOMAIN_SSLIZE + DomainConstant.QUESTION_SYMBOL
                + DomainConstant.CERT_ID + DomainConstant.EQUAL_SYMBOL + sslCertificate.getCertificateId()
                + DomainConstant.AND_SYMBOL + DomainConstant.FORCE_HTTPS
                + DomainConstant.EQUAL_SYMBOL + false;
        Account account = accountService.getAccount();
        ResponsePojo responsePojo = HttpClientUtils.doPut(url, params, account, "", ResponsePojo.class);
        if (CommonConstant.SUCCESS != responsePojo.getCode()) {
            logger.error(responsePojo.getError());
            throw new BusinessException(CommonConstant.ERROR, DomainConstant.ERROR_UPGRADE);
        }
    }

    /**
     * https降级为http
     *
     * @param domainId 域名ID
     * @throws Exception
     */
    @Override
    public void downgrade(String domainId) throws Exception {
        DomainInfo domainInfo = getById(domainId);
        if (domainInfo == null) {
            throw new BusinessException(CommonConstant.ERROR, DomainConstant.DOMAIN_NOT_EXIST);
        }
        Domain domain = domainInfo.getDomain();

        if (domain.getProtocol().equals(DomainConstant.DOMAIN_PROTOCOL_HTTP)) {
            throw new BusinessException(CommonConstant.ERROR, DomainConstant.REPEAT_DOWNGRADE);
        }

        domain.setUpdateTime(new Date());
        //降级的时候删除证书ID
        domain.setSslCertificateId("");
        domain.setProtocol(DomainConstant.DOMAIN_PROTOCOL_HTTP);
        domainMapper.updateByPrimaryKey(domain);
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        String url = new Zone().getApiHttp() + DomainConstant.DOMAIN_PATH + domain.getName() + DomainConstant.DOMAIN_UNSSLIZE;
        Account account = accountService.getAccount();
        ResponsePojo responsePojo = HttpClientUtils.doPut(url, params, account, "", ResponsePojo.class);
        if (CommonConstant.SUCCESS != responsePojo.getCode()) {
            logger.error(responsePojo.getError());
            throw new BusinessException(CommonConstant.ERROR, DomainConstant.ERROR_DOWNGRADE);
        }
    }


    /**
     * 构建domain数据
     *
     * @param name     域名名称
     * @param protocol 协议
     * @param platform 平台类型
     * @param bucketId bucketId
     */
    private void buildDomain(Domain domain, String name, String protocol, String platform, String bucketId) {
        domain.setName(name);
        domain.setProtocol(protocol);
        domain.setPlatform(platform);
        domain.setBelongBucketId(bucketId);
        domain.setDomainId(CommonUtils.generateId());
        domain.setCreateTime(new Date());
        domain.setGeoCover(DomainConstant.DOMAIN_GEO_COVER_CHINA);
        domain.setType(DomainConstant.DOMAIN_TYPE_NORMAL);
        domain.setValidFlag(ValidStatusEnum.VALID.value());
    }

    /**
     * 构建访问参数
     *
     * @param platform   平台类型
     * @param protocol   协议
     * @param bucketName bucket名称
     */
    private LinkedHashMap<String, Object> buildParams(String name, String platform, String protocol, String bucketName, Domain domain) throws Exception {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put(DomainConstant.DOMAIN_TYPE, DomainConstant.DOMAIN_TYPE_NORMAL);
        params.put(DomainConstant.DOMAIN_PLATFORM, platform);
        params.put(DomainConstant.DOMAIN_GEO_COVER, DomainConstant.DOMAIN_GEO_COVER_CHINA);
        //如果是https协议
        if (DomainConstant.DOMAIN_PROTOCOL_HTTPS.equals(protocol)) {
            SslCertificate sslCertificate = sslCertService.getByGenericName(name);
            if (sslCertificate == null) {
                throw new BusinessException(CommonConstant.ERROR, DomainConstant.DOMAIN_CERT_ERROR);
            }
            LinkedHashMap<String, Object> httpsMap = new LinkedHashMap<>();
            httpsMap.put(DomainConstant.CERT_ID, sslCertificate.getCertificateId());
            httpsMap.put(DomainConstant.FORCE_HTTPS, false);
            params.put(DomainConstant.DOMAIN_PROTOCOL_HTTPS, httpsMap);
            domain.setSslCertificateId(sslCertificate.getCertificateId());
        }
        params.put(DomainConstant.DOMAIN_PROTOCOL, protocol);
        LinkedHashMap<String, String> sourceMap = new LinkedHashMap<>();
        sourceMap.put(DomainConstant.DOMAIN_SOURCE_TYPE, DomainConstant.DOMAIN_SOURCE_TYPE_BUCKET);
        sourceMap.put(DomainConstant.DOMAIN_SOURCE_QINIU_BUCKET, bucketName);
        params.put(DomainConstant.DOMAIN_SOURCE, sourceMap);
        LinkedHashMap<String, Object> cacheMap = new LinkedHashMap<>();
        List<LinkedHashMap<String, Object>> cacheList = new ArrayList<>();
        LinkedHashMap<String, Object> cacheControlsMap = new LinkedHashMap<>();
        cacheControlsMap.put(DomainConstant.DOMAIN_CACHE_TIME, 1);
        cacheControlsMap.put(DomainConstant.DOMAIN_CACHE_TIME_UNIT, 5);
        cacheControlsMap.put(DomainConstant.DOMAIN_CACHE_TYPE, DomainConstant.DOMAIN_CACHE_TYPE_ALL);
        cacheControlsMap.put(DomainConstant.DOMAIN_CACHE_RULE, DomainConstant.DOMAIN_CACHE_RULE_ALL);
        cacheList.add(cacheControlsMap);
        cacheMap.put(DomainConstant.DOMAIN_CACHE_CONTROLS, cacheList);
        cacheMap.put(DomainConstant.DOMAIN_CACHE_IGNORE_PARAM, false);
        params.put(DomainConstant.DOMAIN_CACHE, cacheMap);
        return params;
    }

    /**
     * 根据ID查询数据
     *
     * @param domainId 数据ID
     * @return 数据对象
     */
    @Override
    public DomainInfo getById(String domainId) throws Exception {
        DomainInfo domainInfo = new DomainInfo();
        Domain domain = domainMapper.selectByPrimaryKey(domainId);
        if (domain == null || domain.getValidFlag().equals(ValidStatusEnum.INVALID.value())) {
            throw new BusinessException(CommonConstant.ERROR, DomainConstant.DOMAIN_NOT_EXIST);
        }
        try {
            domainInfo.setDomain(domain);
            //获取七牛云上信息
            DomainInfo qiNiuDomain = getQiuNiuDomain(domain.getName());
            domainInfo.setOperatingState(qiNiuDomain.getOperatingState());
        } catch (Exception e) {
            throw new BusinessException(CommonConstant.ERROR, DomainConstant.FAIL, e.getCause());
        }
        return domainInfo;
    }

    /**
     * 修改信息
     *
     * @param domainId 域名ID
     * @param bucketId 空间Id
     */
    @Override
    public void edit(String domainId, String bucketId) throws Exception {

        //判断当前域名是否可被修改
        Domain domain = domainMapper.selectByPrimaryKey(domainId);
        if (domain == null || domain.getValidFlag().equals(ValidStatusEnum.INVALID.value())) {
            throw new BusinessException(CommonConstant.ERROR, DomainConstant.DOMAIN_NOT_EXIST);
        }
        DomainInfo qiuNiuDomain = getQiuNiuDomain(domain.getName());
        if (!(qiuNiuDomain.getOperatingState().equals(DomainConstant.DOMAIN_SUCCESS)) || (qiuNiuDomain.getOperatingState().equals(DomainConstant.DOMAIN_FAILED))) {
            throw new BusinessException(CommonConstant.ERROR, DomainConstant.SUCCESS_STOP_STATE);
        }

        Bucket bucket = bucketService.getById(bucketId);
        if (bucket == null || bucket.getValidFlag().equals(ValidStatusEnum.INVALID.value())) {
            throw new BusinessException(CommonConstant.ERROR, DomainConstant.NOT_EXIST_BUCKET);
        }

        //修改库里数据
        domain.setBelongBucketId(bucket.getBucketId());
        domain.setUpdateTime(new Date());
        domainMapper.updateByPrimaryKey(domain);
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put(DomainConstant.DOMAIN_SOURCE_TYPE, DomainConstant.DOMAIN_SOURCE_TYPE_BUCKET);
        params.put(DomainConstant.DOMAIN_SOURCE_QINIU_BUCKET, bucket.getAliasName());
        String url = new Zone().getApiHttp() + DomainConstant.DOMAIN_PATH + domain.getName() + DomainConstant.DOMAIN_SOURCE_URL
                + DomainConstant.QUESTION_SYMBOL + DomainConstant.DOMAIN_SOURCE_TYPE + DomainConstant.EQUAL_SYMBOL + DomainConstant.DOMAIN_SOURCE_TYPE_BUCKET
                + DomainConstant.AND_SYMBOL + DomainConstant.DOMAIN_SOURCE_QINIU_BUCKET + DomainConstant.EQUAL_SYMBOL + bucket.getAliasName();
        try {

            Account account = accountService.getAccount();
            ResponsePojo responsePojo = HttpClientUtils.doPut(url, params, account, "", ResponsePojo.class);
            if (CommonConstant.SUCCESS != responsePojo.getCode()) {
                logger.error(responsePojo.getError());
                throw new BusinessException(CommonConstant.ERROR, DomainConstant.FAIL_EDIT);
            }
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                throw e;
            } else {
                throw new BusinessException(CommonConstant.ERROR, DomainConstant.FAIL_EDIT, e.getCause());
            }
        }


    }

    /**
     * post请求url
     *
     * @param url     访问的url
     * @param params  参数
     * @param account 密钥
     */
    private void executeUrlPost(String url, LinkedHashMap<String, Object> params, Account account) throws Exception {
        ResponsePojo responsePojo = HttpClientUtils.doPost(url, params, account, "", ResponsePojo.class);
        if (CommonConstant.SUCCESS != responsePojo.getCode()) {
            throw new BusinessException(CommonConstant.ERROR, responsePojo.getError());
        }

    }

    /**
     * 查询证书使用情况
     *
     * @param sslCertificateId
     * @return
     */
    @Override
    public List<Domain> getBySslCertificateId(String sslCertificateId) {
        if (StringUtils.isBlank(sslCertificateId)) {
            return null;
        }
        DomainExample example = new DomainExample();
        example.createCriteria().andValidFlagEqualTo(ValidStatusEnum.VALID.value()).andSslCertificateIdEqualTo(sslCertificateId);

        return domainMapper.selectByExample(example);
    }

    /**
     * 绑定空间域名
     *
     * @param name     域名
     * @param bucketId 空间ID
     * @throws Exception
     */
    @Override
    public void bindFreeDomain(String name, String bucketId) throws Exception {
        create(name, "", "", bucketId);
    }

    /**
     * 释放绑定的域名
     *
     * @param bucketId 空间ID
     * @throws Exception
     */
    @Override
    public void releaseDomain(String bucketId) {
        Domain domain = new Domain();
        domain.setUpdateTime(new Date());
        domain.setBelongBucketId("");
        DomainExample example = new DomainExample();
        DomainExample.Criteria criteria = example.createCriteria();
        criteria.andBelongBucketIdEqualTo(bucketId);
        domainMapper.updateByExampleSelective(domain, example);
    }

    /**
     * 获取库里有效且未绑定的域名（多条取第一条）
     * @return
     */
    @Override
    public Domain getFreeDomain() {
        DomainExample example = new DomainExample();
        DomainExample.Criteria criteria = example.createCriteria();
        DomainExample.Criteria criteria2 = example.createCriteria();
        criteria.andBelongBucketIdIsNull();
        criteria2.andBelongBucketIdEqualTo("");
        example.or(criteria2);
        criteria.andValidFlagEqualTo(ValidStatusEnum.VALID.value());
        List<Domain> domains = domainMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(domains)) {
            return domains.get(0);
        }
        return null;
    }

    /**
     * 获取所有有效的域名信息
     *
     * @return 键是域名ID，值为对象
     */
    @Override
    public Map<String, Domain> getMap() {
        Map<String, Domain> domainMap = new HashMap<>();
        DomainExample example = new DomainExample();
        DomainExample.Criteria criteria = example.createCriteria();
        criteria.andValidFlagEqualTo(ValidStatusEnum.VALID.value());
        List<Domain> domains = domainMapper.selectByExample(example);
        domains.stream().forEach(domain -> {
            domainMap.put(domain.getDomainId(), domain);
        });
        return domainMap;
    }

    /**
     * 判断当前未绑定的域名
     *
     * @param name
     * @return
     */
    private Domain getByName(String name) {
        DomainExample example = new DomainExample();
        DomainExample.Criteria criteria = example.createCriteria();
        DomainExample.Criteria criteria1 = example.createCriteria();
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(QueryUtils.generateLikeString(name));
        }
        criteria1.andBelongBucketIdIsNull();
        criteria1.andBelongBucketIdEqualTo("");
        example.or(criteria1);
        List<Domain> domains = domainMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(domains)) {
            return domains.get(0);
        }
        return null;
    }


}
