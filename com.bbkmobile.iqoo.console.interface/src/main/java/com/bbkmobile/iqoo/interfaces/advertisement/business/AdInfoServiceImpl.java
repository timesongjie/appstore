package com.bbkmobile.iqoo.interfaces.advertisement.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.common.json.JackSonParser;
import com.bbkmobile.iqoo.common.json.ResultObject;
import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.UtilTool;
import com.bbkmobile.iqoo.console.dao.advertisement.Advertisement;
import com.bbkmobile.iqoo.console.dao.advertisement.StartPage;
import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.interfaces.advertisement.dao.AdInfoDAO;
import com.bbkmobile.iqoo.interfaces.advertisement.vo.Advertising;
import com.bbkmobile.iqoo.interfaces.appinfo.dao.AppInfoDAO;
import com.bbkmobile.iqoo.interfaces.common.AppInfoTXML;
import com.bbkmobile.iqoo.interfaces.model.business.ModelInfoService;

@Service("iAdInfoService")
public class AdInfoServiceImpl implements AdInfoService {

    @Resource(name = "iAdInfoDAOImpl")
    private AdInfoDAO adInfoDAO;

    @Resource(name = "iAppInfoDAO")
    private AppInfoDAO appInfoDAO;

    @Resource(name = "iModelInfoService")
    private ModelInfoService modelInfoService;

    // begin:手机接口@haiyan
    public String getPhoneAdvertisementListForXml(String model, String cs,
            Float version) throws Exception {
        StringBuilder sb = new StringBuilder();
        try {
            Short model_id = null;
            Short series_id = 0;
            Model modelClass = null;
            // Integer sdkVersion = null;
            // String drawable_dpi = null;
            // String CPU_ABI = null;

            // 获取机型对应的id 和 系列id
            Model modelInfo = modelInfoService.findModelByMdName(model);
            if (null != modelInfo) {
                model_id = modelInfo.getId();
                series_id = modelInfo.getSeries_id();
                // sdkVersion = modelClass.getSdkVersion();
                // drawable_dpi = modelClass.getDrawable_dpi();
                // CPU_ABI = modelClass.getCPU_ABI();
            }

            if (null != cs && cs.equals("1")) { // PC工具单独定制广告
                String seriesName = "PC系列";
                series_id = modelInfoService.getSeriesWithName(seriesName)
                        .getId();
            }
            List<Advertisement> advertisements = adInfoDAO
                    .getPhoneAdInfoBySeries(series_id, model_id); // 获取对应系列的所有广告
            /*
             * 暂时屏蔽按机型过滤去统计广告的app个数 if(null!=advertisements &&
             * 0!=advertisements.size()){ for(Advertisement
             * advertisement:advertisements){
             * advertisement.setApp_count(adInfoDAO
             * .CountPhoneAdAppsWithModelFilter(advertisement.getId(), model_id,
             * sdkVersion, drawable_dpi, CPU_ABI)); //Advertisement
             * advertisementForFilterApps
             * =adInfoDAO.findPhoneAdByIdForFilterApps(advertisement.getId(),
             * model_id, sdkVersion, drawable_dpi, CPU_ABI);
             * //if(advertisementForFilterApps!=null){
             * //advertisement.setApp_count
             * (advertisementForFilterApps.getAppInfos().size()); //每个广告对应的app数量
             * app按机型过滤 //}else{ //advertisement.setApp_count(0); // } } }
             */
            // AppInfo appInfo = new AppInfo();
            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><AdvertisingList>");

            if (null != advertisements && 0 != advertisements.size()) {
                for (Advertisement advertisement : advertisements) {
                    // int advertisementAppCount =
                    // advertisement.getAppInfos().size();
                    int advertisementAppCount = advertisement.getApp_count();
                    // int advertisementAppCount = advertisement.getApp_count();
                    if (null == cs || "".equals(cs)) {
                        cs = "0";
                    }
                    String adImgUrl = adInfoDAO.getAdImgUrlByAd(
                            advertisement.getId(), cs, version);

                    if (advertisementAppCount != 0) {

                        if (advertisementAppCount == 1) {
                            if (!appInfoDAO.appIsValidForModel(
                                    advertisement.getApp_id(), modelClass)) {
                                continue;
                            }
                        }

                        sb.append("<Advertising><adv_add><![CDATA[");
                        sb.append(advertisement.getAd_name());
                        sb.append("]]></adv_add><image_url><![CDATA[");
                        sb.append(UtilTool.getDownloadImageHttpURL(adImgUrl));
                        sb.append("]]></image_url>");
                        sb.append("<app_list_id><![CDATA[");
                        sb.append(advertisement.getId());
                        sb.append("]]></app_list_id>");
                        if (advertisementAppCount == 1) {
                            sb.append("<app_id><![CDATA[");
                            sb.append(advertisement.getApp_id());
                            /*
                             * Iterator<AppInfo>
                             * appIterator=advertisement.getAppInfos
                             * ().iterator(); //此处没有根据机型过滤app，故ad_app不能只有一个
                             * while(appIterator.hasNext()){
                             * appInfo=appIterator.next();
                             * sb.append(appInfo.getId()); }
                             */
                            sb.append("]]></app_id>");
                        }
                        sb.append("<app_count><![CDATA[");
                        sb.append(advertisementAppCount);
                        sb.append("]]></app_count></Advertising>");
                    }
                }
            }
            sb.append("</AdvertisingList>");

        } catch (Exception e) {
            throw e;
        }
        return sb.toString();
    }

