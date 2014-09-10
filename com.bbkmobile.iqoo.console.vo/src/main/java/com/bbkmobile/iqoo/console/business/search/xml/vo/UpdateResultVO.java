package com.bbkmobile.iqoo.console.business.search.xml.vo;

import java.util.ArrayList;
import java.util.List;

public class UpdateResultVO {

    // update app

    private String statuscode;
    private String statusmessage;
    private List<AppVO> apps = new ArrayList<AppVO>();
    private List<CateVO> cates = new ArrayList<CateVO>();

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getStatusmessage() {
        return statusmessage;
    }

    public void setStatusmessage(String statusmessage) {
        this.statusmessage = statusmessage;
    }

    public List<AppVO> getApps() {
        return this.apps;
    }

    public void setApps(List<AppVO> apps) {
        this.apps = apps;
    }

    public List<CateVO> getCates() {
        return this.cates;
    }

    public void setCates(List<CateVO> cates) {
        this.cates = cates;
    }

}
