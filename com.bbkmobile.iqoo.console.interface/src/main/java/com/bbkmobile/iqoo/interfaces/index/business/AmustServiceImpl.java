package com.bbkmobile.iqoo.interfaces.index.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.common.json.JackSonParser;
import com.bbkmobile.iqoo.common.json.ResultObject;
import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.interfaces.index.dao.AmustDao;
import com.bbkmobile.iqoo.interfaces.index.vo.AmustAppsResultAppInfo;
import com.bbkmobile.iqoo.interfaces.index.vo.AmustResultAppInfo;
import com.bbkmobile.iqoo.interfaces.model.business.ModelInfoService;

@Service
public class AmustServiceImpl implements AmustService {

    @Resource
    private AmustDao amustDaoImpl;
    @Resource(name = "iModelInfoService")
    private ModelInfoService modelInfoService;

    @Override
    public String getAmustStr(char tag, String modelStr, PageVO page)
            throws Exception {
        ResultObject<List<AmustResultAppInfo>> result = new ResultObject<List<AmustResultAppInfo>>();
        List<AmustResultAppInfo> list = getResultAppInfo(tag, modelStr, page);
        result.setValue(list);
        result.setResult(true);
        return JackSonParser.bean2Json(result);
    }

    private List<AmustResultAppInfo> getResultAppInfo(Character tag,
            String modelStr, PageVO page) throws Exception {
        List<AmustResultAppInfo> resultAppInfo = new ArrayList<AmustResultAppInfo>();

        Model model = modelInfoService.findModelByMdName(modelStr);
        List<AmustAppsResultAppInfo> list = amustDaoImpl.getAmustAppInfo(tag,
                model, page);
        if (list != null && list.size() > 0) {
            // Map<String, List<AmustAppsResultAppInfo>> maps = new
            // HashMap<String, List<AmustAppsResultAppInfo>>();
            String title = null;
            AmustResultAppInfo appInfo = null;
            List<AmustAppsResultAppInfo> apps = null;
            for (int i = 0; i < list.size(); i++) {
                AmustAppsResultAppInfo amust = list.get(i);
                if (title == null || !amust.getTitle().equals(title)) {
                    title = amust.getTitle();
                    appInfo = new AmustResultAppInfo();
                    appInfo.setTitle(title);

                    apps = new ArrayList<AmustAppsResultAppInfo>();
                    appInfo.setApps(apps);
                    resultAppInfo.add(appInfo);
                }
                amust.setTitle(null);
                apps.add(amust);
            }
        }
        return resultAppInfo;
    }
}
