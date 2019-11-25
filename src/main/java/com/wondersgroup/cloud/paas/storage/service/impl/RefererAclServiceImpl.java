package com.wondersgroup.cloud.paas.storage.service.impl;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.exception.BusinessException;
import com.wondersgroup.cloud.paas.common.utils.CommonUtils;
import com.wondersgroup.cloud.paas.common.utils.JsonUtils;
import com.wondersgroup.cloud.paas.storage.constant.RefererAclConstant;
import com.wondersgroup.cloud.paas.storage.enums.RedisCacheKeyEnum;
import com.wondersgroup.cloud.paas.storage.enums.RefererAclTypeEnum;
import com.wondersgroup.cloud.paas.storage.mapper.RefererAclMapper;
import com.wondersgroup.cloud.paas.storage.model.RefererAcl;
import com.wondersgroup.cloud.paas.storage.model.RefererAclExample;
import com.wondersgroup.cloud.paas.storage.service.BucketService;
import com.wondersgroup.cloud.paas.storage.service.RefererAclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenlong
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RefererAclServiceImpl implements RefererAclService {

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired(required = false)
    private RefererAclMapper refererAclMapper;


    @PostConstruct
    @Override
    public void load() {
        List<RefererAcl> list = query();
        if (!CollectionUtils.isEmpty(list)) {
            list.parallelStream().forEach(refererAcl -> {
                String bucketId = refererAcl.getBucketId();
                jedisCluster.del(RedisCacheKeyEnum.REFERER_ACL.value() + CommonConstant.UNDERLINE + bucketId);
                jedisCluster.hset(RedisCacheKeyEnum.REFERER_ACL.value() + CommonConstant.UNDERLINE + bucketId, RefererAclConstant.REDIS_REFERER, JsonUtils.getJsonString(refererAcl));
            });
        }
    }
    @Override
    public void create(String bucketId, RefererAcl refererAcl) throws Exception {
        List<String> refererList = Arrays.asList(refererAcl.getDomain().split(","));
        StringBuilder strBuilder = new StringBuilder();
        try{
            refererList.stream().forEach(referer ->{
                if(!checkDomain(referer)){
                    throw new RuntimeException();
                }
                strBuilder.append(referer).append(",");
            });
        }catch (Exception ex){
            throw new BusinessException(RefererAclConstant.DOMAIN_NOT_NORM_CODE, RefererAclConstant.DOMAIN_NOT_NORM_MSG, ex.getCause());
        }

        String domains = strBuilder.substring(0,strBuilder.length() - 1);

        refererAcl.setRefererAclId(CommonUtils.generateId());
        refererAcl.setBucketId(bucketId);
        refererAcl.setDomain(domains);
        refererAcl.setType(RefererAclTypeEnum.WHITE.value());
        refererAclMapper.insert(refererAcl);
        jedisCluster.hset(RedisCacheKeyEnum.REFERER_ACL.value() + CommonConstant.UNDERLINE + bucketId, RefererAclConstant.REDIS_REFERER, JsonUtils.getJsonString(refererAcl));
    }

    @Override
    public void update(String domainId, RefererAcl refererAcl) throws Exception {
        this.delete(domainId);
        this.create(domainId, refererAcl);
    }

    @Override
    public void delete(String bucketId) {
        RefererAclExample example = new RefererAclExample();
        example.createCriteria().andBucketIdEqualTo(bucketId);
        refererAclMapper.deleteByExample(example);
        jedisCluster.del(RedisCacheKeyEnum.REFERER_ACL.value() + CommonConstant.UNDERLINE + bucketId);
    }

    @Override
    public RefererAcl queryByBucketId(String bucketId) {
        String str = jedisCluster.hget(RedisCacheKeyEnum.REFERER_ACL.value() + CommonConstant.UNDERLINE + bucketId, RefererAclConstant.REDIS_REFERER);
        RefererAcl refererAcl = JsonUtils.jsonStringToBean(str, RefererAcl.class);
        return refererAcl;
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    private List<RefererAcl> query() {
        RefererAclExample example = new RefererAclExample();
        List<RefererAcl> list = refererAclMapper.selectByExample(example);
        return list;
    }

    private boolean checkDomain(String domain){
        String pattern = "^(\\*.)?(([a-z0-9A-Z]+)\\.)*[a-z]+$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(domain);
        return m.find();
    }
}
