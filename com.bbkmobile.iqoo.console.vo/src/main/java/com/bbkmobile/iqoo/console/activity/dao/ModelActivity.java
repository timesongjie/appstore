package com.bbkmobile.iqoo.console.activity.dao;

import java.util.Date;

import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;

public class ModelActivity {
    private Integer id;
    private Integer act_id;
    private String name;
    private String img;
    private String pc_img;
    private String description;
    private Integer show_order;
    private Integer app_count;
    private Long app_id;
    private Date add_date;
    private Date modify_date;
    private Date set_date;
    private AppInfo appInfo = new AppInfo();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAct_id() {
        return act_id;
    }

    public void setAct_id(Integer act_id) {
        this.act_id = act_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPc_img() {
        return pc_img;
    }

    public void setPc_img(String pc_img) {
        this.pc_img = pc_img;
    }

    public Integer getShow_order() {
        return show_order;
    }

    public void setShow_order(Integer show_order) {
        this.show_order = show_order;
    }

    public Integer getApp_count() {
        return app_count;
    }

    public void setApp_count(Integer app_count) {
        this.app_count = app_count;
    }

    public Long getApp_id() {
        return app_id;
    }

    public void setApp_id(Long app_id) {
        this.app_id = app_id;
    }

    public Date getAdd_date() {
        return add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public Date getModify_date() {
        return modify_date;
    }

    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }

    public Date getSet_date() {
        return set_date;
    }

    public void setSet_date(Date set_date) {
        this.set_date = set_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

}
