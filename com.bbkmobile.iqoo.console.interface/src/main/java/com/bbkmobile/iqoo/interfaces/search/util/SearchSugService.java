/**
 * 
 */
package com.bbkmobile.iqoo.interfaces.search.util;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.common.json.JackSonParser;
import com.bbkmobile.iqoo.common.vo.SugSearchResultObject;
import com.bbkmobile.iqoo.console.business.search.baidu.SearchSug;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.interfaces.model.business.ModelInfoService;
import com.bbkmobile.iqoo.interfaces.search.dao.SugRecDAO;
import com.bbkmobile.iqoo.interfaces.search.vo.SugRecApp;

/**
 * @author wangbo
 * 
 */
@Service("searchSugService")
// @Scope("prototype")
public class SearchSugService {
    @Resource
    private SugRecDAO sugRecDAOImpl;
    @Resource(name = "iModelInfoService")
    private ModelInfoService modelInfoService;

    /**
     * 返回搜索建议
     * 
     * @param word
     * @return
     * @throws Exception
     */
    public String searchSugs(String word, float app_version, String model)
            throws Exception {
        if (Constants.SWITCH_CLOSE_BAIDU_SUG) {
            String reponse = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
                    + "<response><statuscode>0</statuscode><statusmessage>done</statusmessage><word>"
                    + word + "</word><sugs></sugs></response>";
            return reponse;
        }

        if (app_version >= Constants.APPVERSION530) {
            SugRecApp rec = null;
            Model modelObj = modelInfoService.findModelByMdName(model);
            if (!Constants.SWITCH_CLOSE_RELATION_SUG) {
                // 新增关联查询
                rec = getSugRecApp(word, modelObj);
            }
            if (rec == null && !Constants.SWITCH_CLOSE_MATCH_SUG) {
                // 新增首字母匹配查询
                rec = getMatchRecApp(word, modelObj);
            }
            SugSearchResultObject result = null;
            if (Constants.SWITCH_CLOSE_BAIDU_SUG2) {
                result = new SugSearchResultObject();
            } else {
                String searchSugXml = new SearchSug().searchSug(word);
                result = parseXml(searchSugXml);
            }
            result.setRec(rec);
            return JackSonParser.bean2Json(result);
        } else {
            String searchSugXml = new SearchSug().searchSug(word);
            return searchSugXml.replaceFirst("</statusmessage>",
                    "</statusmessage><word>" + word + "</word>");
        }

        /*
         * xml格式如下：
         * 
         * <response> <statuscode>0</statuscode>
         * <statusmessage>done</statusmessage> <word></word> <sugs>
         * <sug>愤怒的小鸟(高清版)</sug> <sug>愤怒的小鸟</sug> <sug>愤怒的小鸟之里约大冒险</sug>
         * <sug>愤怒的小鸟里约版拯救小鸟 angrybirds</sug> <sug>愤怒的小鸟太空版</sug>
         * <sug>愤怒的小鸟关卡解锁器</sug> <sug>愤怒的小鸟(联机版)</sug> <sug>愤怒的小鸟连连看</sug>
         * <sug>愤怒的猴子</sug> </sugs> </response>
         */
    }

    private SugSearchResultObject parseXml(String xml) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new ByteArrayInputStream(xml
                .getBytes("UTF-8")));
        Element root = document.getRootElement();
        SugSearchResultObject result = new SugSearchResultObject();
        List<Element> ls = root.elements();
        for (Element ele : ls) {
            // if ("statuscode".equals(ele.getName())) {
            // result.setStatuscode(ele.getStringValue());
            // } else if ("statusmessage".equals(ele.getName())) {
            // result.setStatusmessage(ele.getStringValue());
            // } else
            if ("word".equals(ele.getName())) {
                result.setWord(ele.getStringValue());
            } else if ("sugs".equals(ele.getName())) {
                List<Element> eles = ele.elements();
                if (eles != null && eles.size() > 0) {
                    List<String> sugs = new ArrayList<String>(eles.size());
                    for (Element element : eles) {
                        sugs.add(element.getStringValue());
                    }
                    result.setValue(sugs);
                }
            }
        }
        result.setResult(true);
        return result;
    }

    private SugRecApp getSugRecApp(String word, Model model) throws Exception {
        List<SugRecApp> list = sugRecDAOImpl.getSugRecApps(word, model);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    private SugRecApp getMatchRecApp(String word, Model model) throws Exception {
        List<SugRecApp> list = sugRecDAOImpl.getMatchRecApps(word, model);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
