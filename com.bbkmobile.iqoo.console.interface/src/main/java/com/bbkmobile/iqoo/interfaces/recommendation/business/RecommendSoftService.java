package com.bbkmobile.iqoo.interfaces.recommendation.business;

import java.util.List;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.dao.recommendation.RecommendApp;
import com.bbkmobile.iqoo.console.dao.recommendation.RecommendGame;

public interface RecommendSoftService {

    // interface
    public List<CommonResultAppInfo> getRecommendAppsForInterface(PageVO page,
            RecommendApp recommendApp, String app_version) throws Exception;

    public List<CommonResultAppInfo> getRecommendGamesForInterface(PageVO page,
            RecommendGame recommendGame, String app_version) throws Exception;
}
