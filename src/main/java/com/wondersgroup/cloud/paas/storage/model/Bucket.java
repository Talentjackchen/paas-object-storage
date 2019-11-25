package com.wondersgroup.cloud.paas.storage.model;

import java.io.Serializable;
import java.util.Date;

public class Bucket implements Serializable {
    private String bucketId;

    private String name;

    private String aliasName;

    private String region;

    private String type;

    private String domain;

    private String status;

    private String domainId;

    private String accountId;

    private String projectId;

    private String validFlag;

    private String remoteFlag;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Bucket(String bucketId, String name, String aliasName, String region, String type, String domain, String status, String domainId, String accountId, String projectId, String validFlag, String remoteFlag, Date createTime, Date updateTime) {
        this.bucketId = bucketId;
        this.name = name;
        this.aliasName = aliasName;
        this.region = region;
        this.type = type;
        this.domain = domain;
        this.status = status;
        this.domainId = domainId;
        this.accountId = accountId;
        this.projectId = projectId;
        this.validFlag = validFlag;
        this.remoteFlag = remoteFlag;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Bucket() {
        super();
    }

    public String getBucketId() {
        return bucketId;
    }

    public Bucket withBucketId(String bucketId) {
        this.setBucketId(bucketId);
        return this;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId == null ? null : bucketId.trim();
    }

    public String getName() {
        return name;
    }

    public Bucket withName(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAliasName() {
        return aliasName;
    }

    public Bucket withAliasName(String aliasName) {
        this.setAliasName(aliasName);
        return this;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName == null ? null : aliasName.trim();
    }

    public String getRegion() {
        return region;
    }

    public Bucket withRegion(String region) {
        this.setRegion(region);
        return this;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public String getType() {
        return type;
    }

    public Bucket withType(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDomain() {
        return domain;
    }

    public Bucket withDomain(String domain) {
        this.setDomain(domain);
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

    public String getStatus() {
        return status;
    }

    public Bucket withStatus(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getDomainId() {
        return domainId;
    }

    public Bucket withDomainId(String domainId) {
        this.setDomainId(domainId);
        return this;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId == null ? null : domainId.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public Bucket withAccountId(String accountId) {
        this.setAccountId(accountId);
        return this;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public Bucket withProjectId(String projectId) {
        this.setProjectId(projectId);
        return this;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getValidFlag() {
        return validFlag;
    }

    public Bucket withValidFlag(String validFlag) {
        this.setValidFlag(validFlag);
        return this;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag == null ? null : validFlag.trim();
    }

    public String getRemoteFlag() {
        return remoteFlag;
    }

    public Bucket withRemoteFlag(String remoteFlag) {
        this.setRemoteFlag(remoteFlag);
        return this;
    }

    public void setRemoteFlag(String remoteFlag) {
        this.remoteFlag = remoteFlag == null ? null : remoteFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Bucket withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Bucket withUpdateTime(Date updateTime) {
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
        Bucket other = (Bucket) that;
        return (this.getBucketId() == null ? other.getBucketId() == null : this.getBucketId().equals(other.getBucketId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getAliasName() == null ? other.getAliasName() == null : this.getAliasName().equals(other.getAliasName()))
            && (this.getRegion() == null ? other.getRegion() == null : this.getRegion().equals(other.getRegion()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getDomain() == null ? other.getDomain() == null : this.getDomain().equals(other.getDomain()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getDomainId() == null ? other.getDomainId() == null : this.getDomainId().equals(other.getDomainId()))
                && (this.getAccountId() == null ? other.getAccountId() == null : this.getAccountId().equals(other.getAccountId()))
                && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
                && (this.getValidFlag() == null ? other.getValidFlag() == null : this.getValidFlag().equals(other.getValidFlag()))
                && (this.getRemoteFlag() == null ? other.getRemoteFlag() == null : this.getRemoteFlag().equals(other.getRemoteFlag()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBucketId() == null) ? 0 : getBucketId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getAliasName() == null) ? 0 : getAliasName().hashCode());
        result = prime * result + ((getRegion() == null) ? 0 : getRegion().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getDomain() == null) ? 0 : getDomain().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDomainId() == null) ? 0 : getDomainId().hashCode());
        result = prime * result + ((getAccountId() == null) ? 0 : getAccountId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getValidFlag() == null) ? 0 : getValidFlag().hashCode());
        result = prime * result + ((getRemoteFlag() == null) ? 0 : getRemoteFlag().hashCode());
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
        sb.append(", bucketId=").append(bucketId);
        sb.append(", name=").append(name);
        sb.append(", aliasName=").append(aliasName);
        sb.append(", region=").append(region);
        sb.append(", type=").append(type);
        sb.append(", domain=").append(domain);
        sb.append(", status=").append(status);
        sb.append(", domainId=").append(domainId);
        sb.append(", accountId=").append(accountId);
        sb.append(", projectId=").append(projectId);
        sb.append(", validFlag=").append(validFlag);
        sb.append(", remoteFlag=").append(remoteFlag);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}