package com.bbkmobile.iqoo.console.dao.topic;

import java.util.Date;

public class ModelTopic {

    private Long id;
    private Short model_id;
    private Short series_id = 0;
    private TopicInfo topicInfo;
    private Integer show_order;
    private Short type;
    private Date set_time;
    private String set_admin;
    private Short recommend;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getModel_id() {
        return model_id;
    }

    public void setModel_id(Short modelId) {
        model_id = modelId;
    }

    public Short getSeries_id() {
        return series_id;
    }

    public void setSeries_id(Short seriesId) {
        series_id = seriesId;
    }

    public TopicInfo getTopicInfo() {
        return topicInfo;
    }

    public void setTopicInfo(TopicInfo topicInfo) {
        this.topicInfo = topicInfo;
    }

    public Integer getShow_order() {
        return show_order;
    }

    public void setShow_order(Integer showOrder) {
        show_order = showOrder;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Date getSet_time() {
        return set_time;
    }

    public void setSet_time(Date setTime) {
        set_time = setTime;
    }

    public String getSet_admin() {
        return set_admin;
    }

    public void setSet_admin(String setAdmin) {
        set_admin = setAdmin;
    }

    public Short getRecommend() {
        return recommend;
    }

    public void setRecommend(Short recommend) {
        this.recommend = recommend;
    }

}
