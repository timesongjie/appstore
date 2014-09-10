package com.bbkmobile.iqoo.interfaces.apptype.business;

import java.util.List;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.dao.apptype.PersonalType;

public interface PersonalTypeService {
    public List<CommonResultAppInfo> getRecommendAppsForInterface(String model,
            int typeId, PageVO page, String order, String app_version)
            throws Exception;

    public List<PersonalType> list(PersonalType type) throws Exception;
}
