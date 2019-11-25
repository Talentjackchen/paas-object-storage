package com.wondersgroup.cloud.paas.storage.enums;

public enum ResourceOperationTypeEnum {

    ADD("1","新增"),
    UPDATE_STORAGE_TYPE("2","修改存储类型"),
    DELETE("3","删除"),
    COPY("4","复制"),
    MOVE("5","移动"),
    UPDATE_FILE_TYPE("6","修改文件类型"),
    BATCH_COPY("7","批量复制"),
    BATCH_MOVE("8","批量移动"),
    BATCH_DELETE("9","批量删除"),
    MAKE_BLOCK("10","创建块"),
    MAKE_FILE("11","合并文件"),
    DOWNLOAD("12","下载"),
    PREVIEW("13","预览");


    private final String value;

    private final String render;

    ResourceOperationTypeEnum(String value,String render) {
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
        for(ResourceOperationTypeEnum type : ResourceOperationTypeEnum.values()){
            if(type.value().equals(value)){
                return type.render();
            }
        }
        return null;
    }

    public static ResourceOperationTypeEnum value(String value){
        for(ResourceOperationTypeEnum type : ResourceOperationTypeEnum.values()){
            if(type.value().equals(value)){
                return type;
            }
        }
        return null;
    }
}
