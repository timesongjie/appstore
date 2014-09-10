package com.bbkmobile.iqoo.interfaces.index.dao;

import java.util.List;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.NewAppsResultAppInfo;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.index.dao.IndexModelSubTitle;
import com.bbkmobile.iqoo.interfaces.index.vo.AmustAppsResultAppInfo;

public interface AmustDao {

    public List<NewAppsResultAppInfo> getAmustAppInfo(IndexModelSubTitle title,
            Model model, PageVO page) throws Exception;

    public List<AmustAppsResultAppInfo> getAmustAppInfo(Character tag,
            Model model, PageVO page) throws Exception;
}
