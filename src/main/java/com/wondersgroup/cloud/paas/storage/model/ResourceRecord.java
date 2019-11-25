package com.wondersgroup.cloud.paas.storage.model;

import java.io.Serializable;
import java.util.Date;

public class ResourceRecord implements Serializable {
    private String id;

    private String projectId;

    private String bucketId;

    private String resourceId;

    private String operationType;

    private Integer returnCode;

    private Date createTime;

    private String key;

    private static final long serialVersionUID = 1L;

    public ResourceRecord(String id, String projectId, String bucketId, String resourceId, String operationType, Integer returnCode, Date createTime, String key) {
        this.id = id;
        this.projectId = projectId;
        this.bucketId = bucketId;
        this.resourceId = resourceId;
        this.operationType = operationType;
        this.returnCode = returnCode;
        this.createTime = createTime;
        this.key = key;
    }

    public ResourceRecord() {
        super();
    }

    public String getId() {
        return id;
    }

    public ResourceRecord withId(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public ResourceRecord withProjectId(String projectId) {
        this.setProjectId(projectId);
        return this;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getBucketId() {
        return bucketId;
    }

    public ResourceRecord withBucketId(String bucketId) {
        this.setBucketId(bucketId);
        return this;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId == null ? null : bucketId.trim();
    }

    public String getResourceId() {
        return resourceId;
    }

    public ResourceRecord withResourceId(String resourceId) {
        this.setResourceId(resourceId);
        return this;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId == null ? null : resourceId.trim();
    }

    public String getOperationType() {
        return operationType;
    }

    public ResourceRecord withOperationType(String operationType) {
        this.setOperationType(operationType);
        return this;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType == null ? null : operationType.trim();
    }

    public Integer getReturnCode() {
        return returnCode;
    }

    public ResourceRecord withReturnCode(Integer returnCode) {
        this.setReturnCode(returnCode);
        return this;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ResourceRecord withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getKey() {
        return key;
    }

    public ResourceRecord withKey(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
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
        ResourceRecord other = (ResourceRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getBucketId() == null ? other.getBucketId() == null : this.getBucketId().equals(other.getBucketId()))
            && (this.getResourceId() == null ? other.getResourceId() == null : this.getResourceId().equals(other.getResourceId()))
            && (this.getOperationType() == null ? other.getOperationType() == null : this.getOperationType().equals(other.getOperationType()))
            && (this.getReturnCode() == null ? other.getReturnCode() == null : this.getReturnCode().equals(other.getReturnCode()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getKey() == null ? other.getKey() == null : this.getKey().equals(other.getKey()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getBucketId() == null) ? 0 : getBucketId().hashCode());
        result = prime * result + ((getResourceId() == null) ? 0 : getResourceId().hashCode());
        result = prime * result + ((getOperationType() == null) ? 0 : getOperationType().hashCode());
        result = prime * result + ((getReturnCode() == null) ? 0 : getReturnCode().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getKey() == null) ? 0 : getKey().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", bucketId=").append(bucketId);
        sb.append(", resourceId=").append(resourceId);
        sb.append(", operationType=").append(operationType);
        sb.append(", returnCode=").append(returnCode);
        sb.append(", createTime=").append(createTime);
        sb.append(", key=").append(key);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}