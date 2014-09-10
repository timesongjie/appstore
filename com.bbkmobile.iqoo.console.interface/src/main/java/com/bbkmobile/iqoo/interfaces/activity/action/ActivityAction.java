package com.bbkmobile.iqoo.interfaces.activity.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bbkmobile.iqoo.cache.CacheKeyGenerator;
import com.bbkmobile.iqoo.cache.CacheManager;
import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.common.util.RequestUtil;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.interfaces.activity.business.ActivityService;
import com.bbkmobile.iqoo.interfaces.appinfo.business.AppInfoService;
import com.bbkmobile.iqoo.platform.base.BaseAction;

@Component("iActivityAction")
@Scope("prototype")
public class ActivityAction extends BaseAction {

    private static final long serialVersionUID = -5382124176044273435L;
    @Resource(name = "iActivityService")
    private ActivityService activityService;
    @Resource(name = "iAppInfoService")
    private AppInfoService appInfoService;
    @Resource
    private CacheManager cacheManager;

    // begin: 手机接口@haiyan
    public String activities() throws Exception {
        String model = null;
        String cs = null;
        String version = null;
        String activityId = null;
        String cfrom = null;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();

            model = request.getParameter("model");
            cs = request.getParameter("cs");
            String imei = request.getParameter("imei");
            version = request.getParameter("app_version");
            cfrom = request.getParameter("cfrom");

            activityId = StringUtils.defaultIfEmpty(request.getParameter("id"),
                    "0");// 默认获取列表信息

            RequestParameter requestParameter = new RequestParameter();

            if (null != activityId && activityId.trim().length() > 0
                    && StringUtils.isNumeric(activityId)) {
                requestParameter.setId(Integer.parseInt(activityId));
            }

            requestParameter.setModel(model);
            if (null != cs && cs.trim().length() > 0
                    && StringUtils.isNumeric(cs)) {
                requestParameter.setCs(Integer.parseInt(cs));
            }
            requestParameter.setCsStr(cs);
            requestParameter.setImei(imei);

            String ip = RequestUtil.getClientIP(request);
            requestParameter.setIp(ip);
            // requestParameter.setCfrom(Constants.FROM_ACTIVITY);
            // requestParameter.setIdStr("1");
            requestParameter
                    .setElapsedtime(request.getParameter("elapsedtime"));
            if (null != version && version.trim().length() > 0
                    && StringUtils.isNumeric(version)) {
                requestParameter.setApp_version(Float.parseFloat(version));
            } else {
                requestParameter.setApp_version(400);
            }
            String cacheName = null;
            String key = null;
            String data = null;

            if (Constants.USE_EHCACHE && cacheManager != null) {
                try {
                    if (requestParameter.getId() != 0) {
                        cacheName = "activity";
                        key = CacheKeyGenerator.key(cacheName,
                                CacheKeyGenerator
                                        .deadlineVersion(requestParameter
                                                .getApp_version()),
                                requestParameter.getId());
                    } else {
                        cacheName = "activities";
                        key = CacheKeyGenerator.key(cacheName,
                                CacheKeyGenerator
                                        .deadlineVersion(requestParameter
                                                .getApp_version()), model);
                    }
                    data = (String) cacheManager.getCache(cacheName).get(key);
                } catch (Exception e) {
                    data = null;
                    Lg.error(LgType.STDOUT, "获取活动列表缓存信息异常");
                }
            }
            if (data == null) {
                data = activityService.getActivities(requestParameter);
                if (Constants.USE_EHCACHE && cacheManager != null
                        && data != null) {
                    try {
                        cacheManager.getCache(cacheName).put(key, data);
                    } catch (Exception e) {
                        Lg.error(LgType.STDOUT, "保存活动列表缓存信息异常");
                    }
                }
            }
            if (requestParameter.getApp_version() >= Constants.APPVERSION530) {
                outwrite(data, "text/plain;charset=utf-8");
            } else {
                outwrite(data, "text/xml;charset=utf-8");
            }

            requestParameter.setCfrom(Short.parseShort(cfrom));
            if (null != activityId && !activityId.equals("")) {
                if (Constants.SAVE_BROWSE_LOG) {
                    requestParameter.setIdStr(activityId);
                    appInfoService.saveBrowseLog(requestParameter);
                }
            } else {
                requestParameter.setIdStr("0");
                if (Constants.SAVE_BROWSE_LOG) {
                    appInfoService.saveBrowseLog(requestParameter);
                }
            }

        } catch (Exception e) {
            // e.printStackTrace();
            Lg.error(LgType.STDOUT, "手机获取手机活动出错，" + ",model=" + model
                    + ",activityId=" + activityId + ",cs=" + cs
                    + ",appVersion=" + version + ",error=" + e.getMessage());
        }
        return null;
    }

    // end:手机接口
}
