package com.bbkmobile.iqoo.interfaces.advertisement.dao;

import java.util.List;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.dao.advertisement.Advertisement;
import com.bbkmobile.iqoo.console.dao.advertisement.StartPage;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;

public interface AdInfoDAO {

    List<Advertisement> getPhoneAdInfoBySeries(Short series_id, Short model_id)
            throws Exception; // haiyan@根据系列获取手机广告

    Advertisement findPhoneAdByIdForFilterApps(Long id, int apps_per_page,
            int page_index, Short model_id, Integer sdkVersion,
            String drawable_dpi, String CPU_ABI) throws Exception;

    int countPhoneAdAppsWithModelFilter(Long id, Short model_id,
            Integer sdkVersion, String drawable_dpi, String CPU_ABI)
            throws Exception;

    List<StartPage> getValidStartPages(RequestParameter requestParameter)
            throws Exception;

    String getAdImgUrlByAd(Long adId, String cs, Float version)
            throws Exception;

    List<CommonResultAppInfo> getFiltedAppInfo(PageVO page, long id, Model model)
            throws Exception;
}
