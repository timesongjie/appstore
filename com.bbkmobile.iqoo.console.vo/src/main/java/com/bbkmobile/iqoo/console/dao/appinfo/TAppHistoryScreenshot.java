package com.bbkmobile.iqoo.console.dao.appinfo;

import java.util.Date;

// Generated 2012-1-9 9:11:11 by Hibernate Tools 3.4.0.CR1

/**
 * TAppScreenshot generated by hbm2java
 */
public class TAppHistoryScreenshot implements java.io.Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3686965506784243633L;
    private Long id;
    private Long app_id;
    private String screenshot;
    private Integer picorder;
    private Date add_date;
    private String appVersionCode;
    private String appPackage;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApp_id() {
        return app_id;
    }

    public void setApp_id(Long app_id) {
        this.app_id = app_id;
    }

    public String getScreenshot() {
        return this.screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    public Date getAdd_date() {
        return this.add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public void setPicorder(Integer picorder) {
        this.picorder = picorder;
    }

    public Integer getPicorder() {
        return picorder;
    }

    public String getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(String appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }
}
