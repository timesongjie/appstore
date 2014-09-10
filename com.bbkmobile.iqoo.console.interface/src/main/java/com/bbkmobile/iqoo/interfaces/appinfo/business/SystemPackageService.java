package com.bbkmobile.iqoo.interfaces.appinfo.business;

import com.bbkmobile.iqoo.cache.CacheManagerAware;

public interface SystemPackageService extends CacheManagerAware {

    public boolean isSystemPackage(String packageName) throws Exception;

    public boolean isFromBlockedSites(String site);
}
