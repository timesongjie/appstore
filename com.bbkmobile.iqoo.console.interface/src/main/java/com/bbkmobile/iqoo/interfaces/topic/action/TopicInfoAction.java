package com.bbkmobile.iqoo.interfaces.topic.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bbkmobile.iqoo.cache.CacheKeyGenerator;
import com.bbkmobile.iqoo.cache.CacheManager;
import com.bbkmobile.iqoo.common.json.NullResultObject;
import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.common.util.RequestUtil;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.interfaces.appinfo.business.AppInfoService;
import com.bbkmobile.iqoo.interfaces.topic.business.TopicInfoService;
import com.bbkmobile.iqoo.platform.base.BaseAction;

@SuppressWarnings("serial")
@Component("iTopicInfoAction")
@Scope("prototype")
public class TopicInfoAction extends BaseAction {
    @Resource(name = "iTopicInfoService")
    private TopicInfoService topicInfoService;
    @Resource(name = "iAppInfoService")
    private AppInfoService appInfoService;

    @Resource(name = "cacheManager")
    private CacheManager cacheManager;

    // begin: 手机接口@haiyan
    public String topics() throws Exception {
        String model = null;
        String cs = null;
        String version = null;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();

            model = request.getParameter("model");
            cs = request.getParameter("cs");
            String imei = request.getParameter("imei");

            version = request.getParameter("app_version");

            RequestParameter requestParameter = new RequestParameter();

            requestParameter.setModel(model);
            if (null != cs && !"".equals(cs)) {
                requestParameter.setCs(Integer.parseInt(cs));
            }
            requestParameter.setCsStr(cs);
            requestParameter.setImei(imei);

            String ip = RequestUtil.getClientIP(request);
            requestParameter.setIp(ip);
            requestParameter.setCfrom(Constants.FROM_TOPIC);
            requestParameter.setIdStr("1");
            requestParameter
                    .setElapsedtime(request.getParameter("elapsedtime"));
            if (null != version && !version.trim().equals("")
                    && StringUtils.isNumeric(version)) {
                requestParameter.setApp_version(Float.parseFloat(version));
            }

            String cacheName = "topicInfo";
            String data = null;
            String key = null;
            if (Constants.USE_EHCACHE && cacheManager != null) {
                try {
                    key = CacheKeyGenerator.key(cacheName, version, cs, model);
                    data = (String) cacheManager.getCache(cacheName).get(key);
                } catch (Exception e) {
                    Lg.error(LgType.STDOUT, "获取专题列表缓存信息异常");
                }
            }
            if (data == null || data.trim().length() == 0) {
                if (requestParameter.getApp_version() >= Constants.APPVERSION530) {
                    data = topicInfoService
                            .getTopicListForJson(requestParameter);
                } else {
                    data = topicInfoService
                            .getTopicListForXml(requestParameter);
                }
                if (Constants.USE_EHCACHE && cacheManager != null
                        && data != null && data.trim().length() > 0) {
                    try {
                        cacheManager.getCache(cacheName).put(key, data);
                    } catch (Exception e) {
                        Lg.error(LgType.STDOUT, "保存专题列表缓存信息异常");
                    }
                }
            }
            if (requestParameter.getApp_version() >= Constants.APPVERSION530) {
                outwrite(data, "text/plain;charset=utf-8");
            } else {
                outwrite(data, "text/xml;charset=utf-8");
            }
            if (Constants.SAVE_BROWSE_LOG) {
                appInfoService.saveBrowseLog(requestParameter);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Lg.error(LgType.STDOUT, "手机获取手机专题出错，" + ",model=" + model + ",cs="
                    + cs + ",appVersion=" + version + "error=" + e.getMessage());
            outwrite(NullResultObject.print(), "text/plain;charset=utf-8");
        }
        return null;
    }

    public String topics_soft() throws Exception {
        String topic_id = null;
        String model = null;
        String cs = null;
        String version = null;
        String cfrom = null;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            topic_id = request.getParameter("id");
            String apps_per_page = request.getParameter("apps_per_page");
            String page_index = request.getParameter("page_index");
            String order_type = request.getParameter("order_type");

            model = request.getParameter("model");
            cs = request.getParameter("cs");
            String imei = request.getParameter("imei");
            version = request.getParameter("app_version");
            cfrom = request.getParameter("cfrom");

            RequestParameter requestParameter = new RequestParameter();

            String content_complete = request.getParameter("content_complete");
            // add by time
            requestParameter.setContent_complete(content_complete);

            if (null != topic_id && !"".equals(topic_id.trim())) {
                requestParameter.setId(Integer.parseInt(topic_id));
            }
            if (null != order_type && !"".equals(order_type)) {
                requestParameter.setOrder_type(Integer.parseInt(order_type));
            }
            if (null != apps_per_page && !"".equals(apps_per_page)) {
                requestParameter.setApps_per_page(Integer
                        .parseInt(apps_per_page));
            }
            if (null != page_index && !"".equals(page_index)) {
                requestParameter.setPage_index(Integer.parseInt(page_index));
            }
            requestParameter.setModel(model);
            if (null != cs && !"".equals(cs)) {
                requestParameter.setCs(Integer.parseInt(cs));
            } else {
                requestParameter.setCs(0);
            }
            requestParameter.setImei(imei);
            requestParameter.setVersion(version);

            String ip = RequestUtil.getClientIP(request);
            requestParameter.setIp(ip);
            requestParameter.setCfrom(Constants.FROM_TOPIC);
            requestParameter.setIdStr(topic_id);
            requestParameter.setPageIndexStr(page_index);
            requestParameter.setCsStr(cs);
            requestParameter
                    .setElapsedtime(request.getParameter("elapsedtime"));
            if (null != version && !version.equals("")) {
                requestParameter.setApp_version(Float.parseFloat(version));
            }

            String data = null;
            if (requestParameter.getApp_version() >= Constants.APPVERSION530) {
                data = topicInfoService
                        .getTopicAppListForJson(requestParameter);
                outwrite(data, "text/plain;charset=utf-8");
            } else {
                data = topicInfoService.getTopicAppListForXml(requestParameter);
                outwrite(data, "text/xml;charset=utf-8");
            }
            if (cfrom != null && !cfrom.equals("")) {
                short cfromShort = Short.parseShort(cfrom);
                requestParameter.setCfrom(cfromShort);
            }
            if (Constants.SAVE_BROWSE_LOG) {
                appInfoService.saveBrowseLog(requestParameter);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Lg.error(LgType.STDOUT, "手机获取手机专题相应的应用出错，topic_id=" + topic_id
                    + ",model=" + model + ",cs=" + cs + ",appVersion="
                    + version + "error=" + e.getMessage());
            outwrite(NullResultObject.print(), "text/plain;charset=utf-8");
        }
        return null;
    }
    // end:手机接口
}
