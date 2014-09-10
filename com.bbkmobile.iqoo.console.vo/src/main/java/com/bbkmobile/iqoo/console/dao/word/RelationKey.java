package com.bbkmobile.iqoo.console.dao.word;

import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;

public class RelationKey {

    private Long id;
    private AppInfo appInfo;
    private String relation;

    public RelationKey() {
    }

    public RelationKey(AppInfo appInfo) {
        super();
        this.appInfo = appInfo;
    }

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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

}
