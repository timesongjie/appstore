package com.bbkmobile.iqoo.interfaces.recommendation.business;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.constants.UtilTool;
import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.dao.recommendation.RecommendApp;
import com.bbkmobile.iqoo.console.dao.recommendation.RecommendGame;
import com.bbkmobile.iqoo.interfaces.model.business.ModelInfoService;
import com.bbkmobile.iqoo.interfaces.recommendation.dao.RecommendSoftDao;
@Service("iRecommendSoftService")
public class RecommendSoftServiceImpl implements RecommendSoftService{

	@Resource(name="iRecommendSoftDao")
	private RecommendSoftDao recommendSoftDao;
//	private ModelInfoDAO modelInfoDAO;
	@Resource(name="iModelInfoService")
	private ModelInfoService modelInfoService;
	
	///////////////interface//////////////////////
	@Override
	public List<CommonResultAppInfo> getRecommendAppsForInterface(PageVO page,
			RecommendApp app,String app_version) throws Exception {
		Model model = null;
		if(app != null){
			model = filter(app.getAppInfo());
		}
//		if(page.getCurrentPageNum() == 1){
//			page.setRecordCount(recommendSoftDao.countRecommendAppRecords(app,model));
//		}
		return recommendSoftDao.getRecommendAppListForInterface(page, app,model);
	}
	@Override
	public List<CommonResultAppInfo> getRecommendGamesForInterface(PageVO page,
			RecommendGame recommendApp, String app_version) throws Exception {
		Model model = null;
		if(recommendApp != null){
			model = filter(recommendApp.getAppInfo());
		}
//		page.setRecordCount(recommendSoftDao.countRecommendGameRecords(recommendApp,model));
		return recommendSoftDao.getRecommendGameListForInterface(page, recommendApp,model);
	}
	//条件过滤介入点，目前支持机型过滤
	private Model filter(AppInfo info) throws Exception{
		String model = info.getFilter_model();
		if(StringUtils.isNotBlank(model)){
			 //获取机型相关信息 
            return modelInfoService.findModelByMdName(model);//modelInfoDAO.findModelByMdName(model.trim()); 
		}
		return null;
	}
	//统一处理url连接问题
//	private List<CommonResultAppInfo> updateUrl(List<CommonResultAppInfo> infos,String app_version) throws Exception{
//		if(infos !=null && infos.size()>0){
//			for(CommonResultAppInfo info : infos){
//				info.setDownload_url(UtilTool.getHttpURL("/appinfo/downloadApkFile?id=" + info.getId()+"&app_version="+app_version));
//				info.setIcon_url(UtilTool.getDownloadImageHttpURL(info.getIcon_url()));
//			}
//		}
//		return infos;
//	}
}