    public String getPhoneAdvertisementAppListForXml(String ad_id,
            String apps_per_page, String page_index, String model, String cs,
            String appVersion) throws Exception {

        StringBuilder sb = new StringBuilder();

        try {
            Long adId = Long.parseLong(ad_id);
            int apps_per_page_int = 0;
            int page_index_int = 1;
            /*
             * if(cs.equals("1")){ apps_per_page_int =
             * Constants.APPS_PER_PAGE_CS; }
             */
            if (null != apps_per_page && !"".equals(apps_per_page)) {
                apps_per_page_int = Integer.parseInt(apps_per_page);
            }
            if (null != page_index && !"".equals(page_index)) {
                page_index_int = Integer.parseInt(page_index);
            }

            Short model_id = null;
            // Short series_id=null;
            Integer sdkVersion = null;
            String drawable_dpi = null;
            String CPU_ABI = null;

            int total_count = 0;
            int maxpage = page_index_int;

            // 获取机型对应的信息
            Model modelInfo = modelInfoService.findModelByMdName(model);
            if (null != modelInfo) {
                model_id = modelInfo.getId();
                // series_id = modelClass.getSeries_id();
                sdkVersion = modelInfo.getSdkVersion();
                drawable_dpi = modelInfo.getDrawable_dpi();
                CPU_ABI = modelInfo.getCPU_ABI();
            }

            Advertisement advertisement = adInfoDAO
                    .findPhoneAdByIdForFilterApps(adId, apps_per_page_int,
                            page_index_int, model_id, sdkVersion, drawable_dpi,
                            CPU_ABI);

            if (null != advertisement) {
                Set<AppInfo> appInfoSets = advertisement.getAppInfos();

                List<AppInfo> appInfos = new ArrayList<AppInfo>();
                appInfos.addAll(appInfoSets);

                int appInfoCount = appInfos.size();
                if (null != cs && cs.equals("0")) {
                    if (appInfoCount == (apps_per_page_int + 1)) {
                        maxpage = page_index_int + 1;
                        appInfos.remove(appInfoCount - 1); // 去掉最后一个
                    }
                    total_count = appInfos.size(); // 当前页个数
                } else {
                    if (null != appVersion && appVersion.equals("400")
                            && page_index_int > 1) { // 第一页返回total_count为所有app数量
                        if (appInfoCount == (apps_per_page_int + 1)) {
                            maxpage = page_index_int + 1;
                            appInfos.remove(appInfoCount - 1); // 去掉最后一个
                        }
                        total_count = appInfos.size(); // 当前页个数
                    } else {
                        total_count = adInfoDAO
                                .countPhoneAdAppsWithModelFilter(adId,
                                        model_id, sdkVersion, drawable_dpi,
                                        CPU_ABI);
                        maxpage = (int) Math.ceil((float) total_count
                                / apps_per_page_int);
                        if (appInfoCount == (apps_per_page_int + 1)) {
                            appInfos.remove(appInfoCount - 1); // 去掉最后一个
                        }
                    }

                }

                sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><PackageList maxpage=\"");
                sb.append(maxpage);
                sb.append("\" PageNo=\"");
                sb.append(page_index_int);
                sb.append("\" pageSize=\"");
                sb.append(apps_per_page_int);
                sb.append("\" TotalCount=\"");
                sb.append(total_count);
                sb.append("\">");
                // sb.append(appInfoDAO.getXmlForAppInfos(appInfos,
                // appVersion));
                sb.append(AppInfoTXML.getXmlForAppInfos(appInfos, appVersion));
                sb.append("</PackageList>");

            } else {
                sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><PackageList maxpage=\"");
                sb.append(maxpage);
                sb.append("\" PageNo=\"");
                sb.append(page_index_int);
                sb.append("\" pageSize=\"");
                sb.append(apps_per_page_int);
                sb.append("\" TotalCount=\"");
                sb.append(total_count); // 为0
                sb.append("\">");
                sb.append("</PackageList>");
            }
            return sb.toString();
        } catch (Exception e) {
            throw e;
        }
    }

    public String getValidStartPage(RequestParameter requestParameter)
            throws Exception {
        StringBuilder sb = new StringBuilder();
        try {
            // 获取生效时间为未来两天的图片
            List<StartPage> startPages = adInfoDAO
                    .getValidStartPages(requestParameter);
            float appVersion = requestParameter.getApp_version();
            if (appVersion >= Constants.APPVERSION530) {
                ResultObject<List<StartPage>> result = new ResultObject<List<StartPage>>();
                result.setResult(true);
                if (startPages != null) {
                    for (StartPage page : startPages) {
                        page.setValid_date(page.getValid_date() + "/"
                                + page.getInvalid_date());
                        page.setImage(UtilTool.getDownloadImageHttpURL(page
                                .getImage()));
                    }
                }
                result.setValue(startPages);
                sb.append(JackSonParser.bean2Json(result));
                return sb.toString();
            }
            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><StartPageList>");
            if (null != startPages && 0 != startPages.size()) {
                for (StartPage startPage : startPages) {
                    sb.append("<StartPage><default_view>");
                    sb.append(startPage.getDefault_view());
                    sb.append("</default_view><image_url><![CDATA[");
                    sb.append(UtilTool.getDownloadImageHttpURL(startPage
                            .getImage()));
                    sb.append("]]></image_url><valid_date>");
                    sb.append(startPage.getValid_date());
                    sb.append("/");
                    sb.append(startPage.getInvalid_date());
                    sb.append("</valid_date></StartPage>");
                }
            }
            sb.append("</StartPageList>");
        } catch (Exception e) {
            throw e;
        }
        return sb.toString();
    }

    // end:手机接口

    @Override
    public String getPhoneAdvertisementListForJSON(String model, String cs,
            Float version) throws Exception {
        Short model_id = null;
        Short series_id = 0;
        // 获取机型对应的id 和 系列id
        Model modelInfo = modelInfoService.findModelByMdName(model);
        if (null != modelInfo) {
            model_id = modelInfo.getId();
            series_id = modelInfo.getSeries_id();
        }

        if (null != cs && cs.equals("1")) { // PC工具单独定制广告
            String seriesName = "PC系列";
            series_id = modelInfoService.getSeriesWithName(seriesName).getId();
        }
        List<Advertisement> advertisements = adInfoDAO.getPhoneAdInfoBySeries(
                series_id, model_id); // 获取对应系列的所有广告
        if (advertisements != null) {
            List<Advertising> list = new ArrayList<Advertising>(
                    advertisements.size());
            for (Advertisement ad : advertisements) {
                Advertising advertising = new Advertising();
                advertising.setAdv_add(ad.getAd_name());
                advertising.setApp_count(ad.getApp_count());
                if (ad.getApp_count() == 1) {
                    advertising.setApp_id(ad.getApp_id());
                }
                advertising.setApp_list_id(ad.getId());
                String adImgUrl = adInfoDAO.getAdImgUrlByAd(ad.getId(), cs,
                        version);
                advertising.setImage_url(UtilTool
                        .getDownloadImageHttpURL(adImgUrl));
                list.add(advertising);
            }
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("result", true);
            result.put("advertisingList", list);
            return JackSonParser.bean2Json(result);
        }

        return null;
    }

    @Override
    public String getPhoneAdvertisementAppListForJSON(long ad_id, PageVO page,
            String cs, String appVersion, String modelStr) throws Exception {

        Short model_id = null;
        // Short series_id=null;
        Integer sdkVersion = null;
        String drawable_dpi = null;
        String CPU_ABI = null;

        Model model = filter(modelStr);

        int apps_per_page_int = page.getNumPerPage();
        int page_index_int = page.getCurrentPageNum();

        int total_count = 0;
        int maxpage = page_index_int;

        ResultObject<List<CommonResultAppInfo>> result = new ResultObject<List<CommonResultAppInfo>>();
        List<CommonResultAppInfo> appInfos = adInfoDAO.getFiltedAppInfo(page,
                ad_id, model);
        if (null != appInfos && appInfos.size() > 0) {

            int appInfoCount = appInfos.size();
            if (null != cs && cs.equals("0")) {
                if (appInfoCount == (page.getNumPerPage() + 1)) {
                    maxpage = page_index_int + 1;
                    appInfos.remove(appInfoCount - 1); // 去掉最后一个
                }
                total_count = appInfos.size(); // 当前页个数
            } else {// pc 端未更改 TODO
                if (null != appVersion && appVersion.equals("400")
                        && page_index_int > 1) { // 第一页返回total_count为所有app数量
                    if (appInfoCount == (apps_per_page_int + 1)) {
                        maxpage = page_index_int + 1;
                        appInfos.remove(appInfoCount - 1); // 去掉最后一个
                    }
                    total_count = appInfos.size(); // 当前页个数
                } else {

                    total_count = adInfoDAO.countPhoneAdAppsWithModelFilter(
                            ad_id, model_id, sdkVersion, drawable_dpi, CPU_ABI);
                    maxpage = (int) Math.ceil((float) total_count
                            / apps_per_page_int);
                    if (appInfoCount == (apps_per_page_int + 1)) {
                        appInfos.remove(appInfoCount - 1); // 去掉最后一个
                    }
                }
            }
            result.setValue(appInfos);
        }
        result.setMaxPage(maxpage);
        result.setPageNo(page_index_int);
        result.setPageSize(apps_per_page_int);
        result.setTotalCount(total_count);
        result.setResult(true);
        return JackSonParser.bean2Json(result);
    }

    // 条件过滤介入点，目前支持机型过滤
    private Model filter(String model) throws Exception {
        if (StringUtils.isNotBlank(model)) {
            // 获取机型相关信息
            // model = model.split("@")[0];
            return modelInfoService.findModelByMdName(model);// modelInfoDAO.findModelByMdName(model.trim());
            // if (null != modelClass) {
            // // series_id = modelClass.getSeries_id();
            // info.setMinSdkVersion(modelClass.getSdkVersion());
            // info.setMaxSdkVersion(modelClass.getSdkVersion());
            // info.setCPU_ABI(modelClass.getCPU_ABI());
            // info.setFilter_model(String.valueOf(modelClass.getId()));
            // }
        }
        return null;
    }
}
