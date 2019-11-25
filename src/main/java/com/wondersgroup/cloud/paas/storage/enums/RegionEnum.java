package com.wondersgroup.cloud.paas.storage.enums;

public enum RegionEnum {

    HUADONG("z0","华东"),
    HUABEI("z1","华北"),
    HUANAN("z2","华南"),
    BEIMEI("na0","北美"),
    DONGNANYA("as0","东南亚");

    private final String value;

    private final String  render;

    RegionEnum(String value,String render){
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
        for(RegionEnum region : RegionEnum.values()){
            if(region.value().equals(value)){
                return region.render();
            }
        }
        return null;
    }

    public static RegionEnum value(String value){
        for(RegionEnum region : RegionEnum.values()){
            if(region.value().equals(value)){
                return region;
            }
        }
        return null;
    }
}
