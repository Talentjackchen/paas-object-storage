package com.wondersgroup.cloud.paas.storage.enums;

/**
 * @author chenlong
 * Ip类型枚举
 */
public enum IpAclTypeEnum {
    WHITE("WHITE","白名单");

    private String value;

    private String  render;

    IpAclTypeEnum(String value,String render){
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
