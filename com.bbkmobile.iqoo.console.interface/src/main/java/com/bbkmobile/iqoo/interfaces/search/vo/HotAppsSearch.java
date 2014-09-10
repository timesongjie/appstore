package com.bbkmobile.iqoo.interfaces.search.vo;

import org.apache.commons.lang.StringUtils;

import com.bbkmobile.iqoo.console.constants.UtilTool;

public class HotAppsSearch {

    // package_name、version_name、version_code、icon_url、
    // title_zh、title_en、download_url、 id；
    private String package_name;
    private String version_name;
    private String version_code;
    private String icon_url;
    private String title_zh;
    private String title_en;
    private String download_url;
    private Long id;
    private int size;

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getIcon_url() {
        try {
            icon_url = StringUtils.defaultIfEmpty(icon_url, "");
            return UtilTool.getDownloadImageHttpURL(icon_url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getTitle_zh() {
        return title_zh;
    }

    public void setTitle_zh(String title_zh) {
        this.title_zh = title_zh;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getDownload_url() {
        return UtilTool.getHttpURL("/appinfo/downloadApkFile?id=" + id);
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
