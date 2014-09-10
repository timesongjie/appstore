package com.bbkmobile.iqoo.common.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author time 适用于 不完整 应用和 完整应用
 */
public class AppDetailResultAppInfo extends BaseResultAppInfo {
    private Integer parent_id;
    private String developer;
    private Integer download_count;
    private String upload_time;
    private List<String> screenshotList = new ArrayList<String>();
    private List<String> permissionList = new ArrayList<String>();
    private List<SimpleAppComment> appComments = new ArrayList<SimpleAppComment>();
    private String introduction;
    private String app_remark;
    private char ad = '0';// 是否广告 0：未检测 1：不存在 2：存在
    private char stag = '0';// 是否安全 0：未检测 1：有安全结果
    private List<String> safe;// ["i管家检测通过","腾讯检测通过","360检测通过"]
    private char fee = '0';// 是否收费 0未检测 1-免费，2-付费下载，3-应用内付费
    private List acts = new ArrayList(0);// 活动ID列表

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public List<String> getScreenshotList() {
        return screenshotList;
    }

    public void setScreenshotList(List<String> screenshotList) {
        this.screenshotList = screenshotList;
    }

    public List<String> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<String> permissionList) {
        this.permissionList = permissionList;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public Integer getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Integer download_count) {
        this.download_count = download_count;
    }

    public List<SimpleAppComment> getAppComments() {
        return appComments;
    }

    public void setAppComments(List<SimpleAppComment> appComments) {
        this.appComments = appComments;
    }

    public String getApp_remark() {
        return app_remark;
    }

    public void setApp_remark(String app_remark) {
        this.app_remark = app_remark;
    }

    public char getAd() {
        return ad;
    }

    public void setAd(char ad) {
        this.ad = ad;
    }

    public char getStag() {
        return stag;
    }

    public void setStag(char stag) {
        this.stag = stag;
    }

    public char getFee() {
        return fee;
    }

    public void setFee(char fee) {
        this.fee = fee;
    }

    public List<String> getSafe() {
        return safe;
    }

    public void setSafe(List<String> safe) {
        this.safe = safe;
    }

    public List getActs() {
        return acts;
    }

    public void setActs(List acts) {
        this.acts = acts;
    }

}
