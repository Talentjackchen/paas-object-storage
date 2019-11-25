package com.wondersgroup.cloud.paas.storage.enums;

/**
 * 防盗链枚举
 */
public enum AclStatusEnum {

    STOP("0","未开启"),
    START("1","已开启");

    private final String value;

    private final String render;

    AclStatusEnum(String value, String render){
        this.value = value;
        this.render = render;
    }

    public String value(){
        return this.value;
    }

    public String render() {
        return this.render;
    }


}
