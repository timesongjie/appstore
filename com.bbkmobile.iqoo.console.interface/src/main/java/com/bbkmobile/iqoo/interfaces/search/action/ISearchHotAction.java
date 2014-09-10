package com.bbkmobile.iqoo.interfaces.search.action;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bbkmobile.iqoo.cache.CacheManager;
import com.bbkmobile.iqoo.common.json.JackSonParser;
import com.bbkmobile.iqoo.common.json.NullResultObject;
import com.bbkmobile.iqoo.common.json.ResultObject;
import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.common.util.RequestUtil;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.interfaces.appinfo.business.AppInfoService;
import com.bbkmobile.iqoo.interfaces.search.business.AppSearchKeyService;
import com.bbkmobile.iqoo.interfaces.search.business.SearchHotService;
import com.bbkmobile.iqoo.interfaces.search.vo.HotAppsSearch;
import com.bbkmobile.iqoo.platform.base.BaseStreamAction;

@Component("iSearchHotAction")
@Scope("prototype")
public class ISearchHotAction extends BaseStreamAction {

    /**
     * 接口应用搜索关键字Action
     * 
     * @author time 变更：合并SearchHotAction中接口方法 {@link searchHotWords}
     * 
     */
    private static final long serialVersionUID = 1L;
    @Resource(name = "iSearchHotService")
    private SearchHotService searchHotService;
    @Resource(name = "iAppSearchKeyService")
    private AppSearchKeyService appSearchKeyService;
    @Resource(name = "iAppInfoService")
    private AppInfoService appInfoService;

    @Resource
    CacheManager cacheManager;

    private String model;// 机型号

    /* 获取轮播搜索关键字接口 */
    public void searchPopupWords() throws Exception {
        try {
            String words = null;
            String cacheName = "popupWords";
            String key = "word";
            if (Constants.USE_EHCACHE && cacheManager != null) {
                try {
                    words = (String) cacheManager.getCache(cacheName).get(key);
                } catch (Exception e) {
                    words = null;// 保证缓存异常不影响正常流程
                }
            }
            if (words == null || words.trim().length() == 0) {
                words = searchHotService.getPopupWords();
                if (Constants.USE_EHCACHE && cacheManager != null
                        && words != null) {
                    try {
                        cacheManager.getCache(cacheName).put(key, words);
                    } catch (Exception e) {
                    }
                }
            }
            write(words, "text/plain;charset=utf8");
        } catch (Exception e) {
            Lg.error(LgType.STDOUT,
                    "获取搜索popup word时出错" + "，error=" + e.getMessage());
        }
    }

    /* 应用搜索关键字接口 */
    @SuppressWarnings("unchecked")
    public void searchSearchWords() throws Exception {
        try {
            HttpServletRequest request = getHttpServletRequest();
            String dbVersion = request.getParameter("dbversion");
            ResultObject<List<HotAppsSearch>> result = new ResultObject<List<HotAppsSearch>>();

            List<HotAppsSearch> appSearchKeys = appSearchKeyService.list(
                    dbVersion, result);// [[],[]]
            if (appSearchKeys != null && appSearchKeys.size() > 0) {
                result.setValue(appSearchKeys);
            } else {
                result.setValue(Collections.EMPTY_LIST);
            }
            result.setResult(true);
            write(JackSonParser.bean2Json(result), "text/plain;charset=utf8");
            if (Constants.SAVE_BROWSE_LOG) {

                String imei = request.getParameter("imei");
                String app_version = request.getParameter("app_version");
                String model = request.getParameter("model");

                String cfrom = StringUtils.defaultIfEmpty(
                        request.getParameter("cfrom"), "39");
                String ip = RequestUtil.getClientIP(request);
                RequestParameter requestParameter = new RequestParameter();
                requestParameter.setImei(imei);
                requestParameter.setModel(model);
                requestParameter.setIp(ip);
                requestParameter.setCfrom(Short.valueOf(cfrom));
                // requestParameter.setIdStr(idStr);
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
            outwrite(NullResultObject.print(), "text/xml;charset=utf-8");
            Lg.error(LgType.STDOUT,
                    "获取搜索SearchKey时出错" + "，error=" + e.getMessage());
        }
    }

    /* 获取搜索关键字接口 */
    public void appSearchWords() {
        // TODO 应用名称 应用ID icon 整合到搜索关键字
    }

    public void searchHotWords() {
        try {
            String cacheName = "hotWords";
            String key = "word";
            String data = null;
            if (Constants.USE_EHCACHE && cacheManager != null) {
                try {
                    data = (String) cacheManager.getCache(cacheName).get(key);
                } catch (Exception e) {
                    data = null;// 保证缓存异常不影响正常流程
                }
            }
            if (data == null || data.trim().length() == 0) {
                data = searchHotService.getSearchHotWords(model);
                if (Constants.USE_EHCACHE && cacheManager != null
                        && data != null) {
                    try {
                        cacheManager.getCache(cacheName).put(key, data);
                    } catch (Exception e) {
                    }
                }
            }
            write(data, "text/plain;charset=utf8");
        } catch (Exception e) {
            // log.error("异常: ", e);
            Lg.error(LgType.STDOUT, "获取搜索热门词汇时出错:model=" + model + "，error="
                    + e.getMessage());
        }
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
