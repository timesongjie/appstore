package com.bbkmobile.iqoo.console.dao.login;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 */

public class BbkAdminLog implements java.io.Serializable {

    // Fields

    /**
	 * 
	 */
    private static final long serialVersionUID = 5219302987058902638L;
    private Integer logId;
    private BbkAdminUser bbkAdminUser = new BbkAdminUser();
    private Timestamp logtime;
    private String logIp;
    private String logContent;
    private String allHttp;

    private Date from_date;
    private Date to_date;

    // Constructors

    /** default constructor */
    public BbkAdminLog() {
    }

    /** full constructor */

    // Property accessors

    public Integer getLogId() {
        return this.logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public BbkAdminUser getBbkAdminUser() {
        return bbkAdminUser;
    }

    public void setBbkAdminUser(BbkAdminUser bbkAdminUser) {
        this.bbkAdminUser = bbkAdminUser;
    }

    public Timestamp getLogtime() {
        return this.logtime;
    }

    public void setLogtime(Timestamp logtime) {
        this.logtime = logtime;
    }

    public String getLogIp() {
        return this.logIp;
    }

    public void setLogIp(String logIp) {
        this.logIp = logIp;
    }

    public String getLogContent() {
        return this.logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public String getAllHttp() {
        return this.allHttp;
    }

    public void setAllHttp(String allHttp) {
        this.allHttp = allHttp;
    }

    public Date getFrom_date() {
        return from_date;
    }

    public void setFrom_date(Date fromDate) {
        from_date = fromDate;
    }

    public Date getTo_date() {
        return to_date;
    }

    public void setTo_date(Date toDate) {
        to_date = toDate;
    }

}