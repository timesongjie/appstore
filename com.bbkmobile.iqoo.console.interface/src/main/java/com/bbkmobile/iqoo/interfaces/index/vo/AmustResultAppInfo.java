package com.bbkmobile.iqoo.interfaces.index.vo;

import java.util.List;

public class AmustResultAppInfo {

    private String title;
    private List<AmustAppsResultAppInfo> apps;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AmustAppsResultAppInfo> getApps() {
        return apps;
    }

    public void setApps(List<AmustAppsResultAppInfo> apps) {
        this.apps = apps;
    }

    // public List<NewAppsResultAppInfo> getApps() {
    // return apps;
    // }
    //
    // public void setApps(List<NewAppsResultAppInfo> apps) {
    // this.apps = apps;
    // }
}
