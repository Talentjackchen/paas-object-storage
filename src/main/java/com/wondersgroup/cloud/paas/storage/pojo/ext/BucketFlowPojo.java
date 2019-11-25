package com.wondersgroup.cloud.paas.storage.pojo.ext;

import com.wondersgroup.cloud.paas.storage.pojo.ResponsePojo;

import java.util.Map;

/**
 * @author chenlong
 * cdn域名流量
 */
public class BucketFlowPojo extends ResponsePojo {
    private String time;

    private Map<String, Long> values;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Map<String, Long> getValues() {
        return values;
    }

    public void setValues(Map<String, Long> values) {
        this.values = values;
    }
}
