package com.bbkmobile.iqoo.interfaces.topic.vo;

import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;

public class TopicResultAppInfo extends CommonResultAppInfo {

    private String introduction;
    private String remark;
    private String topic_name;
    private String image_url;

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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
}
