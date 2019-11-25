package com.wondersgroup.cloud.paas.storage.model;

import java.io.Serializable;

public class TimestampAcl implements Serializable {
    private String timestampAclId;

    private String mainKey;

    private String spareKey;

    private String bucketId;

    private static final long serialVersionUID = 1L;

    public TimestampAcl(String timestampAclId, String mainKey, String spareKey, String bucketId) {
        this.timestampAclId = timestampAclId;
        this.mainKey = mainKey;
        this.spareKey = spareKey;
        this.bucketId = bucketId;
    }

    public TimestampAcl() {
        super();
    }

    public String getTimestampAclId() {
        return timestampAclId;
    }

    public TimestampAcl withTimestampAclId(String timestampAclId) {
        this.setTimestampAclId(timestampAclId);
        return this;
    }

    public void setTimestampAclId(String timestampAclId) {
        this.timestampAclId = timestampAclId == null ? null : timestampAclId.trim();
    }

    public String getMainKey() {
        return mainKey;
    }

    public TimestampAcl withMainKey(String mainKey) {
        this.setMainKey(mainKey);
        return this;
    }

    public void setMainKey(String mainKey) {
        this.mainKey = mainKey == null ? null : mainKey.trim();
    }

    public String getSpareKey() {
        return spareKey;
    }

    public TimestampAcl withSpareKey(String spareKey) {
        this.setSpareKey(spareKey);
        return this;
    }

    public void setSpareKey(String spareKey) {
        this.spareKey = spareKey == null ? null : spareKey.trim();
    }

    public String getBucketId() {
        return bucketId;
    }

    public TimestampAcl withBucketId(String bucketId) {
        this.setBucketId(bucketId);
        return this;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId == null ? null : bucketId.trim();
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
        TimestampAcl other = (TimestampAcl) that;
        return (this.getTimestampAclId() == null ? other.getTimestampAclId() == null : this.getTimestampAclId().equals(other.getTimestampAclId()))
            && (this.getMainKey() == null ? other.getMainKey() == null : this.getMainKey().equals(other.getMainKey()))
            && (this.getSpareKey() == null ? other.getSpareKey() == null : this.getSpareKey().equals(other.getSpareKey()))
                && (this.getBucketId() == null ? other.getBucketId() == null : this.getBucketId().equals(other.getBucketId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTimestampAclId() == null) ? 0 : getTimestampAclId().hashCode());
        result = prime * result + ((getMainKey() == null) ? 0 : getMainKey().hashCode());
        result = prime * result + ((getSpareKey() == null) ? 0 : getSpareKey().hashCode());
        result = prime * result + ((getBucketId() == null) ? 0 : getBucketId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", timestampAclId=").append(timestampAclId);
        sb.append(", mainKey=").append(mainKey);
        sb.append(", spareKey=").append(spareKey);
        sb.append(", bucketId=").append(bucketId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}