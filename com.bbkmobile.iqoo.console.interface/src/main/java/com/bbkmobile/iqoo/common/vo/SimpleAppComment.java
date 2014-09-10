package com.bbkmobile.iqoo.common.vo;

import java.util.Date;

/**
 * 
 * @author time 接口提供 应用评论
 */
public class SimpleAppComment {

    private String user_name;
    private Float score;
    private String comment;
    private Date comment_date;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getComment_date() {
        return comment_date;
    }

    public void setComment_date(Date comment_date) {
        this.comment_date = comment_date;
    }

}
