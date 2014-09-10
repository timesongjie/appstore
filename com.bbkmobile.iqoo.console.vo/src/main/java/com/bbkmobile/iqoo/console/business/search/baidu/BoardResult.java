package com.bbkmobile.iqoo.console.business.search.baidu;

import com.bbkmobile.iqoo.console.business.search.AndroidInterface;

public class BoardResult extends AndroidInterface {

    public ResponseVO getResponseVO() {
        return getResponseVO(INDEX_BOARD);
    }

    public String getBoard() {
        return HttpURLConn.response(getRequestURL());
    }

    public String getBoardApps(SearchParameters parameters) {

        StringBuilder boardAppsURL = new StringBuilder();
        boardAppsURL.append(getRequestURL());
        boardAppsURL.append("&id=");
        boardAppsURL.append(parameters.getId()); // 最新 最热
        boardAppsURL.append("&rn=");
        boardAppsURL.append(parameters.getRecordNum());
        boardAppsURL.append("&pn=");
        boardAppsURL.append(parameters.getPageNum());

        return HttpURLConn.response(boardAppsURL.toString());
    }
}
