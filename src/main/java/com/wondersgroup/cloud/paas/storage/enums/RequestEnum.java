package com.wondersgroup.cloud.paas.storage.enums;

/**
 * @author chenlong
 */
public enum RequestEnum {
    GET("1", "Get请求"),
    PUT_DELETE("2", "Put/Delete请求");

    private final String value;

    private final String render;

    RequestEnum(String value, String render) {
        this.value = value;
        this.render = render;
    }

    public String value() {
        return this.value;
    }

    public String render() {
        return this.render;
    }

}
