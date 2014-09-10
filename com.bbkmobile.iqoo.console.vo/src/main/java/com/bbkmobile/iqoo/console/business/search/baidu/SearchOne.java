/**
 * 
 */
package com.bbkmobile.iqoo.console.business.search.baidu;

import com.bbkmobile.iqoo.console.business.search.AndroidInterface;

/**
 * @author wangbo
 * 
 */
public class SearchOne extends AndroidInterface {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bbkmobile.iqoo.console.business.search.AndroidInterface#getResponseVO
     * ()
     */
    @Override
    public ResponseVO getResponseVO() {

        return getResponseVO(INDEX_SEARCH);

    }

    public String one(String docid, String package_name, String appVersionCode,
            String search_type) {

        StringBuilder searchURL = new StringBuilder();
        searchURL.append(getRequestURL());
        if (null != search_type && search_type.equals("2")) {
            searchURL.append("&package=");
            searchURL.append(package_name);
            searchURL.append("&versioncode=");
            searchURL.append(appVersionCode);
        } else {
            searchURL.append("&docid=");
            searchURL.append(docid);
        }
        return HttpURLConn.response(searchURL.toString());
    }

}
