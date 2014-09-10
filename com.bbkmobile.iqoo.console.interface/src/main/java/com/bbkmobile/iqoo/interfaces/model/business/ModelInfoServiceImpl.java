package com.bbkmobile.iqoo.interfaces.model.business;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.cache.Cache;
import com.bbkmobile.iqoo.cache.CacheManager;
import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.dao.modelmgr.Series;
import com.bbkmobile.iqoo.interfaces.model.dao.ModelInfoDAO;

@Service("iModelInfoService")
public class ModelInfoServiceImpl implements ModelInfoService {
    @Resource(name = "iModelInfoDAO")
    private ModelInfoDAO modelInfoDAO;
    @Resource
    private CacheManager cacheManager;
    private final String CACHENAME = "model";

    @Override
    public Model findModelByMdName(String model) throws Exception {
        if (null != model && !"".equals(model)) {
            model = model.split("@")[0];

            String key = model;
            Model modelInfo = null;

            Cache<String, Object> cache = null;
            if (cacheManager != null) {
                try {
                    cache = cacheManager.getCache(CACHENAME);
                    Object obj = cache.get(key);
                    if (obj != null && obj instanceof Model) {
                        modelInfo = (Model) obj;
                    }
                } catch (Exception e) {
                    Lg.error(LgType.STDOUT, "获取缓存系统机型信息异常");
                }
            }
            if (modelInfo == null) {
                modelInfo = modelInfoDAO.findModelByMdName(model.trim());
                if (cache != null && modelInfo != null) {
                    try {
                        cache.put(key, modelInfo);
                    } catch (Exception e) {
                        Lg.error(LgType.STDOUT, "缓存系统机型信息异常");
                    }
                }
            }
            if (modelInfo != null) {
                return modelInfo;
            }
        }
        return null;
    }

    @Override
    public Series getSeriesWithName(String seriesName) throws Exception {
        if (StringUtils.isEmpty(seriesName)) {
            return null;
        }
        return modelInfoDAO.getSeriesWithName(seriesName);
    }

}
