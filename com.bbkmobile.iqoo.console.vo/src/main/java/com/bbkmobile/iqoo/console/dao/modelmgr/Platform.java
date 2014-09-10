package com.bbkmobile.iqoo.console.dao.modelmgr;

import java.util.Date;

public class Platform {

    private Short id;
    private String platform; // 系列名称
    private String describe;
    private Date add_date; // 添加时期
    private Date modify_date; // 修改日期

    public Short getId() {
        return this.id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDescribe() {
        return this.describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Date getAdd_date() {
        return this.add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public Date getModify_date() {
        return this.modify_date;
    }

    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }

}
