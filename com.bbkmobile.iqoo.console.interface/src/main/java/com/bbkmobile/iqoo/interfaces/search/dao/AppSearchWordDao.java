package com.bbkmobile.iqoo.interfaces.search.dao;

import java.util.List;

import com.bbkmobile.iqoo.interfaces.search.vo.HotAppsSearch;

public interface AppSearchWordDao {

    public List<HotAppsSearch> list() throws Exception;

}
