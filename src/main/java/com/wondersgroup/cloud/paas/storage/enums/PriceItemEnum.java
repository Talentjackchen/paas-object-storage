package com.wondersgroup.cloud.paas.storage.enums;

/**
 * @author chenlong
 * 存储空间计费项枚举
 */
public enum PriceItemEnum {
    LOW_API_REQUEST("LOW_API_REQUEST", "低频PUT/DELETE请求"),
    LOW_CDN_OUT("LOW_CDN_OUT", "低频CDN回源流出流量"),
    LOW_DATA_READ("LOW_DATA_READ", "低频数据读取"),
    LOW_GET_REQUEST("LOW_GET_REQUEST", "低频GET请求"),
    LOW_STORAGE_Z0("LOW_STORAGE_Z0", "低频存储空间（华东）"),
    LOW_STORAGE_Z1("LOW_STORAGE_Z1", "低频存储空间（华北）"),
    LOW_STORAGE_Z2("LOW_STORAGE_Z2", "标频存储空间（华南）"),
    EXCHANGE("EXCHANGE", "存储类型转换"),
    STANDARD_API_REQUEST("STANDARD_API_REQUEST", "标准PUT/DELETE请求"),
    STANDARD_CDN_OUT("STANDARD_CDN_OUT", "标准CDN回源流出流量"),
    STANDARD_DATA_READ("STANDARD_DATA_READ", "标准数据读取"),
    STANDARD_GET_REQUEST("STANDARD_GET_REQUEST", "标准GET请求"),
    STANDARD_STORAGE_Z0("STANDARD_STORAGE_Z0", "标准存储空间（华东）"),
    STANDARD_STORAGE_Z1("STANDARD_STORAGE_Z1", "标准存储空间（华北）"),
    STANDARD_STORAGE_Z2("STANDARD_STORAGE_Z2", "标准存储空间（华南）");

    private String value;
    private String render;

    PriceItemEnum(String value, String render) {
        this.value = value;
        this.render = render;
    }

    public String value() {
        return this.value;
    }

    public String getRender() {
        return this.render;
    }
}
