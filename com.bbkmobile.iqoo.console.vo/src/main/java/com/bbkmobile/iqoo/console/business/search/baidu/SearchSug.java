/**
 * 
 */
package com.bbkmobile.iqoo.console.business.search.baidu;

import java.net.URLEncoder;

import com.bbkmobile.iqoo.console.business.search.AndroidInterface;

/**
 * 搜索建议
 * 
 * @author wangbo
 * 
 */
public class SearchSug extends AndroidInterface {

    public ResponseVO getResponseVO() {
        return getResponseVO(INDEX_SEARCH_SUG);
    }

    public String searchSug(String word) throws Exception {
        StringBuilder searchSugUrl = new StringBuilder();
        searchSugUrl.append(getRequestURL());
        searchSugUrl.append("&word=");
        searchSugUrl.append(URLEncoder.encode(word, "UTF-8"));

        return HttpURLConn.response(searchSugUrl.toString());
    }

}
