package com.bbkmobile.iqoo.console.dao.contentmanage;

import java.util.Date;

public class FocusRank {
    private Integer id;
    private Short type;
    private Long object_id;
    private Date add_date;
    private Date modify_date;
    private String valid_date;
    private Short operation_type;
    private Integer show_order;

    private String name;
    private String img;

    private String fromDate;
    private String toDate;

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

    public String getValid_date() {
        return valid_date;
    }

    public void setValid_date(String valid_date) {
        this.valid_date = valid_date;
    }

    public Short getOperation_type() {
        return operation_type;
    }

    public void setOperation_type(Short operation_type) {
        this.operation_type = operation_type;
    }

    public Integer getShow_order() {
        return show_order;
    }

    public void setShow_order(Integer show_order) {
        this.show_order = show_order;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
