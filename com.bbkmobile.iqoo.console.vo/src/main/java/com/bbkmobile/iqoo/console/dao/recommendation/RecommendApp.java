package com.bbkmobile.iqoo.console.dao.recommendation;

import java.util.Date;

import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.appinfo.Developer;

/**
 * 应用推荐实体
 * 
 * @author time
 * 
 */
public class RecommendApp {

    private Long id;
    private Integer show_order;
    private Date add_date;
    private AppInfo appInfo = new AppInfo();

    public RecommendApp() {
    }

    public RecommendApp(Long id, Integer show_order, Date add_date, Long appId,
            Developer developer, String appCnName, String appEnName,
            String appVersion, String appIcon, String appApk, Integer apkSize,
            String appVersionCode, String appPackage, float avg_comment,
            Integer commentCount, Integer downloadCount, String patchs,
            Short tag, char official) {
        this.id = id;
        this.show_order = show_order;
        this.add_date = add_date;
        this.appInfo = new AppInfo(appId, developer, appCnName, appEnName,
                appVersion, appIcon, appApk, apkSize, appVersionCode,
                appPackage, avg_comment, commentCount, downloadCount, patchs,
                tag, official);
    }

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
