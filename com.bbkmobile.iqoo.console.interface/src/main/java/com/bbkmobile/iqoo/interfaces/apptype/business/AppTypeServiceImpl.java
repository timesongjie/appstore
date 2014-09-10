/**
 * AppTypeServiceImpl.java
 * com.bbkmobile.iqoo.console.business.apptype
 *
 * Function：  
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2011-12-28 		dengkehai
 *
 * Copyright (c) 2011, TNT All Rights Reserved.
 */

package com.bbkmobile.iqoo.interfaces.apptype.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.common.json.JackSonParser;
import com.bbkmobile.iqoo.common.json.ResultObject;
import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.UtilTool;
import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.apptype.AppType;
import com.bbkmobile.iqoo.console.dao.apptype.PersonalType;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.interfaces.appinfo.dao.AppInfoDAO;
import com.bbkmobile.iqoo.interfaces.apptype.dao.AppTypeDAO;
import com.bbkmobile.iqoo.interfaces.common.AppInfoTXML;
import com.bbkmobile.iqoo.interfaces.model.business.ModelInfoService;

/**
 * ClassName:AppTypeServiceImpl Function:
 * 
 * @author dengkehai
 * @version
 * @since Ver 1.1
 * @Date 2011-12-28 下午5:48:44
 * 
 */
@Service("iAppTypeService")
public class AppTypeServiceImpl implements AppTypeService {

    @Resource(name = "iAppTypeDAO")
    private AppTypeDAO appTypeDAO;

    @Resource(name = "iAppInfoDAO")
    private AppInfoDAO appInfoDAO;

    @Resource(name = "iModelInfoService")
    private ModelInfoService modelInfoService;

    @Resource(name = "iPersonalTypeService")
    private PersonalTypeService personalTypeService;

    List<AppType> all_sub_type = new ArrayList<AppType>();
    List<AppType> all_leaf_type = new ArrayList<AppType>();

    // begin:手机接口@haiyan
    public String getTypeListForXml(String id, String model, String cs,
            String imei, String appVersion) throws Exception {
        StringBuilder sb = new StringBuilder();
        try {
            List<AppType> appTypes = null;
            Integer type_id = Integer.parseInt(id);

            int cs_int = 0;
            if (null != cs && !"".equals(cs)) {
                cs_int = Integer.parseInt(cs);
            }
            appTypes = appTypeDAO.findAllSubType(type_id);
            /*
             * if(cs_int==0){ appTypes = appTypeDAO.findAllSubType(type_id); }
             * else if(cs_int==1){ //按类型id返回所有子孙类型 appTypes =
             * getAllSubTypeById(type_id); }
             */
            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><CategoryList>");

            if (null != appTypes && 0 != appTypes.size()) {
                for (AppType appType : appTypes) {
                    sb.append("<Category><id><![CDATA[");
                    sb.append(appType.getId());
                    sb.append("]]></id><parent_id><![CDATA[");
                    if (null == appType.getParentId()) {
                        sb.append(0);
                    } else {
                        sb.append(appType.getParentId());
                    }
                    sb.append("]]></parent_id><title_zh><![CDATA[");
                    sb.append(appType.getTypeName());
                    sb.append("]]></title_zh><title_en><![CDATA[");
                    sb.append(appType.getTypeEnName());
                    sb.append("]]></title_en><icon_url><![CDATA[");
                    // 图片下载地址,zuoshengdong
                    sb.append(UtilTool.getDownloadImageHttpURL(appType
                            .getTypeIcon()));
                    sb.append("]]></icon_url>");

                    if (null != appVersion
                            && Float.parseFloat(appVersion) >= 510) {
                        Object[] object = appTypeDAO.getTypeIcon(appType
                                .getId());
                        if (object == null || object.length != 3) {
                            object = new String[] { "", "", "" };
                        }
                        sb.append("<icon_url2><![CDATA[");
                        sb.append(UtilTool.getDownloadImageHttpURL(object[1]
                                .toString()));
                        sb.append("]]></icon_url2>");
                        sb.append("<icon_url3><![CDATA[");
                        sb.append(UtilTool.getDownloadImageHttpURL(object[2]
                                .toString()));
                        sb.append("]]></icon_url3>");
                    }

                    sb.append("<subtype><![CDATA[");
                    List<AppType> appSubTypes = appTypeDAO
                            .findAllSubType(appType.getId());
                    if (null != appSubTypes && 0 != appSubTypes.size()) {
                        sb.append(1);
                    } else {
                        sb.append(0); // 0-没有子类型，1-有子类型
                    }
                    sb.append("]]></subtype><app_count><![CDATA[");
                    sb.append(100); // 此参数获取较为耗时，并且不是必须的，暂忽略
                    sb.append("]]></app_count><app_names><![CDATA[");
                    sb.append(appType.getAppCnNames());
                    sb.append("]]></app_names></Category>");
                }
            }

            sb.append("</CategoryList>");
        } catch (Exception e) {
            throw e;
        }
        return sb.toString();
    }

