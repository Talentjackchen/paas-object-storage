package com.wondersgroup.cloud.paas.storage.model;

import java.io.Serializable;
import java.util.Date;

public class Resource implements Serializable {
    private String resourceId;

    private String key;

    private String type;

    private String bucketId;

    private String storageType;

    private Long fileSize;

    private Integer lifeCycle;

    private String createUser;

    private String validFlag;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Resource(String resourceId, String key, String type, String bucketId, String storageType, Long fileSize, Integer lifeCycle, String createUser, String validFlag, Date createTime, Date updateTime) {
        this.resourceId = resourceId;
        this.key = key;
        this.type = type;
        this.bucketId = bucketId;
        this.storageType = storageType;
        this.fileSize = fileSize;
        this.lifeCycle = lifeCycle;
        this.createUser = createUser;
        this.validFlag = validFlag;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Resource() {
        super();
    }

    public String getResourceId() {
        return resourceId;
    }

    public Resource withResourceId(String resourceId) {
        this.setResourceId(resourceId);
        return this;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId == null ? null : resourceId.trim();
    }

    public String getKey() {
        return key;
    }

    public Resource withKey(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    public String getType() {
        return type;
    }

    public Resource withType(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getBucketId() {
        return bucketId;
    }

    public Resource withBucketId(String bucketId) {
        this.setBucketId(bucketId);
        return this;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId == null ? null : bucketId.trim();
    }

    public String getStorageType() {
        return storageType;
    }

    public Resource withStorageType(String storageType) {
        this.setStorageType(storageType);
        return this;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType == null ? null : storageType.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public Resource withFileSize(Long fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getLifeCycle() {
        return lifeCycle;
    }

    public Resource withLifeCycle(Integer lifeCycle) {
        this.setLifeCycle(lifeCycle);
        return this;
    }

    public void setLifeCycle(Integer lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public String getCreateUser() {
        return createUser;
    }

    public Resource withCreateUser(String createUser) {
        this.setCreateUser(createUser);
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getValidFlag() {
        return validFlag;
    }

    public Resource withValidFlag(String validFlag) {
        this.setValidFlag(validFlag);
        return this;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag == null ? null : validFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Resource withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Resource withUpdateTime(Date updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        Resource other = (Resource) that;
        return (this.getResourceId() == null ? other.getResourceId() == null : this.getResourceId().equals(other.getResourceId()))
                && (this.getKey() == null ? other.getKey() == null : this.getKey().equals(other.getKey()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getBucketId() == null ? other.getBucketId() == null : this.getBucketId().equals(other.getBucketId()))
                && (this.getStorageType() == null ? other.getStorageType() == null : this.getStorageType().equals(other.getStorageType()))
                && (this.getFileSize() == null ? other.getFileSize() == null : this.getFileSize().equals(other.getFileSize()))
            && (this.getLifeCycle() == null ? other.getLifeCycle() == null : this.getLifeCycle().equals(other.getLifeCycle()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
                && (this.getValidFlag() == null ? other.getValidFlag() == null : this.getValidFlag().equals(other.getValidFlag()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getResourceId() == null) ? 0 : getResourceId().hashCode());
        result = prime * result + ((getKey() == null) ? 0 : getKey().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getBucketId() == null) ? 0 : getBucketId().hashCode());
        result = prime * result + ((getStorageType() == null) ? 0 : getStorageType().hashCode());
        result = prime * result + ((getFileSize() == null) ? 0 : getFileSize().hashCode());
        result = prime * result + ((getLifeCycle() == null) ? 0 : getLifeCycle().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getValidFlag() == null) ? 0 : getValidFlag().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", resourceId=").append(resourceId);
        sb.append(", key=").append(key);
        sb.append(", type=").append(type);
        sb.append(", bucketId=").append(bucketId);
        sb.append(", storageType=").append(storageType);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", lifeCycle=").append(lifeCycle);
        sb.append(", createUser=").append(createUser);
        sb.append(", validFlag=").append(validFlag);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}