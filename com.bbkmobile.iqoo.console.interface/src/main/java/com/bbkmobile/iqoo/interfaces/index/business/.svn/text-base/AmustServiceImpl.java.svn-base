package com.bbkmobile.iqoo.interfaces.index.business;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.common.json.JackSonParser;
import com.bbkmobile.iqoo.common.json.ResultObject;
import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.NewAppsResultAppInfo;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.index.dao.IndexModelSubTitle;
import com.bbkmobile.iqoo.interfaces.index.dao.AmustDao;
import com.bbkmobile.iqoo.interfaces.index.vo.AmustResultAppInfo;
import com.bbkmobile.iqoo.interfaces.model.business.ModelInfoService;
@Service
public class AmustServiceImpl implements AmustService{

	@Resource
	private AmustDao amustDaoImpl;
	@Resource(name="iModelInfoService")
	private ModelInfoService modelInfoService;
	@Override
	public String getAmustStr(char tag,String modelStr,PageVO page) throws Exception {
		List<String> titles = amustDaoImpl.getAmustSubTitles(tag);
		ResultObject<List<AmustResultAppInfo>> result = new ResultObject<List<AmustResultAppInfo>>();
		if(titles != null && titles.size() >0){
			List<AmustResultAppInfo> list = getResultAppInfo(titles, modelStr, page);
			result.setValue(list);
		}
		result.setResult(true);
		return JackSonParser.bean2Json(result);
	}
	private List<AmustResultAppInfo> getResultAppInfo(List<String> titles ,String modelStr,PageVO page)throws Exception{
		List<AmustResultAppInfo> resultAppInfo = new ArrayList<AmustResultAppInfo>(titles.size());
		
		Model  model = modelInfoService.findModelByMdName(modelStr);
		for(String title : titles){
			
			IndexModelSubTitle subtitle = new IndexModelSubTitle();
			subtitle.setSubTitle(title);
			List<NewAppsResultAppInfo> list = amustDaoImpl.getAmustAppInfo(subtitle, model, page);
			subtitle = null;
			
			AmustResultAppInfo appInfo = new AmustResultAppInfo();
			appInfo.setTitle(title);
			appInfo.setApps(list);
			resultAppInfo.add(appInfo);
		}
		return resultAppInfo;
	}
}
