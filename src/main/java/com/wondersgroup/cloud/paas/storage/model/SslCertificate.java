package com.wondersgroup.cloud.paas.storage.model;

import java.io.Serializable;
import java.util.Date;

public class SslCertificate implements Serializable {
    private String sslCertificateId;

    private String remarksName;

    private String genericName;

    private Date awardTime;

    private Date expireTime;

    private String certificateId;

    private String validFlag;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public SslCertificate(String sslCertificateId, String remarksName, String genericName, Date awardTime, Date expireTime, String certificateId, String validFlag, Date createTime, Date updateTime) {
        this.sslCertificateId = sslCertificateId;
        this.remarksName = remarksName;
        this.genericName = genericName;
        this.awardTime = awardTime;
        this.expireTime = expireTime;
        this.certificateId = certificateId;
        this.validFlag = validFlag;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public SslCertificate() {
        super();
    }

    public String getSslCertificateId() {
        return sslCertificateId;
    }

    public SslCertificate withSslCertificateId(String sslCertificateId) {
        this.setSslCertificateId(sslCertificateId);
        return this;
    }

    public void setSslCertificateId(String sslCertificateId) {
        this.sslCertificateId = sslCertificateId == null ? null : sslCertificateId.trim();
    }

    public String getRemarksName() {
        return remarksName;
    }

    public SslCertificate withRemarksName(String remarksName) {
        this.setRemarksName(remarksName);
        return this;
    }

    public void setRemarksName(String remarksName) {
        this.remarksName = remarksName == null ? null : remarksName.trim();
    }

    public String getGenericName() {
        return genericName;
    }

    public SslCertificate withGenericName(String genericName) {
        this.setGenericName(genericName);
        return this;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName == null ? null : genericName.trim();
    }

    public Date getAwardTime() {
        return awardTime;
    }

    public SslCertificate withAwardTime(Date awardTime) {
        this.setAwardTime(awardTime);
        return this;
    }

    public void setAwardTime(Date awardTime) {
        this.awardTime = awardTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public SslCertificate withExpireTime(Date expireTime) {
        this.setExpireTime(expireTime);
        return this;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public SslCertificate withCertificateId(String certificateId) {
        this.setCertificateId(certificateId);
        return this;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId == null ? null : certificateId.trim();
    }

    public String getValidFlag() {
        return validFlag;
    }

    public SslCertificate withValidFlag(String validFlag) {
        this.setValidFlag(validFlag);
        return this;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag == null ? null : validFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SslCertificate withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public SslCertificate withUpdateTime(Date updateTime) {
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
        SslCertificate other = (SslCertificate) that;
        return (this.getSslCertificateId() == null ? other.getSslCertificateId() == null : this.getSslCertificateId().equals(other.getSslCertificateId()))
            && (this.getRemarksName() == null ? other.getRemarksName() == null : this.getRemarksName().equals(other.getRemarksName()))
            && (this.getGenericName() == null ? other.getGenericName() == null : this.getGenericName().equals(other.getGenericName()))
            && (this.getAwardTime() == null ? other.getAwardTime() == null : this.getAwardTime().equals(other.getAwardTime()))
            && (this.getExpireTime() == null ? other.getExpireTime() == null : this.getExpireTime().equals(other.getExpireTime()))
                && (this.getCertificateId() == null ? other.getCertificateId() == null : this.getCertificateId().equals(other.getCertificateId()))
                && (this.getValidFlag() == null ? other.getValidFlag() == null : this.getValidFlag().equals(other.getValidFlag()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSslCertificateId() == null) ? 0 : getSslCertificateId().hashCode());
        result = prime * result + ((getRemarksName() == null) ? 0 : getRemarksName().hashCode());
        result = prime * result + ((getGenericName() == null) ? 0 : getGenericName().hashCode());
        result = prime * result + ((getAwardTime() == null) ? 0 : getAwardTime().hashCode());
        result = prime * result + ((getExpireTime() == null) ? 0 : getExpireTime().hashCode());
        result = prime * result + ((getCertificateId() == null) ? 0 : getCertificateId().hashCode());
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
        sb.append(", sslCertificateId=").append(sslCertificateId);
        sb.append(", remarksName=").append(remarksName);
        sb.append(", genericName=").append(genericName);
        sb.append(", awardTime=").append(awardTime);
        sb.append(", expireTime=").append(expireTime);
        sb.append(", certificateId=").append(certificateId);
        sb.append(", validFlag=").append(validFlag);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}