package com.bbkmobile.iqoo.console.dao.modelmgr;

import java.util.Date;

public class ConsoleConstant {

    private Short id;
    private String value;
    private String describe;
    private Short type;
    private String c_favor;
    private Date add_date; // 添加时期
    private Date modify_date; // 修改日期

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getC_favor() {
        return c_favor;
    }

    public void setC_favor(String cFavor) {
        c_favor = cFavor;
    }

    public Date getAdd_date() {
        return add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public Date getModify_date() {
        return modify_date;
    }

    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }
}
