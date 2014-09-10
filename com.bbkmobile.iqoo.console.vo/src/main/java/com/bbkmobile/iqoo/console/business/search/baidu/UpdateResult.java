package com.bbkmobile.iqoo.console.business.search.baidu;

import com.bbkmobile.iqoo.console.business.search.AndroidInterface;

public class UpdateResult extends AndroidInterface {
    @SuppressWarnings("unused")
    private static final String TAG = "UpdateResult"; // update app

    @Override
    public ResponseVO getResponseVO() {
        return getResponseVO(INDEX_UPDATE);
    }

    public String updateApps(String jsonStr) {

        StringBuilder searchURL = new StringBuilder();
        searchURL.append(getRequestURL());

        return HttpURLConn.responseJson(searchURL.toString(), jsonStr);

    }
}
