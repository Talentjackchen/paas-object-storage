package com.wondersgroup.cloud.paas.storage.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author chenlong
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceSubPojo {
    private String subkey;

    private String prefixLabel;

    private String value;

    private String suffixLabel;

    private Long scopeBegin;

    private Long scopeEnd;

    public String getSubkey() {
        return subkey;
    }

    public void setSubkey(String subkey) {
        this.subkey = subkey;
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
