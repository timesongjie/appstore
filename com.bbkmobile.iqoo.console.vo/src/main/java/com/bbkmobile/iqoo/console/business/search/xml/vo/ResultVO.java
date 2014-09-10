/**
 * 
 */
package com.bbkmobile.iqoo.console.business.search.xml.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangbo
 * 
 */
public class ResultVO {

    private int pn;
    private int rn;
    private int disp_num;
    private int ret_num;
    private String title;
    private List<AppVO> apps = new ArrayList<AppVO>();

    public int getPn() {
        return pn;
    }

    public void setPn(int pn) {
        this.pn = pn;
    }

    public int getRn() {
        return rn;
    }

    public void setRn(int rn) {
        this.rn = rn;
    }

    public int getDisp_num() {
        return disp_num;
    }

    public void setDisp_num(int dispNum) {
        disp_num = dispNum;
    }

    public int getRet_num() {
        return ret_num;
    }

    public void setRet_num(int retNum) {
        ret_num = retNum;
    }

    public List<AppVO> getApps() {
        return apps;
    }

    public void setApps(List<AppVO> apps) {
        this.apps = apps;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
