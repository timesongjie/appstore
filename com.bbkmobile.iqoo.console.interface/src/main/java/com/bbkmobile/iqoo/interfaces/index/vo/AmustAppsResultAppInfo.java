package com.bbkmobile.iqoo.interfaces.index.vo;

import java.util.Date;

import com.bbkmobile.iqoo.common.vo.BaseResultAppInfo;

public class AmustAppsResultAppInfo extends BaseResultAppInfo {

    private String app_remark;// 小编推荐
    private Date upload_time;
    private String title;
    private Short tag;
    private String developer;
    private Integer download_count;

    public String getApp_remark() {
        return app_remark;
    }

    public void setApp_remark(String app_remark) {
        this.app_remark = app_remark;
    }

    public Date getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(Date upload_time) {
        this.upload_time = upload_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Short getTag() {
        return tag;
    }

    public void setTag(Short tag) {
        this.tag = tag;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public Integer getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Integer download_count) {
        this.download_count = download_count;
    }

}
