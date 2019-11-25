package com.wondersgroup.cloud.paas.storage.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @author chenlong
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PricePojo {
    private String key;

    private String formatType;

    private String label;

    private String prefixLabel;

    private String value;

    private String suffixLabel;

    private String region;

    private String type;

    private Long scopeBegin;

    private Long scopeEnd;

    private List<PriceSubPojo> table;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFormatType() {
        return formatType;
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPrefixLabel() {
        return prefixLabel;
    }

    public void setPrefixLabel(String prefixLabel) {
        this.prefixLabel = prefixLabel;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSuffixLabel() {
        return suffixLabel;
    }

    public void setSuffixLabel(String suffixLabel) {
        this.suffixLabel = suffixLabel;
    }

    public List<PriceSubPojo> getTable() {
        return table;
    }

    public void setTable(List<PriceSubPojo> table) {
        this.table = table;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getScopeBegin() {
        return scopeBegin;
    }

    public Long getScopeEnd() {
        return scopeEnd;
    }

    public void setScopeBegin(Long scopeBegin) {
        this.scopeBegin = scopeBegin;
    }

    public void setScopeEnd(Long scopeEnd) {
        this.scopeEnd = scopeEnd;
    }
}
