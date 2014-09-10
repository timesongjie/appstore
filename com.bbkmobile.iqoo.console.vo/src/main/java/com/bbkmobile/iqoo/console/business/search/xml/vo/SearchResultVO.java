/**
 * 
 */
package com.bbkmobile.iqoo.console.business.search.xml.vo;

/**
 * @author wangbo
 * 
 */
public class SearchResultVO {

    private String statuscode;
    private String statusmessage;
    private ResultVO result;

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

    public ResultVO getResult() {
        return result;
    }

    public void setResult(ResultVO result) {
        this.result = result;
    }

}
