package com.bbkmobile.iqoo.interfaces.focus.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.common.json.JackSonParser;
import com.bbkmobile.iqoo.common.json.ResultObject;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.UtilTool;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.focus.dao.FocusPic;
import com.bbkmobile.iqoo.interfaces.appinfo.dao.AppInfoDAO;
import com.bbkmobile.iqoo.interfaces.focus.dao.FocusPicDAO;
import com.bbkmobile.iqoo.interfaces.model.business.ModelInfoService;

@Service("iFocusPicService")
public class FocusPicServiceImpl implements FocusPicService {
    @Resource(name = "iFocusPicDAO")
    private FocusPicDAO focusPicDAO;
    @Resource(name = "iModelInfoService")
    private ModelInfoService modelInfoService;
    @Resource(name = "iAppInfoDAO")
    private AppInfoDAO appInfoDAO;

    public String getFocusPictures(RequestParameter requestParameter)
            throws Exception {
        String reponse = "";
        try {
            Model modelClass = modelInfoService
                    .findModelByMdName(requestParameter.getModel());
            if (null != modelClass) {
                if (null == modelClass.getSeries_id()) {
                    modelClass.setSeries_id((short) 0);
                }
            }
            List<FocusPic> focusPics = focusPicDAO.getModelFocusPictures();
            if (requestParameter.getApp_version() >= Constants.APPVERSION530) {
                reponse = getModelFocusPictureJson(focusPics, modelClass);
            } else {
                reponse = getModelFocusPictureXml(focusPics, modelClass);
            }
        } catch (Exception e) {
            throw e;
        }
        return reponse;
    }

    private String getModelFocusPictureJson(List<FocusPic> focusPics,
            Model model) throws Exception {
        if (focusPics == null) {
            focusPics = Collections.EMPTY_LIST;
        }

        List<FocusPic> bak = new ArrayList<FocusPic>();

        if (null != focusPics && focusPics.size() > 0) {
            for (FocusPic focusPic : focusPics) {
                if ((2 == focusPic.getType() && 1 == focusPic.getApp_count())
                        || 3 == focusPic.getType()) {
                    if (!appInfoDAO.appIsValidForModel(focusPic.getApp_id(),
                            model)) {
                        continue;
                    }
                }
                focusPic.setImg(UtilTool.getDownloadImageHttpURL(focusPic
                        .getImg()));
                bak.add(focusPic);
            }
        }
        ResultObject<List<FocusPic>> result = new ResultObject<List<FocusPic>>();
        result.setResult(true);
        if (bak.size() > 0) {
            result.setValue(bak);
        } else {
            result.setValue(Collections.EMPTY_LIST);
        }
        return JackSonParser.bean2Json(result);
    }

    // 1-专题 2-广告 3-活动
    private String getModelFocusPictureXml(List<FocusPic> focusPics, Model model)
            throws Exception {

        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("FocusPicList");
        if (null != focusPics && focusPics.size() > 0) {
            for (FocusPic focusPic : focusPics) {
                if ((2 == focusPic.getType() && 1 == focusPic.getApp_count())
                        || 3 == focusPic.getType()) {
                    if (!appInfoDAO.appIsValidForModel(focusPic.getApp_id(),
                            model)) {
                        continue;
                    }
                }
                Element picTureEle = root.addElement("FocusPic");
                picTureEle.addElement("type").setText(focusPic.getType() + "");
                picTureEle.addElement("object_id").setText(
                        focusPic.getObject_id() + "");
                picTureEle.addElement("name").setText(focusPic.getName());
                picTureEle.addElement("img").setText(
                        UtilTool.getDownloadImageHttpURL(focusPic.getImg()));
                picTureEle.addElement("app_count").setText(
                        focusPic.getApp_count() + "");
                picTureEle.addElement("app_id").setText(
                        focusPic.getApp_id() + "");
            }
        }
        return doc.asXML();
    }
}
