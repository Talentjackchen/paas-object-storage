package com.wondersgroup.cloud.paas.storage.enums;

/**
 * 存储空间类型
 * CD002000
 */
public enum BucketTypeEnum {

    PUBLIC("0","公开"),
    PRIVATE("1","私有");

    private final String value;

    private final String render;

    BucketTypeEnum(String value, String render){
        this.value = value;
        this.render = render;
    }

    public String value(){
        return this.value;
    }

    public String render() {
        return this.render;
    }

    public static String getRenderByValue(String value){
        for(BucketTypeEnum type : BucketTypeEnum.values()){
            if(type.value().equals(value)){
                return type.render();
            }
        }
        return null;
    }

    public static BucketTypeEnum value(String value){
        for(BucketTypeEnum type : BucketTypeEnum.values()){
            if(type.value().equals(value)){
                return type;
            }
        }
        return null;
    }
}
