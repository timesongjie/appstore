package com.bbkmobile.iqoo.console.top.dao;

import java.util.Date;

import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;

public class TopBase {

    private String table_name;
    private Long id;
    private AppInfo appInfo = new AppInfo();
    private Integer show_order;
    private Date add_date;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppInfo getAppInfo() {
        return this.appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public Integer getShow_order() {
        return this.show_order;
    }

    public void setShow_order(Integer show_order) {
        this.show_order = show_order;
    }

    public Date getAdd_date() {
        return this.add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }
}
