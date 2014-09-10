package com.bbkmobile.iqoo.interfaces.apptype.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bbkmobile.iqoo.common.json.JackSonParser;
import com.bbkmobile.iqoo.common.json.ResultObject;
import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.util.RequestUtil;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.apptype.PersonalType;
import com.bbkmobile.iqoo.interfaces.appinfo.business.AppInfoService;
import com.bbkmobile.iqoo.interfaces.apptype.business.PersonalTypeService;
import com.bbkmobile.iqoo.platform.base.BaseAction;

@Component("iPersonalTypeAction")
public class IPersonalTypeAction extends BaseAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    @Resource(name = "iPersonalTypeService")
    private PersonalTypeService personalTypeService;
    @Resource(name = "iAppInfoService")
    private AppInfoService appInfoService;

    private ResultObject<List<CommonResultAppInfo>> value = new ResultObject<List<CommonResultAppInfo>>();

    public void personalTypeApps() throws Exception {
        try {
            HttpServletRequest request = getHttpServletRequest();
            // O
            String page_index = StringUtils.defaultIfEmpty(
                    request.getParameter("page_index"), "1");
            String apps_per_page = StringUtils.defaultIfEmpty(
                    request.getParameter("apps_per_page"), "20");

            String imei = request.getParameter("imei");
            String app_version = request.getParameter("app_version");
            String cs = request.getParameter("cs");
            String model = request.getParameter("model");
            String typeId = request.getParameter("id");
            String order_type = request.getParameter("order_type");
            if (StringUtils.isNotBlank(typeId) && StringUtils.isNumeric(typeId)) {// typeId必须
                PageVO page = new PageVO();
                page.setCurrentPageNum(Integer.parseInt(page_index));
                page.setNumPerPage(Integer.valueOf(apps_per_page));
                List<CommonResultAppInfo> apps = null;

                // PersonalTypeApp personalTypeApp = new PersonalTypeApp();
                // personalTypeApp.getType().setId(Integer.valueOf(typeId));

                if (StringUtils.isNotBlank(model)) { // 机型过滤
                    // personalTypeApp.getAppInfo().setFilter_model(model);
                    apps = personalTypeService.getRecommendAppsForInterface(
                            model, Integer.valueOf(typeId), page, order_type,
                            app_version);
                } else {
                    apps = personalTypeService.getRecommendAppsForInterface(
                            null, Integer.valueOf(typeId), page, order_type,
                            app_version);
                }
                value.setResult(true);
                value.setMaxPage(page.getPageCount());
                value.setTotalCount(page.getRecordCount());
                value.setPageNo(page.getCurrentPageNum());
                value.setPageSize(page.getNumPerPage());
                value.setValue(apps);
            }
            outwrite(JackSonParser.bean2Json(value), "text/plain;charset=utf8");
            if (Constants.SAVE_BROWSE_LOG) {
                String cfrom = StringUtils.defaultIfEmpty(
                        request.getParameter("cfrom"), "37");
                String ip = RequestUtil.getClientIP(request);
                RequestParameter requestParameter = new RequestParameter();
                requestParameter.setImei(imei);
                requestParameter.setModel(model);
                requestParameter.setIp(ip);
                requestParameter.setCfrom(Short.valueOf(cfrom));
                requestParameter.setIdStr(typeId);
                requestParameter.setCsStr(cs);
                requestParameter.setElapsedtime(request
                        .getParameter("elapsedtime"));
                if (StringUtils.isNotBlank(app_version)
                        && StringUtils.isNumeric(app_version)) {
                    requestParameter.setApp_version(Float
                            .parseFloat(app_version));
                }
                appInfoService.saveBrowseLog(requestParameter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void personalType() {
        try {
            HttpServletRequest request = getHttpServletRequest();
            // O
            String imei = request.getParameter("imei");
            String app_version = request.getParameter("app_version");
            String cs = request.getParameter("cs");
            String model = request.getParameter("model");
            String tag = StringUtils.defaultIfEmpty(
                    request.getParameter("tag"), "1"); // 1=应用（默认） ，2=游戏

            PersonalType type = new PersonalType();
            type.setTag(tag.charAt(0));
            List<PersonalType> types = personalTypeService.list(type);
            ResultObject<List<PersonalType>> result = new ResultObject<List<PersonalType>>();
            result.setResult(true);
            result.setValue(types);
            outwrite(JackSonParser.bean2Json(result), "text/plain;charset=utf8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PersonalTypeService getPersonalTypeService() {
        return personalTypeService;
    }

    public void setPersonalTypeService(PersonalTypeService personalTypeService) {
        this.personalTypeService = personalTypeService;
    }

}
