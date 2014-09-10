package com.bbkmobile.iqoo.common.vo;

public class RelationRecResultAppInfo extends BaseResultAppInfo {

    private String from;
    private String developer;
    private Integer download_count;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public Integer getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Integer download_count) {
        this.download_count = download_count;
    }
}
