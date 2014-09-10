package com.bbkmobile.iqoo.interfaces.search.vo;

import com.bbkmobile.iqoo.common.vo.BaseResultAppInfo;

/**
 * 搜索关联推荐详情
 * 
 * @author time
 * 
 */
public class SugRecApp extends BaseResultAppInfo {

    private Short tag;
    private Integer parent_id;
    private String developer;
    private Integer sortOrder;
    private Long download_count;
    private String app_remark;// 小编推荐

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

    public String getApp_remark() {
        return app_remark;
    }

    public void setApp_remark(String app_remark) {
        this.app_remark = app_remark;
    }
}
