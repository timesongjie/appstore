package com.bbkmobile.iqoo.interfaces.recommendation.business;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.common.json.JackSonParser;
import com.bbkmobile.iqoo.common.json.NullResultObject;
import com.bbkmobile.iqoo.common.json.ResultObject;
import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.common.vo.NewAppsResultAppInfo;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.dao.recommendation.CellphoneRecommendApp;
import com.bbkmobile.iqoo.console.dao.recommendation.EditorRecommendApp;
import com.bbkmobile.iqoo.console.dao.recommendation.NewApp;
import com.bbkmobile.iqoo.interfaces.appinfo.dao.AppInfoDAO;
import com.bbkmobile.iqoo.interfaces.common.AppInfoTXML;
import com.bbkmobile.iqoo.interfaces.model.business.ModelInfoService;
import com.bbkmobile.iqoo.interfaces.recommendation.dao.RecommendationDAO;
@Service("iRecommendationService")
public class RecommendationServiceImpl implements RecommendationService {

	@Resource(name="iRecommendationDAO")
    private RecommendationDAO recommendationDAO;
	@Resource(name="iAppInfoDAO")
    private AppInfoDAO  appInfoDAO;
	@Resource(name="iModelInfoService")
    private ModelInfoService modelInfoService;
    //begin:手机接口
    public String findCellphoneRecommendAppList(String apps_per_page,String page_index, String model,String cs, float appVersion) throws Exception {
        
        try { 
            int apps_per_page_int = Constants.APPS_PER_PAGE_20;
            int page_index_int = 1;
            if(null!=page_index && !"".equals(page_index)){
                page_index_int = Integer.parseInt(page_index);        
            }
            int total_count = 0;
            int maxpage = page_index_int;
            
            Short model_id=null;
            Short series_id= 0;
            Integer sdkVersion = null;
            String drawable_dpi = null;
            String CPU_ABI = null;
            Model modelClass = modelInfoService.findModelByMdName(model);
            
            PageVO page = new PageVO();
            page.setCurrentPageNum(page_index_int);
            page.setNumPerPage(apps_per_page_int);
            List<CommonResultAppInfo> list = recommendationDAO.getCellphoneRecommendAppsWithFilterModel(page,modelClass);
            
            StringBuilder sb = new StringBuilder();
            if (null != list ){//(null != list ){//
                int cellphoneRecommendAppsSize = list.size();//cellphoneRecommendApps.size();
                if (null!=cs && cs.equals("0")) {
                    if (cellphoneRecommendAppsSize == (apps_per_page_int + 1)) {
                        maxpage = page_index_int + 1;
                        //cellphoneRecommendApps.remove(cellphoneRecommendAppsSize - 1); // 去掉最后一个
                        list.remove(list.size() -1);
                    }
                    total_count = list.size();//cellphoneRecommendApps.size(); // 当前页个数
                    
                } else {//TODO pc端未修改
                    if (appVersion == 400 && page_index_int > 1) { // 第一页返回total_count为所有app数量
                        if (cellphoneRecommendAppsSize == (apps_per_page_int + 1)) {
                            maxpage = page_index_int + 1;
                           // cellphoneRecommendApps.remove(cellphoneRecommendAppsSize - 1); // 去掉最后一个
                            list.remove(list.size() -1);
                        }
                        total_count = list.size();//cellphoneRecommendApps.size(); // 当前页个数
                    } else {
                        total_count = recommendationDAO.countAllCellphoneRecommendAppsWithFilterModel(series_id,model_id, sdkVersion, drawable_dpi, CPU_ABI);   
                        maxpage = (int) Math.ceil((float) total_count / apps_per_page_int);
                        if (cellphoneRecommendAppsSize == (apps_per_page_int + 1)) {
                            //cellphoneRecommendApps.remove(cellphoneRecommendAppsSize - 1); // 去掉最后一个
                        	list.remove(list.size() -1);
                        }
                    }

                }
              if(appVersion >= Constants.APPVERSION530){
            	  ResultObject<List<CommonResultAppInfo>> result = new ResultObject<List<CommonResultAppInfo>>();
	            	  result.setMaxPage(maxpage);
	            	  result.setPageNo(page_index_int);
	            	  result.setPageSize(apps_per_page_int);
	            	  result.setTotalCount(total_count);
	            	  result.setValue(list);
	            	  result.setResult(true);
	             sb.append(JackSonParser.bean2Json(result));	  
              }else{
            	  sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><PackageList maxpage=\"");
                  sb.append(maxpage);
                  sb.append("\" PageNo=\"");
                  sb.append(page_index_int);
                  sb.append("\" pageSize=\"");
                  sb.append(apps_per_page_int);
                  sb.append("\" TotalCount=\"");
                  sb.append(total_count);
                  sb.append("\">");
//                  for(CellphoneRecommendApp cellphoneRecommendAppTmp : cellphoneRecommendApps){
//                      //sb.append(appInfoDAO.getXmlForAppInfo(cellphoneRecommendAppTmp.getAppInfo(),appVersion));
//                  	 sb.append(AppInfoTXML.getXmlForAppInfo(cellphoneRecommendAppTmp.getAppInfo(), appVersion));
                  	sb.append(AppInfoTXML.getXmlForAppInfo(list, appVersion));
//                  }
                  sb.append("</PackageList>");
              }

            } else {
            	if(appVersion >= Constants.APPVERSION530){
            		ResultObject<List<CommonResultAppInfo>> result = new ResultObject<List<CommonResultAppInfo>>();
	            	  result.setMaxPage(maxpage);
	            	  result.setPageNo(page_index_int);
	            	  result.setPageSize(apps_per_page_int);
	            	  result.setTotalCount(total_count);
	            	  result.setResult(true);
	            	  sb.append(JackSonParser.bean2Json(result));
            	}else{
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
            }
            return sb.toString();
        } catch (Exception e) {
            throw e;
        }
    }
    
//    private List<CommonResultAppInfo> addExtraInfo(
//			List<CommonResultAppInfo> list) {
//		if(list != null && list.size() >0){
//			for(CommonResultAppInfo)
//		}
//		return list;
//	}

