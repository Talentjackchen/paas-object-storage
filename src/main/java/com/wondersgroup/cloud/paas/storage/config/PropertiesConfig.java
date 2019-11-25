package com.wondersgroup.cloud.paas.storage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author chenlong
 */
@Component
public class PropertiesConfig {

    public static String AuthServerURL;

    public static boolean AuthEnable;

    public static String ClientSecret;

    public static String ClientId;

    @Value("${auth.server.url}")
    public void setAuthServerURL(String authServerURL) {
        AuthServerURL = authServerURL;
    }

    @Value("${auth.enable}")
    public void setAuthEnable(boolean authEnable) {
        AuthEnable = authEnable;
    }

    @Value("${security.oauth.client.clientSecret}")
    public void setClientSecret(String clientSecret) {
        ClientSecret = clientSecret;
    }

    @Value("${security.oauth.client.clientId}")
    public void setClientId(String clientId) {
        ClientId = clientId;
    }
}
