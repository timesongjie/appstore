package com.bbkmobile.iqoo.interfaces.common;

import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

public class ProjectQuery {

    public static ProjectionList setJoinAppsProList() {
        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("appInfo.id"), "id");
        proList.add(Projections.property("appInfo.tag"), "tag");
        proList.add(Projections.property("appInfo.appPackage"), "package_name");
        proList.add(Projections.property("appInfo.appCnName"), "title_zh");
        proList.add(Projections.property("appInfo.appEnName"), "title_en");
        proList.add(Projections.property("appInfo.appIcon"), "icon_url");
        proList.add(Projections.property("appInfo.appAuthor"), "developer");
        proList.add(Projections.property("appInfo.downloadCount"),
                "download_count");
        proList.add(Projections.property("appInfo.appApk"), "download_url");
        proList.add(Projections.property("appInfo.official"), "offical");
        proList.add(Projections.property("appInfo.patchs"), "patchs");

        proList.add(Projections.property("appInfo.commentCount"),
                "raters_count");
        proList.add(Projections.property("appInfo.avgComment"), "score");
        proList.add(Projections.property("appInfo.apkSize"), "size");
        proList.add(Projections.property("appInfo.appVersion"), "version_name");
        proList.add(Projections.property("appInfo.appVersionCode"),
                "version_code");
        return proList;
    }
}
