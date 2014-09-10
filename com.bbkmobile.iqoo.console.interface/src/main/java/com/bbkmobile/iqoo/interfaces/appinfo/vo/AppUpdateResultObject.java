package com.bbkmobile.iqoo.interfaces.appinfo.vo;

import com.bbkmobile.iqoo.common.json.ResultObject;

public class AppUpdateResultObject<T> extends ResultObject<T> {

    private Integer dbversion;
    private Integer gap;

    public Integer getDbversion() {
        return dbversion;
    }

    public void setDbversion(Integer dbversion) {
        this.dbversion = dbversion;
    }

    public Integer getGap() {
        return gap;
    }

    public void setGap(Integer gap) {
        this.gap = gap;
    }
}
