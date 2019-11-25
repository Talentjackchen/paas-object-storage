package com.wondersgroup.cloud.paas.storage.model;

import java.io.Serializable;

public class RefererAcl implements Serializable {
    private String refererAclId;

    private String domain;

    private String type;

    private String allowedEmpty;

    private String bucketId;

    private static final long serialVersionUID = 1L;

    public RefererAcl(String refererAclId, String domain, String type, String allowedEmpty, String bucketId) {
        this.refererAclId = refererAclId;
        this.domain = domain;
        this.type = type;
        this.allowedEmpty = allowedEmpty;
        this.bucketId = bucketId;
    }

    public RefererAcl() {
        super();
    }

    public String getRefererAclId() {
        return refererAclId;
    }

    public RefererAcl withRefererAclId(String refererAclId) {
        this.setRefererAclId(refererAclId);
        return this;
    }

    public void setRefererAclId(String refererAclId) {
        this.refererAclId = refererAclId == null ? null : refererAclId.trim();
    }

    public String getDomain() {
        return domain;
    }

    public RefererAcl withDomain(String domain) {
        this.setDomain(domain);
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

    public String getType() {
        return type;
    }

    public RefererAcl withType(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getAllowedEmpty() {
        return allowedEmpty;
    }

    public RefererAcl withAllowedEmpty(String allowedEmpty) {
        this.setAllowedEmpty(allowedEmpty);
        return this;
    }

    public void setAllowedEmpty(String allowedEmpty) {
        this.allowedEmpty = allowedEmpty == null ? null : allowedEmpty.trim();
    }

    public String getBucketId() {
        return bucketId;
    }

    public RefererAcl withBucketId(String bucketId) {
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
        RefererAcl other = (RefererAcl) that;
        return (this.getRefererAclId() == null ? other.getRefererAclId() == null : this.getRefererAclId().equals(other.getRefererAclId()))
            && (this.getDomain() == null ? other.getDomain() == null : this.getDomain().equals(other.getDomain()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
                && (this.getAllowedEmpty() == null ? other.getAllowedEmpty() == null : this.getAllowedEmpty().equals(other.getAllowedEmpty()))
                && (this.getBucketId() == null ? other.getBucketId() == null : this.getBucketId().equals(other.getBucketId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRefererAclId() == null) ? 0 : getRefererAclId().hashCode());
        result = prime * result + ((getDomain() == null) ? 0 : getDomain().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getAllowedEmpty() == null) ? 0 : getAllowedEmpty().hashCode());
        result = prime * result + ((getBucketId() == null) ? 0 : getBucketId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", refererAclId=").append(refererAclId);
        sb.append(", domain=").append(domain);
        sb.append(", type=").append(type);
        sb.append(", allowedEmpty=").append(allowedEmpty);
        sb.append(", bucketId=").append(bucketId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}