package com.bbkmobile.iqoo.console.dao.score;

import java.util.Date;

import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;

public class VCoinPresentInfo {
    private Long id;
    private int largess;
    private Date add_date;
    private Date modify_date;
    private AppInfo appInfo = new AppInfo();

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLargess() {
        return this.largess;
    }

    public void setLargess(int largess) {
        this.largess = largess;
    }

    public Date getAdd_date() {
        return this.add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public Date getModify_date() {
        return this.modify_date;
    }

    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }

    public AppInfo getAppInfo() {
        return this.appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

}
