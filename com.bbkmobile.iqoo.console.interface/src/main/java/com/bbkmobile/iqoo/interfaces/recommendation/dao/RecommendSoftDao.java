package com.bbkmobile.iqoo.interfaces.recommendation.dao;

import java.util.List;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.dao.recommendation.RecommendApp;
import com.bbkmobile.iqoo.console.dao.recommendation.RecommendGame;

public interface RecommendSoftDao {

    // interface
    List<CommonResultAppInfo> getRecommendAppListForInterface(PageVO page,
            final RecommendApp recommendApp, Model model) throws Exception;

    List<CommonResultAppInfo> getRecommendGameListForInterface(PageVO page,
            RecommendGame recommendApp, Model model) throws Exception;

    int countRecommendAppRecords(RecommendApp recommendApp, Model model)
            throws Exception;

    int countRecommendGameRecords(RecommendGame recommendGame, Model model)
            throws Exception;
}
