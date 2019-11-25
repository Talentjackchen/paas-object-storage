package com.wondersgroup.cloud.paas.storage.pojo;

import com.wondersgroup.cloud.paas.storage.model.Domain;

/**
/**
 * describe : 域名信息
 * create_by : zhangyongzhao
 * create_time : 2019/5/5
 */
public class DomainInfo extends ResponsePojo {

    //域名最近一次的操作状态
    private String operatingState;

    //域名对象
    private Domain domain;

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public String getOperatingState() {
        return operatingState;
    }

    public void setOperatingState(String operatingState) {
        this.operatingState = operatingState;
    }
}
