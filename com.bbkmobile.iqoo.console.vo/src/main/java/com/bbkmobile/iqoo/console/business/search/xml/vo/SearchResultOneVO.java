/**
 * 
 */
package com.bbkmobile.iqoo.console.business.search.xml.vo;

/**
 * @author wangbo
 * 
 */
public class SearchResultOneVO {

    private String statuscode;
    private String statusmessage;
    private AppVO app;

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

    public AppVO getApp() {
        return app;
    }

    public void setApp(AppVO app) {
        this.app = app;
    }

}
