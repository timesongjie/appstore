package com.bbkmobile.iqoo.interfaces.search.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bbkmobile.iqoo.common.json.JackSonParser;
import com.bbkmobile.iqoo.common.json.NullResultObject;
import com.bbkmobile.iqoo.common.json.ResultObject;
import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.interfaces.search.business.AppSearchKeyService;
import com.bbkmobile.iqoo.interfaces.search.business.SearchHotService;
import com.bbkmobile.iqoo.interfaces.search.vo.HotAppsSearch;
import com.bbkmobile.iqoo.platform.base.BaseStreamAction;

@Component("iSearchHotAction")
@Scope("prototype")

public class ISearchHotAction extends BaseStreamAction {

	/**
	 * 接口应用搜索关键字Action
	 * 
	 * @author time
	 * 变更：合并SearchHotAction中接口方法 {@link searchHotWords}
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource(name = "iSearchHotService")
	private SearchHotService searchHotService;
	@Resource(name = "iAppSearchKeyService")
	private AppSearchKeyService appSearchKeyService;
	private String model;// 机型号

	/* 获取轮播搜索关键字接口 */
	public void searchPopupWords() throws Exception {
		try {
			write(searchHotService.getPopupWords(), "text/plain;charset=utf8");
		} catch (Exception e) {
			Lg.error(LgType.STDOUT,
					"获取搜索popup word时出错" + "，error=" + e.getMessage());
		}
	}

	/* 应用搜索关键字接口 */
	public void searchSearchWords() throws Exception{
		try {
//			String searchKeys = searchHotService.getSearchKeys();// fdsf,sdfsdf形式
//			List<Map<String, String>> result = new ArrayList<Map<String, String>>();
//			if (searchKeys != null && searchKeys.length() > 0) {
//				String[] searchKey = searchKeys.split(",");
//				Map<String, String> msearchKey = new HashMap<String, String>();
//				for (String str : searchKey) {
//					msearchKey.put("appName", str);
//					msearchKey.put("appIcon", null);
//					msearchKey.put("appId", null);
//					result.add(msearchKey);
//				}
//			}
			//package_name、version_name、version_code、icon_url、 title_zh、title_en、download_url、  id；
			ResultObject<List<HotAppsSearch>> result = new ResultObject<List<HotAppsSearch>>();
			List<HotAppsSearch> appSearchKeys = appSearchKeyService.list();// [[],[]]
			if (appSearchKeys != null && appSearchKeys.size() > 0) {
				result.setValue(appSearchKeys);
//				for (Object obj : appSearchKeys) {
//					if (obj != null) {
//						Object[] o = (Object[]) obj;
//						if (o != null && o.length == 3) {
//							Map<String, String> msearchKey = new HashMap<String, String>();
//							msearchKey.put("appName", (String) o[0]);
//							msearchKey.put("appIcon", (String) o[1]);
//							msearchKey.put("id", String.valueOf(o[2]));
//							result.add(msearchKey);
//						}
//					}
//				}
			}
			result.setResult(true);
			write(JackSonParser.bean2Json(result), "text/plain;charset=utf8");
		} catch (Exception e) {
			outwrite(NullResultObject.print(), "text/xml;charset=utf-8");
			Lg.error(LgType.STDOUT,
					"获取搜索SearchKey时出错" + "，error=" + e.getMessage());
		}
	}
	/* 获取搜索关键字接口 */
	public void appSearchWords() {
		// TODO 应用名称 应用ID icon 整合到搜索关键字
	}

	public void searchHotWords() {
		try {
			write(searchHotService.getSearchHotWords(model),
					"text/plain;charset=utf8");

		} catch (Exception e) {
			// log.error("异常: ", e);
			Lg.error(LgType.STDOUT, "获取搜索热门词汇时出错:model=" + model + "，error="
					+ e.getMessage());
		}
	}
	
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}
}
