package com.wondersgroup.cloud.paas.storage.enums;

public enum CertBrandEnum {

    OWN("0","自有");

    private final String value;

    private final String render;

    CertBrandEnum(String value,String render) {
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
        for(CertBrandEnum type : CertBrandEnum.values()){
            if(type.value().equals(value)){
                return type.render();
            }
        }
        return null;
    }

    public static CertBrandEnum value(String value){
        for(CertBrandEnum type : CertBrandEnum.values()){
            if(type.value().equals(value)){
                return type;
            }
        }
        return null;
    }
}
