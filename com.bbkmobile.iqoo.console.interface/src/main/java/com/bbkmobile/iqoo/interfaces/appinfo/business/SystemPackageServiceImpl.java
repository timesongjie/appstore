package com.bbkmobile.iqoo.interfaces.appinfo.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.cache.CacheException;
import com.bbkmobile.iqoo.cache.CacheManager;
import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.interfaces.appinfo.dao.SystemPackageDao;

@Service("systemPackageService")
public class SystemPackageServiceImpl implements SystemPackageService {

    @Resource(name = "systemPackageDao")
    private SystemPackageDao systemPackageDao;
    @Resource
    private CacheManager cacheManager;

    private String cacheName = "systempackage";
    private String key = "systempackage.package";

    @SuppressWarnings("unchecked")
    @Override
    public boolean isSystemPackage(String packageName) throws Exception {

        List<String> packages = null;
        if (cacheManager != null) {
            try {
                packages = (List<String>) cacheManager.getCache(cacheName).get(
                        key);
            } catch (Exception e) {
                Lg.error(LgType.STDOUT, "获取系统包异常");
            }
        }
        if (packages == null) {
            packages = systemPackageDao.getSystemPackage();
            if (cacheManager != null) {
                try {
                    cacheManager.getCache(cacheName).put(key, packages);
                } catch (Exception e) {
                    Lg.error(LgType.STDOUT, "保存系统包异常");
                }
            }
        }
        if (packages != null) {
            return packages.contains(packageName);
        }
        return false;
    }

    @Override
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void preLoad() {
        if (cacheManager != null || systemPackageDao != null) {
            List<String> packages = null;
            try {
                packages = systemPackageDao.getSystemPackage();
                cacheManager.getCache(cacheName).put(key, packages);
            } catch (Exception e) {
                Lg.error(LgType.STDOUT, "系统启动获取系统包异常");
            }
        }
    }

    @Override
    public void refresh() {
        if (cacheManager != null) {
            try {
                cacheManager.getCache(cacheName).removeAll();
            } catch (CacheException e) {
                e.printStackTrace();
            }
            preLoad();
        }
    }

    @Override
    public boolean isFromBlockedSites(String site) {
        return site.matches(Constants.SEARCH_BLOCKED_SITES);
    }

}
