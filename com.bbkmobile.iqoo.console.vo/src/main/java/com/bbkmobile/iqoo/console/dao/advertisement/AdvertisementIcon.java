package com.bbkmobile.iqoo.console.dao.advertisement;

import java.util.Date;

import com.bbkmobile.iqoo.console.dao.modelmgr.ConsoleConstant;
import com.bbkmobile.iqoo.console.dao.modelmgr.ModelScreen;

public class AdvertisementIcon {

    private Long id;
    private Advertisement advertisement;
    private String icon_url;
    private ConsoleConstant modelScreen;
    private Date set_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String iconUrl) {
        icon_url = iconUrl;
    }

    public Date getSet_time() {
        return set_time;
    }

    public void setSet_time(Date setTime) {
        set_time = setTime;
    }

    public ConsoleConstant getModelScreen() {
        return modelScreen;
    }

    public void setModelScreen(ConsoleConstant modelScreen) {
        this.modelScreen = modelScreen;
    }

}
