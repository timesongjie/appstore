/**
 * AppTypeService.java
 * com.bbkmobile.iqoo.console.business.apptype
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2011-12-28 		dengkehai
 *
 * Copyright (c) 2011, TNT All Rights Reserved.
 */

package com.bbkmobile.iqoo.interfaces.apptype.business;

import java.util.List;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;

/**
 * ClassName:AppTypeService Function: TODO ADD FUNCTION
 * 
 * @author dengkehai
 * @version
 * @since Ver 1.1
 * @Date 2011-12-28 下午5:48:14
 * 
 */
public interface AppTypeService {
    
    //begin:手机接口@haiyan
    String getTypeListForXml(String id, String model, String cs, String imei, String appVersion) throws Exception;
    String getAppInfoListForTypeForXml(String id, String order_type, String apps_per_page, String page_index,
            String list,String model,String cs,String imei,String appVersion) throws Exception;
    //end：手机接口
    String getTypeForJson(String type_id,String cs,String tag) throws Exception;
    String getAppByTypeForJson(AppInfo appInfo,List<String> typeIds,PageVO page,Integer order_type)throws Exception;
}
