package com.wondersgroup.cloud.paas.storage.pojo;

import java.util.List;

/**
 * describe : 存储量
 * create_by : zhangyongzhao
 * create_time : 2019/5/22
 */
public class StatisticsStoragePojo extends ResponsePojo {

    //时间
    private List<String> times;
    //数据
    private List<String> datas;

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }
}
