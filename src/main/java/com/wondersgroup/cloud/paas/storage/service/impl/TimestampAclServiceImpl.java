package com.wondersgroup.cloud.paas.storage.service.impl;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.utils.CommonUtils;
import com.wondersgroup.cloud.paas.common.utils.JsonUtils;
import com.wondersgroup.cloud.paas.storage.constant.TimestampAclConstant;
import com.wondersgroup.cloud.paas.storage.enums.RedisCacheKeyEnum;
import com.wondersgroup.cloud.paas.storage.mapper.TimestampAclMapper;
import com.wondersgroup.cloud.paas.storage.model.TimestampAcl;
import com.wondersgroup.cloud.paas.storage.model.TimestampAclExample;
import com.wondersgroup.cloud.paas.storage.service.BucketService;
import com.wondersgroup.cloud.paas.storage.service.TimestampAclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author chenlong
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TimestampAclServiceImpl implements TimestampAclService {
    @Autowired
    private JedisCluster jedisCluster;

    @Autowired(required = false)
    private TimestampAclMapper timestampAclMapper;


    @PostConstruct
    @Override
    public void load() {
        List<TimestampAcl> list = query();
        if (!CollectionUtils.isEmpty(list)) {
            list.parallelStream().forEach(timestampAcl -> {
                String bucketId = timestampAcl.getBucketId();
                jedisCluster.del(RedisCacheKeyEnum.TIMESTAMP_ACL.value() + CommonConstant.UNDERLINE + bucketId);
                jedisCluster.hset(RedisCacheKeyEnum.TIMESTAMP_ACL.value() + CommonConstant.UNDERLINE + bucketId, TimestampAclConstant.REDIS_TIMESTAMP, JsonUtils.getJsonString(timestampAcl));
            });
        }
    }

    @Override
    public void create(String bucketId, String mainKey, String spareKey) {
        TimestampAcl timestampAcl = new TimestampAcl();
        timestampAcl.setTimestampAclId(CommonUtils.generateId());
        timestampAcl.setBucketId(bucketId);
        timestampAcl.setMainKey(mainKey);
        timestampAcl.setSpareKey(spareKey);
        timestampAclMapper.insert(timestampAcl);
        jedisCluster.hset(RedisCacheKeyEnum.TIMESTAMP_ACL.value() + CommonConstant.UNDERLINE + bucketId, TimestampAclConstant.REDIS_TIMESTAMP, JsonUtils.getJsonString(timestampAcl));
    }

    @Override
    public void update(String bucketId, String mainKey, String spareKey) {
        this.delete(bucketId);
        this.create(bucketId, mainKey, spareKey);
    }

    @Override
    public void delete(String bucketId) {
        TimestampAclExample example = new TimestampAclExample();
        example.createCriteria().andBucketIdEqualTo(bucketId);
        timestampAclMapper.deleteByExample(example);
        jedisCluster.del(RedisCacheKeyEnum.TIMESTAMP_ACL.value() + CommonConstant.UNDERLINE + bucketId);
    }

    @Override
    public TimestampAcl queryByBucketId(String bucketId) {
        String str = jedisCluster.hget(RedisCacheKeyEnum.TIMESTAMP_ACL.value() + CommonConstant.UNDERLINE + bucketId, TimestampAclConstant.REDIS_TIMESTAMP);
        TimestampAcl timestampAcl = JsonUtils.jsonStringToBean(str, TimestampAcl.class);
        return timestampAcl;
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    private List<TimestampAcl> query() {
        TimestampAclExample example = new TimestampAclExample();
        List<TimestampAcl> list = timestampAclMapper.selectByExample(example);
        return list;
    }

}
