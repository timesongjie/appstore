package com.bbkmobile.iqoo.interfaces.search.business;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.common.json.ResultObject;
import com.bbkmobile.iqoo.interfaces.appinfo.dao.AppInfoDAO;
import com.bbkmobile.iqoo.interfaces.search.dao.AppSearchWordDao;
import com.bbkmobile.iqoo.interfaces.search.vo.HotAppsSearch;

@Service("iAppSearchKeyService")
public class AppSearchKeyServiceImpl implements AppSearchKeyService {
    @Resource(name = "iAppSearchWordDao")
    private AppSearchWordDao appSearchWordDao;
    @Resource
    private AppInfoDAO iAppInfoDAO;

    @Override
    public List<HotAppsSearch> list(String dbVersion,
            ResultObject<List<HotAppsSearch>> result) throws Exception {
        if (dbVersion != null && StringUtils.isNumeric(dbVersion)) {
            int dBVersion = iAppInfoDAO.getModuleDBVersion("search", 1001);
            result.setDbversion(dBVersion); // 更新到最新dBVersion
            if (dBVersion == Integer.valueOf(dbVersion)) {
                return null;
            }
        }
        return appSearchWordDao.list();
    }
}
