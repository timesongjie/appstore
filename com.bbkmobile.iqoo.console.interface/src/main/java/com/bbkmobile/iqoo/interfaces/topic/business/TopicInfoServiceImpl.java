package com.bbkmobile.iqoo.interfaces.topic.business;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.common.json.JackSonParser;
import com.bbkmobile.iqoo.common.json.ResultObject;
import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.UtilTool;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.dao.topic.TopicInfo;
import com.bbkmobile.iqoo.interfaces.appinfo.dao.AppInfoDAO;
import com.bbkmobile.iqoo.interfaces.model.business.ModelInfoService;
import com.bbkmobile.iqoo.interfaces.topic.dao.TopicInfoDAO;
import com.bbkmobile.iqoo.interfaces.topic.vo.TopicBasicInfo;
import com.bbkmobile.iqoo.interfaces.topic.vo.TopicResultAppInfo;
import com.bbkmobile.iqoo.interfaces.topic.vo.TopicResultObject;

@Service("iTopicInfoService")
public class TopicInfoServiceImpl implements TopicInfoService {
    @Resource(name = "iTopicInfoDAO")
    private TopicInfoDAO topicInfoDAO;

    @Resource(name = "iModelInfoService")
    private ModelInfoService modelInfoService;

    @Resource(name = "iAppInfoDAO")
    private AppInfoDAO appInfoDAO;

    // begin:手机接口@haiyan
    public String getTopicListForXml(RequestParameter requestParameter)
            throws Exception {
        StringBuilder sb = new StringBuilder();
        try {

            String model = requestParameter.getModel();
            Model modelClass = modelInfoService.findModelByMdName(model);
            if (null != modelClass) {
                if (null == modelClass.getSeries_id()) {
                    modelClass.setSeries_id((short) 0);
                }
            }
            Long app_id;
            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><TopicList>");
            List<TopicBasicInfo> result = topicInfoDAO.getTopicInfo(modelClass,
                    requestParameter);
            if (null != result && 0 != result.size()) {
                for (TopicBasicInfo topic : result) {
                    sb.append("<Topic><topic_name><![CDATA[");
                    sb.append(topic.getTopic_name());
                    sb.append("]]></topic_name><image_url><![CDATA[");
                    sb.append(UtilTool.getDownloadImageHttpURL(topic
                            .getImage_url()));
                    sb.append("]]></image_url>");
                    if (1 == topic.getApp_count()) {
                        sb.append("<app_id><![CDATA[");
                        app_id = topic.getApp_id().longValue();
                        sb.append(app_id);
                        sb.append("]]></app_id>");
                    } else {
                        sb.append("<topic_list_id><![CDATA[");
                        sb.append(topic.getTopic_list_id().intValue());
                        sb.append("]]></topic_list_id>");
                    }

                    sb.append("<app_count><![CDATA[");
                    sb.append(topic.getApp_count());
                    sb.append("]]></app_count><introduction><![CDATA[");
                    sb.append(topic.getIntroduction());
                    sb.append("]]></introduction><update_time><![CDATA[");
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String releaseDate = dateFormat.format(topic
                            .getUpdate_time());
                    sb.append(releaseDate);
                    sb.append("]]></update_time><recommend><![CDATA[");
                    sb.append(topic.getRecommend());
                    sb.append("]]></recommend></Topic>");
                }
            }
            sb.append("</TopicList>");

        } catch (Exception e) {
            throw e;
        }
        return sb.toString();
    }

