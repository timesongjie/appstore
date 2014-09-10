package com.bbkmobile.iqoo.console.dao.appinfo;

public class SystemPackage {
    private static final String TAG = "SystemPackage";
    private long id;
    private char tag;// 0 系统应用 1去重应用
    private String appCnName;
    private String systemPackage;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSystemPackage() {
        return this.systemPackage;
    }

    public void setSystemPackage(String systemPackage) {
        this.systemPackage = systemPackage;
    }

    public char getTag() {
        return tag;
    }

    public void setTag(char tag) {
        this.tag = tag;
    }

    public String getAppCnName() {
        return appCnName;
    }

    public void setAppCnName(String appCnName) {
        this.appCnName = appCnName;
    }

}
