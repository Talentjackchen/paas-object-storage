package com.wondersgroup.cloud.paas.storage.model;

import java.io.Serializable;
import java.util.Date;

public class ProjectAuthority implements Serializable {
    private String projectAuthorityId;

    private String projectId;

    private String accessKey;

    private String secretKey;

    private String status;

    private String validFlag;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public ProjectAuthority(String projectAuthorityId, String projectId, String accessKey, String secretKey, String status, String validFlag, Date createTime, Date updateTime) {
        this.projectAuthorityId = projectAuthorityId;
        this.projectId = projectId;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.status = status;
        this.validFlag = validFlag;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public ProjectAuthority() {
        super();
    }

    public String getProjectAuthorityId() {
        return projectAuthorityId;
    }

    public ProjectAuthority withProjectAuthorityId(String projectAuthorityId) {
        this.setProjectAuthorityId(projectAuthorityId);
        return this;
    }

    public void setProjectAuthorityId(String projectAuthorityId) {
        this.projectAuthorityId = projectAuthorityId == null ? null : projectAuthorityId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public ProjectAuthority withProjectId(String projectId) {
        this.setProjectId(projectId);
        return this;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getAccessKey() {
        return accessKey;
    }

    public ProjectAuthority withAccessKey(String accessKey) {
        this.setAccessKey(accessKey);
        return this;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey == null ? null : accessKey.trim();
    }

    public String getSecretKey() {
        return secretKey;
    }

    public ProjectAuthority withSecretKey(String secretKey) {
        this.setSecretKey(secretKey);
        return this;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey == null ? null : secretKey.trim();
    }

    public String getStatus() {
        return status;
    }

    public ProjectAuthority withStatus(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getValidFlag() {
        return validFlag;
    }

    public ProjectAuthority withValidFlag(String validFlag) {
        this.setValidFlag(validFlag);
        return this;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag == null ? null : validFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ProjectAuthority withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ProjectAuthority withUpdateTime(Date updateTime) {
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
        ProjectAuthority other = (ProjectAuthority) that;
        return (this.getProjectAuthorityId() == null ? other.getProjectAuthorityId() == null : this.getProjectAuthorityId().equals(other.getProjectAuthorityId()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getAccessKey() == null ? other.getAccessKey() == null : this.getAccessKey().equals(other.getAccessKey()))
            && (this.getSecretKey() == null ? other.getSecretKey() == null : this.getSecretKey().equals(other.getSecretKey()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getValidFlag() == null ? other.getValidFlag() == null : this.getValidFlag().equals(other.getValidFlag()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getProjectAuthorityId() == null) ? 0 : getProjectAuthorityId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getAccessKey() == null) ? 0 : getAccessKey().hashCode());
        result = prime * result + ((getSecretKey() == null) ? 0 : getSecretKey().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
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
        sb.append(", projectAuthorityId=").append(projectAuthorityId);
        sb.append(", projectId=").append(projectId);
        sb.append(", accessKey=").append(accessKey);
        sb.append(", secretKey=").append(secretKey);
        sb.append(", status=").append(status);
        sb.append(", validFlag=").append(validFlag);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}