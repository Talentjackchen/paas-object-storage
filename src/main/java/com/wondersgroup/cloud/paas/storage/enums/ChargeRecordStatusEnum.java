package com.wondersgroup.cloud.paas.storage.enums;

public enum ChargeRecordStatusEnum {

    FAILED("0", "失败"),
    SUCCESS("1", "成功");

    private final String value;

    private final String render;

    ChargeRecordStatusEnum(String value, String render) {
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
