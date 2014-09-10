package com.bbkmobile.iqoo.console.dao.contentmanage;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;

public class AppHubInfo {

    private Integer id;
    private String hub_name;
    private String en_name;
    private Date add_time;
    private Date modify_time;

    private Set<AppInfo> appInfos = new HashSet<AppInfo>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHub_name() {
        return hub_name;
    }

    public void setHub_name(String hubName) {
        hub_name = hubName;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String enName) {
        en_name = enName;
    }

    public Date getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Date addTime) {
        add_time = addTime;
    }

    public Date getModify_time() {
        return modify_time;
    }

    public void setModify_time(Date modifyTime) {
        modify_time = modifyTime;
    }

    public Set<AppInfo> getAppInfos() {
        return appInfos;
    }

    public void setAppInfos(Set<AppInfo> appInfos) {
        this.appInfos = appInfos;
    }

}
