package com.wondersgroup.cloud.paas.storage.config.redis;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zhangyongzhao
 */

@Configuration
@ConditionalOnClass({JedisCluster.class})
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

    @Resource
    private RedisProperties redisProperties;

    @Bean
    @Scope("singleton")
    public JedisCluster getJedisCluster() {
        String[] nodesArray = redisProperties.getNodes().split(",");
        Set<HostAndPort> nodes = new HashSet<>();
        for (String ipPort: nodesArray) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(),Integer.valueOf(ipPortPair[1].trim())));
        }
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(redisProperties.getMaxIdle());
        poolConfig.setMaxTotal(redisProperties.getMaxTotal());
        poolConfig.setMinIdle(redisProperties.getMinIdle());
        if(StringUtils.isNotBlank(redisProperties.getPassword())){
            return new JedisCluster(nodes, redisProperties.getConnectionTimeout(), redisProperties.getSocketTimeout(),
                    redisProperties.getMaxAttempts(), redisProperties.getPassword(), poolConfig);
        }else{
            return new JedisCluster(nodes, redisProperties.getConnectionTimeout(), redisProperties.getSocketTimeout(),
                    redisProperties.getMaxAttempts(), poolConfig);
        }
    }

}
