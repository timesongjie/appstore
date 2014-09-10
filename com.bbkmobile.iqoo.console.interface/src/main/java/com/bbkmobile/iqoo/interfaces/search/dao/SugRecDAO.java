package com.bbkmobile.iqoo.interfaces.search.dao;

import java.util.List;

import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.interfaces.search.vo.SugRecApp;

/**
 * 相关推荐DAO
 * 
 * @author time
 * 
 */
public interface SugRecDAO {

    List<SugRecApp> getSugRecApps(String key, Model model) throws Exception;

    List<SugRecApp> getMatchRecApps(String key, Model model) throws Exception;
}