    // 手机获取类型对应的app列表，id为app类型id， order_type为对应的排序方式
    public String getAppInfoListForTypeForXml(String id, String order_type,
            String apps_per_page, String page_index, String list, String model,
            String cs, String imei, String appVersion) throws Exception {
        StringBuilder sb = new StringBuilder();
        try {
            int type_id = Integer.parseInt(id);

            int apps_per_page_int = Constants.APPS_PER_PAGE_CS;
            int page_index_int = 1;
            int order_type_int = Constants.TOP_AVGCOMMENT_DESC;

            if (null != apps_per_page && !"".equals(apps_per_page)) {
                apps_per_page_int = Integer.parseInt(apps_per_page);
            }

            if (null != page_index && !"".equals(page_index)) {
                page_index_int = Integer.parseInt(page_index);
            }

            if (null != order_type && !"".equals(order_type)) {
                order_type_int = Integer.parseInt(order_type);
            }

            int cs_int = 0;
            if (null != cs && !"".equals(cs)) {
                cs_int = Integer.parseInt(cs);
            }

            int total_count = 0;
            int maxpage = page_index_int;

            Short model_id = null;
            // Short series_id=null;
            Integer sdkVersion = null;
            String drawable_dpi = null;
            String CPU_ABI = null;
            // 获取机型对应的信息
            Model modelClass = modelInfoService.findModelByMdName(model);
            if (null != modelClass) {
                model_id = modelClass.getId();
                // series_id = modelClass.getSeries_id();
                sdkVersion = modelClass.getSdkVersion();
                drawable_dpi = modelClass.getDrawable_dpi();
                CPU_ABI = modelClass.getCPU_ABI();
            }
            List<AppType> appTypesList = null;
            AppType appType = null;
            boolean is_list_all = false;

            List<AppInfo> appInfos = new ArrayList<AppInfo>();
            if (null != list && list.equals("all")) { // 获取非子类型全部app列表
                appTypesList = findAllSubTypeByParentId(type_id);
                if (null != appTypesList && appTypesList.size() > 0) {
                    appInfos = appInfoDAO.getAppsByParentTypeIdWithFilterModel(
                            appTypesList, apps_per_page_int, page_index_int,
                            order_type_int, model_id, sdkVersion, drawable_dpi,
                            CPU_ABI);
                    is_list_all = true;

                } else {
                    appType = appTypeDAO.findById(type_id);
                    appInfos = appInfoDAO.getAppsByLeafTypeIdWithFilterModel(
                            appType, apps_per_page_int, page_index_int,
                            order_type_int, model_id, sdkVersion, drawable_dpi,
                            CPU_ABI);
                }
            } else { // 获取子类型全部app列表
                appType = appTypeDAO.findById(type_id);
                appInfos = appInfoDAO.getAppsByLeafTypeIdWithFilterModel(
                        appType, apps_per_page_int, page_index_int,
                        order_type_int, model_id, sdkVersion, drawable_dpi,
                        CPU_ABI);

            }

            if (null != appInfos) {
                int appInfosSize = appInfos.size();

                if (cs_int == 0) {
                    if (appInfosSize == (apps_per_page_int + 1)) {
                        maxpage = page_index_int + 1;
                        appInfos.remove(appInfosSize - 1); // 去掉最后一个
                    }
                    total_count = appInfos.size(); // 当前页个数
                } else {
                    if (null != appVersion && appVersion.equals("400")
                            && page_index_int > 1) { // 第一页返回total_count为所有app数量
                        if (appInfosSize == (apps_per_page_int + 1)) {
                            maxpage = page_index_int + 1;
                            appInfos.remove(appInfosSize - 1); // 去掉最后一个
                        }
                        total_count = appInfos.size(); // 当前页个数
                    } else {
                        if (is_list_all) {
                            total_count = appInfoDAO
                                    .countAppsByParentTypeIdWithFilterModel(
                                            appTypesList, apps_per_page_int,
                                            page_index_int, order_type_int,
                                            model_id, sdkVersion, drawable_dpi,
                                            CPU_ABI);
                        } else {
                            total_count = appInfoDAO
                                    .countAppsByLeafTypeIdWithFilterModel(
                                            appType, modelClass);
                        }
                        maxpage = (int) Math.ceil((float) total_count
                                / apps_per_page_int);

                        if (appInfosSize == (apps_per_page_int + 1)) {
                            appInfos.remove(appInfosSize - 1); // 去掉最后一个
                        }
                    }

                }

                sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><PackageList maxpage=\"");
                sb.append(maxpage);
                sb.append("\" PageNo=\"");
                sb.append(page_index_int);
                sb.append("\" pageSize=\"");
                sb.append(apps_per_page_int);
                sb.append("\" TotalCount=\"");
                sb.append(total_count);
                sb.append("\">");
                // sb.append(appInfoDAO.getXmlForAppInfos(appInfos,
                // appVersion));
                sb.append(AppInfoTXML.getXmlForAppInfos(appInfos, appVersion));// by
                                                                               // time
                sb.append("</PackageList>");

            } else {
                sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><PackageList maxpage=\"");
                sb.append(maxpage);
                sb.append("\" PageNo=\"");
                sb.append(page_index_int);
                sb.append("\" pageSize=\"");
                sb.append(apps_per_page_int);
                sb.append("\" TotalCount=\"");
                sb.append(total_count); // 为0
                sb.append("\">");
                sb.append("</PackageList>");
            }