	public String getNewApps(RequestParameter requestParameter) throws Exception {
        
        try { 
            int apps_per_page_int = requestParameter.getApps_per_page();
            int page_index_int = requestParameter.getPage_index();
            String cs = requestParameter.getCsStr();
            float appVersion = requestParameter.getApp_version();
//            
            if(0 == apps_per_page_int){
                requestParameter.setApps_per_page(Constants.APPS_PER_PAGE_2);
                apps_per_page_int = Constants.APPS_PER_PAGE_2;
            }
            
            if(0 == page_index_int){
                requestParameter.setPage_index(1);
                page_index_int = 1;
            }
            
            int total_count = 0;
            int maxpage = page_index_int;
            
            Model modelClass =null;
            String model = requestParameter.getModel();
            modelClass = modelInfoService.findModelByMdName(model);
            //List<NewApp> newApps = recommendationDAO.getNewApps(requestParameter,modelClass);
            PageVO page = new PageVO();
            page.setCurrentPageNum(page_index_int);
            page.setNumPerPage(apps_per_page_int);
            List<NewAppsResultAppInfo> apps = null;
            if(appVersion >= Constants.APPVERSION530){
            	apps = recommendationDAO.getNewsAppsWithFilterModel(page, modelClass,false);
            }else{
            	apps = recommendationDAO.getNewsAppsWithFilterModel(page, modelClass,true);
            }
            if (null != apps) {
                int newAppsSize = apps.size();
                if( 1 == page_index_int ){                  // 第一页返回total_count为所有app数量
                    total_count = recommendationDAO.countNewApps(requestParameter, modelClass);  
                    maxpage = (int) Math.ceil((float) total_count / apps_per_page_int);
                    if (newAppsSize == (apps_per_page_int + 1)) {
                    	apps.remove(newAppsSize - 1); // 去掉最后一个
                    }
                  
                }else{
                    if (newAppsSize == (apps_per_page_int + 1)) {
                        maxpage = page_index_int + 1;
                        apps.remove(newAppsSize - 1); // 去掉最后一个
                    }
                    total_count = apps.size(); // 当前页个数
                }
                /*
                List<NewApp> newApps;        
                if (null!=cs && cs.equals("0")) {
                    if (newAppsSize == (apps_per_page_int + 1)) {
                        maxpage = page_index_int + 1;
                        newApps.remove(newAppsSize - 1); // 去掉最后一个
                    }
                    total_count = newApps.size(); // 当前页个数
                    
                } else {
                    if (null != appVersion && appVersion.equals("400") && page_index_int > 1) { // 第一页返回total_count为所有app数量
                        if (newAppsSize == (apps_per_page_int + 1)) {
                            maxpage = page_index_int + 1;
                            newApps.remove(newAppsSize - 1); // 去掉最后一个
                        }
                        total_count = newApps.size(); // 当前页个数
                    } else {
                        total_count = recommendationDAO.countNewApps(requestParameter, modelClass);  
                        maxpage = (int) Math.ceil((float) total_count / apps_per_page_int);
                        if (newAppsSize == (apps_per_page_int + 1)) {
                            newApps.remove(newAppsSize - 1); // 去掉最后一个
                        }
                    }

                }
                */
                page.setRecordCount(total_count);
                page.setPageCount(maxpage);
                return getResultStr(page,apps,appVersion);
            } else {
            	return nullResultStr(true,appVersion);
            }
        } catch (Exception e) {
        	e.printStackTrace();
            throw e;
        }
    }
    
