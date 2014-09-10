package com.bbkmobile.iqoo.interfaces.activity.business;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.common.json.JackSonParser;
import com.bbkmobile.iqoo.common.json.ResultObject;
import com.bbkmobile.iqoo.console.activity.dao.ActivityInfo;
import com.bbkmobile.iqoo.console.activity.dao.ModelActivity;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.UtilTool;
import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.interfaces.activity.dao.ActivityDAO;
import com.bbkmobile.iqoo.interfaces.activity.vo.BasicActivity;
import com.bbkmobile.iqoo.interfaces.activity.vo.DetailActivity;
import com.bbkmobile.iqoo.interfaces.appinfo.dao.AppInfoDAO;
import com.bbkmobile.iqoo.interfaces.model.business.ModelInfoService;

@Service("iActivityService")
public class ActivityServiceImpl implements ActivityService {

    @Resource(name = "iActivityDAO")
    private ActivityDAO activityDAO;

    @Resource(name = "iAppInfoDAO")
    private AppInfoDAO appInfoDAO;

    @Resource(name = "iModelInfoService")
    private ModelInfoService modelInfoService;

    public String getActivities(RequestParameter requestParameter)
            throws Exception {
        String reponse = "";
        try {
            if (0 != requestParameter.getId()) { // 获取详情

                ActivityInfo activityInfo = activityDAO
                        .getModelActivityById(requestParameter.getId());
                if (requestParameter.getApp_version() >= Constants.APPVERSION530) {// high
                                                                                   // version
                    reponse = getModelActivityJson(activityInfo);
                } else {
                    reponse = getModelActivityXml(activityInfo);
                }
            } else { // 获取列表
                String model = requestParameter.getModel();

                Model modelClass = modelInfoService.findModelByMdName(model);
                if (modelClass != null) {
                    modelClass.setSeries_id((short) 0);
                }
                List<ModelActivity> modelActivities = activityDAO
                        .getModelActivities();

                if (requestParameter.getApp_version() >= Constants.APPVERSION530) {// high
                                                                                   // version
                    reponse = getModelActivitiesJson(modelActivities,
                            modelClass);
                } else {
                    reponse = getModelActivitiesXml(modelActivities, modelClass);
                }
            }

        } catch (Exception e) {
            throw e;
        }
        return reponse;
    }

    private String getModelActivitiesJson(List<ModelActivity> modelActivities,
            Model modelClass) throws Exception {

        List<BasicActivity> list = new ArrayList<BasicActivity>();
        if (null != modelActivities && modelActivities.size() > 0) {
            for (ModelActivity modelActivity : modelActivities) {
                if (appInfoDAO.appIsValidForModel(modelActivity.getApp_id(),
                        modelClass)) {
                    BasicActivity basic = new BasicActivity();
                    basic.setAct_id(modelActivity.getAct_id());
                    basic.setAct_name(modelActivity.getName());
                    basic.setApp_id("");
                    basic.setImage_url(UtilTool
                            .getDownloadImageHttpURL(modelActivity.getImg()));
                    list.add(basic);
                }
            }
        }
        ResultObject<List<BasicActivity>> result = new ResultObject<List<BasicActivity>>();
        result.setResult(true);
        result.setValue(list);
        return JackSonParser.bean2Json(result);
    }

    private String getModelActivityJson(ActivityInfo activityInfo)
            throws Exception {
        ResultObject<DetailActivity> result = new ResultObject<DetailActivity>();
        DetailActivity activity = new DetailActivity();
        if (null != activityInfo) {
            activity.setAct_id(activityInfo.getId());
            activity.setAct_name(activityInfo.getName());
            activity.setImage_url(UtilTool.getDownloadImageHttpURL(activityInfo
                    .getImg()));
            activity.setDescription(activityInfo.getDescription());

            AppInfo appInfo = appInfoDAO.getAppInfoById(activityInfo
                    .getApp_id());
            if (appInfo != null) {
                activity.setApp_id(String.valueOf(appInfo.getId()));
                activity.setPatchs(appInfo.getPatchs());
                activity.setDownload_url(UtilTool
                        .getHttpURL("/appinfo/downloadApkFile?id="
                                + appInfo.getId()));
                activity.setSize(appInfo.getApkSize());
                activity.setScore(appInfo.getAvgComment());
                activity.setOffical(appInfo.getOfficial());
            }
        }
        result.setValue(activity);
        result.setResult(true);
        return JackSonParser.bean2Json(result);
    }

    private String getModelActivityXml(ActivityInfo activityInfo)
            throws Exception {
        StringBuilder sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><Activity>");

        if (null != activityInfo) {
            sb.append("<act_id><![CDATA[");
            sb.append(activityInfo.getId());
            sb.append("]]></act_id><act_name><![CDATA[");
            sb.append(activityInfo.getName());
            sb.append("]]></act_name><image_url><![CDATA[");
            sb.append(UtilTool.getDownloadImageHttpURL(activityInfo.getImg()));
            sb.append("]]></image_url><description><![CDATA[");
            sb.append(activityInfo.getDescription());
            sb.append("]]></description>");

            AppInfo appInfo = appInfoDAO.getAppInfoById(activityInfo
                    .getApp_id());
            if (appInfo != null) {
                sb.append(getAppInfoXml(appInfo));
            }
        }
        sb.append("</Activity>");
        return sb.toString();
    }

    private String getAppInfoXml(AppInfo appInfo) throws Exception {
        try {
            StringBuilder sb = new StringBuilder();

            sb.append("<app_id><![CDATA[");
            sb.append(appInfo.getId());
            sb.append("]]></app_id><patchs><![CDATA[");
            if (null != appInfo.getPatchs()) {
                sb.append(appInfo.getPatchs());
            }
            sb.append("]]></patchs><download_url><![CDATA[");
            sb.append(UtilTool.getHttpURL("/appinfo/downloadApkFile?id="
                    + appInfo.getId()));
            sb.append("]]></download_url><size><![CDATA[");
            sb.append(appInfo.getApkSize());
            sb.append("]]></size><score><![CDATA[");
            sb.append(appInfo.getAvgComment());
            sb.append("]]></score><offical><![CDATA[");
            sb.append(appInfo.getOfficial());
            sb.append("]]></offical>");

            return sb.toString();
        } catch (Exception e) {
            throw e;
        }

    }

    private String getModelActivitiesXml(List<ModelActivity> modelActivities,
            Model model) throws Exception {
        StringBuilder sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><ActivityList>");

        if (null != modelActivities && modelActivities.size() > 0) {
            for (ModelActivity modelActivity : modelActivities) {

                if (!appInfoDAO.appIsValidForModel(modelActivity.getApp_id(),
                        model)) {
                    continue;
                }

                sb.append("<Activity><act_id><![CDATA[");
                sb.append(modelActivity.getAct_id());
                sb.append("]]></act_id><act_name><![CDATA[");
                sb.append(modelActivity.getName());
                sb.append("]]></act_name><image_url><![CDATA[");
                sb.append(UtilTool.getDownloadImageHttpURL(modelActivity
                        .getImg()));
                sb.append("]]></image_url><app_id><![CDATA[");

                sb.append("]]></app_id></Activity>");
            }
        }
        sb.append("</ActivityList>");
        return sb.toString();
    }

}
