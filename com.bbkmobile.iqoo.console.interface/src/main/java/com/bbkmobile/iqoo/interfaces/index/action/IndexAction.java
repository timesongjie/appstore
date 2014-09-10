package com.bbkmobile.iqoo.interfaces.index.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bbkmobile.iqoo.cache.CacheKeyGenerator;
import com.bbkmobile.iqoo.cache.CacheManager;
import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.util.RequestUtil;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.interfaces.appinfo.business.AppInfoService;
import com.bbkmobile.iqoo.interfaces.index.business.AmustService;
import com.bbkmobile.iqoo.platform.base.BaseAction;

@Component
@Scope("prototype")
public class IndexAction extends BaseAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    @Resource
    private AmustService amustServiceImpl;
    @Resource(name = "iAppInfoService")
    private AppInfoService appInfoService;
    @Resource
    CacheManager cacheManager;

    public void index() {
        try {
            HttpServletRequest request = getHttpServletRequest();
            // O
            // String page_index = StringUtils.defaultIfEmpty(
            // request.getParameter("page_index"), "1");
            // String apps_per_page = StringUtils.defaultIfEmpty(
            // request.getParameter("apps_per_page"), "20");

            String imei = request.getParameter("imei");
            String app_version = request.getParameter("app_version");
            String model = request.getParameter("model");
            String tag = StringUtils.defaultIfEmpty(
                    request.getParameter("tag"), "1");
            /* String order_type = request.getParameter("order_type"); */

            PageVO page = null;// new PageVO();
            // if (Integer.parseInt(page_index) <= 0) {
            // page.setCurrentPageNum(1);
            // } else {
            // page.setCurrentPageNum(Integer.parseInt(page_index));
            // }
            // page.setNumPerPage(Integer.valueOf(apps_per_page));

            String str = null;
            String cacheName = "amust";
            String key = null;
            if (Constants.USE_EHCACHE && cacheManager != null) {
                try {
                    key = CacheKeyGenerator.key(cacheName, model, tag);
                    str = (String) cacheManager.getCache(cacheName).get(key);
                } catch (Exception e) {
                    Lg.error(LgType.STDOUT, "获取必备信息异常");
                }
            }
            if (str == null) {
                str = amustServiceImpl.getAmustStr(tag.charAt(0), model, page);
                if (Constants.USE_EHCACHE && cacheManager != null
                        && str != null) {
                    try {
                        cacheManager.getCache(cacheName).put(key, str);
                    } catch (Exception e) {
                        Lg.error(LgType.STDOUT, "保存必备信息异常");
                    }
                }
            }
            outwrite(str, "text/plain;charset=utf-8");

            if (Constants.SAVE_BROWSE_LOG) {
                String ip = RequestUtil.getClientIP(request);
                String cs = request.getParameter("cs");
                String cfrom = StringUtils.defaultIfEmpty(
                        request.getParameter("cfrom"), "34");
                RequestParameter requestParameter = new RequestParameter();
                requestParameter.setImei(imei);
                requestParameter.setModel(model);
                requestParameter.setIp(ip);
                requestParameter.setCfrom(Short.valueOf(cfrom));
                requestParameter.setIdStr(tag);
                // requestParameter.setPageIndexStr(page_index);
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
}
