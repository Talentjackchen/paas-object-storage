package com.wondersgroup.cloud.paas.storage.model;

import java.io.Serializable;
import java.util.Date;

public class RequestStatistic implements Serializable {
    private String requestStatisticId;

    private Integer amount;

    private String type;

    private String storageType;

    private String bucketId;

    private Date recordTime;

    private static final long serialVersionUID = 1L;

    public RequestStatistic(String requestStatisticId, Integer amount, String type, String storageType, String bucketId, Date recordTime) {
        this.requestStatisticId = requestStatisticId;
        this.amount = amount;
        this.type = type;
        this.storageType = storageType;
        this.bucketId = bucketId;
        this.recordTime = recordTime;
    }

    public RequestStatistic() {
        super();
    }

    public String getRequestStatisticId() {
        return requestStatisticId;
    }

    public RequestStatistic withRequestStatisticId(String requestStatisticId) {
        this.setRequestStatisticId(requestStatisticId);
        return this;
    }

    public void setRequestStatisticId(String requestStatisticId) {
        this.requestStatisticId = requestStatisticId == null ? null : requestStatisticId.trim();
    }

    public Integer getAmount() {
        return amount;
    }

    public RequestStatistic withAmount(Integer amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public RequestStatistic withType(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getStorageType() {
        return storageType;
    }

    public RequestStatistic withStorageType(String storageType) {
        this.setStorageType(storageType);
        return this;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType == null ? null : storageType.trim();
    }

    public String getBucketId() {
        return bucketId;
    }

    public RequestStatistic withBucketId(String bucketId) {
        this.setBucketId(bucketId);
        return this;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId == null ? null : bucketId.trim();
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public RequestStatistic withRecordTime(Date recordTime) {
        this.setRecordTime(recordTime);
        return this;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
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
        RequestStatistic other = (RequestStatistic) that;
        return (this.getRequestStatisticId() == null ? other.getRequestStatisticId() == null : this.getRequestStatisticId().equals(other.getRequestStatisticId()))
                && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
                && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
                && (this.getStorageType() == null ? other.getStorageType() == null : this.getStorageType().equals(other.getStorageType()))
                && (this.getBucketId() == null ? other.getBucketId() == null : this.getBucketId().equals(other.getBucketId()))
                && (this.getRecordTime() == null ? other.getRecordTime() == null : this.getRecordTime().equals(other.getRecordTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRequestStatisticId() == null) ? 0 : getRequestStatisticId().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getStorageType() == null) ? 0 : getStorageType().hashCode());
        result = prime * result + ((getBucketId() == null) ? 0 : getBucketId().hashCode());
        result = prime * result + ((getRecordTime() == null) ? 0 : getRecordTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", requestStatisticId=").append(requestStatisticId);
        sb.append(", amount=").append(amount);
        sb.append(", type=").append(type);
        sb.append(", storageType=").append(storageType);
        sb.append(", bucketId=").append(bucketId);
        sb.append(", recordTime=").append(recordTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}