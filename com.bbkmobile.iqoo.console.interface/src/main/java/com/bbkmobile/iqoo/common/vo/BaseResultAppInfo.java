package com.bbkmobile.iqoo.common.vo;

import org.apache.commons.lang.StringUtils;

import com.bbkmobile.iqoo.console.constants.UtilTool;

/**
 * 
 * @author time 适用于手机更新列表
 */
public class BaseResultAppInfo {
    private Long id;
    private String title_zh;
    private String title_en;
    private Float score;
    private Integer raters_count;
    private String package_name;
    private String version_name;
    private String version_code;
    private String download_url;
    private Integer size;
    private String icon_url;
    private Character offical;
    private String patchs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getRaters_count() {
        return raters_count;
    }

    public void setRaters_count(Integer raters_count) {
        this.raters_count = raters_count;
    }

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

    public String getDownload_url() {
        if (this.download_url != null
                && this.download_url.startsWith("http://")) {// from 百度
            return this.download_url;
        }
        return UtilTool.getHttpURL("/appinfo/downloadApkFile?id=" + id);
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getIcon_url() {
        try {
            if (this.icon_url != null && this.icon_url.startsWith("http://")) {// from
                                                                               // 百度
                return this.icon_url;
            }
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

    public Character getOffical() {
        return offical;
    }

    public void setOffical(Character offical) {
        if (offical == null || offical.equals(' ')) {
            offical = '0';
        }
        this.offical = offical;
    }

    public String getPatchs() {
        return patchs;
    }

    public void setPatchs(String patchs) {
        this.patchs = patchs;
    }
}
