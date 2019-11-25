package com.wondersgroup.cloud.paas.storage.pojo;


public class CertPojo {
    private String certId;
    private String name;
    private String uid;
    private String common_name;
    private String[] dnsnames;
    private Long create_time;
    private Long not_before;
    private Long not_after;
    private String orderid;
    private String product_short_name;
    private String product_type;
    private String encrypt;
    private String encryptParameter;
    private Boolean enable;
    private String ca;
    private String pri;

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCommon_name() {
        return common_name;
    }

    public void setCommon_name(String common_name) {
        this.common_name = common_name;
    }

    public String[] getDnsnames() {
        return dnsnames;
    }

    public void setDnsnames(String[] dnsnames) {
        this.dnsnames = dnsnames;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public Long getNot_before() {
        return not_before;
    }

    public void setNot_before(Long not_before) {
        this.not_before = not_before;
    }

    public Long getNot_after() {
        return not_after;
    }

    public void setNot_after(Long not_after) {
        this.not_after = not_after;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getProduct_short_name() {
        return product_short_name;
    }

    public void setProduct_short_name(String product_short_name) {
        this.product_short_name = product_short_name;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getEncryptParameter() {
        return encryptParameter;
    }

    public void setEncryptParameter(String encryptParameter) {
        this.encryptParameter = encryptParameter;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getPri() {
        return pri;
    }

    public void setPri(String pri) {
        this.pri = pri;
    }
}
