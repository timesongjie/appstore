package com.bbkmobile.iqoo.interfaces.recommendation.dao;

import java.util.List;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.common.vo.NewAppsResultAppInfo;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;

public interface RecommendationDAO {
    // 手机接口

    int countAllCellphoneRecommendAppsWithFilterModel(Short series_id,
            Short model_id, Integer sdkVersion, String drawable_dpi,
            String CPU_ABI) throws Exception;

    int countNewApps(RequestParameter requestParameter, Model model)
            throws Exception;

    // ////
    List<CommonResultAppInfo> getCellphoneRecommendAppsWithFilterModel(
            PageVO page, Model model) throws Exception;

    List<NewAppsResultAppInfo> getNewsAppsWithFilterModel(PageVO page,
            Model model, boolean showScreen) throws Exception;

    List<CommonResultAppInfo> getInstallAppsWithFilterModel(Model model)
            throws Exception;
}
