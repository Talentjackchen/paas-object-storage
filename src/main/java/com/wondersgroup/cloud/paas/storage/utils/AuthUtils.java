package com.wondersgroup.cloud.paas.storage.utils;

import com.wondersgroup.cloud.paas.common.constant.AuthConstant;
import com.wondersgroup.cloud.paas.common.utils.HttpClientUtils;
import com.wondersgroup.cloud.paas.common.utils.JsonUtils;
import com.wondersgroup.cloud.paas.storage.config.PropertiesConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthUtils {

    private static JedisCluster jedisCluster;

    private static String key = "CLIENT_CREDENTIAL_ACCESS_TOKEN";

    /**
     * 凭证失效时间
     */
    private static int expireTime;

    @Autowired
    private void setJedisCluster(JedisCluster jedisCluster) {
        AuthUtils.jedisCluster = jedisCluster;
    }

    @Value("${client.credential.access.token.expire.time}")
    private void setExpireTime(int time) {
        expireTime = time;
    }

    /**
     * 获取token令牌（client_credentials）
     *
     * @return
     */
    public static String getClientCredentialAccessToken() {
        String url = PropertiesConfig.AuthServerURL + "/oauth/token";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("grant_type", "client_credentials");
        paramMap.put("client_id", PropertiesConfig.ClientId);
        paramMap.put("client_secret", PropertiesConfig.ClientSecret);
        String accessTokenStr = HttpClientUtils.doPost(url, paramMap);
        Map accessTokenMap = JsonUtils.jsonToMap(accessTokenStr);
        String accessToken = (String) accessTokenMap.get("access_token");
        return accessToken == null ? "" : accessToken;
    }

    /**
     * 从缓存中获取客户端凭证
     *
     * @return
     */
    public static String getClientCredentialAccessTokenFromCache() {
        String accessToken = jedisCluster.get(key);
        if (StringUtils.isBlank(accessToken)) {
            accessToken = getClientCredentialAccessToken();
            jedisCluster.setex(key, expireTime, accessToken);
        }
        return AuthConstant.BEARER + accessToken;
    }
}
