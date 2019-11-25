package com.wondersgroup.cloud.paas.storage.service.impl;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.exception.BusinessException;
import com.wondersgroup.cloud.paas.common.utils.CommonUtils;
import com.wondersgroup.cloud.paas.common.utils.IpUtils;
import com.wondersgroup.cloud.paas.common.utils.JsonUtils;
import com.wondersgroup.cloud.paas.storage.constant.IpAclConstant;
import com.wondersgroup.cloud.paas.storage.enums.IpAclTypeEnum;
import com.wondersgroup.cloud.paas.storage.enums.RedisCacheKeyEnum;
import com.wondersgroup.cloud.paas.storage.mapper.IpAclMapper;
import com.wondersgroup.cloud.paas.storage.model.IpAcl;
import com.wondersgroup.cloud.paas.storage.model.IpAclExample;
import com.wondersgroup.cloud.paas.storage.service.IpAclService;
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
public class IpAclServiceImpl implements IpAclService {
    @Autowired
    private JedisCluster jedisCluster;

    @Autowired(required = false)
    private IpAclMapper ipAclMapper;


    @PostConstruct
    @Override
    public void load() {
        List<IpAcl> list = query();
        if (!CollectionUtils.isEmpty(list)) {
            list.parallelStream().forEach(ipAcl -> {
                String bucketId = ipAcl.getBucketId();
                jedisCluster.del(RedisCacheKeyEnum.IP_ACL.value() + CommonConstant.UNDERLINE + bucketId);
                jedisCluster.hset(RedisCacheKeyEnum.IP_ACL.value() + CommonConstant.UNDERLINE + bucketId, IpAclConstant.REDIS_IP, JsonUtils.getJsonString(ipAcl));
            });
        }
    }

    @Override
    public void create(String bucketId, List<String> ipList) throws Exception {
        StringBuilder strBuilder = new StringBuilder();
        try {
            ipList.stream().forEach(ip -> {
                if (!IpUtils.checkIp(ip) && !IpUtils.checkSegment(ip)) {
                    throw new RuntimeException();
                }
                strBuilder.append(ip).append(",");
            });
        } catch (Exception ex) {
            throw new BusinessException(IpAclConstant.IP_NOT_NORM_CODE, IpAclConstant.IP_NOT_NORM_MSG, ex.getCause());
        }

        String addresses = strBuilder.substring(0,strBuilder.length() - 1);
        IpAcl ipAcl = new IpAcl();
        ipAcl.setIpAclId(CommonUtils.generateId());
        ipAcl.setBucketId(bucketId);
        ipAcl.setIpAddress(addresses);
        ipAcl.setType(IpAclTypeEnum.WHITE.value());
        ipAclMapper.insert(ipAcl);
        jedisCluster.hset(RedisCacheKeyEnum.IP_ACL.value() + CommonConstant.UNDERLINE + bucketId, IpAclConstant.REDIS_IP, JsonUtils.getJsonString(ipAcl));
    }

    @Override
    public void update(String domainId, List<String> ipList) throws Exception {
        this.delete(domainId);
        this.create(domainId,ipList);
    }

    @Override
    public void delete(String bucketId) {
        IpAclExample example = new IpAclExample();
        example.createCriteria().andBucketIdEqualTo(bucketId);
        ipAclMapper.deleteByExample(example);
        jedisCluster.del(RedisCacheKeyEnum.IP_ACL.value() + CommonConstant.UNDERLINE + bucketId);
    }

    @Override
    public IpAcl queryByBucketId(String bucketId) {
        String str = jedisCluster.hget(RedisCacheKeyEnum.IP_ACL.value() + CommonConstant.UNDERLINE + bucketId, IpAclConstant.REDIS_IP);
        IpAcl ipAcl = JsonUtils.jsonStringToBean(str, IpAcl.class);
        return ipAcl;
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    private List<IpAcl> query() {
        IpAclExample example = new IpAclExample();
        List<IpAcl> list = ipAclMapper.selectByExample(example);
        return list;
    }

}
