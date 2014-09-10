package com.bbkmobile.iqoo.interfaces.contentmanager.business;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.common.json.JackSonParser;
import com.bbkmobile.iqoo.common.json.ResultObject;
import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.top.dao.TopBase;
import com.bbkmobile.iqoo.interfaces.common.AppInfoTXML;
import com.bbkmobile.iqoo.interfaces.contentmanager.dao.AppRankSetDAO;
import com.bbkmobile.iqoo.interfaces.model.business.ModelInfoService;

@Service("iAppRankSetServiceImpl")
public class AppRankSetServiceImpl implements AppRankSetService {

    @Resource(name = "iAppRankSetDAO")
    private AppRankSetDAO appRankSetDAO;
    @Resource(name = "iModelInfoService")
    private ModelInfoService modelInfoService;

    //
    // begin:手机接口@haiyan 修改：zhangyi
    // 手机按某一排名获取对应的app列表，order_type为对应的排序方式
    public String getAppInfoListForTopForXml(String order_type,
            String apps_per_page, String page_index, String model, String cs,
            String imei, String appVersion) throws Exception {
        try {
            int total_count = Constants.TOP_COUNT; // 手机取排行榜数量 200

            int order_type_int = Integer.parseInt(order_type);

            int apps_per_page_int = 0;
            int page_index_int = 1;

            int cs_int = 0;
            if (null != cs && !"".equals(cs)) {
                cs_int = Integer.parseInt(cs);
            }

            if (cs_int == 0) {
                apps_per_page_int = Constants.APPS_PER_PAGE_CS; // 25
            }

            if (null != apps_per_page && !"".equals(apps_per_page)) {
                apps_per_page_int = Integer.parseInt(apps_per_page);
            }
            if (null != page_index && !"".equals(page_index)) {
                page_index_int = Integer.parseInt(page_index);
            }

            if (cs_int == 1) // pc端
            {
                if (0 == apps_per_page_int) {
                    apps_per_page_int = Constants.APPS_PER_PAGE_PC_TOP; // 10
                }
                total_count = apps_per_page_int; // pc取的排行榜数量
            }

            PageVO page = new PageVO();
            page.setCurrentPageNum(page_index_int);
            page.setNumPerPage(apps_per_page_int);
            // 获取机型相关信息
            Model modelClass = modelInfoService.findModelByMdName(model);// modelInfoDAO.findModelByMdName(model.trim());
            List<CommonResultAppInfo> list = getResult(page, modelClass,
                    order_type_int);
            int maxpage = (int) Math.ceil((float) total_count
                    / apps_per_page_int);
            if (StringUtils.isNotBlank(appVersion)
                    && StringUtils.isNumeric(appVersion)) {
                if (Float.parseFloat(appVersion) >= Constants.APPVERSION530) {
                    ResultObject<List<CommonResultAppInfo>> result = new ResultObject<List<CommonResultAppInfo>>();
                    result.setMaxPage(maxpage);
                    result.setPageNo(page_index_int);
                    result.setPageSize(apps_per_page_int);
                    result.setTotalCount(total_count);
                    result.setResult(true);
                    result.setValue(list);
                    return JackSonParser.bean2Json(result);
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><PackageList maxpage=\"");
            sb.append(maxpage);
            sb.append("\" PageNo=\"");
            sb.append(page_index_int);
            sb.append("\" pageSize=\"");
            sb.append(apps_per_page_int);
            sb.append("\" TotalCount=\"");
            sb.append(total_count);
            sb.append("\">");
            float appVersionf = StringUtils.isNotBlank(appVersion)
                    && StringUtils.isNumeric(appVersion) ? Float
                    .valueOf(appVersion) : 0;
            sb.append(AppInfoTXML.getXmlForAppInfo(list, appVersionf));
            sb.append("</PackageList>");
            return sb.toString();
        } catch (Exception e) {
            throw e;
        }
    }

    private List<CommonResultAppInfo> getResult(PageVO page, Model modelClass,
            int order_type_int) throws Exception {
        TopBase top = null;// 拓展 new TopBase();
        AppInfo info = null;
        List<CommonResultAppInfo> list = null;

        switch (order_type_int) {
        case Constants.TOP_DOWNLOADCOUNT_DESC:
            list = appRankSetDAO
                    .getTopApps(page, top, modelClass, "t_rank_hot");
            if (null == list || 0 == list.size()) { // 如果定时任务getAppRank没有获取到数据，则跑以前的流程
                list = appRankSetDAO.getTopAppsWithFilterModel(order_type_int,
                        page, modelClass);
            }
            break;
        case Constants.TOP_AVGCOMMENT_DESC:
            list = appRankSetDAO.getScoreTopApps(page, info, modelClass);
            break;
        case Constants.TOP_ONSALEDATE_DESC:
            list = appRankSetDAO
                    .getTopApps(page, top, modelClass, "t_rank_new");
            if (null == list || 0 == list.size()) { // 如果定时任务getAppRank没有获取到数据，则跑以前的流程
                list = appRankSetDAO.getTopAppsWithFilterModel(order_type_int,
                        page, modelClass);
            }
            break;
        case Constants.TOP_APPLICATION:
            list = appRankSetDAO.getTopApps(page, top, modelClass,
                    "t_top_application");
            break;
        case Constants.TOP_GAME:
            list = appRankSetDAO
                    .getTopApps(page, top, modelClass, "t_top_game");
            break;
        case Constants.TOP_RISE:
            list = appRankSetDAO
                    .getTopApps(page, top, modelClass, "t_top_risk");
            break;
        }
        return list;
    }

    /*
     * //begin:手机接口@haiyan //手机按某一排名获取对应的app列表，order_type为对应的排序方式 public String
     * getAppInfoListForTopForXml(String order_type, String apps_per_page,
     * String page_index, String model, String cs, String imei, String
     * appVersion) throws Exception{ StringBuilder sb = new StringBuilder(); try
     * { int total_count = Constants.TOP_COUNT; //手机取排行榜数量 200
     * 
     * int order_type_int = Integer.parseInt(order_type);
     * 
     * int apps_per_page_int = 0; int page_index_int = 1;
     * 
     * int cs_int = 0; if(null!=cs && !"".equals(cs)){
     * cs_int=Integer.parseInt(cs); }
     * 
     * if(cs_int==0){ apps_per_page_int=Constants.APPS_PER_PAGE_CS; //25 }
     * 
     * if(null!=apps_per_page && !"".equals(apps_per_page)){ apps_per_page_int =
     * Integer.parseInt(apps_per_page); } if(null!=page_index &&
     * !"".equals(page_index)){ page_index_int = Integer.parseInt(page_index); }
     * 
     * if(cs_int==1) //pc端 { if(0==apps_per_page_int){
     * apps_per_page_int=Constants.APPS_PER_PAGE_PC_TOP; //10 } total_count =
     * apps_per_page_int; //pc取的排行榜数量 } Short model_id=null; //Short
     * series_id=null; Integer sdkVersion = null; String drawable_dpi = null;
     * String CPU_ABI = null; //获取机型相关信息 if(null!=model && !"".equals(model)){
     * Model modelClass=modelInfoDAO.findModelByMdName(model.trim());
     * if(null!=modelClass){ model_id = modelClass.getId(); //series_id =
     * modelClass.getSeries_id(); sdkVersion = modelClass.getSdkVersion();
     * drawable_dpi = modelClass.getDrawable_dpi(); CPU_ABI =
     * modelClass.getCPU_ABI(); } } //int total_count =
     * appInfoDAO.getTopAppsWithFilterModel(order_type_int, 0, 0, model_id,
     * sdkVersion, drawable_dpi, CPU_ABI).size(); //List<AppInfo> appInfos =
     * appInfoDAO.getTopAppsWithFilterModel(order_type_int, apps_per_page_int,
     * page_index_int, // model_id, sdkVersion, drawable_dpi, CPU_ABI);
     * 
     * int maxpage = (int)Math.ceil((float)total_count/apps_per_page_int);
     * 
     * sb.append(
     * "<?xml version=\"1.0\" encoding=\"utf-8\" ?><PackageList maxpage=\"");
     * sb.append(maxpage); sb.append("\" PageNo=\""); sb.append(page_index_int);
     * sb.append("\" pageSize=\""); sb.append(apps_per_page_int);
     * sb.append("\" TotalCount=\""); sb.append(total_count); sb.append("\">");
     * 
     * switch (order_type_int) { case Constants.TOP_DOWNLOADCOUNT_DESC: //case
     * Constants.TOP_DOWNLOADCOUNT_ASC: List<AppDownloadTop> appDownloadRanks =
     * null; appDownloadRanks = appRankSetDAO.getDownloadTopApps(order_type_int,
     * apps_per_page_int, page_index_int, model_id, sdkVersion, drawable_dpi,
     * CPU_ABI); if(null!=appDownloadRanks && 0!=appDownloadRanks.size()){
     * for(AppDownloadTop appDownloadRank:appDownloadRanks){
     * sb.append(appInfoDAO
     * .getXmlForAppInfo(appDownloadRank.getAppInfo(),appVersion)); } }else{
     * //如果定时任务getAppRank没有获取到数据，则跑以前的流程 List<AppInfo> downloadApps = null;
     * downloadApps =appInfoDAO.getTopAppsWithFilterModel(order_type_int,
     * apps_per_page_int, page_index_int, model_id, sdkVersion, drawable_dpi,
     * CPU_ABI); if (null != downloadApps && 0 != downloadApps.size()) {
     * sb.append(appInfoDAO.getXmlForAppInfos(downloadApps,appVersion)); } }
     * break; case Constants.TOP_AVGCOMMENT_DESC: //case
     * Constants.TOP_AVGCOMMENT_ASC: List<AppInfo> appInfos = null; appInfos =
     * appRankSetDAO.getScoreTopApps(order_type_int, apps_per_page_int,
     * page_index_int, model_id, sdkVersion, drawable_dpi, CPU_ABI);
     * if(null!=appInfos && 0!=appInfos.size()){
     * sb.append(appInfoDAO.getXmlForAppInfos(appInfos,appVersion)); } break;
     * case Constants.TOP_ONSALEDATE_DESC: //case Constants.TOP_ONSALEDATE_ASC:
     * List<AppOnsaleDateTop> appOnsaleDateRanks = null; appOnsaleDateRanks =
     * appRankSetDAO.getOnsaleDateTopApps(order_type_int, apps_per_page_int,
     * page_index_int, model_id, sdkVersion, drawable_dpi, CPU_ABI);
     * 
     * //System.out.println("appOnsaleDateRanks.size()="+appOnsaleDateRanks.size(
     * )); if(null!=appOnsaleDateRanks && 0!=appOnsaleDateRanks.size()){
     * for(AppOnsaleDateTop appOnsaleDateRank:appOnsaleDateRanks){
     * sb.append(appInfoDAO
     * .getXmlForAppInfo(appOnsaleDateRank.getAppInfo(),appVersion));
     * //System.out
     * .println("appCnName=="+appOnsaleDateRank.getAppInfo().getAppCnName()); }
     * }else{ //如果定时任务getAppRank没有获取到数据，则跑以前的流程 List<AppInfo> onsaleDateApps =
     * null; onsaleDateApps
     * =appInfoDAO.getTopAppsWithFilterModel(order_type_int, apps_per_page_int,
     * page_index_int, model_id, sdkVersion, drawable_dpi, CPU_ABI);
     * if(null!=onsaleDateApps && 0!=onsaleDateApps.size()){
     * sb.append(appInfoDAO.getXmlForAppInfos(onsaleDateApps,appVersion)); } }
     * break; case Constants.TOP_APPLICATION: List<ApplicationTop>
     * applicationTops = null; applicationTops =
     * appRankSetDAO.getApplicationTops(order_type_int, apps_per_page_int,
     * page_index_int, model_id, sdkVersion, drawable_dpi, CPU_ABI);
     * if(null!=applicationTops){ for(ApplicationTop
     * applicationTop:applicationTops){
     * sb.append(appInfoDAO.getXmlForAppInfo(applicationTop
     * .getAppInfo(),appVersion)); } } break; case Constants.TOP_GAME:
     * List<GameTop> gameTops = null; gameTops =
     * appRankSetDAO.getGameTops(order_type_int, apps_per_page_int,
     * page_index_int, model_id, sdkVersion, drawable_dpi, CPU_ABI);
     * if(null!=gameTops){ for(GameTop gameTop:gameTops){
     * sb.append(appInfoDAO.getXmlForAppInfo(gameTop.getAppInfo(),appVersion));
     * } } break; case Constants.TOP_RISE: //case
     * Constants.TOP_DOWNLOADCOUNT_ASC: List<AppDownloadTop> appDownloadRanks2 =
     * null; appDownloadRanks2 =
     * appRankSetDAO.getDownloadTopApps(order_type_int, apps_per_page_int,
     * page_index_int, model_id, sdkVersion, drawable_dpi, CPU_ABI);
     * if(null!=appDownloadRanks2 && 0!=appDownloadRanks2.size()){
     * for(AppDownloadTop appDownloadRank:appDownloadRanks2){
     * sb.append(appInfoDAO
     * .getXmlForAppInfo(appDownloadRank.getAppInfo(),appVersion)); } } break; }
     * sb.append("</PackageList>"); } catch (Exception e) { throw e; } return
     * sb.toString(); }
     */
    // end:手机接口

}
