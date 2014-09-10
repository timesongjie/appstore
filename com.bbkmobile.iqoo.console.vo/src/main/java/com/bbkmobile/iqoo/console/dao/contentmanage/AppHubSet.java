package com.bbkmobile.iqoo.console.dao.contentmanage;

import java.util.Date;

import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;

public class AppHubSet {

    private Long id;
    private Integer hub_id;
    private AppInfo appInfo;
    private int show_order;
    private Short series_id; // 手机系列ID
    private char recommend = '0'; // 是否主推此汇的此应用，默认0，不主推
    private short level = 1; // 主推程度，可以根据相对值设置主推程度
    private short type;
    private String admin_name;
    private Date set_date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHub_id() {
        return hub_id;
    }

    public void setHub_id(Integer hubId) {
        hub_id = hubId;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public int getShow_order() {
        return show_order;
    }

    public void setShow_order(int showOrder) {
        show_order = showOrder;
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

    public Short getSeries_id() {
        return series_id;
    }

    public void setSeries_id(Short seriesId) {
        series_id = seriesId;
    }

    public char getRecommend() {
        return recommend;
    }

    public void setRecommend(char recommend) {
        this.recommend = recommend;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

}
