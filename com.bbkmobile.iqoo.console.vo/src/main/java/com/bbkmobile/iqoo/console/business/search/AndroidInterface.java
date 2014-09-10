/**
 * 
 */
package com.bbkmobile.iqoo.console.business.search;

import com.bbkmobile.iqoo.console.business.search.baidu.HttpURLConn;
import com.bbkmobile.iqoo.console.business.search.baidu.ResponseVO;

/**
 * @author wangbo
 * 
 */
public abstract class AndroidInterface {

    public ResponseVO getResponseVO(String index) {
        String searchUrl = "http://m.baidu.com/api?from=" + PARAMETER_FROM
                + "&token=" + PARAMETER_TOKEN + "&type=app&index=" + index;
        String responseXml = HttpURLConn.response(searchUrl);

        ResponseVO responseVO = new ResponseVO();
        responseVO.setUrl(responseXml.toString()
                .substring(responseXml.indexOf("<url>") + 5,
                        responseXml.indexOf("</url>")));

        return responseVO;
    }

    abstract public ResponseVO getResponseVO();

    public String getRequestURL() {
        StringBuilder url = new StringBuilder();
        url.append(getResponseVO().getUrl());
        url.append("&from=");
        url.append(PARAMETER_FROM);
        url.append("&token=");
        url.append(PARAMETER_TOKEN);
        url.append("&type=app");

        return url.toString();
    }

    protected final static String INDEX_SEARCH = "4"; // 搜索
    protected final static String INDEX_SEARCH_SUG = "10"; // 搜索建议
    protected final static String PARAMETER_FROM = "563i";
    protected final static String PARAMETER_TOKEN = "vivo";

    protected final static String INDEX_UPDATE = "3"; // update app
    protected final static String INDEX_RELATED_REC = "5"; // 相关推荐

    protected final static String INDEX_BOARD = "1"; // 榜单
    protected final static String INDEX_CATE = "2"; // 分类

}
