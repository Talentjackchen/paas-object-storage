package com.wondersgroup.cloud.paas.storage.service.impl;

import com.wondersgroup.cloud.paas.common.constant.CommonConstant;
import com.wondersgroup.cloud.paas.common.enums.status.ValidStatusEnum;
import com.wondersgroup.cloud.paas.common.utils.CollectionUtils;
import com.wondersgroup.cloud.paas.storage.constant.MdConfigConstant;
import com.wondersgroup.cloud.paas.storage.enums.RedisCacheKeyEnum;
import com.wondersgroup.cloud.paas.storage.mapper.MdConfigMapper;
import com.wondersgroup.cloud.paas.storage.model.MdConfig;
import com.wondersgroup.cloud.paas.storage.model.MdConfigExample;
import com.wondersgroup.cloud.paas.storage.service.MdConfigService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenlong
 */
@Service
public class MdConfigServiceImpl implements MdConfigService {

    private Logger logger = Logger.getLogger(MdConfigService.class);

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired(required = false)
    private MdConfigMapper mdConfigMapper;

    @PostConstruct
    @Override
    public void load() {
        List<MdConfig> list = list();
        Map<String, Map<String, MdConfig>> map = buildMap(list);
        if (map != null && map.size() > 0) {
            map.keySet().stream().forEach(key -> {
                byte[] byteKey = (RedisCacheKeyEnum.MD_CONFIG.value() + CommonConstant.UNDERLINE + key).getBytes();
                jedisCluster.del(byteKey);
                jedisCluster.hset(byteKey, MdConfigConstant.REDIS_MD_CONFIG.getBytes(), mapToByteArray(map.get(key)));
            });
        }
    }

    @Override
    public Map<String, String> getMapByParentId(String parentId) {
        Map<String, String> map = new HashMap<>();
        byte[] byteKey = (RedisCacheKeyEnum.MD_CONFIG.value() + CommonConstant.UNDERLINE + parentId).getBytes();
        Map<String, MdConfig> mdConfigMap = (Map<String, MdConfig>) byteArrayToMap(jedisCluster.hget(byteKey, MdConfigConstant.REDIS_MD_CONFIG.getBytes()));
        if (mdConfigMap != null && mdConfigMap.size() > 0) {
            mdConfigMap.values().parallelStream().forEach(mdConfig -> {
                map.put(mdConfig.getKey(), mdConfig.getValue());
            });
        }

        return map;
    }

    private Map<String, Map<String, MdConfig>> buildMap(List<MdConfig> list) {
        Map<String, Map<String, MdConfig>> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(mdConfig -> {
                if (!StringUtils.isEmpty(mdConfig.getParentConfigId())) {
                    String key = mdConfig.getParentConfigId();
                    Map<String, MdConfig> mdConfigMap = null;
                    if (map.containsKey(key)) {
                        mdConfigMap = map.get(key);
                    } else {
                        mdConfigMap = new HashMap<>();
                        map.put(key, mdConfigMap);
                    }
                    mdConfigMap.put(mdConfig.getKey(), mdConfig);
                }
            });
        }

        return map;
    }

    private List<MdConfig> list() {
        MdConfigExample example = new MdConfigExample();
        example.createCriteria().andValidFlagEqualTo(ValidStatusEnum.VALID.value());
        List<MdConfig> list = mdConfigMapper.selectByExample(example);
        return list;
    }

    private byte[] mapToByteArray(Map<String, MdConfig> map) {
        byte[] bt = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            os.writeObject(map);
            bt = bos.toByteArray();
            os.close();
            bos.close();
        } catch (Exception ex) {
            logger.error(ex);
        }
        return bt;
    }

    public Object byteArrayToMap(byte[] in) {
        Object object = null;
        ByteArrayInputStream bis;
        ObjectInputStream is;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                object = is.readObject();
                is.close();
                bis.close();
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
        return object;
    }
}
