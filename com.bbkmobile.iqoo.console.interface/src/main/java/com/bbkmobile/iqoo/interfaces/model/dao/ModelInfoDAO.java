package com.bbkmobile.iqoo.interfaces.model.dao;

import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.dao.modelmgr.Series;

public interface ModelInfoDAO {
    Model findModelByMdName(String md_name) throws Exception;

    Series getSeriesWithName(String seriesName) throws Exception;
}
