package com.wondersgroup.cloud.paas.storage.enums;

public enum ResourceOperationEnum {

    DELETE("0"),//删除
    ADD("1");//新增

    private String value;

    ResourceOperationEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
