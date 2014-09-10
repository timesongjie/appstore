package com.bbkmobile.iqoo.console.dao.recommendation;

import java.util.Date;

import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;

/**
 * 游戏推荐实体
 * 
 * @author time
 * 
 */
public class RecommendGame {

    private Long id;
    private Integer show_order;
    private Date add_date;
    private AppInfo appInfo = new AppInfo();

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getShow_order() {
        return this.show_order;
    }

    public void setShow_order(Integer show_order) {
        this.show_order = show_order;
    }

    public Date getAdd_date() {
        return this.add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public AppInfo getAppInfo() {
        return this.appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }
}
