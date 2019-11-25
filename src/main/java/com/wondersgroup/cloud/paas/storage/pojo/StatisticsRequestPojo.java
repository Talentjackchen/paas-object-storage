package com.wondersgroup.cloud.paas.storage.pojo;

import java.util.Map;

/**
 * describe : API请求
 * create_by : zhangyongzhao
 * create_time : 2019/5/22
 */
public class StatisticsRequestPojo extends ResponsePojo {

    //时间
    private String time;
    //数据
    private Map<String, Object> values;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }
}
