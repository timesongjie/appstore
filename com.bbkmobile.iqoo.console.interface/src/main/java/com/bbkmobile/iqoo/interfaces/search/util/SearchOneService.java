/**
 * 
 */
package com.bbkmobile.iqoo.interfaces.search.util;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.console.business.search.baidu.SearchOne;
import com.bbkmobile.iqoo.console.business.search.xml.vo.AppVO;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;

/**
 * @author wangbo
 * 
 */
@Service("iSearchOneService")
@Scope("prototype")
public class SearchOneService {
    @Resource(name = "searchXmlProcessor")
    private SearchXmlProcessor xmlProc;

    public String searchOne(RequestParameter requestParameter) {
        SearchOne searchOne = new SearchOne();

        // SearchXmlProcessor proc = new SearchXmlProcessor();
        String xmlFromBaidu = searchOne.one(requestParameter.getIdStr(),
                requestParameter.getPackages(),
                requestParameter.getVersioncode(),
                requestParameter.getSearch_type());
        return xmlProc.createXmlOneForPhone(requestParameter, xmlFromBaidu);
    }

    public AppVO getBaiduAppDetail(RequestParameter requestParameter) {
        SearchOne searchOne = new SearchOne();

        // SearchXmlProcessor proc = new SearchXmlProcessor();
        String xmlFromBaidu = searchOne.one(requestParameter.getIdStr(),
                requestParameter.getPackages(),
                requestParameter.getVersioncode(),
                requestParameter.getSearch_type());
        return xmlProc.getBaiduAppVO(xmlFromBaidu);
    }

    public SearchXmlProcessor getXmlProc() {
        return this.xmlProc;
    }

    public void setXmlProc(SearchXmlProcessor xmlProc) {
        this.xmlProc = xmlProc;
    }

}
