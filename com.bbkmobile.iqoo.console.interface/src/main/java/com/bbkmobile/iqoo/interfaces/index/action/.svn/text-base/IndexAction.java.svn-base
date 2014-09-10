package com.bbkmobile.iqoo.interfaces.index.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.interfaces.index.business.AmustService;
import com.bbkmobile.iqoo.platform.base.BaseAction;
@Component
@Scope("prototype")
public class IndexAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private AmustService amustServiceImpl;
	
	public void index(){
		try {
//			String apps = "{\"developer\": \"艾酷科技\", \"tag\": 2, \"download_count\": 310, \"app_remark\": \"null\", \"upload_time\": \"2014-03-27 08:24:47\", \"id\": 48352, \"size\": 894,"
//	            +"\"package_name\": \"com.dotgears.flappybird\", \"version_name\": \"1.3\", \"version_code\": \"4\","
//	            +"\"patchs\": \"null\", \"score\": 5, \"icon_url\": \"http://219.130.55.42:8088/developer/icon/201403/20140325091640660.jpg\","
//	            +"\"title_zh\": \"笨鸟先飞\", \"title_en\": \"null\", \"raters_count\": 2, \"download_url\": \"http://appstore.bbk.com/appinfo/downloadApkFile?id=48352\","
//	            +"\"offical\": \"1\"}";
//			String str = "{\"value\":[{\"subtitle\":\"美好生活\",\"apps\":["+apps+"]}],\"result\":\"true\"}";
			char tag = '1';
			String model = "vivo Xplay3S";
			PageVO page = null;
			String str = amustServiceImpl.getAmustStr(tag, model, page);
			outwrite(str, "text/plain;charset=utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
