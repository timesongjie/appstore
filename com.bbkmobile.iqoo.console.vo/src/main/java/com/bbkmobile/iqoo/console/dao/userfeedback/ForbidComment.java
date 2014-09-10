package com.bbkmobile.iqoo.console.dao.userfeedback;

import java.util.Date;

public class ForbidComment {
    private Integer id;
    private String imei;
    private Short forbid_status;
    private Integer detail_id;
    private Date add_date;
    private ForbidCommentReason forbidCommentSeason = new ForbidCommentReason();

    private Integer startPosition;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImei() {
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Short getForbid_status() {
        return this.forbid_status;
    }

    public void setForbid_status(Short forbid_status) {
        this.forbid_status = forbid_status;
    }

    public Integer getDetail_id() {
        return this.detail_id;
    }

    public void setDetail_id(Integer detail_id) {
        this.detail_id = detail_id;
    }

    public Date getAdd_date() {
        return this.add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public ForbidCommentReason getForbidCommentSeason() {
        return this.forbidCommentSeason;
    }

    public void setForbidCommentSeason(ForbidCommentReason forbidCommentSeason) {
        this.forbidCommentSeason = forbidCommentSeason;
    }

    public Integer getStartPosition() {
        return this.startPosition;
    }

    public void setStartPosition(Integer startPosition) {
        this.startPosition = startPosition;
    }

}
