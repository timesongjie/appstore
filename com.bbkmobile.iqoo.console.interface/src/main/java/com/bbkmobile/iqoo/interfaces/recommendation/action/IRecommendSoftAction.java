package com.bbkmobile.iqoo.interfaces.recommendation.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bbkmobile.iqoo.common.json.JackSonParser;
import com.bbkmobile.iqoo.common.json.NullResultObject;
import com.bbkmobile.iqoo.common.json.ResultObject;
import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.util.RequestUtil;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.recommendation.RecommendApp;
import com.bbkmobile.iqoo.console.dao.recommendation.RecommendGame;
import com.bbkmobile.iqoo.interfaces.appinfo.business.AppInfoService;
import com.bbkmobile.iqoo.interfaces.recommendation.business.RecommendSoftService;
import com.bbkmobile.iqoo.platform.base.BaseAction;

/**
 * 手机端接口Action
 * 
 * @author time
 * 
 */
@Component("iRecommendSoftAction")
@Scope("prototype")
public class IRecommendSoftAction extends BaseAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Resource(name = "iRecommendSoftService")
    private RecommendSoftService recommendSoftService;
    @Resource(name = "iAppInfoService")
    private AppInfoService appInfoService;

    private ResultObject<List<CommonResultAppInfo>> value = new ResultObject<List<CommonResultAppInfo>>();

    public void getRecommendApp() throws Exception {
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

            PageVO page = new PageVO();
            if (Integer.parseInt(page_index) <= 0) {
                page.setCurrentPageNum(1);
            } else {
                page.setCurrentPageNum(Integer.parseInt(page_index));
            }
            page.setNumPerPage(Integer.valueOf(apps_per_page));

            List<CommonResultAppInfo> apps = null;
            RecommendApp recommendApp = new RecommendApp();
            if (StringUtils.isNotBlank(model)) { // 机型过滤
                recommendApp.getAppInfo().setFilter_model(model);
                apps = recommendSoftService.getRecommendAppsForInterface(page,
                        recommendApp, app_version);
            } else {
                apps = recommendSoftService.getRecommendAppsForInterface(page,
                        recommendApp, app_version);
            }
            value.setResult(true);
            if (apps != null) {
                if (apps.size() == page.getNumPerPage() + 1) {
                    apps.remove(apps.size() - 1);// 移除多查询出的记录
                    value.setMaxPage(page.getCurrentPageNum() + 1);
                    value.setTotalCount(apps.size());
                } else {
                    value.setMaxPage(page.getCurrentPageNum());
                    value.setTotalCount(apps.size());
                }
            }
            value.setPageNo(page.getCurrentPageNum());
            value.setPageSize(page.getNumPerPage());
            value.setValue(apps);
            outwrite(JackSonParser.bean2Json(value), "text/plain;charset=utf8");
            if (Constants.SAVE_BROWSE_LOG) {
                String cfrom = StringUtils.defaultIfEmpty(
                        request.getParameter("cfrom"), "35");
                String ip = RequestUtil.getClientIP(request);
                RequestParameter requestParameter = new RequestParameter();
                requestParameter.setImei(imei);
                requestParameter.setModel(model);
                requestParameter.setIp(ip);
                requestParameter.setCfrom(Short.valueOf(cfrom));
                // requestParameter.setIdStr(idStr);
                requestParameter.setPageIndexStr(page_index);
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
            outwrite(NullResultObject.print(), "text/xml;charset=utf-8");
        }
    }

    public void getRecommendGame() throws Exception {
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

            PageVO page = new PageVO();
            page.setCurrentPageNum(Integer.parseInt(page_index));
            page.setNumPerPage(Integer.valueOf(apps_per_page));

            List<CommonResultAppInfo> apps = null;
            RecommendGame recommendApp = new RecommendGame();
            if (StringUtils.isNotBlank(model)) { // 机型过滤
                recommendApp.getAppInfo().setFilter_model(model);
                apps = recommendSoftService.getRecommendGamesForInterface(page,
                        recommendApp, app_version);
            } else {
                apps = recommendSoftService.getRecommendGamesForInterface(page,
                        recommendApp, app_version);
            }
            value.setResult(true);
            if (apps != null) {
                if (apps.size() == page.getNumPerPage() + 1) {
                    apps.remove(apps.size() - 1);// 移除多查询出的记录
                    value.setMaxPage(page.getCurrentPageNum() + 1);
                    value.setTotalCount(apps.size());
                } else {
                    value.setMaxPage(page.getCurrentPageNum());
                    value.setTotalCount(apps.size());
                }
            }
            value.setPageNo(page.getCurrentPageNum());
            value.setPageSize(page.getNumPerPage());
            value.setValue(apps);
            outwrite(JackSonParser.bean2Json(value), "text/plain;charset=utf8");
            if (Constants.SAVE_BROWSE_LOG) {
                String cfrom = StringUtils.defaultIfEmpty(
                        request.getParameter("cfrom"), "36");
                String ip = RequestUtil.getClientIP(request);
                RequestParameter requestParameter = new RequestParameter();
                requestParameter.setImei(imei);
                requestParameter.setModel(model);
                requestParameter.setIp(ip);
                requestParameter.setCfrom(Short.valueOf(cfrom));
                // requestParameter.setIdStr(idStr);
                requestParameter.setPageIndexStr(page_index);
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
            outwrite(NullResultObject.print(), "text/xml;charset=utf-8");
        }
    }

    public RecommendSoftService getRecommendSoftService() {
        return recommendSoftService;
    }

    public void setRecommendSoftService(
            RecommendSoftService recommendSoftService) {
        this.recommendSoftService = recommendSoftService;
    }

    public ResultObject<List<CommonResultAppInfo>> getValue() {
        return value;
    }

    public void setValue(ResultObject<List<CommonResultAppInfo>> value) {
        this.value = value;
    }

}
