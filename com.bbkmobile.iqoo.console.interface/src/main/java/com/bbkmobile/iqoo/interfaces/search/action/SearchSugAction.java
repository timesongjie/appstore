/**
 * 
 */
package com.bbkmobile.iqoo.interfaces.search.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bbkmobile.iqoo.common.json.NullResultObject;
import com.bbkmobile.iqoo.common.util.RequestUtil;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.interfaces.appinfo.business.AppInfoService;
import com.bbkmobile.iqoo.interfaces.search.util.SearchSugService;
import com.bbkmobile.iqoo.platform.base.BaseStreamAction;

/**
 * @author wangbo
 * 
 */
@Component("iSearchSugAction")
@Scope("prototype")
public class SearchSugAction extends BaseStreamAction {

    public void searchSug() throws Exception {

        try {
            // String reponse =
            // "<response><statuscode>0</statuscode><statusmessage>done</statusmessage><sugs></sugs></response>";
            // write(reponse, "text/xml;charset=utf8");
            app_version = StringUtils.defaultIfEmpty(app_version, "530");
            if (StringUtils.isNumeric(app_version)) {
                write(getSsServ().searchSugs(getWord(),
                        Float.valueOf(app_version), this.model),
                        "text/plain;charset=utf8");
            }
            // if (Constants.SAVE_SEARCH_KEY_LOG) {
            // HttpServletRequest request = getHttpServletRequest();
            // String cfrom = StringUtils.defaultIfEmpty(
            // request.getParameter("cfrom"), "2");
            // String ip = RequestUtil.getClientIP(request);
            // RequestParameter requestParameter = new RequestParameter();
            // requestParameter.setImei(imei);
            // requestParameter.setModel(model);
            // requestParameter.setIp(ip);
            // requestParameter.setCfrom(Short.valueOf(cfrom));
            // // requestParameter.setIdStr(idStr);
            // requestParameter.setElapsedtime(request
            // .getParameter("elapsedtime"));
            // if (StringUtils.isNotBlank(app_version)
            // && StringUtils.isNumeric(app_version)) {
            // requestParameter.setApp_version(Float
            // .parseFloat(app_version));
            // }
            // requestParameter.setWord(getWord());
            // appInfoService.saveSeachWordLog(requestParameter);
            // }
        } catch (Exception e) {
            log.error(e.toString());
            outwrite(NullResultObject.print(), "text/plain;charset=utf-8");
        }

    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public SearchSugService getSsServ() {
        return ssServ;
    }

    public void setSsServ(SearchSugService ssServ) {
        this.ssServ = ssServ;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    private String imei;// imei码
    private String model;// 机型号
    private String word;// 关键词
    private String app_version;

    @Resource(name = "searchSugService")
    private SearchSugService ssServ;
    @Resource(name = "iAppInfoService")
    private AppInfoService appInfoService;

    private Log log = LogFactory.getLog(SearchSugAction.class);
    /**
	 * 
	 */
    private static final long serialVersionUID = -5879574825366808133L;
}
