package com.bbkmobile.iqoo.console.dao.appinfo;

import java.util.Date;

import com.bbkmobile.iqoo.console.dao.login.BbkAdminUser;

public class AppOrderSet {

    private Long id;
    private AppInfo appInfo; // 设置应用
    private Character set_status; // 设置状态：当前最新设置状态：1 非最新设置状态：0
    private String true_date; // 实际发布时间
    private String set_date; // 设置发布时间
    private String true_score; // 实际平均评分
    private String set_score; // 设置平均评分
    private String true_click; // 实际点击量
    private String set_click; // 设置点击量
    private String true_download; // 实际下载量
    private String set_download; // 设置下载量
    private Date set_time; // 设置时间
    private String admin_name; // 设置管理员

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

    public Character getSet_status() {
        return set_status;
    }

    public void setSet_status(Character setStatus) {
        set_status = setStatus;
    }

    public String getTrue_date() {
        return true_date;
    }

    public void setTrue_date(String trueDate) {
        true_date = trueDate;
    }

    public String getSet_date() {
        return set_date;
    }

    public void setSet_date(String setDate) {
        set_date = setDate;
    }

    public String getTrue_score() {
        return true_score;
    }

    public void setTrue_score(String trueScore) {
        true_score = trueScore;
    }

    public String getSet_score() {
        return set_score;
    }

    public void setSet_score(String setScore) {
        set_score = setScore;
    }

    public String getTrue_click() {
        return true_click;
    }

    public void setTrue_click(String trueClick) {
        true_click = trueClick;
    }

    public String getSet_click() {
        return set_click;
    }

    public void setSet_click(String setClick) {
        set_click = setClick;
    }

    public String getTrue_download() {
        return true_download;
    }

    public void setTrue_download(String trueDownload) {
        true_download = trueDownload;
    }

    public String getSet_download() {
        return set_download;
    }

    public void setSet_download(String setDownload) {
        set_download = setDownload;
    }

    public Date getSet_time() {
        return set_time;
    }

    public void setSet_time(Date setTime) {
        set_time = setTime;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String adminName) {
        admin_name = adminName;
    }

}
