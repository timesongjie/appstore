package com.bbkmobile.iqoo.console.dao.appinfo;

import java.util.Date;

public class AndroidPermission {
    @SuppressWarnings("unused")
    private static final String TAG = "AndroidPermission";

    private Long id;
    private String permission;
    private Date add_date;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermission() {
        return this.permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Date getAdd_date() {
        return this.add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

}
