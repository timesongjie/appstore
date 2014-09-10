package com.bbkmobile.iqoo.interfaces.recommendation.action;

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
import com.bbkmobile.iqoo.interfaces.recommendation.business.RecommendationService;
import com.bbkmobile.iqoo.platform.base.BaseAction;

@SuppressWarnings("serial")
@Component("iRcommendationAction")
@Scope("prototype")
public class IRcommendationAction extends BaseAction {
    @Resource(name = "iRecommendationService")
    private RecommendationService recommendationService;
    @Resource(name = "iAppInfoService")
    private AppInfoService appInfoService;
    @Resource
    private CacheManager cacheManager;

    // begin:手机接口
    // 手机推荐
    public String packages_recommend() throws Exception {
        String model = null;
        String cs = null;
        String appVersion = null;
        String page_index = null;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();

            String apps_per_page = StringUtils.defaultIfEmpty(
                    request.getParameter("apps_per_page"), "20");
            page_index = StringUtils.defaultIfEmpty(
                    request.getParameter("page_index"), "1");

            model = request.getParameter("model");
            cs = request.getParameter("cs");

            appVersion = request.getParameter("app_version");

            String imei = request.getParameter("imei");
            String ip = RequestUtil.getClientIP(request);

            RequestParameter requestParameter = new RequestParameter();
            requestParameter.setImei(imei);
            requestParameter.setModel(model);
            requestParameter.setIp(ip);
            requestParameter.setCfrom(Constants.FROM_RECOMMEND);
            // requestParameter.setIdStr(idStr);
            requestParameter.setPageIndexStr(page_index);
            requestParameter.setCsStr(cs);
            requestParameter
                    .setElapsedtime(request.getParameter("elapsedtime"));
            if (StringUtils.isNotBlank(appVersion)
                    && StringUtils.isNumeric(appVersion)) {
                requestParameter.setApp_version(Float.parseFloat(appVersion));
            }

            String data = null;
            String cacheName = "recommend";
            String key = null;
            int page_index_int = 1;
            if (StringUtils.isNumeric(page_index)) {
                page_index_int = Integer.parseInt(page_index);
            }
            if (Constants.USE_EHCACHE && cacheManager != null) {
                try {
                    if (page_index_int == 1) {
                        String temp = requestParameter.getApp_version() >= Constants.APPVERSION530 ? "530"
                                : (requestParameter.getApp_version() == 400 ? "400"
                                        : "500");
                        key = CacheKeyGenerator.key(cacheName,
                                CacheKeyGenerator.csTStr(cs), temp, model,
                                apps_per_page);
                        data = (String) cacheManager.getCache(cacheName).get(
                                key);
                    }
                } catch (Exception e) {
                    Lg.error(LgType.STDOUT, "获取推荐列表缓存信息异常");
                }
            }
            if (data == null || data.trim().length() == 0) {
                data = recommendationService.findCellphoneRecommendAppList(
                        apps_per_page, page_index, model, cs,
                        requestParameter.getApp_version());

                if (Constants.USE_EHCACHE && cacheManager != null
                        && StringUtils.isNotBlank(data)) {
                    try {
                        if (page_index_int == 1) {
                            cacheManager.getCache(cacheName).put(key, data);
                        }
                    } catch (Exception e) {
                        Lg.error(LgType.STDOUT, "保存推荐列表缓存信息异常");
                    }
                }
            }

            // String xml_data =
            // recommendationService.findCellphoneRecommendAppList(apps_per_page,
            // page_index, model, cs, appVersion);
            if (requestParameter.getApp_version() >= Constants.APPVERSION530) {
                outwrite(data, "text/plain;charset=utf-8");
            } else {
                outwrite(data, "text/xml;charset=utf-8");
            }

            if (Constants.SAVE_BROWSE_LOG) {
                appInfoService.saveBrowseLog(requestParameter);
            }
        } catch (Exception e) {
            outwrite(NullResultObject.print(), "text/plain;charset=utf-8");
            Lg.error(LgType.STDOUT, "手机获取手机推荐记录出错,model=" + model + ",cs=" + cs
                    + ",appVesion=" + appVersion + ",page_index" + page_index
                    + ",error=" + e.getMessage());
        }
        return null;
    }

    public String newapps() throws Exception {
        String model = null;
        String cs = null;
        String appVersion = null;
        String page_index = null;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();

            String apps_per_page = StringUtils.defaultIfEmpty(
                    request.getParameter("apps_per_page"), "15");
            page_index = StringUtils.defaultIfEmpty(
                    request.getParameter("page_index"), "1");
            model = request.getParameter("model");
            cs = request.getParameter("cs");
            appVersion = request.getParameter("app_version");

            String imei = request.getParameter("imei");
            String ip = RequestUtil.getClientIP(request);

            RequestParameter requestParameter = new RequestParameter();
            requestParameter.setImei(imei);
            requestParameter.setModel(model);
            requestParameter.setIp(ip);
            requestParameter.setCfrom(Constants.FROM_NEW);
            // requestParameter.setIdStr(idStr);
            requestParameter.setPageIndexStr(page_index);
            requestParameter.setCsStr(cs);
            requestParameter
                    .setElapsedtime(request.getParameter("elapsedtime"));

            requestParameter.setApps_per_page(Integer.parseInt(apps_per_page));
            requestParameter.setPage_index(Integer.parseInt(page_index));

            if (StringUtils.isNotBlank(appVersion)
                    && StringUtils.isNumeric(appVersion)) {
                requestParameter.setApp_version(Float.parseFloat(appVersion));
            }
            String data = null;
            String cacheName = "newapps";
            String key = null;
            if (Constants.USE_EHCACHE && cacheManager != null) {
                try {
                    key = CacheKeyGenerator.key(cacheName, CacheKeyGenerator
                            .csTStr(cs),
                            CacheKeyGenerator.deadlineVersion(requestParameter
                                    .getApp_version()), model, page_index,
                            apps_per_page);
                    data = (String) cacheManager.getCache(cacheName).get(key);
                } catch (Exception e) {
                    Lg.error(LgType.STDOUT, "获取新品速递缓存信息异常");
                }
            }
            if (StringUtils.isBlank(data)) {
                data = recommendationService.getNewApps(requestParameter);
                if (Constants.USE_EHCACHE && cacheManager != null
                        && StringUtils.isNotBlank(data)) {
                    try {
                        cacheManager.getCache(cacheName).put(key, data);
                    } catch (Exception e) {
                        Lg.error(LgType.STDOUT, "保存新品速递缓存信息异常");
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
            outwrite(NullResultObject.print(), "text/xml;charset=utf-8");
            Lg.error(LgType.STDOUT, "终端获取新品速递时出错,model=" + model + ",cs=" + cs
                    + ",appVesion=" + appVersion + ",page_index" + page_index
                    + ",error=" + e.getMessage());
        }
        return null;
    }

    public String installedapps() throws Exception {
        String model = null;
        String cs = null;
        String appVersion = null;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            model = request.getParameter("model");
            cs = request.getParameter("cs");
            appVersion = request.getParameter("app_version");

            String imei = request.getParameter("imei");
            String ip = RequestUtil.getClientIP(request);

            RequestParameter requestParameter = new RequestParameter();
            requestParameter.setImei(imei);
            requestParameter.setModel(model);
            requestParameter.setIp(ip);
            requestParameter.setCfrom(Constants.FROM_INSTALLED);
            requestParameter.setCsStr(cs);
            requestParameter
                    .setElapsedtime(request.getParameter("elapsedtime"));

            if (null != appVersion && !appVersion.equals("")) {
                requestParameter.setApp_version(Float.parseFloat(appVersion));
            }
            // requestParameter.setVersion(appVersion);
            String data = null;
            String cacheName = "installedapps";
            String key = null;
            if (Constants.USE_EHCACHE && cacheManager != null) {
                try {
                    key = CacheKeyGenerator.key(cacheName, CacheKeyGenerator
                            .csTStr(cs),
                            CacheKeyGenerator.deadlineVersion(requestParameter
                                    .getApp_version()), model);
                    data = (String) cacheManager.getCache(cacheName).get(key);
                } catch (Exception e) {
                    Lg.error(LgType.STDOUT, "获取装机必备缓存信息异常");
                }
            }
            if (StringUtils.isBlank(data)) {
                data = recommendationService
                        .getEditorRecommendApps(requestParameter);
                if (Constants.USE_EHCACHE && cacheManager != null
                        && StringUtils.isNotBlank(data)) {
                    try {
                        cacheManager.getCache(cacheName).put(key, data);
                    } catch (Exception e) {
                        Lg.error(LgType.STDOUT, "保存装机必备缓存信息异常");
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
            outwrite(NullResultObject.print(), "text/plain;charset=utf-8");
            Lg.error(LgType.STDOUT, "终端获取装机必备时出错,model=" + model + ",cs=" + cs
                    + ",appVesion=" + appVersion + ",error=" + e.getMessage());
        }
        return null;
    }
    // end:手机接口
}
