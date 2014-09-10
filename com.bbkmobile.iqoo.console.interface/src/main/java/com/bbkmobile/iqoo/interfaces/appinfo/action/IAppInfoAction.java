package com.bbkmobile.iqoo.interfaces.appinfo.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bbkmobile.iqoo.common.json.JackSonParser;
import com.bbkmobile.iqoo.common.json.NullResultObject;
import com.bbkmobile.iqoo.common.json.ResultObject;
import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.common.search.SearchConstants;
import com.bbkmobile.iqoo.common.util.ReflectUtil;
import com.bbkmobile.iqoo.common.util.RequestUtil;
import com.bbkmobile.iqoo.common.util.TokenUtil;
import com.bbkmobile.iqoo.common.vo.AppDetailResultAppInfo;
import com.bbkmobile.iqoo.common.vo.SimpleAppComment;
import com.bbkmobile.iqoo.console.business.search.xml.vo.AppVO;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.console.dao.appinfo.BaiduAppId;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.interfaces.appinfo.business.AppInfoService;
import com.bbkmobile.iqoo.interfaces.search.util.SearchOneService;
import com.bbkmobile.iqoo.platform.base.BaseAction;

@Component("iAppInfoAction")
@Scope("prototype")
public class IAppInfoAction extends BaseAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    @Resource
    private AppInfoService iAppInfoService;
    @Resource(name = "iSearchOneService")
    private SearchOneService searchOneService;
    private String key;

    // begin:手机接口@haiyan
    // 获取app
    public String getPackage() throws Exception {
        String app_id = null;
        String package_name = null;
        String model = null;
        String cs = null;
        String imei = null;
        String cfrom = null;
        String version = null;
        String module_id = null;
        String related = null;

        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            app_id = StringUtils
                    .defaultIfEmpty(request.getParameter("id"), "0").trim();

            model = request.getParameter("model");
            cs = request.getParameter("cs");
            imei = request.getParameter("imei");
            module_id = request.getParameter("module_id");
            related = request.getParameter("related");

            version = request.getParameter("app_version"); // version>300
                                                           // app_down_url不在服务器重定向
            float store_version = 0;
            if (null != version && !version.equals("")) {
                store_version = Float.parseFloat(version);
            }

            String content_complete = request.getParameter("content_complete");
            String need_comment = request.getParameter("need_comment");

            String target = request.getParameter("target");
            package_name = request.getParameter("package_name");
            String search_type = request.getParameter("search_type"); // 2-按包名获取
                                                                      // 否则按id获取

            cfrom = request.getParameter("cfrom");
            String ip = RequestUtil.getClientIP(request);
            RequestParameter requestParameter = new RequestParameter();
            requestParameter.setApp_version(store_version);
            requestParameter.setImei(imei);
            requestParameter.setModel(model);
            requestParameter.setIp(ip);

            requestParameter.setModule_id(module_id);
            if ("1".equals(related)) {
                requestParameter.setRelated(related);
            } else {
                requestParameter.setRelated("-1");
            }

            requestParameter
                    .setElapsedtime(request.getParameter("elapsedtime"));
            // add by time
            requestParameter.setContent_complete(content_complete);
            requestParameter.setNeed_comment(need_comment);
            requestParameter.setSearch_type(search_type);
            requestParameter.setPackages(package_name);

            if (null != cfrom) {
                requestParameter.setCfrom(Short.parseShort(cfrom));
            } else {
                requestParameter.setCfrom((short) 1); // 0代表下载，1代表浏览
            }
            requestParameter.setIdStr(app_id);
            // requestParameter.setPageIndexStr(page_index);
            requestParameter.setCsStr(cs);

            boolean from_baidu = false;
            if (StringUtils.isNotBlank(version)
                    && StringUtils.isNumeric(version)
                    && Float.valueOf(version) >= Constants.APPVERSION530) {
                if (null != search_type && search_type.equals("2")) {
                    if (null != target && "baidu".equals(target)) {
                        handleBaiduSearchForHighVersion(requestParameter);// by
                                                                          // time
                    } else if (null != target && "unknown".equals(target)) {
                        ResultObject<AppDetailResultAppInfo> result = handleLocalSearchForHighVersion(requestParameter);// by
                                                                                                                        // time
                        if (result == null) {
                            handleBaiduSearchForHighVersion(requestParameter);// by
                                                                              // time
                        } else {
                            outwrite(JackSonParser.bean2Json(result),
                                    "text/plain;charset=utf-8");
                        }
                    } else {// 本机获取
                        ResultObject<AppDetailResultAppInfo> result = handleLocalSearchForHighVersion(requestParameter);
                        if (result != null) {
                            outwrite(JackSonParser.bean2Json(result),
                                    "text/plain;charset=utf-8");
                        } else {
                            handleBaiduSearchForHighVersion();
                        }
                    }
                } else {
                    if (null != target && "baidu".equals(target)) {
                        handleBaiduSearchForHighVersion(requestParameter);// by
                                                                          // time
                        requestParameter.setCfrom(Constants.FROM_BAIDU_APP);
                    } else {
                        ResultObject<AppDetailResultAppInfo> result = handleLocalSearchForHighVersion(requestParameter);
                        if (result != null) {
                            outwrite(JackSonParser.bean2Json(result),
                                    "text/plain;charset=utf-8");
                        } else {
                            handleBaiduSearchForHighVersion();
                        }
                    }

                }
            } else {
                String xml_data = "";
                if (null != search_type && search_type.equals("2")) { // 根据包名获取详情
                    if (null != target && "baidu".equals(target)) {
                        xml_data = searchOneService.searchOne(requestParameter);
                    } else if (null != target && "unknown".equals(target)) { // 不明确的第三方，先到本地数据库获取，如果本地没有，则到百度获取
                        xml_data = iAppInfoService.getAppInfoForXml(app_id,
                                content_complete, need_comment, model, cs,
                                imei, package_name, search_type, version);
                        if (xml_data.matches(".*info=\"0\".*")
                                || xml_data.equals("")) {
                            requestParameter
                                    .setContent_complete(content_complete);
                            requestParameter.setPackages(package_name);
                            requestParameter.setSearch_type(search_type);
                            xml_data = searchOneService
                                    .searchOne(requestParameter);
                        }
                    } else {// 本机获取
                        xml_data = iAppInfoService.getAppInfoForXml(app_id,
                                content_complete, need_comment, model, cs,
                                imei, package_name, search_type, version);
                    }

                } else { // 根据id获取详情
                    if (app_id.matches("-.*")) {// 兼容老版本
                        from_baidu = true;
                        app_id = app_id.substring(app_id.indexOf("-") + 1,
                                app_id.length());
                        Long id = Long.parseLong(app_id);
                        BaiduAppId baiduAppIdResult = iAppInfoService
                                .getLocalBaiduAppIdById(id);
                        if (null != baiduAppIdResult) {
                            requestParameter.setIdStr(baiduAppIdResult
                                    .getBaidu_id());
                            xml_data = searchOneService
                                    .searchOne(requestParameter);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><Package info=\"0\">");
                            sb.append("</Package>");
                            xml_data = sb.toString();
                        }
                        requestParameter.setCfrom(Constants.FROM_BAIDU_APP);
                    } else {
                        if (null != target && "baidu".equals(target)) {
                            xml_data = searchOneService
                                    .searchOne(requestParameter);
                            requestParameter.setCfrom(Constants.FROM_BAIDU_APP);
                        } else {
                            xml_data = iAppInfoService.getAppInfoForXml(app_id,
                                    content_complete, need_comment, model, cs,
                                    imei, package_name, search_type, version);
                        }
                    }
                }
                if (StringUtils.isEmpty(xml_data)) {
                    xml_data = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><Package info=\"0\"></Package>";
                }
                outwrite(xml_data, "text/xml;charset=utf-8");
            }

            if (Constants.SAVE_BROWSE_APP_LOG) {
                if (null != search_type && search_type.equals("2")) {
                    iAppInfoService.saveBrowseAppPackageLog(requestParameter);
                } else {
                    if (null != target && "baidu".equals(target) || from_baidu) {
                        iAppInfoService.saveBrowseBaiduAppLog(requestParameter);
                    } else {
                        iAppInfoService.saveBrowseAppLog(requestParameter);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Lg.error(LgType.STDOUT, "手机获取app信息时出错:app_id=" + app_id
                    + ",package_name=" + package_name + ",model=" + model
                    + ",cs=" + cs + ",version=" + version + ",cfrom=" + cfrom
                    + ",module_id=" + module_id + ",related=" + related
                    + ",imei=" + imei + ",error=" + e.getMessage());
            outwrite(NullResultObject.print(), "text/xml;charset=utf-8");
        }
        return null;
    }

    // 下载app
    public String downloadApkFile() throws Exception {
        String app_id = "";
        String appVersion = "";
        String patch = "";

        String model = "";
        String cs = "";
        String imei = "";
        String cfrom = "";
        String module_id = "";
        String related = "";
        String update = "";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            app_id = request.getParameter("id");
            appVersion = request.getParameter("app_version");
            patch = request.getParameter("patch");

            String etag = request.getHeader("If-Match");
            String target = request.getParameter("target");

            boolean isFirst = false; // 是否为断点续传

            if (null == etag) {
                RequestParameter requestParameter = new RequestParameter();
                model = request.getParameter("model");
                cs = request.getParameter("cs");
                imei = request.getParameter("imei");

                cfrom = request.getParameter("cfrom");
                String ip = RequestUtil.getClientIP(request);
                module_id = request.getParameter("module_id");
                related = request.getParameter("related");
                update = request.getParameter("update");

                requestParameter.setImei(imei);
                requestParameter.setModel(model);
                requestParameter.setIp(ip);
                requestParameter.setVersion(appVersion);
                requestParameter.setModule_id(module_id);
                requestParameter.setRelated(related);
                requestParameter.setElapsedtime(request
                        .getParameter("elapsedtime"));
                requestParameter.setUpdate(update);

                if (null != cfrom) {
                    requestParameter.setCfrom(Short.parseShort(cfrom + '0'));
                } else {
                    requestParameter.setCfrom((short) 0); // 0代表下载，1代表浏览
                }

                requestParameter.setUser_id(request.getParameter("uuid"));
                requestParameter
                        .setUser_name(request.getParameter("user_name"));

                // if(null!=target && "baidu".equals(target)){
                // requestParameter.setCfrom(Constants.FROM_BAIDU_APP_DOWNLOAD);
                // }

                requestParameter.setIdStr(app_id);
                // requestParameter.setPageIndexStr(page_index);
                requestParameter.setCsStr(cs);

                if (null != target && "baidu".equals(target)) {
                    requestParameter
                            .setCfrom(Constants.FROM_BAIDU_APP_DOWNLOAD);
                    if (Constants.SAVE_APP_BAIDU_DOWNLOAD_LOG) {
                        iAppInfoService
                                .saveDownloadBaiduAppLog(requestParameter);
                    }
                } else {
                    if (Long.parseLong(app_id) > 0) {
                        isFirst = true;
                        // iAppInfoService.updateDownloadCount(app_id); //
                        // 下载量增加1
                        if (Constants.SAVE_APP_DOWNLOAD_LOG) {
                            iAppInfoService
                                    .saveDownloadAppLog(requestParameter);
                        }

                    } else {
                        requestParameter
                                .setCfrom(Constants.FROM_BAIDU_APP_DOWNLOAD);
                        if (Constants.SAVE_APP_BAIDU_DOWNLOAD_LOG) {
                            iAppInfoService
                                    .saveDownloadBaiduAppLog(requestParameter);
                        }
                    }
                }
            }

            String filePath = iAppInfoService.getApkFilePath(app_id,
                    appVersion, isFirst, target, patch, model);

            getHttpServletResponse().sendRedirect(filePath);
        } catch (Exception e) {
            Lg.error(LgType.STDOUT, "手机下载apk文件时出错:app_id=" + app_id
                    + ",appVersion=" + appVersion + ",patch=" + patch
                    + ",model=" + model + ",cs=" + cs + ",imei=" + imei
                    + ",cfrom=" + cfrom + ",module_id=" + module_id
                    + ",related=" + related + ",update=" + update + ",error="
                    + e.getMessage());
            outwrite(NullResultObject.print(), "text/xml;charset=utf-8");
        }
        return null;

    }

    public String getDownloadApkFileUrl() {
        String app_id = StringUtils.EMPTY;
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, String> params = RequestUtil.getParameterMap(request);
        try {
            app_id = params.get("id");
            String checkToken = TokenUtil.getDownloadApkToken(app_id,
                    Constants.TOKEN_KEY);
            String token = params.get("token");
            JSONObject jo = new JSONObject();
            if (null == token || !token.equals(checkToken)) {
                jo.put("error", "token error");
            } else {
                String taget = params.get("taget");
                if ("download".equals(taget)) {
                    String filePath = iAppInfoService
                            .getAppDownloadPath(app_id);
                    RequestParameter requestParameter = new RequestParameter();
                    params.put("idStr", app_id);
                    params.put("packages", params.remove("package_name"));
                    params.put("ip", RequestUtil.getClientIP(request));
                    ReflectUtil.setObjectPropertyValues(requestParameter,
                            params);
                    if (Constants.SAVE_APP_DOWNLOAD_LOG) {
                        iAppInfoService.savePcDownloadAppLog(requestParameter);
                    }
                    getHttpServletResponse().sendRedirect(filePath);
                    return null;
                } else {
                    String path = request.getRequestURI();
                    jo = iAppInfoService.getDownloadApkFileUrl(path, params);
                }
            }
            outwrite(ObjectUtils.toString(jo), "text/plain;charset=utf-8");
        } catch (Exception e) {
            Lg.error(LgType.STDOUT, "获取手机下载apk文件URL出错:app_id=" + app_id
                    + params + ",error=" + e.getMessage());
        }
        return null;
    }

    // 软件更新
    public String packages_update() throws Exception {
        String model = null;
        String cs = null;
        String appVersion = null;
        String imei = null;
        String dbversion = null;

        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String packages = request.getParameter("packages");
            dbversion = request.getParameter("dbversion");

            model = request.getParameter("model");
            cs = request.getParameter("cs");
            imei = request.getParameter("imei");
            String times = StringUtils.defaultIfEmpty(
                    request.getParameter("n"), "0");
            if (!StringUtils.isNumeric(times)) {
                times = "0";
            }
            appVersion = request.getParameter("app_version");
            String ip = RequestUtil.getClientIP(request);
            RequestParameter requestParameter = new RequestParameter();
            requestParameter.setImei(imei);
            requestParameter.setModel(model);
            requestParameter.setIp(ip);
            requestParameter.setCsStr(cs);
            requestParameter.setPackages(packages);
            requestParameter.setVersion(appVersion);
            requestParameter
                    .setElapsedtime(request.getParameter("elapsedtime"));
            String data = "";
            if (Constants.UPDATE_PACKAGES_SWITCH) {
                if (null != packages && !packages.trim().equals("")) {
                    // String vivoSystemApp = "com.bbk.appstore,";
                    // packages = vivoSystemApp + packages;
                    data = iAppInfoService.getAppInfoListForUpdateForXml(
                            packages, dbversion, model, cs, imei, appVersion,
                            Integer.valueOf(times));
                }
            } else {
                data = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><PackageList dbversion=\"1000\"></PackageList>";
            }
            if (appVersion != null
                    && Float.valueOf(appVersion) >= Constants.APPVERSION530) {
                outwrite(data, "text/plain;charset=utf-8");
            } else {
                outwrite(data, "text/xml;charset=utf-8");
            }
            if (Constants.SAVE_PACKAGE_UPDATE_LOG) {
                iAppInfoService.savePackagesUpdateLog(requestParameter);
            }

        } catch (Exception e) {
            // e.printStackTrace();
            Lg.error(LgType.STDOUT, "手机更新apps版本信息时出错，model=" + model + ",cs="
                    + cs + ",version=" + appVersion + ",imei=" + imei
                    + ",dbversion=" + dbversion + ",error=" + e.getMessage());
            outwrite(NullResultObject.print(), "text/xml;charset=utf-8");
        }
        return null;
    }

    public String getResultByKey() {
        String foreward = "to_searchlist";
        if (null == key || key.trim().length() == 0) {
            foreward = "subtype_list";
        }
        return foreward;
    }

    // end:手机接口
    private void handleBaiduSearchForHighVersion(
            RequestParameter requestParameter) throws Exception {
        float store_version = requestParameter.getApp_version();
        String xml_data = null;
        if (store_version >= Constants.APPVERSION530) {
            AppVO app = searchOneService.getBaiduAppDetail(requestParameter);
            AppDetailResultAppInfo info = this.appVo2(app);
            if (info == null) {
                handleBaiduSearchForHighVersion();
            }
            ResultObject<AppDetailResultAppInfo> result = new ResultObject<AppDetailResultAppInfo>();
            result.setResult(true);
            result.setValue(info);
            result.setInfo("1");
            result.setComment("Y");
            xml_data = JackSonParser.bean2Json(result);
            outwrite(xml_data, "text/plain;charset=utf-8");
        }
    }

    // 为空处理
    public void handleBaiduSearchForHighVersion() throws Exception {
        ResultObject<AppDetailResultAppInfo> result = new ResultObject<AppDetailResultAppInfo>();
        result.setResult(true);
        result.setValue(new AppDetailResultAppInfo());
        result.setInfo("0");
        result.setComment("N");
        String data = JackSonParser.bean2Json(result);
        outwrite(data, "text/plain;charset=utf-8");
    }

    /**
     * 
     * @param requestParameter
     * @return null 表示低版本处理，不为 null ，result.getResult() == false;表示没找到
     * @throws Exception
     */
    private ResultObject<AppDetailResultAppInfo> handleLocalSearchForHighVersion(
            RequestParameter requestParameter) throws Exception {
        // float store_version = requestParameter.getApp_version();
        // if(store_version >= Constants.APPVERSION530){
        AppDetailResultAppInfo info = iAppInfoService
                .getImCompleteDetailAppInfo(requestParameter);
        if (info == null) {
            return null;
        } else {
            ResultObject<AppDetailResultAppInfo> result = new ResultObject<AppDetailResultAppInfo>();
            result.setResult(true);
            result.setValue(info);
            result.setComment("Y");
            result.setInfo("1");
            return result;
        }
        // }
        // return null;
    }

    private AppDetailResultAppInfo appVo2(AppVO app) {
        AppDetailResultAppInfo appInfo = null;
        if (app != null) {
            appInfo = new AppDetailResultAppInfo();
            List<SimpleAppComment> appComments = new ArrayList<SimpleAppComment>(
                    1);
            appInfo.setAppComments(appComments);
            appInfo.setDownload_url(app.getUrl());

            String introduction = app.getDescription() == null ? "" : app
                    .getDescription();
            introduction = Constants.BAIDU_APP_DISCLAIMER_FORMAT + introduction;
            appInfo.setIntroduction(introduction);

            appInfo.setOffical('0');
            appInfo.setPatchs("");

            List<String> permissionList = new ArrayList<String>(1);
            appInfo.setPermissionList(permissionList);

            List<String> screenshotList = new ArrayList<String>(1);
            if (app.getBigmaplink1() != null) {
                screenshotList.add(app.getBigmaplink1());
            }
            if (app.getBigmaplink2() != null) {
                screenshotList.add(app.getBigmaplink2());
            }
            // screenshotListEle.addElement("screenshot").setText(app.getBigmaplink1()==null?"":app.getBigmaplink1());
            // screenshotListEle.addElement("screenshot").setText(app.getBigmaplink2()==null?"":app.getBigmaplink2());
            appInfo.setScreenshotList(screenshotList);
            appInfo.setSize((int) app.getPackagesize() / 1024);
            appInfo.setUpload_time(app.getReleasedate());

            appInfo.setPackage_name(app.getPackagename());
            appInfo.setTitle_zh(app.getSname());
            appInfo.setTitle_en(app.getSname());
            appInfo.setIcon_url(app.getIcon());
            appInfo.setDeveloper(SearchConstants.DEVELOPER_FROM_NETWORK);
            String temp = app.getScore();
            if (StringUtils.isNotBlank(temp) && StringUtils.isNumeric(temp)) {
                appInfo.setScore(Float.valueOf(temp) / 20f);
            } else {
                appInfo.setScore(5.0f);
            }
            temp = app.getScore_count();
            if (StringUtils.isNotBlank(temp) && StringUtils.isNumeric(temp)) {
                appInfo.setRaters_count(Integer.valueOf(temp));
            } else {
                appInfo.setRaters_count(5);
            }
            appInfo.setVersion_code(app.getVersioncode() + "");
            appInfo.setVersion_name(app.getVersionname());
            appInfo.setDownload_count(StringUtils.isNumeric(app
                    .getDownload_count()) ? Integer.parseInt(app
                    .getDownload_count()) : 0);

        }
        return appInfo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