            return sb.toString();

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 找出指定类型的所有字类型
     */
    public List<AppType> findAllSubTypeByParentId(Integer id) throws Exception {
        List<AppType> list_type = new ArrayList<AppType>();
        List<AppType> allSubType = appTypeDAO.findSubType(id); // 根据父类型找出所有子类型
        for (AppType appType : allSubType) {
            all_leaf_type.clear();
            List<AppType> nextSubType = new ArrayList<AppType>();
            nextSubType = appTypeDAO.findSubType(appType.getId());
            if (null != nextSubType && nextSubType.size() > 0) {
                nextSubType = findLeafSubType(nextSubType);
                for (AppType subType : nextSubType) {
                    list_type.add(subType);
                }
            } else {
                list_type.add(appType);
            }
        }
        return list_type;
    }

    public List<AppType> findLeafSubType(List<AppType> list_type)
            throws Exception {

        for (AppType appType : list_type) {
            List<AppType> nextSubType = new ArrayList<AppType>();
            nextSubType = appTypeDAO.findSubType(appType.getId());
            if (null != nextSubType && nextSubType.size() > 0) {
                findLeafSubType(nextSubType);
            } else {
                all_leaf_type.add(appType);
            }
        }
        return all_leaf_type;
    }

    // end:手机接口

