package com.wondersgroup.cloud.paas.storage.model;

import java.io.Serializable;
import java.util.Date;

public class ResourceStatistic implements Serializable {
    private String resourceStatisticId;

    private Long fileSize;

    private String storageType;

    private String operation;

    private String bucketId;

    private String resourceId;

    private String projectId;

    private Date recordTime;

    private Date addRecordTime;

    private static final long serialVersionUID = 1L;

    public ResourceStatistic(String resourceStatisticId, Long fileSize, String storageType, String operation, String bucketId, String resourceId, String projectId, Date recordTime, Date addRecordTime) {
        this.resourceStatisticId = resourceStatisticId;
        this.fileSize = fileSize;
        this.storageType = storageType;
        this.operation = operation;
        this.bucketId = bucketId;
        this.resourceId = resourceId;
        this.projectId = projectId;
        this.recordTime = recordTime;
        this.addRecordTime = addRecordTime;
    }

    public ResourceStatistic() {
        super();
    }

    public String getResourceStatisticId() {
        return resourceStatisticId;
    }

    public ResourceStatistic withResourceStatisticId(String resourceStatisticId) {
        this.setResourceStatisticId(resourceStatisticId);
        return this;
    }

    public void setResourceStatisticId(String resourceStatisticId) {
        this.resourceStatisticId = resourceStatisticId == null ? null : resourceStatisticId.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public ResourceStatistic withFileSize(Long fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getStorageType() {
        return storageType;
    }

    public ResourceStatistic withStorageType(String storageType) {
        this.setStorageType(storageType);
        return this;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType == null ? null : storageType.trim();
    }

    public String getOperation() {
        return operation;
    }

    public ResourceStatistic withOperation(String operation) {
        this.setOperation(operation);
        return this;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public String getBucketId() {
        return bucketId;
    }

    public ResourceStatistic withBucketId(String bucketId) {
        this.setBucketId(bucketId);
        return this;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId == null ? null : bucketId.trim();
    }

    public String getResourceId() {
        return resourceId;
    }

    public ResourceStatistic withResourceId(String resourceId) {
        this.setResourceId(resourceId);
        return this;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId == null ? null : resourceId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public ResourceStatistic withProjectId(String projectId) {
        this.setProjectId(projectId);
        return this;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public ResourceStatistic withRecordTime(Date recordTime) {
        this.setRecordTime(recordTime);
        return this;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public Date getAddRecordTime() {
        return addRecordTime;
    }

    public ResourceStatistic withAddRecordTime(Date addRecordTime) {
        this.setAddRecordTime(addRecordTime);
        return this;
    }

    public void setAddRecordTime(Date addRecordTime) {
        this.addRecordTime = addRecordTime;
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
        ResourceStatistic other = (ResourceStatistic) that;
        return (this.getResourceStatisticId() == null ? other.getResourceStatisticId() == null : this.getResourceStatisticId().equals(other.getResourceStatisticId()))
                && (this.getFileSize() == null ? other.getFileSize() == null : this.getFileSize().equals(other.getFileSize()))
                && (this.getStorageType() == null ? other.getStorageType() == null : this.getStorageType().equals(other.getStorageType()))
                && (this.getOperation() == null ? other.getOperation() == null : this.getOperation().equals(other.getOperation()))
                && (this.getBucketId() == null ? other.getBucketId() == null : this.getBucketId().equals(other.getBucketId()))
                && (this.getResourceId() == null ? other.getResourceId() == null : this.getResourceId().equals(other.getResourceId()))
                && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
                && (this.getRecordTime() == null ? other.getRecordTime() == null : this.getRecordTime().equals(other.getRecordTime()))
                && (this.getAddRecordTime() == null ? other.getAddRecordTime() == null : this.getAddRecordTime().equals(other.getAddRecordTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getResourceStatisticId() == null) ? 0 : getResourceStatisticId().hashCode());
        result = prime * result + ((getFileSize() == null) ? 0 : getFileSize().hashCode());
        result = prime * result + ((getStorageType() == null) ? 0 : getStorageType().hashCode());
        result = prime * result + ((getOperation() == null) ? 0 : getOperation().hashCode());
        result = prime * result + ((getBucketId() == null) ? 0 : getBucketId().hashCode());
        result = prime * result + ((getResourceId() == null) ? 0 : getResourceId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getRecordTime() == null) ? 0 : getRecordTime().hashCode());
        result = prime * result + ((getAddRecordTime() == null) ? 0 : getAddRecordTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", resourceStatisticId=").append(resourceStatisticId);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", storageType=").append(storageType);
        sb.append(", operation=").append(operation);
        sb.append(", bucketId=").append(bucketId);
        sb.append(", resourceId=").append(resourceId);
        sb.append(", projectId=").append(projectId);
        sb.append(", recordTime=").append(recordTime);
        sb.append(", addRecordTime=").append(addRecordTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}