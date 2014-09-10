package com.bbkmobile.iqoo.interfaces.focus.action;

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
import com.bbkmobile.iqoo.interfaces.focus.business.FocusPicService;
import com.bbkmobile.iqoo.platform.base.BaseAction;

@Component("iFocusPicAction")
@Scope("prototype")
public class FocusPicAction extends BaseAction {
    private static final long serialVersionUID = 5522271437484225134L;
    @Resource(name = "iFocusPicService")
    private FocusPicService focusPicService;
    @Resource(name = "iAppInfoService")
    private AppInfoService appInfoService;
    @Resource
    private CacheManager cacheManager;

    // begin:接口

    public String focuspics() throws Exception {
        String model = null;
        String cs = null;
        String version = null;
        // String focusPictureId = null;
        // String focusType = null;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();

            model = request.getParameter("model");
            cs = StringUtils.defaultIfEmpty(request.getParameter("cs"), "0");
            String imei = request.getParameter("imei");
            version = request.getParameter("app_version");

            // focusPictureId = request.getParameter("id");
            // focusType = request.getParameter("type");

            RequestParameter requestParameter = new RequestParameter();

            // if(null!=focusPictureId && !focusPictureId.equals("")){
            // requestParameter.setId(Integer.parseInt(focusPictureId));
            // requestParameter.setType(focusType);
            // }

            requestParameter.setModel(model);
            if (null != cs && StringUtils.isNumeric(cs)) {
                requestParameter.setCs(Integer.parseInt(cs));
            }
            requestParameter.setCsStr(cs);
            requestParameter.setImei(imei);

            String ip = RequestUtil.getClientIP(request);
            requestParameter.setIp(ip);
            requestParameter.setCfrom(Constants.FROM_FOCUS_PICTURE);
            // requestParameter.setIdStr("1");
            requestParameter
                    .setElapsedtime(request.getParameter("elapsedtime"));
            if (null != version && version.trim().length() > 0
                    && StringUtils.isNumeric(version)) {
                requestParameter.setApp_version(Float.parseFloat(version));
            }
            String data = null;
            String cacheName = "focusPic";
            String key = null;
            if (Constants.USE_EHCACHE && cacheManager != null) {
                try {
                    key = CacheKeyGenerator.key(cacheName,
                            CacheKeyGenerator.deadlineVersion(requestParameter
                                    .getApp_version()), model);
                    data = (String) cacheManager.getCache(cacheName).get(key);
                } catch (Exception e) {
                    Lg.error(LgType.STDOUT, "获取焦点图缓存信息异常");
                }
            }
            if (data == null) {
                data = focusPicService.getFocusPictures(requestParameter);
                if (Constants.USE_EHCACHE && cacheManager != null
                        && data != null) {
                    try {
                        data = (String) cacheManager.getCache(cacheName).put(
                                key, data);
                    } catch (Exception e) {
                        Lg.error(LgType.STDOUT, "保存焦点图缓存信息异常");
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
            // e.printStackTrace();
            Lg.error(
                    LgType.STDOUT,
                    "手机获取焦点图时出错，" + ",model=" + model + ",cs=" + cs
                            + ",appVersion=" + version + ",error="
                            + e.getMessage());
            outwrite(NullResultObject.print(), "text/plain;charset=utf-8");
        }
        return null;
    }
    // end:接口
}
