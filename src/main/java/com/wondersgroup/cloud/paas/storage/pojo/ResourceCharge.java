package com.wondersgroup.cloud.paas.storage.pojo;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author chenlong
 */
public class ResourceCharge {

    private String accountId;

    private String projectId;

    private Map<String, BigDecimal> fetchMeterMap;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Map<String, BigDecimal> getFetchMeterMap() {
        return fetchMeterMap;
    }

    public void setFetchMeterMap(Map<String, BigDecimal> fetchMeterMap) {
        this.fetchMeterMap = fetchMeterMap;
    }
}
