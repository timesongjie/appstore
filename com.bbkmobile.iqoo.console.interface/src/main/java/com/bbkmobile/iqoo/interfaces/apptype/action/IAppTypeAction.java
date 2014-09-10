/**
 * AppTypeAction.java
 * com.bbkmobile.iqoo.console.action.apptype
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2011-12-28 		dengkehai
 *
 * Copyright (c) 2011, TNT All Rights Reserved.
 */

package com.bbkmobile.iqoo.interfaces.apptype.action;

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
import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.util.RequestUtil;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.interfaces.appinfo.business.AppInfoService;
import com.bbkmobile.iqoo.interfaces.apptype.business.AppTypeService;
import com.bbkmobile.iqoo.platform.base.BaseAction;

/**
 * ClassName:AppTypeAction Function:
 * 
 * @author dengkehai
 * @version
 * @since Ver 1.1
 * @Date 2011-12-28 下午5:46:48
 * 
 */
@SuppressWarnings("serial")
@Component("iAppTypeAction")
@Scope("prototype")
public class IAppTypeAction extends BaseAction {

    @Resource(name = "iAppTypeService")
    private AppTypeService appTypeService;
    @Resource(name = "iAppInfoService")
    private AppInfoService appInfoService;
    @Resource
    private CacheManager cacheManager;

