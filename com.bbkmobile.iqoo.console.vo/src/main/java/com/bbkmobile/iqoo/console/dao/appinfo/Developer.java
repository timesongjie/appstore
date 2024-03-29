package com.bbkmobile.iqoo.console.dao.appinfo;

// Generated 2012-1-3 14:53:46 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bbkmobile.iqoo.console.dao.review.DeveloperReviewRecords;

/**
 * Developer generated by hbm2java
 */
public class Developer implements java.io.Serializable {

    private Integer id;
    private String name;
    private Short isActive;
    private String mail;
    private char type;
    private Date registerTime;
    private Date lastLoginTime;

    private Set<AppInfo> appInfos = new HashSet<AppInfo>(0);
    private Set<DeveloperReviewRecords> developer_review_records = new HashSet<DeveloperReviewRecords>();

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public char getType() {
        return this.type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public Set<AppInfo> getAppInfos() {
        return this.appInfos;
    }

    public void setAppInfos(Set<AppInfo> appInfos) {
        this.appInfos = appInfos;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Set<DeveloperReviewRecords> getDeveloper_review_records() {
        return developer_review_records;
    }

    public void setDeveloper_review_records(
            Set<DeveloperReviewRecords> developerReviewRecords) {
        developer_review_records = developerReviewRecords;
    }

}
