package com.bbkmobile.iqoo.console.dao.recommendation;

import java.util.Date;

import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.dao.modelmgr.Series;

public class CellphoneRecommendApp {
    private Long id;
    private Character order_type; // 排序类型：1-公共排序，2-系列排序，3-机型排序
    private Integer show_order;
    private Date add_date;
    private AppInfo appInfo = new AppInfo();
    private Model model;
    private Series series;
    private Short series_id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getShow_order() {
        return this.show_order;
    }

    public void setShow_order(Integer show_order) {
        this.show_order = show_order;
    }

    public Date getAdd_date() {
        return this.add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public AppInfo getAppInfo() {
        return this.appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public Character getOrder_type() {
        return this.order_type;
    }

    public void setOrder_type(Character order_type) {
        this.order_type = order_type;
    }

    public Series getSeries() {
        return this.series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Model getModel() {
        return this.model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Short getSeries_id() {
        return this.series_id;
    }

    public void setSeries_id(Short series_id) {
        this.series_id = series_id;
    }

}
