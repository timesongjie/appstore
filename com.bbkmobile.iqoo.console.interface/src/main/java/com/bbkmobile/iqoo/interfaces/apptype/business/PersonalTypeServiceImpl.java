package com.bbkmobile.iqoo.interfaces.apptype.business;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.dao.apptype.PersonalType;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.interfaces.apptype.dao.PersonalTypeAppDao;
import com.bbkmobile.iqoo.interfaces.model.business.ModelInfoService;

@Service("iPersonalTypeService")
public class PersonalTypeServiceImpl implements PersonalTypeService {

    @Resource(name = "iPersonalTypeAppDao")
    private PersonalTypeAppDao personalTypeAppDao;
    @Resource(name = "iModelInfoService")
    private ModelInfoService modelInfoService;

    @Override
    public List<CommonResultAppInfo> getRecommendAppsForInterface(
            String modelStr, int typeId, PageVO vo, String order,
            String app_version) throws Exception {
        Model model = filter(modelStr);
        // vo.setRecordCount(personalTypeAppDao.count(app));
        // page.setCurrentPageNum(Integer.parseInt(page_index));
        // page.setNumPerPage(Integer.valueOf(apps_per_page));
        int pageSize = vo.getNumPerPage();
        vo.setNumPerPage(pageSize + 1);
        List<CommonResultAppInfo> list = personalTypeAppDao.listForInterface(
                typeId, vo, null, model, order);
        if (list != null && list.size() == pageSize + 1) {
            vo.setPageCount(vo.getCurrentPageNum() + 1);
            vo.setRecordCount(list.size() - 1);
        } else {
            vo.setPageCount(vo.getCurrentPageNum());
            vo.setRecordCount(list.size());
        }
        vo.setNumPerPage(pageSize);
        return list;
    }

    // 条件过滤介入点，目前支持机型过滤
    private Model filter(String model) throws Exception {
        if (model != null && StringUtils.isNotBlank(model)) {
            // 获取机型相关信息
            // model = model.split("@")[0];
            return modelInfoService.findModelByMdName(model);// modelInfoDAO.findModelByMdName(model.trim());
        }
        return null;
    }

    public PersonalTypeAppDao getPersonalTypeAppDao() {
        return personalTypeAppDao;
    }

    public void setPersonalTypeAppDao(PersonalTypeAppDao personalTypeAppDao) {
        this.personalTypeAppDao = personalTypeAppDao;
    }

    @Override
    public List<PersonalType> list(PersonalType type) throws Exception {
        return personalTypeAppDao.list(type);
    }
}
