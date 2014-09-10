package com.bbkmobile.iqoo.interfaces.apptype.dao;

import java.util.List;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.dao.apptype.PersonalType;
import com.bbkmobile.iqoo.console.dao.apptype.PersonalTypeApp;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;

public interface PersonalTypeAppDao {

    public List<CommonResultAppInfo> listForInterface(int typeId, PageVO page,
            String orderBy, Model model, String order) throws Exception;

    public int count(PersonalTypeApp app) throws Exception;

    public List<PersonalType> list(PersonalType type) throws Exception;
}
