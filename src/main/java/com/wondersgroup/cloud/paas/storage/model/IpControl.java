package com.wondersgroup.cloud.paas.storage.model;

import java.io.Serializable;

public class IpControl implements Serializable {
    private String ipControlId;

    private String address;

    private String type;

    private String cdnId;

    private static final long serialVersionUID = 1L;

    public IpControl(String ipControlId, String address, String type, String cdnId) {
        this.ipControlId = ipControlId;
        this.address = address;
        this.type = type;
        this.cdnId = cdnId;
    }

    public IpControl() {
        super();
    }

    public String getIpControlId() {
        return ipControlId;
    }

    public IpControl withIpControlId(String ipControlId) {
        this.setIpControlId(ipControlId);
        return this;
    }

    public void setIpControlId(String ipControlId) {
        this.ipControlId = ipControlId == null ? null : ipControlId.trim();
    }

    public String getAddress() {
        return address;
    }

    public IpControl withAddress(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getType() {
        return type;
    }

    public IpControl withType(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getCdnId() {
        return cdnId;
    }

    public IpControl withCdnId(String cdnId) {
        this.setCdnId(cdnId);
        return this;
    }

    public void setCdnId(String cdnId) {
        this.cdnId = cdnId == null ? null : cdnId.trim();
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
        IpControl other = (IpControl) that;
        return (this.getIpControlId() == null ? other.getIpControlId() == null : this.getIpControlId().equals(other.getIpControlId()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getCdnId() == null ? other.getCdnId() == null : this.getCdnId().equals(other.getCdnId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIpControlId() == null) ? 0 : getIpControlId().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getCdnId() == null) ? 0 : getCdnId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ipControlId=").append(ipControlId);
        sb.append(", address=").append(address);
        sb.append(", type=").append(type);
        sb.append(", cdnId=").append(cdnId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}