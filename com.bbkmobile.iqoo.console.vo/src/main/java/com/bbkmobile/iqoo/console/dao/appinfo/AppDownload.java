package com.bbkmobile.iqoo.console.dao.appinfo;

import java.util.Date;

public class AppDownload {

    private Long id;
    private AppInfo appInfo;
    private Long user_id;
    private Character download_type;
    private Date download_date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long userId) {
        user_id = userId;
    }

    public Character getDownload_type() {
        return download_type;
    }

    public void setDownload_type(Character downloadType) {
        download_type = downloadType;
    }

    public Date getDownload_date() {
        return download_date;
    }

    public void setDownload_date(Date downloadDate) {
        download_date = downloadDate;
    }

}
