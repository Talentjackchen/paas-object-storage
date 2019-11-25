package com.wondersgroup.cloud.paas.storage.model;

import java.io.Serializable;

public class MdConfig implements Serializable {
    private String configId;

    private String parentConfigId;

    private String key;

    private String value;

    private String validFlag;

    private static final long serialVersionUID = 1L;

    public MdConfig(String configId, String parentConfigId, String key, String value, String validFlag) {
        this.configId = configId;
        this.parentConfigId = parentConfigId;
        this.key = key;
        this.value = value;
        this.validFlag = validFlag;
    }

    public MdConfig() {
        super();
    }

    public String getConfigId() {
        return configId;
    }

    public MdConfig withConfigId(String configId) {
        this.setConfigId(configId);
        return this;
    }

    public void setConfigId(String configId) {
        this.configId = configId == null ? null : configId.trim();
    }

    public String getParentConfigId() {
        return parentConfigId;
    }

    public MdConfig withParentConfigId(String parentConfigId) {
        this.setParentConfigId(parentConfigId);
        return this;
    }

    public void setParentConfigId(String parentConfigId) {
        this.parentConfigId = parentConfigId == null ? null : parentConfigId.trim();
    }

    public String getKey() {
        return key;
    }

    public MdConfig withKey(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    public String getValue() {
        return value;
    }

    public MdConfig withValue(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getValidFlag() {
        return validFlag;
    }

    public MdConfig withValidFlag(String validFlag) {
        this.setValidFlag(validFlag);
        return this;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag == null ? null : validFlag.trim();
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
        MdConfig other = (MdConfig) that;
        return (this.getConfigId() == null ? other.getConfigId() == null : this.getConfigId().equals(other.getConfigId()))
            && (this.getParentConfigId() == null ? other.getParentConfigId() == null : this.getParentConfigId().equals(other.getParentConfigId()))
            && (this.getKey() == null ? other.getKey() == null : this.getKey().equals(other.getKey()))
            && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()))
            && (this.getValidFlag() == null ? other.getValidFlag() == null : this.getValidFlag().equals(other.getValidFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getConfigId() == null) ? 0 : getConfigId().hashCode());
        result = prime * result + ((getParentConfigId() == null) ? 0 : getParentConfigId().hashCode());
        result = prime * result + ((getKey() == null) ? 0 : getKey().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        result = prime * result + ((getValidFlag() == null) ? 0 : getValidFlag().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", configId=").append(configId);
        sb.append(", parentConfigId=").append(parentConfigId);
        sb.append(", key=").append(key);
        sb.append(", value=").append(value);
        sb.append(", validFlag=").append(validFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}