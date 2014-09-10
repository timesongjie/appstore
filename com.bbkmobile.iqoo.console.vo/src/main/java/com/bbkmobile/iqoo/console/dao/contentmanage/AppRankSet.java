package com.bbkmobile.iqoo.console.dao.contentmanage;

import java.util.Date;

import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;

public class AppRankSet {

    private Long id;
    private AppInfo appInfo;
    private Integer rank_order;
    private Integer app_type; // 1:免费应用 2：收费应用 3：免费游戏 4：收费游戏
    private Character rank_status;
    private String admin_name;
    private Date set_date;

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

    public Integer getRank_order() {
        return rank_order;
    }

    public void setRank_order(Integer rankOrder) {
        rank_order = rankOrder;
    }

    public Integer getApp_type() {
        return app_type;
    }

    public void setApp_type(Integer appType) {
        app_type = appType;
    }

    public Character getRank_status() {
        return rank_status;
    }

    public void setRank_status(Character rankStatus) {
        rank_status = rankStatus;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String adminName) {
        admin_name = adminName;
    }

    public Date getSet_date() {
        return set_date;
    }

    public void setSet_date(Date setDate) {
        set_date = setDate;
    }

}