    public String getTopicAppListForXml(RequestParameter requestParameter)
            throws Exception {

        StringBuilder sb = new StringBuilder();

        try {
            if (0 == requestParameter.getApps_per_page()) {
                requestParameter.setApps_per_page(Constants.APPS_PER_PAGE_CS);
            }

            if (0 == requestParameter.getPage_index()) {
                requestParameter.setPage_index(1);
            }

            Integer cs = requestParameter.getCs();
            String appVersion = requestParameter.getVersion();

            int apps_per_page_int = requestParameter.getApps_per_page();
            int page_index_int = requestParameter.getPage_index();
            int maxpage = page_index_int;
            int total_count = 0;

            String model = requestParameter.getModel();
            Model modelClass = modelInfoService.findModelByMdName(model);
            // List<TopicApp> topicAppList = null;
            TopicInfo topicInfo = topicInfoDAO
                    .findTopicInfoById((long) requestParameter.getId());
            // int total_count=
            // topicInfoDAO.countTopicAppsByIdWihtFilterModel(topicInfo,requestParameter,modelClass);

            // TopicInfo topicInfo = new TopicInfo();
            // topicInfo.setId((long) requestParameter.getId());
            // TopicInfo topicInfo = topicInfoDAO
            // .getModelTopicById((long) requestParameter.getId());
            // if (topicInfo != null) {
            // topicAppList = topicInfoDAO.getTopicAppsByIdWihtFilterModel(
            // topicInfo, requestParameter, modelClass);
            // }
            List<TopicResultAppInfo> topicAppList = getTopicAppList(requestParameter);
            if (null != topicAppList) {

                int appInfoCount = topicAppList.size();
                if (0 == cs) {
                    if (appInfoCount == (apps_per_page_int + 1)) {
                        maxpage = page_index_int + 1;
                        topicAppList.remove(appInfoCount - 1); // 去掉最后一个
                    }
                    total_count = topicAppList.size(); // 当前页个数
                } else {
                    if (null != appVersion && appVersion.equals("400")
                            && page_index_int > 1) { // 第一页返回total_count为所有app数量
                        if (appInfoCount == (apps_per_page_int + 1)) {
                            maxpage = page_index_int + 1;
                            topicAppList.remove(appInfoCount - 1); // 去掉最后一个
                        }
                        total_count = topicAppList.size(); // 当前页个数
                    } else {
                        total_count = topicInfoDAO
                                .countTopicAppsByIdWihtFilterModel(topicInfo,
                                        requestParameter, modelClass);
                        maxpage = (int) Math.ceil((float) total_count
                                / apps_per_page_int);
                        if (appInfoCount == (apps_per_page_int + 1)) {
                            topicAppList.remove(appInfoCount - 1); // 去掉最后一个
                        }
                    }

                }

                sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><PackageList maxpage=\"");
                sb.append(maxpage);
                sb.append("\" PageNo=\"");
                sb.append(page_index_int);
                sb.append("\" pageSize=\"");
                sb.append(apps_per_page_int);
                sb.append("\" TotalCount=\"");
                sb.append(total_count);
                sb.append("\"");
                if (1 == page_index_int) {
                    sb.append(" introduction=\"");
                    sb.append(topicInfo.getTopic_describe());
                    sb.append("\"");
                }
                sb.append(">");
                for (TopicResultAppInfo topicAppTmp : topicAppList) {
                    sb.append(getXmlForAppInfo(topicAppTmp,
                            requestParameter.getVersion()));
                }
                sb.append("</PackageList>");
            } else {
                sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><PackageList maxpage=\"");
                sb.append(maxpage);
                sb.append("\" PageNo=\"");
                sb.append(page_index_int);
                sb.append("\" pageSize=\"");
                sb.append(apps_per_page_int);
                sb.append("\" TotalCount=\"");
                sb.append(total_count); // 为0
                sb.append("\">");
                sb.append("</PackageList>");
            }
            return sb.toString();
        } catch (Exception e) {
            throw e;
        }

    }

    private Object getXmlForAppInfo(TopicResultAppInfo topicAppTmp,
            String version) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("<Package><id><![CDATA[");
        sb.append(topicAppTmp.getId());
        sb.append("]]></id><tag><![CDATA[");
        sb.append(topicAppTmp.getTag());
        sb.append("]]></tag><package_name><![CDATA[");
        sb.append(topicAppTmp.getPackage_name());
        sb.append("]]></package_name><parent_id><![CDATA[");
        sb.append("");
        sb.append("]]></parent_id><title_zh><![CDATA[");
        sb.append(topicAppTmp.getTitle_zh());
        sb.append("]]></title_zh><title_en><![CDATA[");
        sb.append(topicAppTmp.getTitle_en());
        sb.append("]]></title_en><icon_url><![CDATA[");
        sb.append(topicAppTmp.getIcon_url());// UtilTool.getDownloadImageHttpURL()
        sb.append("]]></icon_url><developer><![CDATA[");
        sb.append(topicAppTmp.getDeveloper());
        sb.append("]]></developer><score><![CDATA[");
        sb.append(topicAppTmp.getScore());
        sb.append("]]></score><raters_count><![CDATA[");
        sb.append(topicAppTmp.getRaters_count());
        sb.append("]]></raters_count><version_name><![CDATA[");
        sb.append(topicAppTmp.getVersion_name());
        sb.append("]]></version_name><version_code><![CDATA[");
        sb.append(topicAppTmp.getVersion_code());
        sb.append("]]></version_code><download_url><![CDATA[");
        sb.append(UtilTool.getHttpURL("/appinfo/downloadApkFile?id="
                + topicAppTmp.getId() + "&app_version=" + version));
        sb.append("]]></download_url><size><![CDATA[");
        sb.append(topicAppTmp.getSize());
        sb.append("]]></size><download_count><![CDATA[");
        sb.append(topicAppTmp.getDownload_url());
        sb.append("]]></download_count><offical><![CDATA[");
        sb.append("0");
        sb.append("]]></offical><patchs><![CDATA[");
        if (null != topicAppTmp.getPatchs()) {
            sb.append(topicAppTmp.getPatchs());
        }
        sb.append("]]></patchs><remark><![CDATA[");
        sb.append(topicAppTmp.getRemark());
        sb.append("]]></remark></Package>");
        return sb.toString();
    }

    // public String getXmlForAppInfo(AppInfo appInfo, String appVersion)
    // throws Exception {
    // StringBuilder sb = new StringBuilder();
    // sb.append("<Package><id><![CDATA[");
    // sb.append(appInfo.getId());
    // sb.append("]]></id><tag><![CDATA[");
    // sb.append(appInfo.getTag());
    // sb.append("]]></tag><package_name><![CDATA[");
    // sb.append(appInfo.getAppPackage());
    // sb.append("]]></package_name><parent_id><![CDATA[");
    // sb.append("");
    // sb.append("]]></parent_id><title_zh><![CDATA[");
    // sb.append(appInfo.getAppCnName());
    // sb.append("]]></title_zh><title_en><![CDATA[");
    // sb.append(appInfo.getAppEnName());
    // sb.append("]]></title_en><icon_url><![CDATA[");
    // sb.append(UtilTool.getDownloadImageHttpURL(appInfo.getAppIcon()));
    // sb.append("]]></icon_url><developer><![CDATA[");
    // sb.append(appInfo.getAppAuthor());
    // sb.append("]]></developer><score><![CDATA[");
    // sb.append(appInfo.getAvgComment());
    // sb.append("]]></score><raters_count><![CDATA[");
    // sb.append(appInfo.getCommentCount());
    // sb.append("]]></raters_count><version_name><![CDATA[");
    // sb.append(appInfo.getAppVersion());
    // sb.append("]]></version_name><version_code><![CDATA[");
    // sb.append(appInfo.getAppVersionCode());
    // sb.append("]]></version_code><download_url><![CDATA[");
    // sb.append(UtilTool.getHttpURL("/appinfo/downloadApkFile?id="
    // + appInfo.getId() + "&app_version=" + appVersion));
    // sb.append("]]></download_url><size><![CDATA[");
    // sb.append(appInfo.getApkSize());
    // sb.append("]]></size><download_count><![CDATA[");
    // sb.append(appInfo.getDownloadCount());
    // sb.append("]]></download_count><offical><![CDATA[");
    // sb.append("0");
    // sb.append("]]></offical><patchs><![CDATA[");
    // if (null != appInfo.getPatchs()) {
    // sb.append(appInfo.getPatchs());
    // }
    // sb.append("]]></patchs><remark><![CDATA[");
    // sb.append(appInfo.getApp_remark());
    // sb.append("]]></remark></Package>");
    // return sb.toString();
    // }

    @Override
    public String getTopicListForJson(RequestParameter requestParameter)
            throws Exception {

        String model = requestParameter.getModel();
        Model modelClass = modelInfoService.findModelByMdName(model);
        if (null != modelClass) {
            if (null == modelClass.getSeries_id()) {
                modelClass.setSeries_id((short) 0);
            }
        }
        List<TopicBasicInfo> result = topicInfoDAO.getTopicInfo(modelClass,
                requestParameter);

        ResultObject<List<TopicBasicInfo>> value = new ResultObject<List<TopicBasicInfo>>();
        value.setResult(true);
        if (result != null && result.size() > 0) {
            for (TopicBasicInfo topic : result) {
                topic.setImage_url(UtilTool.getDownloadImageHttpURL(topic
                        .getImage_url()));
            }
            value.setValue(result);
        } else {
            value.setValue(Collections.EMPTY_LIST);
        }
        return JackSonParser.bean2Json(value);
    }

    // end:手机接口
    @Override
    public String getTopicAppListForJson(RequestParameter requestParameter)
            throws Exception {

        Integer cs = requestParameter.getCs();
        // String appVersion = requestParameter.getVersion();

        int apps_per_page_int = requestParameter.getApps_per_page();
        int page_index_int = requestParameter.getPage_index();
        int maxpage = page_index_int;
        int total_count = 0;

        List<TopicResultAppInfo> topicAppList = getTopicAppList(requestParameter);
        TopicResultObject result = new TopicResultObject();
        result.setResult(true);
        if (null != topicAppList && topicAppList.size() > 0) {
            for (TopicResultAppInfo topic : topicAppList) {
                if ("1".equals(requestParameter.getContent_complete())
                        && result.getImage_url() == null) {
                    result.setImage_url(UtilTool.getDownloadImageHttpURL(topic
                            .getImage_url()));
                    result.setTopic_name(topic.getTopic_name());
                }
                if (result.getIntroduction() == null) {
                    result.setIntroduction(topic.getIntroduction());
                }
                topic.setTopic_name(null);
                topic.setImage_url(null);
                topic.setIntroduction(null);
            }
            int appInfoCount = topicAppList.size();
            if (0 == cs) {
                if (appInfoCount == (apps_per_page_int + 1)) {
                    maxpage = page_index_int + 1;
                    topicAppList.remove(appInfoCount - 1); // 去掉最后一个
                }
                total_count = topicAppList.size(); // 当前页个数
                result.setValue(topicAppList);
            }
        } else {
            result.setValue(Collections.EMPTY_LIST);
        }
        result.setMaxPage(maxpage);
        result.setPageNo(page_index_int);
        result.setPageSize(apps_per_page_int);
        result.setTotalCount(total_count);
        return JackSonParser.bean2Json(result);

    }

    @Override
    public List<TopicResultAppInfo> getTopicAppList(
            RequestParameter requestParameter) throws Exception {
        if (0 == requestParameter.getApps_per_page()) {
            requestParameter.setApps_per_page(Constants.APPS_PER_PAGE_CS);
        }
        if (0 == requestParameter.getPage_index()) {
            requestParameter.setPage_index(1);
        }
        int apps_per_page_int = requestParameter.getApps_per_page();
        int page_index_int = requestParameter.getPage_index();

        String model = requestParameter.getModel();
        Model modelClass = modelInfoService.findModelByMdName(model);

        TopicInfo topic = new TopicInfo();
        topic.setId((long) requestParameter.getId());
        PageVO page = new PageVO();
        page.setCurrentPageNum(page_index_int);
        page.setNumPerPage(apps_per_page_int + 1);
        if ("1".equals(requestParameter.getContent_complete())) {
            topic.setTag("tag");// 需要获取专题/图表信息
        }
        return topicInfoDAO.getTopicAppList(modelClass, topic, page);
    }
}
