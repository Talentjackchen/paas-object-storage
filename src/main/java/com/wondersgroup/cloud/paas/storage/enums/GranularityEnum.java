package com.wondersgroup.cloud.paas.storage.enums;

public enum GranularityEnum {

    DAY("day","每天"),
    HOUR("hour","每小时"),
    MIN("5min","每5分钟");

    private final String value;

    private final String render;

    GranularityEnum(String value,String render) {
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
        for(GranularityEnum type : GranularityEnum.values()){
            if(type.value().equals(value)){
                return type.render();
            }
        }
        return null;
    }

    public static GranularityEnum value(String value){
        for(GranularityEnum type : GranularityEnum.values()){
            if(type.value().equals(value)){
                return type;
            }
        }
        return null;
    }
}
