package com.bbkmobile.iqoo.console.business.search.baidu;

import com.bbkmobile.iqoo.console.business.search.AndroidInterface;

public class CateResult extends AndroidInterface {
    public ResponseVO getResponseVO() {
        return getResponseVO(INDEX_CATE);
    }

    public String getCate() {
        return HttpURLConn.response(getRequestURL());
    }

    public String getCateApps(SearchParameters parameters) {

        StringBuilder boardAppsURL = new StringBuilder();
        boardAppsURL.append(getRequestURL());
        if (null != parameters.getId()) {
            boardAppsURL.append("&id=");
            boardAppsURL.append(parameters.getId()); // 最新 最热
            boardAppsURL.append("&rn=");
            boardAppsURL.append(parameters.getRecordNum());
            boardAppsURL.append("&pn=");
            boardAppsURL.append(parameters.getPageNum());
        }
        // System.out.println("boardAppsURL="+boardAppsURL.toString());
        return HttpURLConn.response(boardAppsURL.toString());
    }
}
