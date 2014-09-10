package com.bbkmobile.iqoo.interfaces.advertisement.vo;

public class Advertising {

    private String adv_add;
    private String image_url;
    private Long app_list_id;
    private Integer app_count;
    private Long app_id;

    public String getAdv_add() {
        return adv_add;
    }

    public void setAdv_add(String adv_add) {
        this.adv_add = adv_add;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Long getApp_list_id() {
        return app_list_id;
    }

    public void setApp_list_id(Long app_list_id) {
        this.app_list_id = app_list_id;
    }

    public Integer getApp_count() {
        return app_count;
    }

    public void setApp_count(Integer app_count) {
        this.app_count = app_count;
    }

    public Long getApp_id() {
        return app_id;
    }

    public void setApp_id(Long app_id) {
        this.app_id = app_id;
    }

}
