/**
 * AppInfoDAO.java
 * com.bbkmobile.iqoo.console.dao.appinfo
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-1-3 		dengkehai
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.bbkmobile.iqoo.interfaces.appinfo.dao;

import java.util.List;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.appinfo.BaiduAppId;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.appinfo.SearchKey;
import com.bbkmobile.iqoo.console.dao.appinfo.TAppScreenshot;
import com.bbkmobile.iqoo.console.dao.apptype.AppType;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.interfaces.appinfo.vo.SearchResultForm;

/**
 * ClassName:AppInfoDAO Function: TODO ADD FUNCTION
 * 
 * @author dengkehai
 * @version
 * @since Ver 1.1
 * @Date 2012-1-3 下午3:24:59
 * 
 */
public interface AppInfoDAO {

    AppInfo findOnSaleAppByPackage(String appPackage) throws Exception;

    AppInfo findValidAppById(Long app_id) throws Exception;

    AppInfo findAppById(Long app_id) throws Exception;

    List<TAppScreenshot> findAppScreenshot(AppInfo appInfo) throws Exception;

    List<AppInfo> getAppsByKeyNameForModel(final SearchKey searchKey,
            final Model modelClass) throws Exception;

    String getRemarkByAppId(Long id) throws Exception;

    public void updateDownloadCountForAppInfo(AppInfo appInfo) throws Exception;

    BaiduAppId getLocalBaiduAppIdById(Long id) throws Exception;

    int getModuleDBVersion(String module, int module_id) throws Exception;

    List<AppInfo> getAppInfoListForUpdate(String packages, Model model)
            throws Exception;

    // AppInfo getAppInfoForUpdate(String app_package, Model model)
    // throws Exception;

    boolean appIsValidForModel(Long app_id, Model model) throws Exception;

    boolean saveBrowseAppLog(RequestParameter requestParameter)
            throws Exception;

    boolean saveSeachWordLog(RequestParameter requestParameter)
            throws Exception;

    boolean saveBrowseLog(RequestParameter requestParameter) throws Exception;

    boolean savePackagesUpdateLog(RequestParameter requestParameter)
            throws Exception;

    boolean saveBrowseBaiduAppLog(RequestParameter requestParameter)
            throws Exception;

    boolean saveBrowseAppPackageLog(RequestParameter requestParameter)
            throws Exception;

    boolean saveDownloadAppLog(RequestParameter requestParameter)
            throws Exception;

    boolean savePcDownloadAppLog(RequestParameter requestParameter)
            throws Exception;

    boolean saveDownloadBaiduAppLog(RequestParameter requestParameter)
            throws Exception;

    List<AppInfo> getAppsByParentTypeIdWithFilterModel(
            List<AppType> appTypesList, int apps_per_page, int page_index,
            int order_type, Short model_id, Integer sdkVersion,
            String drawable_dpi, String CPU_ABI) throws Exception;

    int countAppsByLeafTypeIdWithFilterModel(AppType appType, Model modelClass)
            throws Exception;

    List<AppInfo> getAppsByLeafTypeIdWithFilterModel(AppType appType,
            int apps_per_page, int page_index, int order_type, Short model_id,
            Integer sdkVersion, String drawable_dpi, String CPU_ABI)
            throws Exception;

    int countAppsByParentTypeIdWithFilterModel(List<AppType> appTypesList,
            int apps_per_page, int page_index, int order_type, Short model_id,
            Integer sdkVersion, String drawable_dpi, String CPU_ABI)
            throws Exception;

    // baidu
    BaiduAppId getLocalBaiduAppId(BaiduAppId baiduAppId) throws Exception;

    boolean saveBaiduAppUrl(Long id, String url) throws Exception;

    AppInfo getAppInfoByPackageName(SearchResultForm requestParas)
            throws Exception;

    BaiduAppId saveBaiduAppId(BaiduAppId baiduAppId) throws Exception;

    // common
    List<AppInfo> getJoinApps(final Class className,
            final RequestParameter requestParameter, final Model model)
            throws Exception;

    AppInfo findOnSaleApp(RequestParameter requestParameter) throws Exception;

    /**
     * 按需查询，对应应用列表
     * 
     * @param appTypesList
     *            当对应多个应用类型，否则类型设置到参数appInfo中，该入参为null
     * @param appInfo
     * @param page
     * @param model
     * @return
     * @throws Exception
     */
    List<CommonResultAppInfo> getAppInfoList(List<String> appTypeIdsList,
            int typeId, PageVO page, Model model, Integer order_type)
            throws Exception;

    int countAppsByKeyNameForModel(final SearchKey searchKey,
            final Model modelClass) throws Exception;

    AppInfo findAppByPackage(String appPackage) throws Exception;

    List<AppInfo> getAppsByLeafType(AppType appType, PageVO page,
            Model modelClass) throws Exception;

    AppInfo getApppInfoWithPackage(String appPackage) throws Exception;

    List<AppInfo> getTopAppsWithFilterModel(int order_type, int apps_per_page,
            int page_index, Short model_id, Integer sdkVersion,
            String drawable_dpi, String CPU_ABI) throws Exception;

    boolean saveAppInfoForSaveComment(AppInfo appInfo, Float score)
            throws Exception;

    // 获取指定列
    AppInfo getAppInfoById(Long app_id) throws Exception;

    boolean packageIsExist(String appPackage) throws Exception;

    List<AppInfo> getAppsOrder(AppType appType, PageVO page, Model modelClass)
            throws Exception;

    boolean saveLogs(List<String> logs) throws Exception;

    // ///////////
    // boolean packageIsExist(String appPackage) throws Exception;
}
