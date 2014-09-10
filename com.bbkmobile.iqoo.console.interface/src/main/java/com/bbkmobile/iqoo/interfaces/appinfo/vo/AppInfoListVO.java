/**
 * 
 */
package com.bbkmobile.iqoo.interfaces.appinfo.vo;

import java.util.List;

import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;

/**
 * @author wangbo
 * 
 */
public class AppInfoListVO {

    private int page_index;
    private int apps_per_page;
    private int total_count;
    private List<AppInfo> appInfoLs;
    private int maxPage;

    public int getPage_index() {
        return page_index;
    }

    public void setPage_index(int pageIndex) {
        page_index = pageIndex;
    }

    public int getApps_per_page() {
        return apps_per_page;
    }

    public void setApps_per_page(int appsPerPage) {
        apps_per_page = appsPerPage;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int totalCount) {
        total_count = totalCount;
    }

    public List<AppInfo> getAppInfoLs() {
        return appInfoLs;
    }

    public void setAppInfoLs(List<AppInfo> appInfoLs) {
        this.appInfoLs = appInfoLs;
    }

    public int getMaxPage() {
        return this.maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }
}
