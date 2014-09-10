package com.bbkmobile.iqoo.interfaces.advertisement.dao;

import java.util.List;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.dao.advertisement.Advertisement;
import com.bbkmobile.iqoo.console.dao.advertisement.AdvertisementApp;
import com.bbkmobile.iqoo.console.dao.advertisement.ModelAdvertisement;
import com.bbkmobile.iqoo.console.dao.advertisement.StartPage;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;

public interface AdInfoDAO {

    //begin: 手机接口@haiyan
    //List<Advertisement> getPhoneAdInfoBySeriesWithFilterModel(final Short series_id,final Short model_id, final Integer sdkVersion, final String drawable_dpi, final String  CPU_ABI) throws Exception;
    List<ModelAdvertisement> getModelAdvertisementBySeriesId(Short series_id) throws Exception;
    
    List<Advertisement> getPhoneAdInfoBySeries(Short series_id, Short model_id) throws Exception; //haiyan@根据系列获取手机广告
    
    List<AdvertisementApp> getAllAdvertisementAppListByAd(Advertisement advertisement, Short model_id, Integer sdkVersion, String drawable_dpi, String  CPU_ABI) throws Exception;
    
    Advertisement findPhoneAdByIdForFilterApps(Long id, int apps_per_page, int page_index, Short model_id, Integer sdkVersion, String drawable_dpi, String  CPU_ABI) throws Exception;
   
    int countPhoneAdAppsWithModelFilter(Long id, Short model_id, Integer sdkVersion, String drawable_dpi, String  CPU_ABI) throws Exception;
    
    Advertisement getAdForAppsById(Long id, int apps_per_page, int page_index, Short model_id, Integer sdkVersion, String drawable_dpi, String  CPU_ABI) throws Exception;
    
    List<StartPage> getValidStartPages(RequestParameter requestParameter) throws Exception;

    //end:手机接口
    String getAdImgUrlByAd(Long adId,String cs,Float version) throws Exception;
    
    List<CommonResultAppInfo> getFiltedAppInfo(PageVO page,Advertisement advertisement, Model model)throws Exception;
}
