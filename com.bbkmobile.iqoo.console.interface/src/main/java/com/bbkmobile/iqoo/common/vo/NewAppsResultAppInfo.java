package com.bbkmobile.iqoo.common.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bbkmobile.iqoo.console.constants.UtilTool;

/**
 * 
 * @author time 新品速递
 */
public class NewAppsResultAppInfo extends BaseResultAppInfo {

    private Short tag;
    private List<String> screenshotList;
    private Date upload_time;
    private String developer;
    private Integer parent_id;
    private Long download_count;
    private String app_remark;// 小编推荐

    private String screenshot;

    public List<String> getScreenshotList() {
        if (screenshotList != null && screenshotList.size() > 0) {
            for (int i = 0; i < screenshotList.size(); i++) {
                try {
                    screenshotList.set(i, UtilTool
                            .getDownloadImageHttpURL(screenshotList.get(i)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return screenshotList;
    }

    public void setScreenshotList(List<String> screenshotList) {
        this.screenshotList = screenshotList;
    }

    public Date getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(Date upload_time) {
        this.upload_time = upload_time;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public Long getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Long download_count) {
        this.download_count = download_count;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        if (this.screenshotList == null) {
            this.screenshotList = new ArrayList<String>();
        }
        this.screenshotList.add(screenshot);
    }

    public Short getTag() {
        return tag;
    }

    public void setTag(Short tag) {
        this.tag = tag;
    }

    public String getApp_remark() {
        app_remark = StringUtils.defaultIfEmpty(app_remark, "");
        return app_remark;
    }

    public void setApp_remark(String app_remark) {
        this.app_remark = app_remark;
    }

}
