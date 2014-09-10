package com.bbkmobile.iqoo.interfaces.contentmanager.action;

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
import com.bbkmobile.iqoo.interfaces.contentmanager.business.AppRankSetService;
import com.bbkmobile.iqoo.platform.base.BaseAction;

@Component("iAppRankSetAction")
@Scope("prototype")
public class IAppRankSetAction extends BaseAction {

    /**
     * zuoshengdong
     */
    private static final long serialVersionUID = 877179835872409920L;
    @Resource(name = "iAppRankSetServiceImpl")
    private AppRankSetService appRankSetService;
    @Resource(name = "iAppInfoService")
    private AppInfoService appInfoService;
    @Resource
    private CacheManager cacheManager;

    // begin:手机接口@haiyan
    // 获取排行榜中的app列表
    public String packages_top() throws Exception {
        String order_type = null;
        String model = null;
        String cs = null;
        String appVersion = null;
        String page_index = null;
        String req_id = null;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();

            order_type = request.getParameter("type");
            req_id = request.getParameter("req_id");
            String apps_per_page = getHttpServletRequest().getParameter(
                    "apps_per_page");
            page_index = getHttpServletRequest().getParameter("page_index");

            model = request.getParameter("model");
            cs = request.getParameter("cs");
            String imei = request.getParameter("imei");
            appVersion = request.getParameter("app_version");

            String ip = RequestUtil.getClientIP(request);
            RequestParameter requestParameter = new RequestParameter();
            requestParameter.setImei(imei);
            requestParameter.setModel(model);
            requestParameter.setIp(ip);
            requestParameter.setCfrom(Constants.FROM_TOP);
            requestParameter.setPageIndexStr(page_index);
            requestParameter.setCsStr(cs);
            requestParameter
                    .setElapsedtime(request.getParameter("elapsedtime"));
            if (null != appVersion && appVersion.trim().length() > 0) {
                requestParameter.setApp_version(Float.parseFloat(appVersion));
            }
            String data = null;
            String key = null;
            String cacheName = "packages_top";
            if (Constants.USE_EHCACHE && cacheManager != null) {
                try {
                    if ("1".equals(page_index)) {
                        key = CacheKeyGenerator.key(cacheName, order_type,
                                apps_per_page, model, appVersion);
                        data = (String) cacheManager.getCache(cacheName).get(
                                key);
                    }
                } catch (Exception e) {
                    Lg.error(LgType.STDOUT, "获取排行榜缓存信息异常", e);
                }
            }
            if (data == null || data.trim().length() == 0) {
                data = appRankSetService.getAppInfoListForTopForXml(order_type,
                        apps_per_page, page_index, model, cs, imei, appVersion);
                if (Constants.USE_EHCACHE && cacheManager != null
                        && data != null) {
                    try {
                        if ("1".equals(page_index)) {
                            cacheManager.getCache(cacheName).put(key, data);
                        }
                    } catch (Exception e) {
                        Lg.error(LgType.STDOUT, "缓存排行榜缓存信息异常", e);
                    }
                }
            }
            if (StringUtils.isNotBlank(appVersion)
                    && StringUtils.isNumeric(appVersion)
                    && Float.parseFloat(appVersion) >= Constants.APPVERSION530) {
                outwrite(data, "text/plain;charset=utf-8");
            } else {
                outwrite(data, "text/xml;charset=utf-8");
            }
            if (Constants.SAVE_BROWSE_LOG) {
                requestParameter.setIdStr(req_id);
                appInfoService.saveBrowseLog(requestParameter);
            }
        } catch (Exception e) {
            outwrite(NullResultObject.print(), "text/xml;charset=utf-8");
            Lg.error(
                    LgType.STDOUT,
                    "手机获取app排行榜信息时出错：order_type=" + order_type + ",model="
                            + model + ",cs=" + cs + ",appVersion=" + appVersion
                            + ",page_index=" + page_index + ",error="
                            + e.getMessage());

        }
        return null;
    }
    // end:手机接口

}
