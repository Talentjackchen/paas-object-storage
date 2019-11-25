package com.wondersgroup.cloud.paas.storage.model;

import java.io.Serializable;

public class IpAcl implements Serializable {
    private String ipAclId;

    private String ipAddress;

    private String type;

    private String bucketId;

    private static final long serialVersionUID = 1L;

    public IpAcl(String ipAclId, String ipAddress, String type, String bucketId) {
        this.ipAclId = ipAclId;
        this.ipAddress = ipAddress;
        this.type = type;
        this.bucketId = bucketId;
    }

    public IpAcl() {
        super();
    }

    public String getIpAclId() {
        return ipAclId;
    }

    public IpAcl withIpAclId(String ipAclId) {
        this.setIpAclId(ipAclId);
        return this;
    }

    public void setIpAclId(String ipAclId) {
        this.ipAclId = ipAclId == null ? null : ipAclId.trim();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public IpAcl withIpAddress(String ipAddress) {
        this.setIpAddress(ipAddress);
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public String getType() {
        return type;
    }

    public IpAcl withType(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getBucketId() {
        return bucketId;
    }

    public IpAcl withBucketId(String bucketId) {
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
        IpAcl other = (IpAcl) that;
        return (this.getIpAclId() == null ? other.getIpAclId() == null : this.getIpAclId().equals(other.getIpAclId()))
            && (this.getIpAddress() == null ? other.getIpAddress() == null : this.getIpAddress().equals(other.getIpAddress()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
                && (this.getBucketId() == null ? other.getBucketId() == null : this.getBucketId().equals(other.getBucketId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIpAclId() == null) ? 0 : getIpAclId().hashCode());
        result = prime * result + ((getIpAddress() == null) ? 0 : getIpAddress().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getBucketId() == null) ? 0 : getBucketId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ipAclId=").append(ipAclId);
        sb.append(", ipAddress=").append(ipAddress);
        sb.append(", type=").append(type);
        sb.append(", bucketId=").append(bucketId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}