    // begin:手机接口@haiyan
    // 获取类型列表
    public String categories() throws Exception {
        String type_id = null;
        String model = null;
        String cs = null;
        String appVersion = null;

        // String cacheName="appstore";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            type_id = request.getParameter("id");

            model = request.getParameter("model");
            cs = request.getParameter("cs");
            String imei = request.getParameter("imei");
            appVersion = request.getParameter("app_version");

            String ip = RequestUtil.getClientIP(request);
            // String tag = request.getParameter("tag");

            RequestParameter requestParameter = new RequestParameter();
            requestParameter.setImei(imei);
            requestParameter.setModel(model);
            requestParameter.setIp(ip);
            requestParameter.setCfrom(Constants.FROM_TYPE);
            requestParameter.setIdStr(type_id);
            // requestParameter.setPageIndexStr(page_index);
            requestParameter.setCsStr(cs);
            requestParameter
                    .setElapsedtime(request.getParameter("elapsedtime"));
            if (null != appVersion && !appVersion.equals("")) {
                requestParameter.setApp_version(Float.parseFloat(appVersion));
            } else {
                appVersion = "510";
                requestParameter.setApp_version(Float.parseFloat(appVersion));
            }

            String key = null;
            String cacheName = "type";
            String data = null;
            if (Constants.USE_EHCACHE) {
                // xml_data由参数type_id(0,55)和appVersion(小于510，大于等于510)决定的 String
                // (,510) [510,530) [530,)
                try {
                    String tAppVersion = requestParameter.getApp_version() >= 530 ? "530"
                            : (requestParameter.getApp_version() >= 510 ? "510"
                                    : "500");
                    key = CacheKeyGenerator.key(cacheName,
                            CacheKeyGenerator.csTStr(cs), tAppVersion, type_id);
                    String value = (String) cacheManager.getCache(cacheName)
                            .get(key);// (String)
                                      // EhcacheUtil.getInstance().get(cacheName,
                                      // key);
                    if (null != value && !value.trim().equals("")) {
                        data = value;
                    }
                } catch (Exception e) {
                    Lg.error(LgType.STDOUT, "获取应用分类缓存信息异常");
                }
            }
            if (StringUtils.isBlank(data)) {
                if (Float.parseFloat(appVersion) >= Constants.APPVERSION530) {// by
                    data = appTypeService.getTypeForJson(type_id, cs);
                    outwrite(data, "text/plain;charset=utf-8");
                } else {
                    data = appTypeService.getTypeListForXml(type_id, model, cs,
                            imei, appVersion);
                    outwrite(data, "text/xml;charset=utf-8");
                }
                if (Constants.USE_EHCACHE && StringUtils.isNotBlank(data)) {
                    try {
                        cacheManager.getCache(cacheName).put(key, data);
                    } catch (Exception e) {
                        Lg.error(LgType.STDOUT, "保存应用分类缓存信息异常");
                    }
                }
            } else {
                if (Float.parseFloat(appVersion) >= Constants.APPVERSION530) {// by
                    outwrite(data, "text/plain;charset=utf-8");
                } else {
                    outwrite(data, "text/xml;charset=utf-8");
                }
            }
            if (Constants.SAVE_BROWSE_LOG) {
                appInfoService.saveBrowseLog(requestParameter);
            }

        } catch (Exception e) {
            outwrite(NullResultObject.print(), "text/plain;charset=utf-8");
            Lg.error(LgType.STDOUT, "手机获取app类别信息时出错，type_id=" + type_id
                    + ",model=" + model + ",cs=" + cs + ",appVersion="
                    + appVersion + ",error=" + e.getMessage());

        }
        return null;
    }

    // 获取对应类型的app列表
    public String packages() throws Exception {
        String id = null;
        String model = null;
        String cs = null;
        String appVersion = null;
        String page_index = null;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();

            id = request.getParameter("id");
            String order_type = request.getParameter("order_type");
            String apps_per_page = request.getParameter("apps_per_page");
            page_index = request.getParameter("page_index");

            String list = request.getParameter("list");

            model = request.getParameter("model");
            cs = request.getParameter("cs");
            String imei = request.getParameter("imei");
            appVersion = request.getParameter("app_version");

            String ip = RequestUtil.getClientIP(request);
            RequestParameter requestParameter = new RequestParameter();
            requestParameter.setImei(imei);
            requestParameter.setModel(model);
            requestParameter.setIp(ip);
            requestParameter.setCfrom(Constants.FROM_TYPE);
            requestParameter.setIdStr(id);
            requestParameter.setPageIndexStr(page_index);
            requestParameter.setCsStr(cs);
            requestParameter
                    .setElapsedtime(request.getParameter("elapsedtime"));
            if (null != appVersion && appVersion.trim().length() > 0) {
                requestParameter.setApp_version(Float.parseFloat(appVersion));
            }
            if (requestParameter.getApp_version() >= Constants.APPVERSION530) {
                requestParameter.setOrder_type(Integer.valueOf(StringUtils
                        .defaultIfEmpty(order_type, "0")));
                handleHighVersion(requestParameter, request);// by time
            } else {
                String xml_data = appTypeService.getAppInfoListForTypeForXml(
                        id, order_type, apps_per_page, page_index, list, model,
                        cs, imei, appVersion);

                outwrite(xml_data, "text/xml;charset=utf-8");
            }
            if (Constants.SAVE_BROWSE_LOG) {
                appInfoService.saveBrowseLog(requestParameter);
            }
        } catch (Exception e) {
            outwrite(NullResultObject.print(), "text/xml;charset=utf-8");
            Lg.error(LgType.STDOUT, "手机获取app类别信息时出错，id=" + id + ",model="
                    + model + ",cs=" + cs + ",appVersion=" + appVersion
                    + ",page_index=" + page_index + ",error=" + e.getMessage());

        }
        return null;
    }

    // end:手机接口
    private void handleHighVersion(RequestParameter requestParameter,
            HttpServletRequest request) throws Exception {
        String list = request.getParameter("list");
        String apps_per_page = request.getParameter("apps_per_page");
        String page_index = request.getParameter("page_index");
        String output = null;

        if (StringUtils.isEmpty(list)) {
            // AppInfo appInfo = new AppInfo();
            // appInfo.setFilter_model(requestParameter.getModel());
            // appInfo.setType(Integer.valueOf(requestParameter.getIdStr()));
            PageVO page = null;
            if (StringUtils.isNotBlank(page_index)
                    && StringUtils.isNotBlank(apps_per_page)) {
                page = new PageVO();
                page.setNumPerPage(Integer.valueOf(apps_per_page));
                page.setCurrentPageNum(Integer.valueOf(page_index));
            }
            output = appTypeService.getAppByTypeForJson(
                    requestParameter.getModel(),
                    Integer.valueOf(requestParameter.getIdStr()), null, page,
                    requestParameter.getOrder_type());
        }
        outwrite(output, "text/plain;charset=utf-8");
    }
}
