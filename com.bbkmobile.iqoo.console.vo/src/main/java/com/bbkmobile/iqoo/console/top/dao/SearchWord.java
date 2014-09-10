package com.bbkmobile.iqoo.console.top.dao;

import java.util.Date;

import com.bbkmobile.iqoo.platform.annotation.field.FieldConfig;
import com.bbkmobile.iqoo.platform.annotation.field.type.MatchModeType;

public class SearchWord {

    private Long id;
    @FieldConfig(criterionMatchMode = MatchModeType.EXACT)
    private String word;
    private Long search_count;
    private Integer show_order;
    private Date add_date;
    private String tableName;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getSearch_count() {
        return search_count;
    }

    public void setSearch_count(Long search_count) {
        this.search_count = search_count;
    }

    public Integer getShow_order() {
        return show_order;
    }

    public void setShow_order(Integer show_order) {
        this.show_order = show_order;
    }

    public Date getAdd_date() {
        return add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
