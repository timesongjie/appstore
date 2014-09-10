package com.bbkmobile.iqoo.interfaces.common.cache;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.bbkmobile.iqoo.cache.CacheManager;
import com.bbkmobile.iqoo.cache.CacheManagerAware;
import com.bbkmobile.iqoo.cache.ehcache.EhcachCacheManager;
import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.interfaces.appinfo.business.SystemPackageService;
import com.bbkmobile.iqoo.platform.base.BaseAction;

@Component
public class CacheMonitor extends BaseAction implements
        ApplicationContextAware, CacheManagerAware {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private ApplicationContext applicationContext;
    @Resource
    private CacheManager cacheManager;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    // 查看ehcache的使用情况
    public String printEhcache() {

        String password = null;
        String xml_data = null;
        String ehcacheName = null;
        String isPrintKey = null;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            password = request.getParameter("password");
            ehcacheName = request.getParameter("cachename");
            isPrintKey = request.getParameter("printkey");
            if ("iqoo".equals(password)) {
                if (null != ehcacheName && !ehcacheName.equals("")) {
                    if (cacheManager != null
                            && cacheManager instanceof EhcachCacheManager) {
                        net.sf.ehcache.CacheManager manager = ((EhcachCacheManager) cacheManager)
                                .getInstance();
                        // xml_data = new
                        // EhcacheUtil(manager).getEhCacheXml(ehcacheName,
                        // isPrintKey); TODO
                        outwrite(xml_data, "text/xml;charset=utf-8");
                    }
                } else {
                    xml_data = "password is error";
                    outwrite(xml_data, "text/plain;charset=utf-8");
                }

            } else {
                xml_data = "please input ehcacheName";
                outwrite(xml_data, "text/plain;charset=utf-8");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Lg.error(LgType.ACTIVITYINFO, "获取ehcacheh缓存信息出错" + e.getMessage());
        }
        return null;
    }

    @Override
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void refreshSystemPackageCache() {
        if (applicationContext != null) {
            SystemPackageService systemPackageService = applicationContext
                    .getBean("systemPackageService", SystemPackageService.class);
            if (systemPackageService instanceof CacheManagerAware) {
                systemPackageService.refresh();
            }
        }
    }

    @Override
    public void preLoad() {

    }

    @Override
    public void refresh() {

    }
}
