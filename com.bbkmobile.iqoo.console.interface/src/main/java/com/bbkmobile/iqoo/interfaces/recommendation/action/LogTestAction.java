package com.bbkmobile.iqoo.interfaces.recommendation.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bbkmobile.iqoo.common.json.NullResultObject;
import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.common.util.RequestUtil;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.interfaces.appinfo.business.AppInfoService;
import com.bbkmobile.iqoo.platform.base.BaseAction;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class LogTestAction extends BaseAction {
    @Resource(name = "iAppInfoService")
    private AppInfoService appInfoService;

    // begin:手机接口
    // 手机推荐
    public void logTest() throws Exception {
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
            if (Constants.SAVE_BROWSE_LOG) {
                appInfoService.saveBrowseLog(requestParameter);
            }
            outwrite("success", "text/plain;charSet=utf-8");
        } catch (Exception e) {
            outwrite(NullResultObject.print(), "text/plain;charset=utf-8");
            Lg.error(LgType.STDOUT, "手机获取手机推荐记录出错,model=" + model + ",cs=" + cs
                    + ",appVesion=" + appVersion + ",page_index" + page_index
                    + ",error=" + e.getMessage());
        }
    }
    // end:手机接口
}
