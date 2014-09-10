package com.bbkmobile.iqoo.interfaces.topic.dao;

import java.util.List;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.dao.topic.ModelTopic;
import com.bbkmobile.iqoo.console.dao.topic.TopicApp;
import com.bbkmobile.iqoo.console.dao.topic.TopicInfo;
import com.bbkmobile.iqoo.interfaces.topic.vo.TopicBasicInfo;
import com.bbkmobile.iqoo.interfaces.topic.vo.TopicResultAppInfo;

public interface TopicInfoDAO {

    // 手机接口@haiyan
    List<ModelTopic> getModelTopicsBySeries(Model model) throws Exception;

    List<TopicInfo> getTopicsBySeries(Model model) throws Exception;

    int countTopicAppsByIdWihtFilterModel(TopicInfo topicInfo,
            RequestParameter requestParameter, Model model) throws Exception;

    List<TopicApp> getTopicAppsByIdWihtFilterModel(TopicInfo topicInfo,
            RequestParameter requestParameter, Model model) throws Exception;

    TopicInfo getModelTopicById(long id) throws Exception;

    String getTopicImgUrlByIdWithScreen(Long topicId, String cs)
            throws Exception;

    // end

    List<TopicBasicInfo> getTopicInfo(Model modelClass,
            RequestParameter requestParameter) throws Exception;

    List<TopicResultAppInfo> getTopicAppList(Model model, TopicInfo topic,
            PageVO page) throws Exception;

    TopicInfo findTopicInfoById(Long id) throws Exception;

}
