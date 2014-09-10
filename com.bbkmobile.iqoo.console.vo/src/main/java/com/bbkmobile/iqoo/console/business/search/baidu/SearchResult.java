/**
 * 
 */
package com.bbkmobile.iqoo.console.business.search.baidu;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.bbkmobile.iqoo.console.business.search.AndroidInterface;

/**
 * 搜索结果
 * 
 * @author wangbo
 * 
 */
public class SearchResult extends AndroidInterface {

    public ResponseVO getResponseVO() {

        return getResponseVO(INDEX_SEARCH);

    }

    // word=愤怒&rn=20&pn=20&version=2.2.1&dpi=320_480
    @SuppressWarnings("deprecation")
    public String search(SearchParameters parameters) throws Exception {

        StringBuilder searchURL = new StringBuilder();
        searchURL.append(getRequestURL());
        searchURL.append("&word=");
        if (parameters.getWord() != null) {
            searchURL.append(URLEncoder.encode(parameters.getWord(), "UTF-8"));
        }
        searchURL.append("&rn=");
        searchURL.append(parameters.getRecordNum());
        searchURL.append("&pn=");
        searchURL.append(parameters.getPageNum());

        searchURL.append("&version=");
        if (parameters.getVersion() != null) {
            searchURL.append(parameters.getVersion());
        }
        searchURL.append("&dpi=");
        if (parameters.getDpi() != null) {
            searchURL.append(URLEncoder.encode(parameters.getDpi(), "UTF-8"));
        }
        return HttpURLConn.response(searchURL.toString());

    }
}
