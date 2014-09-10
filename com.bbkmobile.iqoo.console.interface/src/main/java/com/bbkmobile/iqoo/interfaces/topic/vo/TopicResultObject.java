package com.bbkmobile.iqoo.interfaces.topic.vo;

import java.util.List;

import com.bbkmobile.iqoo.common.json.ResultObject;

public class TopicResultObject extends ResultObject<List<TopicResultAppInfo>> {

    private String topic_name;
    private String image_url;
    private String introduction;

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

}
