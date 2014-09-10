package com.bbkmobile.iqoo.interfaces.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.common.vo.NewAppsResultAppInfo;
import com.bbkmobile.iqoo.console.constants.UtilTool;
import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.appinfo.TAppScreenshot;

/**
 * 
 * @author time 工具类，
 */
public class AppInfoTXML {
    public static String getXmlForAppInfos(List<AppInfo> appInfos,
            String appVersion) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (AppInfo appInfo : appInfos) {
            sb.append(getXmlForAppInfo(appInfo, appVersion));
        }
        return sb.toString();
    }

    public static String getXmlForAppInfo(AppInfo appInfo, String appVersion)
            throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("<Package><id><![CDATA[");
        sb.append(appInfo.getId());
        sb.append("]]></id><tag><![CDATA[");
        sb.append(appInfo.getTag());
        sb.append("]]></tag><package_name><![CDATA[");
        sb.append(appInfo.getAppPackage());
        sb.append("]]></package_name><parent_id><![CDATA[");
        // sb.append(appInfo.getAppType().getParentId());
        sb.append("null");
        sb.append("]]></parent_id><title_zh><![CDATA[");
        sb.append(appInfo.getAppCnName());
        sb.append("]]></title_zh><title_en><![CDATA[");
        sb.append(appInfo.getAppEnName());
        sb.append("]]></title_en><icon_url><![CDATA[");
        sb.append(UtilTool.getDownloadImageHttpURL(appInfo.getAppIcon()));
        sb.append("]]></icon_url><developer><![CDATA[");
        // sb.append(appInfo.getDeveloper().getName());
        sb.append(appInfo.getAppAuthor());
        sb.append("]]></developer><score><![CDATA[");
        sb.append(appInfo.getAvgComment());
        sb.append("]]></score><raters_count><![CDATA[");
        sb.append(appInfo.getCommentCount());
        sb.append("]]></raters_count><version_name><![CDATA[");
        sb.append(appInfo.getAppVersion());
        sb.append("]]></version_name><version_code><![CDATA[");
        sb.append(appInfo.getAppVersionCode());
        sb.append("]]></version_code><download_url><![CDATA[");
        sb.append(UtilTool.getHttpURL("/appinfo/downloadApkFile?id="
                + appInfo.getId() + "&app_version=" + appVersion));
        sb.append("]]></download_url><size><![CDATA[");
        sb.append(appInfo.getApkSize());
        sb.append("]]></size><download_count><![CDATA[");
        sb.append(appInfo.getDownloadCount());
        sb.append("]]></download_count><offical><![CDATA[");
        sb.append("0");
        sb.append("]]></offical><patchs><![CDATA[");
        if (null != appInfo.getPatchs()) {
            sb.append(appInfo.getPatchs());
        }
        sb.append("]]></patchs></Package>");
        return sb.toString();
    }

    public static String getXmlForAppInfoWithShotscreen(AppInfo appInfo,
            String appVersion) throws Exception {
        List<TAppScreenshot> appScreenshots = new ArrayList<TAppScreenshot>(
                appInfo.getTAppScreenshots());// findAppScreenshot(appInfo);
        StringBuilder sb = new StringBuilder();
        sb.append("<Package><id><![CDATA[");
        sb.append(appInfo.getId());
        sb.append("]]></id><tag><![CDATA[");
        sb.append(appInfo.getTag());
        sb.append("]]></tag><package_name><![CDATA[");
        sb.append(appInfo.getAppPackage());
        sb.append("]]></package_name><parent_id><![CDATA[");
        // sb.append(appInfo.getAppType().getParentId());
        sb.append("null");
        sb.append("]]></parent_id><title_zh><![CDATA[");
        sb.append(appInfo.getAppCnName());
        sb.append("]]></title_zh><title_en><![CDATA[");
        sb.append(appInfo.getAppEnName());
        sb.append("]]></title_en><icon_url><![CDATA[");
        sb.append(UtilTool.getDownloadImageHttpURL(appInfo.getAppIcon()));
        sb.append("]]></icon_url><developer><![CDATA[");
        // sb.append(appInfo.getDeveloper().getName());
        sb.append(appInfo.getAppAuthor());
        sb.append("]]></developer><score><![CDATA[");
        sb.append(appInfo.getAvgComment());
        sb.append("]]></score><raters_count><![CDATA[");
        sb.append(appInfo.getCommentCount());
        sb.append("]]></raters_count><version_name><![CDATA[");
        sb.append(appInfo.getAppVersion());
        sb.append("]]></version_name><version_code><![CDATA[");
        sb.append(appInfo.getAppVersionCode());
        sb.append("]]></version_code><download_url><![CDATA[");
        sb.append(UtilTool.getHttpURL("/appinfo/downloadApkFile?id="
                + appInfo.getId() + "&app_version=" + appVersion));
        sb.append("]]></download_url><upload_time><![CDATA[");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String releaseDate = dateFormat.format(appInfo.getUpdateDate()); // getOnSaleDate
        sb.append(releaseDate);
        sb.append("]]></upload_time><size><![CDATA[");
        sb.append(appInfo.getApkSize());
        sb.append("]]></size><download_count><![CDATA[");
        sb.append(appInfo.getDownloadCount());
        sb.append("]]></download_count><offical><![CDATA[");
        sb.append("0");
        sb.append("]]></offical><patchs><![CDATA[");
        if (null != appInfo.getPatchs()) {
            sb.append(appInfo.getPatchs());
        }
        sb.append("]]></patchs><ScreenshotList>");
        if (null != appScreenshots && 0 != appScreenshots.size()) {
            for (TAppScreenshot appScreenshot : appScreenshots) {
                sb.append("<screenshot><![CDATA[");
                // sb.append(baseURI+appScreenshot.getScreenshot());
                sb.append(UtilTool.getDownloadImageHttpURL(appScreenshot
                        .getScreenshot()));
                sb.append("]]></screenshot>");
            }
        }
        sb.append("</ScreenshotList></Package>");
        return sb.toString();
    }

    public static String getXmlForAppInfo(List<CommonResultAppInfo> list,
            float appVersion) throws Exception {
        StringBuilder sb = new StringBuilder();
        if (list != null) {
            for (CommonResultAppInfo appInfo : list) {
                sb.append("<Package><id><![CDATA[");
                sb.append(appInfo.getId());
                sb.append("]]></id><tag><![CDATA[");
                sb.append(appInfo.getTag());
                sb.append("]]></tag><package_name><![CDATA[");
                sb.append(appInfo.getPackage_name());
                sb.append("]]></package_name><parent_id><![CDATA[");
                // sb.append(appInfo.getAppType().getParentId());
                sb.append("null");
                sb.append("]]></parent_id><title_zh><![CDATA[");
                sb.append(appInfo.getTitle_zh());
                sb.append("]]></title_zh><title_en><![CDATA[");
                sb.append(appInfo.getTitle_en());
                sb.append("]]></title_en><icon_url><![CDATA[");
                if (appInfo.getIcon_url() != null
                        && appInfo.getIcon_url().startsWith("http://")) {
                    sb.append(appInfo.getIcon_url());
                } else {
                    sb.append(UtilTool.getDownloadImageHttpURL(appInfo
                            .getIcon_url()));
                }
                sb.append("]]></icon_url><developer><![CDATA[");
                // sb.append(appInfo.getDeveloper().getName());
                sb.append(appInfo.getDeveloper());
                sb.append("]]></developer><score><![CDATA[");
                sb.append(appInfo.getScore());
                sb.append("]]></score><raters_count><![CDATA[");
                sb.append(appInfo.getRaters_count());
                sb.append("]]></raters_count><version_name><![CDATA[");
                sb.append(appInfo.getVersion_name());
                sb.append("]]></version_name><version_code><![CDATA[");
                sb.append(appInfo.getVersion_code());
                sb.append("]]></version_code><download_url><![CDATA[");
                sb.append(UtilTool.getHttpURL("/appinfo/downloadApkFile?id="
                        + appInfo.getId() + "&app_version=" + appVersion));
                sb.append("]]></download_url><size><![CDATA[");
                sb.append(appInfo.getSize());
                sb.append("]]></size><download_count><![CDATA[");
                sb.append(appInfo.getDownload_count());
                sb.append("]]></download_count><offical><![CDATA[");
                sb.append("0");
                sb.append("]]></offical><patchs><![CDATA[");
                if (null != appInfo.getPatchs()) {
                    sb.append(appInfo.getPatchs());
                }
                sb.append("]]></patchs></Package>");
            }
        }
        return sb.toString();
    }

    public static String getXmlForAppInfoWithShotscreen(
            NewAppsResultAppInfo appInfo, float appVersion) throws Exception {
        // List<TAppScreenshot> appScreenshots = new
        // ArrayList<TAppScreenshot>(appInfo.getTAppScreenshots());//findAppScreenshot(appInfo);
        List<String> appScreenshots = appInfo.getScreenshotList();
        StringBuilder sb = new StringBuilder();
        sb.append("<Package><id><![CDATA[");
        sb.append(appInfo.getId());
        sb.append("]]></id><tag><![CDATA[");
        sb.append(appInfo.getTag());
        sb.append("]]></tag><package_name><![CDATA[");
        sb.append(appInfo.getPackage_name());
        sb.append("]]></package_name><parent_id><![CDATA[");
        // sb.append(appInfo.getAppType().getParentId());
        sb.append("null");
        sb.append("]]></parent_id><title_zh><![CDATA[");
        sb.append(appInfo.getTitle_zh());
        sb.append("]]></title_zh><title_en><![CDATA[");
        sb.append(appInfo.getTitle_en());
        sb.append("]]></title_en><icon_url><![CDATA[");
        sb.append(appInfo.getIcon_url());// UtilTool.getDownloadImageHttpURL()
        sb.append("]]></icon_url><developer><![CDATA[");
        // sb.append(appInfo.getDeveloper().getName());
        sb.append(appInfo.getDeveloper());
        sb.append("]]></developer><score><![CDATA[");
        sb.append(appInfo.getScore());
        sb.append("]]></score><raters_count><![CDATA[");
        sb.append(appInfo.getRaters_count());
        sb.append("]]></raters_count><version_name><![CDATA[");
        sb.append(appInfo.getVersion_name());
        sb.append("]]></version_name><version_code><![CDATA[");
        sb.append(appInfo.getVersion_code());
        sb.append("]]></version_code><download_url><![CDATA[");
        sb.append(UtilTool.getHttpURL("/appinfo/downloadApkFile?id="
                + appInfo.getId() + "&app_version=" + appVersion));
        sb.append("]]></download_url><upload_time><![CDATA[");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String releaseDate = appInfo.getUpload_time() == null ? "" : dateFormat
                .format(appInfo.getUpload_time()); // getOnSaleDate
        sb.append(releaseDate);
        sb.append("]]></upload_time><size><![CDATA[");
        sb.append(appInfo.getSize());
        sb.append("]]></size><download_count><![CDATA[");
        sb.append(appInfo.getDownload_count());
        sb.append("]]></download_count><offical><![CDATA[");
        sb.append("0");
        sb.append("]]></offical><patchs><![CDATA[");
        if (null != appInfo.getPatchs()) {
            sb.append(appInfo.getPatchs());
        }
        sb.append("]]></patchs><ScreenshotList>");
        if (null != appScreenshots && 0 != appScreenshots.size()) {
            for (String appScreenshot : appScreenshots) {
                sb.append("<screenshot><![CDATA[");
                // sb.append(baseURI+appScreenshot.getScreenshot());
                sb.append(appScreenshot);// UtilTool.getDownloadImageHttpURL()
                sb.append("]]></screenshot>");
            }
        }
        sb.append("</ScreenshotList></Package>");
        return sb.toString();
    }
}
