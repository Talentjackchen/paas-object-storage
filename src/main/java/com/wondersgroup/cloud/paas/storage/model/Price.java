package com.wondersgroup.cloud.paas.storage.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Price implements Serializable {
    private String priceId;

    private String code;

    private String subcode;

    private BigDecimal value;

    private Long scopeBegin;

    private Long scopeEnd;

    private String prefixlabel;

    private String suffixlabel;

    private String desc;

    private static final long serialVersionUID = 1L;

    public Price(String priceId, String code, String subcode, BigDecimal value, Long scopeBegin, Long scopeEnd, String prefixlabel, String suffixlabel, String desc) {
        this.priceId = priceId;
        this.code = code;
        this.subcode = subcode;
        this.value = value;
        this.scopeBegin = scopeBegin;
        this.scopeEnd = scopeEnd;
        this.prefixlabel = prefixlabel;
        this.suffixlabel = suffixlabel;
        this.desc = desc;
    }

    public Price() {
        super();
    }

    public String getPriceId() {
        return priceId;
    }

    public Price withPriceId(String priceId) {
        this.setPriceId(priceId);
        return this;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId == null ? null : priceId.trim();
    }

    public String getCode() {
        return code;
    }

    public Price withCode(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getSubcode() {
        return subcode;
    }

    public Price withSubcode(String subcode) {
        this.setSubcode(subcode);
        return this;
    }

    public void setSubcode(String subcode) {
        this.subcode = subcode == null ? null : subcode.trim();
    }

    public BigDecimal getValue() {
        return value;
    }

    public Price withValue(BigDecimal value) {
        this.setValue(value);
        return this;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Long getScopeBegin() {
        return scopeBegin;
    }

    public Price withScopeBegin(Long scopeBegin) {
        this.setScopeBegin(scopeBegin);
        return this;
    }

    public void setScopeBegin(Long scopeBegin) {
        this.scopeBegin = scopeBegin;
    }

    public Long getScopeEnd() {
        return scopeEnd;
    }

    public Price withScopeEnd(Long scopeEnd) {
        this.setScopeEnd(scopeEnd);
        return this;
    }

    public void setScopeEnd(Long scopeEnd) {
        this.scopeEnd = scopeEnd;
    }

    public String getPrefixlabel() {
        return prefixlabel;
    }

    public Price withPrefixlabel(String prefixlabel) {
        this.setPrefixlabel(prefixlabel);
        return this;
    }

    public void setPrefixlabel(String prefixlabel) {
        this.prefixlabel = prefixlabel == null ? null : prefixlabel.trim();
    }

    public String getSuffixlabel() {
        return suffixlabel;
    }

    public Price withSuffixlabel(String suffixlabel) {
        this.setSuffixlabel(suffixlabel);
        return this;
    }

    public void setSuffixlabel(String suffixlabel) {
        this.suffixlabel = suffixlabel == null ? null : suffixlabel.trim();
    }

    public String getDesc() {
        return desc;
    }

    public Price withDesc(String desc) {
        this.setDesc(desc);
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Price other = (Price) that;
        return (this.getPriceId() == null ? other.getPriceId() == null : this.getPriceId().equals(other.getPriceId()))
                && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
                && (this.getSubcode() == null ? other.getSubcode() == null : this.getSubcode().equals(other.getSubcode()))
                && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()))
                && (this.getScopeBegin() == null ? other.getScopeBegin() == null : this.getScopeBegin().equals(other.getScopeBegin()))
                && (this.getScopeEnd() == null ? other.getScopeEnd() == null : this.getScopeEnd().equals(other.getScopeEnd()))
                && (this.getPrefixlabel() == null ? other.getPrefixlabel() == null : this.getPrefixlabel().equals(other.getPrefixlabel()))
                && (this.getSuffixlabel() == null ? other.getSuffixlabel() == null : this.getSuffixlabel().equals(other.getSuffixlabel()))
                && (this.getDesc() == null ? other.getDesc() == null : this.getDesc().equals(other.getDesc()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPriceId() == null) ? 0 : getPriceId().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getSubcode() == null) ? 0 : getSubcode().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        result = prime * result + ((getScopeBegin() == null) ? 0 : getScopeBegin().hashCode());
        result = prime * result + ((getScopeEnd() == null) ? 0 : getScopeEnd().hashCode());
        result = prime * result + ((getPrefixlabel() == null) ? 0 : getPrefixlabel().hashCode());
        result = prime * result + ((getSuffixlabel() == null) ? 0 : getSuffixlabel().hashCode());
        result = prime * result + ((getDesc() == null) ? 0 : getDesc().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", priceId=").append(priceId);
        sb.append(", code=").append(code);
        sb.append(", subcode=").append(subcode);
        sb.append(", value=").append(value);
        sb.append(", scopeBegin=").append(scopeBegin);
        sb.append(", scopeEnd=").append(scopeEnd);
        sb.append(", prefixlabel=").append(prefixlabel);
        sb.append(", suffixlabel=").append(suffixlabel);
        sb.append(", desc=").append(desc);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}