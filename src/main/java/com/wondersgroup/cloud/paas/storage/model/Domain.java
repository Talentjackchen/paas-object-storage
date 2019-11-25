package com.wondersgroup.cloud.paas.storage.model;

import java.io.Serializable;
import java.util.Date;

public class Domain implements Serializable {
    private String domainId;

    private String name;

    private String type;

    private String platform;

    private String geoCover;

    private String protocol;

    private String sslCertificateId;

    private String belongBucketId;

    private String validFlag;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Domain(String domainId, String name, String type, String platform, String geoCover, String protocol, String sslCertificateId, String belongBucketId, String validFlag, Date createTime, Date updateTime) {
        this.domainId = domainId;
        this.name = name;
        this.type = type;
        this.platform = platform;
        this.geoCover = geoCover;
        this.protocol = protocol;
        this.sslCertificateId = sslCertificateId;
        this.belongBucketId = belongBucketId;
        this.validFlag = validFlag;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Domain() {
        super();
    }

    public String getDomainId() {
        return domainId;
    }

    public Domain withDomainId(String domainId) {
        this.setDomainId(domainId);
        return this;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId == null ? null : domainId.trim();
    }

    public String getName() {
        return name;
    }

    public Domain withName(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public Domain withType(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getPlatform() {
        return platform;
    }

    public Domain withPlatform(String platform) {
        this.setPlatform(platform);
        return this;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public String getGeoCover() {
        return geoCover;
    }

    public Domain withGeoCover(String geoCover) {
        this.setGeoCover(geoCover);
        return this;
    }

    public void setGeoCover(String geoCover) {
        this.geoCover = geoCover == null ? null : geoCover.trim();
    }

    public String getProtocol() {
        return protocol;
    }

    public Domain withProtocol(String protocol) {
        this.setProtocol(protocol);
        return this;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol == null ? null : protocol.trim();
    }

    public String getSslCertificateId() {
        return sslCertificateId;
    }

    public Domain withSslCertificateId(String sslCertificateId) {
        this.setSslCertificateId(sslCertificateId);
        return this;
    }

    public void setSslCertificateId(String sslCertificateId) {
        this.sslCertificateId = sslCertificateId == null ? null : sslCertificateId.trim();
    }

    public String getBelongBucketId() {
        return belongBucketId;
    }

    public Domain withBelongBucketId(String belongBucketId) {
        this.setBelongBucketId(belongBucketId);
        return this;
    }

    public void setBelongBucketId(String belongBucketId) {
        this.belongBucketId = belongBucketId == null ? null : belongBucketId.trim();
    }

    public String getValidFlag() {
        return validFlag;
    }

    public Domain withValidFlag(String validFlag) {
        this.setValidFlag(validFlag);
        return this;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag == null ? null : validFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Domain withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Domain withUpdateTime(Date updateTime) {
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
        Domain other = (Domain) that;
        return (this.getDomainId() == null ? other.getDomainId() == null : this.getDomainId().equals(other.getDomainId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getPlatform() == null ? other.getPlatform() == null : this.getPlatform().equals(other.getPlatform()))
            && (this.getGeoCover() == null ? other.getGeoCover() == null : this.getGeoCover().equals(other.getGeoCover()))
            && (this.getProtocol() == null ? other.getProtocol() == null : this.getProtocol().equals(other.getProtocol()))
            && (this.getSslCertificateId() == null ? other.getSslCertificateId() == null : this.getSslCertificateId().equals(other.getSslCertificateId()))
                && (this.getBelongBucketId() == null ? other.getBelongBucketId() == null : this.getBelongBucketId().equals(other.getBelongBucketId()))
                && (this.getValidFlag() == null ? other.getValidFlag() == null : this.getValidFlag().equals(other.getValidFlag()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDomainId() == null) ? 0 : getDomainId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getPlatform() == null) ? 0 : getPlatform().hashCode());
        result = prime * result + ((getGeoCover() == null) ? 0 : getGeoCover().hashCode());
        result = prime * result + ((getProtocol() == null) ? 0 : getProtocol().hashCode());
        result = prime * result + ((getSslCertificateId() == null) ? 0 : getSslCertificateId().hashCode());
        result = prime * result + ((getBelongBucketId() == null) ? 0 : getBelongBucketId().hashCode());
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
        sb.append(", domainId=").append(domainId);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", platform=").append(platform);
        sb.append(", geoCover=").append(geoCover);
        sb.append(", protocol=").append(protocol);
        sb.append(", sslCertificateId=").append(sslCertificateId);
        sb.append(", belongBucketId=").append(belongBucketId);
        sb.append(", validFlag=").append(validFlag);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}