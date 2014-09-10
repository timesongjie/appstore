package com.bbkmobile.iqoo.interfaces.contentmanager.dao;

import java.util.List;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.top.dao.TopBase;

public interface AppRankSetDAO {
    List<AppInfo> getScoreTopApps(int order_type_int, int apps_per_page_int, int page_index_int, Short model_id,
            Integer sdkVersion, String drawable_dpi, String CPU_ABI) throws Exception;
	public List<TopBase> getRankTops(final int order_type_int,final int apps_per_page_int,final int page_index_int,
            final Short model_id,final Integer sdkVersion,final String drawable_dpi,final String CPU_ABI,String tableName) throws Exception;
	
	/////////
	List<CommonResultAppInfo> getTopApps(PageVO page,TopBase top,Model model ,String topName)throws Exception;
	List<CommonResultAppInfo> getScoreTopApps(PageVO page,AppInfo appInfo,Model model ) throws Exception;
	List<CommonResultAppInfo> getTopAppsWithFilterModel(int order_type,
			PageVO page, Model model) throws Exception;
}