    public String getEditorRecommendApps(RequestParameter requestParameter) throws Exception {

        try {
            float app_version = requestParameter.getApp_version();

            Model modelClass = null;
            String model = requestParameter.getModel();
            modelClass = modelInfoService.findModelByMdName(model);
            List<CommonResultAppInfo> installApps = recommendationDAO.getInstallAppsWithFilterModel(modelClass);
            if (null != installApps) {
               return getResultStr(installApps,app_version);
            } else {
               return nullResultStr(true,app_version);
            }
        } catch (Exception e) {
            throw e;
        }
    }
    //end：手机接口
    private String getResultStr(List<CommonResultAppInfo> installApps,float app_version)throws Exception{
    	StringBuilder sb = new StringBuilder();
    	if(app_version >= Constants.APPVERSION530){
    		ResultObject<List<CommonResultAppInfo>> result = new ResultObject<List<CommonResultAppInfo>>();
    		result.setTotalCount(installApps.size());
    		result.setValue(installApps);
    		result.setResult(true);
    		sb.append(JackSonParser.bean2Json(result));
    	}else{
            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><PackageList TotalCount=\"");
            sb.append(installApps.size());
            sb.append("\">");
        	sb.append(AppInfoTXML.getXmlForAppInfo(installApps, app_version));
        	//sb.append(appInfoDAO.getXmlForAppInfo(editorRecommendAppTmp.getAppInfo(), appVersion));
            sb.append("</PackageList>");
    	}
        return sb.toString();
    }
    private String getResultStr(PageVO vo,List<NewAppsResultAppInfo> apps,float appVersion)throws Exception{
    	StringBuilder sb = new StringBuilder();
    	if(appVersion >= Constants.APPVERSION530){
    		ResultObject<List<NewAppsResultAppInfo>> result = new ResultObject<List<NewAppsResultAppInfo>>();
    		result.setMaxPage(vo.getPageCount());
    		result.setPageNo(vo.getCurrentPageNum());
    		result.setPageSize(vo.getNumPerPage());
    		result.setTotalCount(vo.getRecordCount());
    		result.setValue(apps);
    		result.setResult(true);
    		sb.append(JackSonParser.bean2Json(result));
    	}else{
            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><PackageList maxpage=\"");
            sb.append(vo.getPageCount());
            sb.append("\" PageNo=\"");
            sb.append(vo.getCurrentPageNum());
            sb.append("\" pageSize=\"");
            sb.append(vo.getNumPerPage());
            sb.append("\" TotalCount=\"");
            sb.append(vo.getRecordCount());
            sb.append("\">");
            for(NewAppsResultAppInfo newAppTmp : apps){
            	   sb.append(AppInfoTXML.getXmlForAppInfoWithShotscreen(newAppTmp,appVersion));
            }
            sb.append("</PackageList>");
    	}
        return sb.toString();
    }
    private String nullResultStr(boolean needPage,float app_version){
    	StringBuilder sb = new StringBuilder();
    	if(app_version >= Constants.APPVERSION530){
    		NullResultObject result = NullResultObject.getInstance();
    		return sb.append(JackSonParser.bean2Json(result)).toString();
    	}else{
    		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><PackageList");
    	        if(needPage){
    	        	 sb.append(" maxpage=\"");
    	             sb.append(1);
    	             sb.append("\" PageNo=\"");
    	             sb.append(1);
    	             sb.append("\" pageSize=\"");
    	             sb.append(1);
    	        }
	        sb.append("\" TotalCount=\"");
	        sb.append(0); // 为0
	        sb.append("\">");
	        sb.append("</PackageList>");
    	}
        return sb.toString();
    }
}
