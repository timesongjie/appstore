package com.bbkmobile.iqoo.interfaces.push.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bbkmobile.iqoo.common.json.JackSonParser;
import com.bbkmobile.iqoo.common.json.ResultObject;
import com.bbkmobile.iqoo.common.util.RequestUtil;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.interfaces.appinfo.business.AppInfoService;
import com.bbkmobile.iqoo.interfaces.push.business.IPushService;
import com.bbkmobile.iqoo.interfaces.push.vo.PushActivity;
import com.bbkmobile.iqoo.platform.base.BaseAction;

@Component("iPushAction")
@Scope("prototype")
public class IPushAction extends BaseAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    @Resource
    private IPushService iPushServiceImpl;
    @Resource(name = "iAppInfoService")
    private AppInfoService appInfoService;

    public void pushback() {
        String model = null;
        String appVersion = null;
        String imei = null;
        String ip = null;
        String pid = null;
        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            model = request.getParameter("model");
            appVersion = request.getParameter("app_version");
            imei = request.getParameter("imei");
            ip = RequestUtil.getClientIP(request);
            pid = request.getParameter("pid");

            RequestParameter requestParameter = new RequestParameter();
            requestParameter.setImei(imei);
            requestParameter.setModel(model);
            requestParameter.setIp(ip);
            if (StringUtils.isNotBlank(appVersion)
                    && StringUtils.isNumeric(appVersion)) {
                requestParameter.setApp_version(Float.parseFloat(appVersion));
            }
            PushActivity activity = new PushActivity();
            activity.setPid(pid);

            String newestPID = iPushServiceImpl.getNewestPid();
            if (pid == null || pid.trim().length() == 0
                    || !pid.trim().equals(newestPID)) {
                {
                    activity = iPushServiceImpl.getPushActivity(
                            requestParameter, activity);
                }
            }
            ResultObject<PushActivity> result = new ResultObject<PushActivity>();
            result.setResult(true);
            result.setValue(activity);
            outwrite(JackSonParser.bean2Json(result),
                    "text/plain;charset=utf-8");
            if (Constants.SAVE_BROWSE_LOG) {
                if (request.getParameter("cfrom") != null) {
                    requestParameter.setCfrom(Short.valueOf(request
                            .getParameter("cfrom").trim()));
                }
                requestParameter.setElapsedtime(request
                        .getParameter("elapsedtime"));
                appInfoService.saveBrowseLog(requestParameter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pushFeedback() {
        String model = null;
        String appVersion = null;
        String imei = null;
        String ip = null;
        String pid = null;
        String cs = null;
        String elapsedtime;
        String id = null;
        HttpServletRequest request = ServletActionContext.getRequest();
        model = request.getParameter("model");
        appVersion = request.getParameter("app_version");
        imei = request.getParameter("imei");
        ip = RequestUtil.getClientIP(request);
        pid = request.getParameter("pid");
        id = request.getParameter("id");
        elapsedtime = request.getParameter("elapsedtime");
        cs = request.getParameter("cs");
        try {
            iPushServiceImpl.savePushLogTable(appVersion, model, pid, id,
                    Long.parseLong(elapsedtime), cs, imei, ip);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
