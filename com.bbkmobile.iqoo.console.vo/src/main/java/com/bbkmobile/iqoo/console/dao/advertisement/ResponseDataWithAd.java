package com.bbkmobile.iqoo.console.dao.advertisement;

import java.util.List;

import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;

public class ResponseDataWithAd {
    public String generateXmlForAd(List<Advertisement> advertisements)
            throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><AdvertisingList>");

        if (null != advertisements && 0 != advertisements.size()) {
            for (Advertisement advertisement : advertisements) {
                sb.append("<Advertising><adv_add><![CDATA[");
                sb.append(advertisement.getAd_name());
                sb.append("]]></adv_add><image_url><![CDATA[");
                sb.append("http://172.20.20.139:8080/App_Console"
                        + advertisement.getAd_icon());
                sb.append("]]></image_url><app_list_id><![CDATA[");
                sb.append(advertisement.getId());
                sb.append("]]></app_list_id><app_count><![CDATA[");
                sb.append(advertisement.getApp_count());
                sb.append("]]></app_count></Advertising>");
            }
        }
        sb.append("</AdvertisingList>");
        return sb.toString();
    }

    public String generateXmlForAdApp(List<AppInfo> appInfos,
            int apps_per_page, int page_index, int total_count)
            throws Exception {
        StringBuilder sb = new StringBuilder();
        int maxpage = (int) Math.ceil((float) total_count / apps_per_page);

        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><PackageList maxpage=\"");
        sb.append(maxpage);
        sb.append("\" PageNo=\"");
        sb.append(page_index);
        sb.append("\" pageSize=\"");
        sb.append(apps_per_page);
        sb.append("\" TotalCount=\"");
        sb.append(total_count);
        sb.append("\">");

        if (null != appInfos && 0 != appInfos.size()) {
            for (AppInfo appInfo : appInfos) {
                sb.append("<Package><id><![CDATA[");
                sb.append(appInfo.getId());
                sb.append("]]></id><package_name><![CDATA[");
                sb.append(appInfo.getAppPackage());
                sb.append("]]></package_name><parent_id><![CDATA[");
                sb.append(appInfo.getAppType().getParentId());
                sb.append("]]></parent_id><title_zh><![CDATA[");
                sb.append(appInfo.getAppCnName());
                sb.append("]]></title_zh><title_en><![CDATA[");
                sb.append(appInfo.getAppEnName());
                sb.append("]]></title_en><icon_url><![CDATA[");
                sb.append(appInfo.getAppIcon());
                sb.append("]]></icon_url><developer><![CDATA[");
                sb.append(appInfo.getDeveloper().getName());
                sb.append("]]></developer><score><![CDATA[");
                sb.append(appInfo.getAvgComment());
                sb.append("]]></score><raters_count><![CDATA[");
                sb.append(appInfo.getCommentCount());
                sb.append("]]></raters_count><version_name><![CDATA[");
                sb.append(appInfo.getAppVersion());
                sb.append("]]></version_name><version_code><![CDATA[");
                sb.append(appInfo.getAppVersionCode());
                sb.append("]]></version_code></Package>");
            }
        }
        sb.append("</PackageList>");

        return sb.toString();
    }
}
