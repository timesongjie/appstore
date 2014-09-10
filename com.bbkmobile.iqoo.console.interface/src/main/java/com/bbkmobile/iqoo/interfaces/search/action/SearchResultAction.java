/**
 * 
 */
package com.bbkmobile.iqoo.interfaces.search.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.common.json.NullResultObject;
import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.common.util.RequestUtil;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.interfaces.appinfo.business.AppInfoService;
import com.bbkmobile.iqoo.interfaces.appinfo.vo.SearchResultForm;
import com.bbkmobile.iqoo.interfaces.search.util.SearchResultService;
import com.bbkmobile.iqoo.platform.base.BaseStreamAction;

/**
 * @author wangbo
 * 
 */
@Service("iSearchResultAction")
@Scope("prototype")
public class SearchResultAction extends BaseStreamAction {

    public void searchResult() throws Exception {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String ip = RequestUtil.getClientIP(request);
            form.setIp(ip);
            if (form.getVersion() >= Constants.APPVERSION530) {
                String data = getSrServ().searchResForHigh(form);
                write(data, "text/plain;charset=utf8");
            } else {
                String data = getSrServ().searchRes(form);
                write(data, "text/xml;charset=utf8");
            }

            if (Constants.SAVE_SEARCH_KEY_LOG && key.length() <= 10) {
                RequestParameter requestParameter = new RequestParameter();
                requestParameter.setImei(imei);
                requestParameter.setModel(model);
                requestParameter.setIp(ip);
                requestParameter.setWord(key);
                // requestParameter.setKey("store");
                requestParameter.setKey(cfrom);
                requestParameter.setCsStr(cs);
                requestParameter.setPage_index(page_index);
                requestParameter.setElapsedtime(elapsedtime);
                if (null != app_version && !app_version.equals("")) {
                    requestParameter.setApp_version(Float
                            .parseFloat(app_version));
                }
                appInfoService.saveSeachWordLog(requestParameter);
            }
        } catch (Exception e) {
            outwrite(NullResultObject.print(), "text/plain;charset=utf-8");
            Lg.error(LgType.STDOUT, "搜索app信息时出错:key=" + key + ",model=" + model
                    + ",cs=" + cs + ",app_version=" + app_version
                    + ",page_index=" + page_index + "，error=" + e.getMessage());
        }
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        form.setImei(imei);
        this.imei = imei;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        form.setModel(model);
        this.model = model;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String word) {
        form.setWord(word);
        this.key = word;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        if (null != app_version && !app_version.equals("")) {
            if (app_version.matches(".*\\..*")) {
                app_version = app_version.replaceAll("\\..*", "");
            }
            form.setVersion(Integer.parseInt(app_version));
        }
        this.app_version = app_version;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        form.setDpi(dpi);
        this.dpi = dpi;
    }

    public int getApps_per_page() {
        return apps_per_page;
    }

    public void setApps_per_page(int recordNum) {
        form.setRecordNum(recordNum);
        this.apps_per_page = recordNum;
    }

    public int getPage_index() {
        return page_index;
    }

    public void setPage_index(int pageNum) {
        form.setPageNum(pageNum);
        this.page_index = pageNum;
    }

    public String getOsversion() {
        return osversion;
    }

    public void setOsversion(String osversion) {
        form.setOsversion(osversion);
        this.osversion = osversion;
    }

    public SearchResultService getSrServ() {
        return srServ;
    }

    public void setSrServ(SearchResultService srServ) {
        this.srServ = srServ;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        form.setCs(cs);
        this.cs = cs;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        form.setTarget(target);
        this.target = target;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AppInfoService getAppInfoService() {
        return this.appInfoService;
    }

    public void setAppInfoService(AppInfoService appInfoService) {
        this.appInfoService = appInfoService;
    }

    public String getElapsedtime() {
        return this.elapsedtime;
    }

    public void setElapsedtime(String elapsedtime) {
        this.elapsedtime = elapsedtime;
    }

    public String getCfrom() {
        return this.cfrom;
    }

    public void setCfrom(String cfrom) {
        this.cfrom = cfrom;
    }

    private String elapsedtime;
    private String imei;// imei码
    private String model;// 机型号
    private String key;// 关键词
    private String app_version;// 通信协议版本
    private String osversion;// OS版本
    private String dpi;// 分辨率
    private int apps_per_page;// 每页记录数
    private int page_index;// 页数
    private String cs; // 请求来源
    private String target; // 请求目标 本地数据 或 百度数据
    private String id; // 请求类别，0表示所有的 分类
    private String cfrom;
    private SearchResultForm form = new SearchResultForm();
    @Resource(name = "searchResultService")
    private SearchResultService srServ;
    @Resource(name = "iAppInfoService")
    private AppInfoService appInfoService;

    /*
     * word 搜索关键词，必须 version 交互协议版本号，>=300 搜索百度内容，必须 osversion 操作系统版本号，非必须 dpi
     * 设备分辨率，非必须 model 设备型号， 必须 imei 设备imei码，必须 recordNum 每页显示记录数 pageNum 请求页码
     * cs 0手机 1 pc target local 本地 baidu 百度
     */

    private Log log = LogFactory.getLog(SearchResultAction.class);
    /**
	 * 
	 */
    private static final long serialVersionUID = -2087790524628526610L;

}
