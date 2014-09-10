package com.bbkmobile.iqoo.interfaces.advertisement.business;


import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.console.dao.advertisement.Advertisement;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;

public interface AdInfoService {
    //begin:手机接口
    String getPhoneAdvertisementListForXml(String model, String cs, Float version) throws Exception;
    String getPhoneAdvertisementAppListForXml(String ad_id,String apps_per_page,String page_index,String model,String cs,String appVersion)  throws Exception; 
    String getValidStartPage(RequestParameter requestParameter) throws Exception;
    //end：手机接口
    
    String getPhoneAdvertisementListForJSON(String model, String cs, Float version) throws Exception;
    public String getPhoneAdvertisementAppListForJSON(Advertisement advertisement,PageVO page,String cs,
			String appVersion) throws Exception;
    
}
