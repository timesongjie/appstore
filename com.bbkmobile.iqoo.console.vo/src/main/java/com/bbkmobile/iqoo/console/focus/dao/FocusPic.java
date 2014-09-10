package com.bbkmobile.iqoo.console.focus.dao;

import java.util.Date;

public class FocusPic {
    private Integer id;
    private Short type;// 1-专题 2-广告 3-活动
    private Long object_id;
    private String name;
    private String img;
    private Date add_date;
    private Date set_date;
    private Integer show_order;
    private Integer app_count;
    private Long app_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Long getObject_id() {
        return object_id;
    }

    public void setObject_id(Long object_id) {
        this.object_id = object_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getAdd_date() {
        return add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public Date getSet_date() {
        return set_date;
    }

    public void setSet_date(Date set_date) {
        this.set_date = set_date;
    }

    public Integer getShow_order() {
        return show_order;
    }

    public void setShow_order(Integer show_order) {
        this.show_order = show_order;
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
