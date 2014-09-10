/**
 * AppInfoService.java
 * com.bbkmobile.iqoo.console.business.appinfo
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-1-3 		dengkehai
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.bbkmobile.iqoo.interfaces.appinfo.business;

import java.util.Map;

import net.sf.json.JSONObject;

import com.bbkmobile.iqoo.common.vo.AppDetailResultAppInfo;
import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.appinfo.BaiduAppId;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.appinfo.SearchKey;
import com.bbkmobile.iqoo.interfaces.appinfo.vo.AppInfoListVO;

/**
 * ClassName:AppInfoService Function: TODO ADD FUNCTION
 * 
 * @author dengkehai
 * @version
 * @since Ver 1.1
 * @Date 2012-1-3 下午3:23:42
 * 
 */
public interface AppInfoService {
    // begin:手机接口@haiyan
    String getAppInfoForXml(String app_id, String content_complete,
            String need_comment, String model, String cs, String imei,
            String package_name, String search_type, String version)
            throws Exception;

    String getAppInfoListForKeyNameForXml(SearchKey searchKey) throws Exception;

    BaiduAppId getLocalBaiduAppIdById(Long id) throws Exception;

    String getApkFilePath(String id, String appVersion, boolean isFirst,
            String target, String patch, String model) throws Exception;

    String getAppInfoListForUpdateForXml(String packages, String dbversion,
            String model, String cs, String imei, String appVersion, int times)
            throws Exception;

    // String getAppInfoForUpdateForXml(String app_package, String dbversion,
    // String model, String cs, String imei) throws Exception;
    // end:手机接口
    boolean saveDownloadAppLog(RequestParameter requestParameter)
            throws Exception;

    boolean saveDownloadBaiduAppLog(RequestParameter requestParameter)
            throws Exception;

    boolean saveBrowseAppPackageLog(RequestParameter requestParameter)
            throws Exception;

    boolean saveBrowseBaiduAppLog(RequestParameter requestParameter)
            throws Exception;

    boolean saveBrowseAppLog(RequestParameter requestParameter)
            throws Exception;

    boolean savePackagesUpdateLog(RequestParameter requestParameter)
            throws Exception;

    boolean saveBrowseLog(RequestParameter requestParameter) throws Exception;

    public boolean saveSeachWordLog(RequestParameter requestParameter)
            throws Exception;

    AppInfo getAppInfo(RequestParameter requestParameter) throws Exception;

    AppDetailResultAppInfo getImCompleteDetailAppInfo(
            RequestParameter requestParameter) throws Exception;

    public AppInfoListVO getAppInfoListForKey(SearchKey searchKey)
            throws Exception;

    public String getAppInfoListForKeyNameForXml(AppInfoListVO vo,
            Float appVersion) throws Exception;

    public int countAppsByKeyNameForModel(SearchKey searchKey) throws Exception;

    public JSONObject getDownloadApkFileUrl(String path,
            Map<String, String> params) throws Exception;

    public String getAppDownloadPath(String id) throws Exception;

    public boolean savePcDownloadAppLog(RequestParameter requestParameter)
            throws Exception;
}
