package com.bbkmobile.iqoo.common.vo;

/**
 * 
 * @author time 装机必备应用列表、推荐应用列表、广告应用列表、排行榜、子分类和搜索应用列表
 */
public class CommonResultAppInfo extends BaseResultAppInfo {

    private Short tag;
    private Integer parent_id;
    private String developer;
    private Integer sortOrder;
    private Long download_count;
    private String app_remark;// 小编推荐

    public CommonResultAppInfo() {

    }

    public String getApp_remark() {
        return app_remark;
    }

    public void setApp_remark(String app_remark) {
        this.app_remark = app_remark;
    }

    public Short getTag() {
        return tag;
    }

    public void setTag(Short tag) {
        this.tag = tag;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Long getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Long download_count) {
        this.download_count = download_count;
    }
}
