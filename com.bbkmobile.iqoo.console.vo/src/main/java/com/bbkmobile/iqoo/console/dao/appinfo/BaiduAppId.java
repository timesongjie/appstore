package com.bbkmobile.iqoo.console.dao.appinfo;

public class BaiduAppId {
    private static final String TAG = "BaiduAppInfo";

    Long id;
    String baidu_id;
    String url;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBaidu_id() {
        return this.baidu_id;
    }

    public void setBaidu_id(String baidu_id) {
        this.baidu_id = baidu_id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
