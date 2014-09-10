package com.bbkmobile.iqoo.console.dao.modelmgr;

import java.util.Date;

public class ModelScreen {

    private Short id;
    private String screen_size;
    private char type; // 0： PC类的分辨率 ，1：手机类的分辨率
    private Date add_time;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getScreen_size() {
        return screen_size;
    }

    public void setScreen_size(String screenSize) {
        screen_size = screenSize;
    }

    public Date getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Date addTime) {
        add_time = addTime;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

}
