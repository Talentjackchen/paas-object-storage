package com.wondersgroup.cloud.paas.storage.enums;

/**
 * create_by : zhangyongzhao
 * create_time : 2019/5/21
 */
public enum BucketStatusEnum {

    FORMAL("1", "正式空间"),
    TEMPORARY("0", "临时空间");

    private String value;

    private String render;

    BucketStatusEnum(String value, String render) {
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