    @Override
    public String getTypeForJson(String type_id, String cs) throws Exception {
        // int cs_int = 0;
        // if (null != cs && !"".equals(cs)) {
        // cs_int = Integer.parseInt(cs);
        // }

        Integer typeId = Integer.parseInt(StringUtils.defaultIfEmpty(type_id,
                "0"));
        String tag = null;
        if ("0".equals(type_id)) {
            tag = "1";
        } else {
            tag = "2";
        }
        List<Map<String, String>> mtypes = new ArrayList<Map<String, String>>();

        PersonalType type = new PersonalType();
        type.setTag(tag.charAt(0));
        List<PersonalType> pTypes = personalTypeService.list(type);
        for (PersonalType personal : pTypes) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", objectTStr(personal.getId()));
            map.put("title_zh", objectTStr(personal.getName()));
            map.put("title_en", objectTStr(""));
            map.put("icon_url",
                    UtilTool.getDownloadImageHttpURL(personal.getIcon()));
            map.put("type", "2");// 1 普通分类 2 个性化分类
            mtypes.add(map);
        }

        List<Object[]> types = appTypeDAO.findAllType(typeId);
        for (Object[] array : types) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", array[0].toString());
            map.put("parent_id", objectTStr(array[1]));
            map.put("title_zh", objectTStr(array[2]));
            map.put("title_en", objectTStr(array[3]));
            map.put("icon_url",
                    UtilTool.getDownloadImageHttpURL(objectTStr(array[4])));
            map.put("icon_url2",
                    UtilTool.getDownloadImageHttpURL(objectTStr(array[5])));
            map.put("icon_url3",
                    UtilTool.getDownloadImageHttpURL(objectTStr(array[6])));
            map.put("app_names", objectTStr(array[7]));
            map.put("type", "1");// 1 普通分类 2 个性化分类
            mtypes.add(map);
        }
        ResultObject<List<Map<String, String>>> result = new ResultObject<List<Map<String, String>>>();
        result.setResult(true);
        result.setValue(mtypes);
        return JackSonParser.bean2Json(result);
    }

    private String objectTStr(Object obj) {
        if (obj != null) {
            return obj.toString();
        }
        return "";
    }

    @Override
    public String getAppByTypeForJson(String model, int typeId,
            List<String> typeIds, PageVO page, Integer order_type)
            throws Exception {
        Model modelObj = filter(model);
        // //手机端特殊的分页处理算法，
        // {
        // page.setNumPerPage(page.getNumPerPage()+1);
        // }

        List<CommonResultAppInfo> list = appInfoDAO.getAppInfoList(typeIds,
                typeId, page, modelObj, order_type);

        ResultObject<List<CommonResultAppInfo>> result = new ResultObject<List<CommonResultAppInfo>>();
        // 手机端特殊的分页处理算法，
        {
            if (list != null
                    && (page != null && list.size() == page.getNumPerPage() + 1)) {

                list.remove(list.size() - 1);
                result.setMaxPage(page.getCurrentPageNum() + 1);
                result.setTotalCount(list.size());
                result.setPageNo(page.getCurrentPageNum());
                result.setPageSize(page.getNumPerPage());
            } else {
                if (list == null) {
                    list = Collections.emptyList();
                } else {
                    result.setMaxPage(page.getCurrentPageNum());
                    result.setTotalCount(list.size());
                    result.setPageNo(page.getCurrentPageNum());
                    result.setPageSize(page.getNumPerPage());
                }
            }
        }
        result.setValue(list);
        result.setResult(true);
        return JackSonParser.bean2Json(result);
    }

    // 条件过滤介入点，目前支持机型过滤
    private Model filter(String model) throws Exception {
        if (StringUtils.isNotBlank(model)) {
            // 获取机型相关信息
            // model = model.split("@")[0];
            return modelInfoService.findModelByMdName(model);// modelInfoDAO.findModelByMdName(model.trim());
            // if (null != modelClass) {
            // // series_id = modelClass.getSeries_id();
            // info.setMinSdkVersion(modelClass.getSdkVersion());
            // info.setMaxSdkVersion(modelClass.getSdkVersion());
            // info.setCPU_ABI(modelClass.getCPU_ABI());
            // info.setFilter_model(String.valueOf(modelClass.getId()));
            // }
        }
        return null;
    }
}
