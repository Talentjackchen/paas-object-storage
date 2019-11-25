package com.wondersgroup.cloud.paas.storage.pojo;

/**
 * @author chenlong
 */
public class TimestampAclPojo {
    private String mainKey;

    private String spareKey;

    private String url;

    public String getMainKey() {
        return mainKey;
    }

    public void setMainKey(String mainKey) {
        this.mainKey = mainKey;
    }

    public String getSpareKey() {
        return spareKey;
    }

    public void setSpareKey(String spareKey) {
        this.spareKey = spareKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
