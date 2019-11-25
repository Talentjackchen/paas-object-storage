package com.wondersgroup.cloud.paas.storage.pojo.ext;

import com.wondersgroup.cloud.paas.storage.pojo.CertPojo;
import com.wondersgroup.cloud.paas.storage.pojo.ResponsePojo;

public class CertResponse extends ResponsePojo {
    private String certID;
    private CertPojo cert;

    public String getCertID() {
        return certID;
    }

    public void setCertID(String certID) {
        this.certID = certID;
    }

    public CertPojo getCert() {
        return cert;
    }

    public void setCert(CertPojo certPojo) {
        this.cert = certPojo;
    }
}
