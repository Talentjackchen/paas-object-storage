package com.wondersgroup.cloud.paas.storage.enums;

/**
 * @author chenlong
 */
public enum FormatTypeEnum {
    ROW("row", "行"),
    TABLE("table", "表格");

    private final String value;

    private final String render;

    FormatTypeEnum(String value, String render) {
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
