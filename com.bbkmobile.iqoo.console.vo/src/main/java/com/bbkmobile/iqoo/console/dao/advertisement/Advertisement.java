package com.bbkmobile.iqoo.console.dao.advertisement;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;

public class Advertisement implements java.io.Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1535100742318988405L;
    private Long id;
    private String ad_name;
    private Character ad_type;
    private String ad_icon;
    private String ad_link;
    private Date add_date;
    private Date modify_date;
    private Integer show_order;

    private String tag;

    private Set<ModelAdvertisement> modelAdvertisement = new HashSet<ModelAdvertisement>();
    private Set<AppInfo> appInfos = new HashSet<AppInfo>();

    private AppInfo appInfo;

    private Integer app_count; // add by haiyan
    private Long app_id;

    public Long getApp_id() {
        return app_id;
    }

    public void setApp_id(Long app_id) {
        this.app_id = app_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAd_name() {
        return ad_name;
    }

    public void setAd_name(String adName) {
        ad_name = adName;
    }

    public Character getAd_type() {
        return ad_type;
    }

    public void setAd_type(Character adType) {
        ad_type = adType;
    }

    public String getAd_icon() {
        return ad_icon;
    }

    public void setAd_icon(String adIcon) {
        ad_icon = adIcon;
    }

    public String getAd_link() {
        return ad_link;
    }

    public void setAd_link(String adLink) {
        ad_link = adLink;
    }

    public Date getAdd_date() {
        return add_date;
    }

    public void setAdd_date(Date addDate) {
        add_date = addDate;
    }

    public Date getModify_date() {
        return modify_date;
    }

    public void setModify_date(Date modifyDate) {
        modify_date = modifyDate;
    }

    public Integer getShow_order() {
        return show_order;
    }

    public void setShow_order(Integer showOrder) {
        show_order = showOrder;
    }

    public Integer getApp_count() {
        return this.app_count;
    }

    public void setApp_count(Integer app_count) {
        this.app_count = app_count;
    }

    public Set<ModelAdvertisement> getModelAdvertisement() {
        return this.modelAdvertisement;
    }

    public void setModelAdvertisement(Set<ModelAdvertisement> modelAdvertisement) {
        this.modelAdvertisement = modelAdvertisement;
    }

    public Set<AppInfo> getAppInfos() {
        return this.appInfos;
    }

    public void setAppInfos(Set<AppInfo> appInfos) {
        this.appInfos = appInfos;
    }

    public AppInfo getAppInfo() {
        return this.appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
