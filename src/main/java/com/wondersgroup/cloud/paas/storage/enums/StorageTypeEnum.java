package com.wondersgroup.cloud.paas.storage.enums;

/**
 * 上传文件存储类型
 * CD001000
 */
public enum StorageTypeEnum {

    COMMON("0","标准存储"),
    INFREQUENCY("1","低频存储");

    private final String value;

    private final String render;

    StorageTypeEnum(String value,String render) {
        this.value = value;
        this.render = render;
    }

    public String value() {
        return this.value;
    }

    public String render() {
        return this.render;
    }

    public static String getRenderByValue(String value){
        for(StorageTypeEnum type : StorageTypeEnum.values()){
            if(type.value().equals(value)){
                return type.render();
            }
        }
        return null;
    }

    public static StorageTypeEnum value(String value){
        for(StorageTypeEnum type : StorageTypeEnum.values()){
            if(type.value().equals(value)){
                return type;
            }
        }
        return null;
    }
}
