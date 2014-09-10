package com.bbkmobile.iqoo.interfaces.search.business;

import java.util.List;

import com.bbkmobile.iqoo.common.json.ResultObject;
import com.bbkmobile.iqoo.interfaces.search.vo.HotAppsSearch;

public interface AppSearchKeyService {

    public List<HotAppsSearch> list(String dbVersion,
            ResultObject<List<HotAppsSearch>> result) throws Exception;
}
