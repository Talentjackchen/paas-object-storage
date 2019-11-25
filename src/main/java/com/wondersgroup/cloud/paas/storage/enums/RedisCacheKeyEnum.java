package com.wondersgroup.cloud.paas.storage.enums;

/**
 * @author chenlong
 * Redis缓存Key枚举
 */
public enum RedisCacheKeyEnum {
    /**
     * Referer防盗链
     */
    REFERER_ACL("REFERER_ACL"),

    /**
     * IP防盗链
     */
    IP_ACL("IP_ACL"),

    /**
     * 时间戳防盗链
     */
    TIMESTAMP_ACL("TIMESTAMP_ACL"),

    /**
     * 数据字典
     */
    MD_CONFIG("MD_CONFIG");
    private String value;

    RedisCacheKeyEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
