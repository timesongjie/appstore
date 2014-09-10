/**
 * AppInfoDAOImpl.java
 * com.bbkmobile.iqoo.console.dao.appinfo
 *
 * Function：  
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-1-3 		dengkehai
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.bbkmobile.iqoo.interfaces.appinfo.dao;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.util.CommonUtil;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.console.constants.UtilTool;
import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.appinfo.BaiduAppId;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.appinfo.SearchKey;
import com.bbkmobile.iqoo.console.dao.appinfo.TAppScreenshot;
import com.bbkmobile.iqoo.console.dao.apptype.AppType;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.interfaces.appinfo.vo.SearchResultForm;
import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;
import com.bbkmobile.iqoo.interfaces.common.AppInfoFilter;
import com.bbkmobile.iqoo.interfaces.common.SqlBuffer;
import com.bbkmobile.iqoo.interfaces.common.log.SaveBrowseAppLogCache;
import com.bbkmobile.iqoo.interfaces.common.log.SaveBrowseLogCache;
import com.bbkmobile.iqoo.interfaces.common.log.SaveDownloadAppLogCache;
import com.bbkmobile.iqoo.interfaces.common.log.SavePackagesUpdateLogCache;
import com.bbkmobile.iqoo.interfaces.common.log.SaveSeachWordLogCache;

/**
 * ClassName:AppInfoDAOImpl Function:
 * 
 * @author dengkehai
 * @version
 * @since Ver 1.1
 * @Date 2012-1-3 下午3:25:31
 * 
 */
@Repository("iAppInfoDAO")
public class AppInfoDAOImpl extends AnnotationBaseDao implements AppInfoDAO {

    @Resource
    private SaveBrowseLogCache saveBrowseLogCache;
    @Resource
    private SaveDownloadAppLogCache saveDownloadAppLogCache;
    @Resource
    private SavePackagesUpdateLogCache savePackagesUpdateLogCache;
    @Resource
    private SaveSeachWordLogCache saveSeachWordLogCache;
    @Resource
    private SaveBrowseAppLogCache saveBrowseAppLogCache;

    /**
     * 根据id查询应用信息
     * 
     * (non-Javadoc)
     * 
     * @see com.bbkmobile.iqoo.console.dao.appinfo.AppInfoDAO#findAppById(java.lang.Long)
     */
    @Override
    public AppInfo findAppById(Long app_id) throws Exception {

        // return getHibernateTemplate().load(AppInfo.class, app_id);

        AppInfo appInfo = null;
        List<AppInfo> appInfos = getHibernateTemplate().find(
                "from AppInfo where id =?", app_id);
        if (null != appInfos && appInfos.size() > 0) {
            return appInfo = (AppInfo) appInfos.get(0);
        }
        return appInfo;

    }

    @Override
    public String getRemarkByAppId(Long id) throws Exception {
        String remark = "";
        String sql = "select app_remark from t_app_info where id=" + id;
        @SuppressWarnings("unchecked")
        List<Object> list = getSession().createSQLQuery(sql)
                .addScalar("app_remark", Hibernate.STRING).list();
        if (null != list && list.size() > 0) {
            remark = list.get(0).toString();
        }
        return remark;
    }

    @Override
    public AppInfo findValidAppById(Long app_id) throws Exception {

        // return getHibernateTemplate().load(AppInfo.class, app_id);

        AppInfo appInfo = null;
        List<AppInfo> appInfos = getHibernateTemplate().find(
                "from AppInfo where id =? and appStatus!=12", app_id);
        if (null != appInfos && appInfos.size() > 0) {
            return appInfo = (AppInfo) appInfos.get(0);
        }
        return appInfo;

    }

    // liuhaiyan@20120517@获取应用截图
    @Override
    @SuppressWarnings("unchecked")
    public List<TAppScreenshot> findAppScreenshot(AppInfo appInfo)
            throws Exception {
        String queryString = "from TAppScreenshot where appInfo=?";
        Session session = getSession();
        Query queryObj = session.createQuery(queryString);
        queryObj.setEntity(0, appInfo);
        return queryObj.list();
    }

    // liuhaiyan@按搜索关键词获取某类型中所有app列表并按机型过滤
    @Override
    public List<AppInfo> getAppsByKeyNameForModel(final SearchKey searchKey,
            final Model modelClass) throws Exception {

        String sql = null;
        String key = searchKey.getKey_name().trim().replaceAll(" ", "%");
        key = "%" + key + "%";
        if (null == modelClass) {
            // sql =
            // "select id,tag,appCnName,appAuthor,appPackage,appCnName,appIcon,avgComment,commentCount,"
            // +
            // "appVersion,appVersionCode,downloadCount,patchs,apkSize,official from t_app_info"
            // + " where"
            // + " (appCnName like '%"
            // + key
            // + "%'"
            // + " or appKeyWord like '%"
            // + searchKey.getKey_name().trim()
            // + "%')"
            // + " and appStatus!="
            // + Constants.APP_OFFSALE
            // + " order by downloadCount desc"
            // + " limit "
            // + (searchKey.getPage_index() - 1)
            // * searchKey.getApps_per_page()
            // + ","
            // + (searchKey.getApps_per_page() + 1);
            sql = "select id,tag,appCnName,appAuthor,appPackage,appCnName,appIcon,avgComment,commentCount,"
                    + "appVersion,appVersionCode,downloadCount,patchs,apkSize,official from t_app_info"
                    + " where"
                    + " (appCnName like ?"
                    + " or appKeyWord like ?"
                    + " )"
                    + " and appStatus!="
                    + Constants.APP_OFFSALE
                    + " order by downloadCount desc"
                    + " limit "
                    + (searchKey.getPage_index() - 1)
                    * searchKey.getApps_per_page()
                    + ","
                    + (searchKey.getApps_per_page() + 1);
        } else {
            /*
             * String subquery= ""; if(null!=modelClass.getCPU_ABI()){ subquery
             * = "(CPU_ABI is null"; String
             * cpuabis[]=modelClass.getCPU_ABI().split(","); for(int i=0;
             * i<cpuabis.length; i++) { String cpuabi=cpuabis[i];
             * subquery=subquery+" or CPU_ABI like '%,"+cpuabi+",%'"; }
             * subquery=subquery+")";
             * 
             * }
             */
            // sql =
            // "select id,tag,appCnName,appAuthor,appPackage,appCnName,appIcon,avgComment,commentCount,"
            // +
            // "appVersion,appVersionCode,downloadCount,patchs,apkSize,official from t_app_info"
            // + " where" + " (appCnName like '%"
            // + key
            // + "%'"
            // + " or appKeyWord like '%"
            // + searchKey.getKey_name().trim()
            // + "%')"
            // + " and (appStatus="
            // + Constants.APP_ONSALE
            // + " or (appStatus="
            // + Constants.APP_PART_ONSALE
            // + " and filter_model not like '%,"
            // + modelClass.getId()
            // + ",%'))"
            // + " and (minSdkVersion is null or minSdkVersion<="
            // + modelClass.getSdkVersion()
            // + ")"
            // + " and (maxSdkVersion is null or maxSdkVersion>="
            // + modelClass.getSdkVersion()
            // + ")"
            // +
            // // " and " + subquery +
            // " order by downloadCount desc"
            // + " limit "
            // + (searchKey.getPage_index() - 1)
            // * searchKey.getApps_per_page()
            // + ","
            // + (searchKey.getApps_per_page() + 1);
            sql = "select id,tag,appCnName,appAuthor,appPackage,appCnName,appIcon,avgComment,commentCount,"
                    + "appVersion,appVersionCode,downloadCount,patchs,apkSize,official from t_app_info"
                    + " where"
                    + " (appCnName like ?"
                    + " or appKeyWord like ?"
                    + " )"
                    + " and (appStatus="
                    + Constants.APP_ONSALE
                    + " or (appStatus="
                    + Constants.APP_PART_ONSALE
                    + " and filter_model not like '%,"
                    + modelClass.getId()
                    + ",%'))"
                    + " and (minSdkVersion is null or minSdkVersion<="
                    + modelClass.getSdkVersion()
                    + ")"
                    + " and (maxSdkVersion is null or maxSdkVersion>="
                    + modelClass.getSdkVersion()
                    + ")"
                    +
                    // " and " + subquery +
                    " order by downloadCount desc"
                    + " limit "
                    + (searchKey.getPage_index() - 1)
                    * searchKey.getApps_per_page()
                    + ","
                    + (searchKey.getApps_per_page() + 1);
        }

        @SuppressWarnings("unchecked")
        List<AppInfo> list = getSession()
                .createSQLQuery(sql)
                .addScalar("id", Hibernate.LONG)
                .addScalar("tag", Hibernate.SHORT)
                .addScalar("appCnName", Hibernate.STRING)
                .addScalar("appAuthor", Hibernate.STRING)
                .addScalar("appPackage", Hibernate.STRING)
                // .addScalar("parent_id", Hibernate.STRING)
                .addScalar("appCnName", Hibernate.STRING)
                // .addScalar("appEnName", Hibernate.STRING)
                .addScalar("appIcon", Hibernate.STRING)
                .addScalar("avgComment", Hibernate.FLOAT)
                .addScalar("commentCount", Hibernate.INTEGER)
                .addScalar("appVersion", Hibernate.STRING)
                .addScalar("appVersionCode", Hibernate.STRING)
                .addScalar("downloadCount", Hibernate.INTEGER)
                .addScalar("patchs", Hibernate.STRING)
                .addScalar("apkSize", Hibernate.INTEGER)
                .addScalar("official", Hibernate.CHARACTER).setString(0, key)
                .setString(1, key)
                .setResultTransformer(Transformers.aliasToBean(AppInfo.class))
                .list();

        return list;
    }

    @Override
    public int getModuleDBVersion(String module, int module_id)
            throws Exception {
        try {
            String sqlString = "select version from t_db_version where module_id="
                    + module_id + " and module='" + module + "'";
            int db_version = (Integer) getSession().createSQLQuery(sqlString)
                    .uniqueResult();
            return db_version;
        } catch (Exception e) {
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AppInfo> getAppInfoListForUpdate(String packages, Model model)
            throws Exception {

        List<AppInfo> appInfoList = null;
        Session session = getSession();
        /*
         * Query query = null; String packagesSql = "(\'" + packages + "\')";
         * packagesSql = packagesSql.replace(",", "\',\'"); String queryString =
         * null; if (null != model) { queryString =
         * "select id,appCnName,appEnName,avgComment,commentCount,appPackage,appVersion,appVersionCode,apkSize,appIcon,patchs,official from t_app_info where appPackage in "
         * + packagesSql + " and " + "(minSdkVersion<=" + model.getSdkVersion()
         * + " or minSdkVersion is null) and (maxSdkVersion>=" +
         * model.getSdkVersion() + " or maxSdkVersion is null) and " +
         * "(appStatus=0 or (appStatus=13 and " + "filter_model not like '%," +
         * model.getId() + ",%'))"; } else { queryString =
         * "select id,appCnName,appEnName,avgComment,commentCount,appPackage,appVersion,appVersionCode,apkSize,appIcon,patchs,official from t_app_info where appPackage in "
         * + packagesSql + " and appStatus!=12"; } query =
         * session.createSQLQuery(queryString) .addScalar("id", Hibernate.LONG)
         * .addScalar("appCnName", Hibernate.STRING) .addScalar("appEnName",
         * Hibernate.STRING) .addScalar("avgComment", Hibernate.FLOAT)
         * .addScalar("commentCount", Hibernate.INTEGER)
         * .addScalar("appPackage", Hibernate.STRING) .addScalar("appVersion",
         * Hibernate.STRING) .addScalar("appVersionCode", Hibernate.STRING)
         * .addScalar("apkSize", Hibernate.INTEGER) .addScalar("appIcon",
         * Hibernate.STRING) .addScalar("patchs", Hibernate.STRING)
         * .addScalar("official", Hibernate.CHARACTER)
         * .setResultTransformer(Transformers.aliasToBean(AppInfo.class));
         */

        Criteria criteria = session.createCriteria(AppInfo.class, "appInfo");
        String[] values = packages.split(",");
        criteria.add(Restrictions.in("appInfo.appPackage", values));
        if (null != model) {
            criteria.add(Restrictions.or(
                    Restrictions.le("appInfo.minSdkVersion",
                            model.getSdkVersion()),
                    Restrictions.isNull("appInfo.minSdkVersion")));
            criteria.add(Restrictions.or(
                    Restrictions.ge("appInfo.maxSdkVersion",
                            model.getSdkVersion()),
                    Restrictions.isNull("appInfo.maxSdkVersion")));
            criteria.add(Restrictions.or(Restrictions.eq("appInfo.appStatus",
                    (short) 0), Restrictions.and(Restrictions.eq(
                    "appInfo.appStatus", (short) 13), Restrictions
                    .not(Restrictions.like("appInfo.filter_model", "%,"
                            + String.valueOf(model.getId()) + ",%")))));
        } else {
            criteria.add(Restrictions.ne("appInfo.appStatus", (short) 12));
        }
        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("id"), "id");
        proList.add(Projections.property("appCnName"), "appCnName");
        proList.add(Projections.property("appEnName"), "appEnName");
        proList.add(Projections.property("avgComment"), "avgComment");
        proList.add(Projections.property("commentCount"), "commentCount");
        proList.add(Projections.property("appPackage"), "appPackage");
        proList.add(Projections.property("appVersion"), "appVersion");
        proList.add(Projections.property("appVersionCode"), "appVersionCode");
        proList.add(Projections.property("apkSize"), "apkSize");
        proList.add(Projections.property("appIcon"), "appIcon");
        proList.add(Projections.property("patchs"), "patchs");
        proList.add(Projections.property("official"), "official");
        criteria.setProjection(proList);
        criteria.setResultTransformer(Transformers.aliasToBean(AppInfo.class));
        appInfoList = criteria.list();

        // List<AppInfo> list = query.list();
        // if (null != list && list.size() > 0) {
        // appInfoList = list;
        // }
        return appInfoList;
    }

    // @Override
    // public AppInfo getAppInfoForUpdate(String app_package, Model model)
    // throws Exception {
    //
    // AppInfo appInfo = null;
    // Session session = getSession();
    // Query query = null;
    //
    // String queryString = "";
    // if (null != model) {
    // /*
    // * String CPU_ABI = model.getCPU_ABI(); String sql = ""; if (CPU_ABI
    // * != null) { sql = "(CPU_ABI is null"; String cpuabis[] =
    // * CPU_ABI.split(","); for (int i = 0; i < cpuabis.length; i++) {
    // * String cpuabi = cpuabis[i]; sql = sql + " or CPU_ABI like '%," +
    // * cpuabi + ",%'"; } sql = sql + ")"; }
    // */
    // queryString =
    // "select id,appCnName,appEnName,avgComment,commentCount,appPackage,appVersion,appVersionCode,apkSize,appIcon,patchs,official from t_app_info where appPackage='"
    // + app_package
    // + "' and "
    // + "(minSdkVersion<="
    // + model.getSdkVersion()
    // + " or minSdkVersion is null) and (maxSdkVersion>="
    // + model.getSdkVersion()
    // + " or maxSdkVersion is null) and " // + sql + " and "
    // + "(appStatus=0 or (appStatus=13 and "
    // + "filter_model not like '%," + model.getId() + ",%'))";
    // } else {
    // queryString =
    // "select id,appCnName,appEnName,avgComment,commentCount,appPackage,appVersion,appVersionCode,apkSize,appIcon,patchs,official from t_app_info where appPackage='"
    // + app_package + "' and appStatus!=12";
    // }
    //
    // query = session.createSQLQuery(queryString)
    // .addScalar("id", Hibernate.LONG)
    // .addScalar("appCnName", Hibernate.STRING)
    // .addScalar("appEnName", Hibernate.STRING)
    // .addScalar("avgComment", Hibernate.FLOAT)
    // .addScalar("commentCount", Hibernate.INTEGER)
    // .addScalar("appPackage", Hibernate.STRING)
    // .addScalar("appVersion", Hibernate.STRING)
    // .addScalar("appVersionCode", Hibernate.STRING)
    // .addScalar("apkSize", Hibernate.INTEGER)
    // .addScalar("appIcon", Hibernate.STRING)
    // .addScalar("patchs", Hibernate.STRING)
    // .addScalar("official", Hibernate.CHARACTER)
    // .setResultTransformer(Transformers.aliasToBean(AppInfo.class));
    // // query = session.createSQLQuery(queryString).addEntity(AppInfo.class);
    // List<AppInfo> list = query.list();
    // if (null != list && list.size() > 0) {
    // appInfo = list.get(0);
    // }
    // return appInfo;
    // }

    @Override
    public void updateDownloadCountForAppInfo(AppInfo appInfo) throws Exception {
        String sql = "update AppInfo a set " + "a.downloadCount=? "
                + "where a.id=?";
        getSession().createQuery(sql)
                .setInteger(0, appInfo.getDownloadCount() + 1)
                .setLong(1, appInfo.getId()).executeUpdate();
    }

    private ProjectionList setJoinAppsProList() {
        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("appInfo.id"), "id");
        proList.add(Projections.property("appInfo.tag"), "tag");

        proList.add(Projections.property("appInfo.appCnName"), "appCnName");
        proList.add(Projections.property("appInfo.appEnName"), "appEnName");
        proList.add(Projections.property("appInfo.appPackage"), "appPackage");
        proList.add(Projections.property("appInfo.appIcon"), "appIcon");
        proList.add(Projections.property("appInfo.appAuthor"), "appAuthor");
        proList.add(Projections.property("appInfo.avgComment"), "avgComment");
        proList.add(Projections.property("appInfo.commentCount"),
                "commentCount");
        proList.add(Projections.property("appInfo.appVersion"), "appVersion");
        proList.add(Projections.property("appInfo.appVersionCode"),
                "appVersionCode");
        proList.add(Projections.property("appInfo.apkSize"), "apkSize");
        proList.add(Projections.property("appInfo.downloadCount"),
                "downloadCount");
        proList.add(Projections.property("appInfo.official"), "official");
        proList.add(Projections.property("appInfo.patchs"), "patchs");
        proList.add(Projections.property("appInfo.appDesc"), "appDesc");
        proList.add(Projections.property("appInfo.updateDate"), "updateDate");
        proList.add(Projections.property("appInfo.app_remark"), "app_remark");

        proList.add(Projections.property("appInfo.safe"), "safe");
        proList.add(Projections.property("appInfo.advertise"), "advertise");
        return proList;
    }

    public List<AppInfo> getJoinApps(final Class clazz,
            final RequestParameter requestParameter, final Model model)
            throws Exception {

        Criteria criteria = getSession().createCriteria(clazz);

        Criteria app_criteria = criteria.createAlias("appInfo", "appInfo",
                Criteria.LEFT_JOIN);

        criteria.setProjection(setJoinAppsProList());

        if (null != model) {
            app_criteria.add(Restrictions.or(
                    Restrictions.le("minSdkVersion", model.getSdkVersion()),
                    Restrictions.isNull("minSdkVersion")));

            app_criteria.add(Restrictions.or(
                    Restrictions.ge("maxSdkVersion", model.getSdkVersion()),
                    Restrictions.isNull("maxSdkVersion")));

            app_criteria
                    .add(Restrictions
                            .sqlRestriction("(appStatus=0 or (appStatus=13 and filter_model not like '%,"
                                    + model.getId() + ",%'))"));
        } else {
            app_criteria.add(Restrictions.ne("appInfo.appStatus", (short) 12));
        }

        criteria.addOrder(Order.asc("show_order"));
        // app_criteria.addOrder(Order.desc("updateDate"));

        criteria.setFirstResult((requestParameter.getPage_index() - 1)
                * requestParameter.getApps_per_page());
        criteria.setMaxResults(requestParameter.getApps_per_page() + 1);

        criteria.setResultTransformer(Transformers.aliasToBean(AppInfo.class));
        @SuppressWarnings("unchecked")
        List<AppInfo> appInfos = criteria.list();

        if (null != appInfos && appInfos.size() > 0) {
            return appInfos;
        } else {
            return null;
        }
    }

    // liuhaiyan@保存终端浏览记录
    public boolean saveBrowseLog(RequestParameter requestParameter)
            throws Exception {
        try {
            /*
             * int weekDay=WeekdayUtil.getWeekday(); Session session =
             * getSession(); Query query = null; String sql; //SimpleDateFormat
             * sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
             * 
             * sql = "insert into t_browse_log_"+weekDay+
             * "(imei,model,ip,cfrom,req_id,cs,page_index,elapsedtime,version) "
             * +"value('" +requestParameter.getImei()+"','"
             * +requestParameter.getModel()+"','"
             * +requestParameter.getIp()+"','"
             * +requestParameter.getCfrom()+"','"
             * +requestParameter.getIdStr()+"','"
             * +requestParameter.getCsStr()+"','"
             * +requestParameter.getPageIndexStr()+"','"
             * +requestParameter.getElapsedtime()+"','"
             * +(int)requestParameter.getApp_version()+"')";
             * 
             * query = session.createSQLQuery(sql); query.executeUpdate();
             * 
             * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             * Calendar day = Calendar.getInstance(); String date =
             * sdf.format(day.getTime()).replace("-", ""); String sql =
             * "insert into t_browse_log_" + date +
             * "(imei,model,ip,cfrom,req_id,cs,page_index,elapsedtime,version,channel) "
             * + "value('" + requestParameter.getImei() + "','" +
             * requestParameter.getModel() + "',INET_ATON('" +
             * requestParameter.getIp() + "'),'" + requestParameter.getCfrom() +
             * "','" + requestParameter.getIdStr() + "','" +
             * requestParameter.getCsStr() + "','" +
             * requestParameter.getPageIndexStr() + "','" +
             * requestParameter.getElapsedtime() + "','" + (int)
             * requestParameter.getApp_version() + "','" +
             * requestParameter.getChannle() + "')";
             */
            if (Constants.USE_LOGCACHE) {
                // UpgradeQueryLogCache.getIns().add(sql);
                saveBrowseLogCache.add(requestParameter.getImei(),
                        requestParameter.getModel(), requestParameter.getIp(),
                        String.valueOf(requestParameter.getCfrom()),
                        requestParameter.getIdStr(),
                        requestParameter.getCsStr(),
                        requestParameter.getPageIndexStr(),
                        requestParameter.getElapsedtime(),
                        String.valueOf(requestParameter.getApp_version()),
                        requestParameter.getChannle());
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar day = Calendar.getInstance();
                String date = sdf.format(day.getTime()).replace("-", "");
                String sql = "insert into t_browse_log_"
                        + date
                        + "(imei,model,ip,cfrom,req_id,cs,page_index,elapsedtime,version,channel)value(?,?,INET_ATON(?),?,?,?,?,?,?,?)";
                Session session = getSession();
                session.createSQLQuery(sql)
                        .setParameter(0, requestParameter.getImei())
                        .setParameter(1, requestParameter.getModel())
                        .setParameter(2, requestParameter.getIp())
                        .setParameter(3, requestParameter.getCfrom())
                        .setParameter(4, requestParameter.getIdStr())
                        .setParameter(5, requestParameter.getCsStr())
                        .setParameter(6, requestParameter.getPageIndexStr())
                        .setParameter(7, requestParameter.getElapsedtime())
                        .setParameter(8, requestParameter.getApp_version())
                        .setParameter(9, requestParameter.getChannle())
                        .executeUpdate();
                // getSession().createSQLQuery(sql).executeUpdate();
            }
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean saveDownloadAppLog(RequestParameter requestParameter)
            throws Exception {
        try {
            /*
             * Session session = getSession(); Query query = null; String sql;
             * 
             * sql =
             * "insert into t_app_download_log(imei,model,ip,cfrom,app_id,module_id,related,elapsedtime,cs,version) "
             * +"value('" +requestParameter.getImei()+"','"
             * +requestParameter.getModel()+"','"
             * +requestParameter.getIp()+"','"
             * +requestParameter.getCfrom()+"','"
             * +requestParameter.getIdStr()+"','"
             * +requestParameter.getModule_id()+"','"
             * +requestParameter.getRelated()+"','"
             * +requestParameter.getElapsedtime()+"','"
             * +requestParameter.getCsStr()+"','"
             * +requestParameter.getVersion()+"')";
             * 
             * query = session.createSQLQuery(sql); query.executeUpdate();
             */
            // int module_id = -1;
            long elapsedtime = 0;
            short related = -1;
            short updated = -1;
            short cs = -1;
            String imei = null;
            /*
             * if(null!=requestParameter.getModule_id() &&
             * !requestParameter.getModule_id().equals("")){ module_id =
             * Short.parseShort(requestParameter.getModule_id()); }
             */
            if (null != requestParameter.getElapsedtime()
                    && !requestParameter.getElapsedtime().equals("")) {
                elapsedtime = Long.parseLong(requestParameter.getElapsedtime());
            }
            if (null != requestParameter.getRelated()
                    && !requestParameter.getRelated().equals("")) {
                related = Short.parseShort(requestParameter.getRelated());
            }
            if (null != requestParameter.getUpdate()
                    && !requestParameter.getUpdate().equals("")) {
                updated = Short.parseShort(requestParameter.getUpdate());
            }
            if (null != requestParameter.getCsStr()
                    && !requestParameter.getCsStr().equals("")) {
                cs = Short.parseShort(requestParameter.getCsStr());
            }
            if (null != requestParameter.getImei()
                    && !requestParameter.getImei().equals("")) {
                imei = requestParameter.getImei();
            }
            if (Constants.USE_LOGCACHE) {
                saveDownloadAppLogCache.add(imei, requestParameter.getModel(),
                        requestParameter.getIp(),
                        String.valueOf(requestParameter.getCfrom()),
                        requestParameter.getIdStr(),
                        requestParameter.getModule_id(),
                        String.valueOf(related), String.valueOf(updated),
                        String.valueOf(elapsedtime), String.valueOf(cs),
                        requestParameter.getVersion(),
                        requestParameter.getUser_id(),
                        requestParameter.getUser_name(), String.valueOf(1));
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar day = Calendar.getInstance();
                String date = sdf.format(day.getTime()).replace("-", "");
                String sql = "insert into t_app_download_log_"
                        + date
                        + "(imei,model,ip,cfrom,app_id,module_id,related,updated,elapsedtime,cs,version,uuid,user_name,status)"
                        + "value(?,?,INET_ATON(?),?,?,?,?,?,?,?,?,?,?,?)";
                // String sql = "insert into t_app_download_log_"
                // + date
                // +
                // "(imei,model,ip,cfrom,app_id,module_id,related,updated,elapsedtime,cs,version,uuid,user_name,status) "
                // + "value(" + imei + ",'" + requestParameter.getModel()
                // + "',INET_ATON('" + requestParameter.getIp() + "'),"
                // + requestParameter.getCfrom() + ","
                // + Long.parseLong(requestParameter.getIdStr()) + ",'"
                // + requestParameter.getModule_id() + "'," + related + ","
                // + updated + "," + elapsedtime + "," + cs + ",'"
                // + requestParameter.getVersion() + "','"
                // + requestParameter.getUser_id() + "','"
                // + requestParameter.getUser_name() + "','" + "1'" + ")";
                getSession().createSQLQuery(sql).setParameter(0, imei)
                        .setParameter(1, requestParameter.getModel())
                        .setParameter(2, requestParameter.getIp())
                        .setParameter(3, requestParameter.getCfrom())
                        .setParameter(4, requestParameter.getIdStr())
                        .setParameter(5, requestParameter.getModule_id())
                        .setParameter(6, related).setParameter(7, updated)
                        .setParameter(8, elapsedtime).setParameter(9, cs)
                        .setParameter(10, requestParameter.getVersion())
                        .setParameter(11, requestParameter.getUser_id())
                        .setParameter(12, requestParameter.getUser_name())
                        .setParameter(13, 1).executeUpdate();
            }
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean savePcDownloadAppLog(RequestParameter requestParameter)
            throws Exception {
        try {
            String sql = "insert into t_app_pc_download_log(app_id,package_name,ip,cfrom,version,model,imei,cp_name) "
                    + "value(?,?,?,?,?,?,?,?)";
            getSession().createSQLQuery(sql)
                    .setLong(0, Long.parseLong(requestParameter.getIdStr()))
                    .setString(1, requestParameter.getPackages())
                    .setLong(2, UtilTool.inet_aton(requestParameter.getIp()))
                    .setShort(3, requestParameter.getCfrom())
                    .setString(4, requestParameter.getVersion())
                    .setString(5, requestParameter.getModel())
                    .setString(6, requestParameter.getImei())
                    .setString(7, requestParameter.getCp_name())
                    .executeUpdate();
            return true;
        } catch (Exception e) {
            // e.printStackTrace();
            throw e;
        }
    }

    public boolean saveDownloadBaiduAppLog(RequestParameter requestParameter)
            throws Exception {
        try {
            Session session = getSession();
            Query query = null;
            String sql;

            sql = "insert into t_app_baidu_download_log(imei,model,ip,cfrom,app_id,module_id,related,elapsedtime,cs,version) "
                    + "value(?,?,INET_ATON(?),?,?,?,?,?,?,?) ";
            /*
             * + "value('" + requestParameter.getImei() + "','" +
             * requestParameter.getModel() + "','" + requestParameter.getIp() +
             * "','" + requestParameter.getCfrom() + "','" +
             * requestParameter.getIdStr() + "','" +
             * requestParameter.getModule_id() + "','" +
             * requestParameter.getRelated() + "','" +
             * requestParameter.getElapsedtime() + "','" +
             * requestParameter.getCsStr() + "','" +
             * requestParameter.getVersion() + "')";
             */

            query = session.createSQLQuery(sql)
                    .setString(0, requestParameter.getImei())
                    .setString(1, requestParameter.getModel())
                    .setString(2, requestParameter.getIp())
                    .setShort(3, requestParameter.getCfrom())
                    .setString(4, requestParameter.getIdStr())
                    .setString(5, requestParameter.getModule_id())
                    .setString(6, requestParameter.getRelated())
                    .setString(7, requestParameter.getElapsedtime())
                    .setString(8, requestParameter.getCsStr())
                    .setString(9, requestParameter.getVersion());
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    // liuhaiyan@保存终端更新packages记录
    public boolean savePackagesUpdateLog(RequestParameter requestParameter)
            throws Exception {
        try {
            /*
             * int weekDay=WeekdayUtil.getWeekday(); Session session =
             * getSession(); Query query = null; String sql;
             * 
             * sql = "insert into t_packages_update_log_"+weekDay+
             * "(imei,model,ip,packages,elapsedtime,cs,version) " +"value('"
             * +requestParameter.getImei()+"','"
             * +requestParameter.getModel()+"','"
             * +requestParameter.getIp()+"','"
             * +requestParameter.getPackages()+"','"
             * +requestParameter.getElapsedtime()+"','"
             * +requestParameter.getCsStr()+"','"
             * +requestParameter.getVersion()+"')"; query =
             * session.createSQLQuery(sql); query.executeUpdate();
             */
            if (Constants.USE_LOGCACHE) {
                // UpgradeQueryLogCache.getIns().add(sql);
                savePackagesUpdateLogCache.add(requestParameter.getImei(),
                        requestParameter.getModel(), requestParameter.getIp(),
                        requestParameter.getPackages(),
                        requestParameter.getElapsedtime(),
                        requestParameter.getCsStr(),
                        requestParameter.getVersion());
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar day = Calendar.getInstance();
                String date = sdf.format(day.getTime()).replace("-", "");
                String sql = "insert into t_packages_update_log_"
                        + date
                        + "(imei,model,ip,packages,elapsedtime,cs,version)value(?,?,INET_ATON(?),?,?,?,?) ";
                // String sql = "insert into t_packages_update_log_" + date
                // + "(imei,model,ip,packages,elapsedtime,cs,version) "
                // + "value('" + requestParameter.getImei() + "','"
                // + requestParameter.getModel() + "',INET_ATON('"
                // + requestParameter.getIp() + "'),'"
                // + requestParameter.getPackages() + "','"
                // + requestParameter.getElapsedtime() + "','"
                // + requestParameter.getCsStr() + "','"
                // + requestParameter.getVersion() + "')";
                getSession().createSQLQuery(sql)
                        .setParameter(0, requestParameter.getImei())
                        .setParameter(1, requestParameter.getModel())
                        .setParameter(2, requestParameter.getIp())
                        .setParameter(3, requestParameter.getPackages())
                        .setParameter(4, requestParameter.getElapsedtime())
                        .setParameter(5, requestParameter.getCsStr())
                        .setParameter(6, requestParameter.getVersion())
                        .executeUpdate();
            }
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    // liuhaiyan@保存终端搜索关键词记录
    public boolean saveSeachWordLog(RequestParameter requestParameter)
            throws Exception {
        try {
            /*
             * Session session = getSession(); Query query = null; String sql;
             * 
             * sql =
             * "insert into t_search_word_log(imei,model,ip,word,cfrom,cs,page_index,elapsedtime,version) "
             * +"value('" +requestParameter.getImei()+"','"
             * +requestParameter.getModel()+"','"
             * +requestParameter.getIp()+"','" +requestParameter.getWord()+"','"
             * +requestParameter.getKey()+"','"
             * +requestParameter.getCsStr()+"','"
             * +requestParameter.getPage_index()+"','"
             * +requestParameter.getElapsedtime()+"','"
             * +(int)requestParameter.getApp_version()+"')"; query =
             * session.createSQLQuery(sql); query.executeUpdate();
             */
            if (requestParameter != null && requestParameter.getWord() != null) {
                String key = requestParameter.getWord().trim();
                if (CommonUtil.sql_inj(key)) {
                    Lg.info(LgType.KEYWORD, "非法搜索关键字: " + key);
                    return true;
                }
                if (Constants.USE_LOGCACHE) {
                    // UpgradeQueryLogCache.getIns().add(sql);
                    saveSeachWordLogCache.add(requestParameter.getImei(),
                            requestParameter.getModel(),
                            requestParameter.getIp(), key,
                            requestParameter.getKey(),
                            requestParameter.getCsStr(),
                            String.valueOf(requestParameter.getPage_index()),
                            requestParameter.getElapsedtime(),
                            String.valueOf(requestParameter.getApp_version()));
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar day = Calendar.getInstance();
                    String date = sdf.format(day.getTime()).replace("-", "");
                    String sql = "insert into t_search_word_log_"
                            + date
                            + "(imei,model,ip,word,cfrom,cs,page_index,elapsedtime,version)value(?,?,INET_ATON(?),?,?,?,?,?,?) ";
                    // String sql = "insert into t_search_word_log_"
                    // + date
                    // +
                    // "(imei,model,ip,word,cfrom,cs,page_index,elapsedtime,version) "
                    // + "value('" + requestParameter.getImei() + "','"
                    // + requestParameter.getModel() + "',INET_ATON('"
                    // + requestParameter.getIp() + "'),'" + key + "','"
                    // + requestParameter.getCfrom() + "','"
                    // + requestParameter.getCsStr() + "','"
                    // + requestParameter.getPage_index() + "','"
                    // + requestParameter.getElapsedtime() + "','"
                    // + (int) requestParameter.getApp_version() + "')";
                    getSession().createSQLQuery(sql)
                            .setParameter(0, requestParameter.getImei())
                            .setParameter(1, requestParameter.getModel())
                            .setParameter(2, requestParameter.getIp())
                            .setParameter(3, key)
                            .setParameter(4, requestParameter.getKey())
                            .setParameter(5, requestParameter.getCsStr())
                            .setParameter(6, requestParameter.getPage_index())
                            .setParameter(7, requestParameter.getElapsedtime())
                            .setParameter(8, requestParameter.getApp_version())
                            .executeUpdate();
                }
            }
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean saveBrowseAppLog(RequestParameter requestParameter)
            throws Exception {
        try {
            /*
             * int weekDay=WeekdayUtil.getWeekday(); Session session =
             * getSession(); Query query = null; String sql;
             * 
             * sql = "insert into t_browse_app_log_"+weekDay+
             * "(imei,model,ip,cfrom,app_id,module_id,related,elapsedtime,cs,version) "
             * +"value('" +requestParameter.getImei()+"','"
             * +requestParameter.getModel()+"','"
             * +requestParameter.getIp()+"','"
             * +requestParameter.getCfrom()+"','"
             * +requestParameter.getIdStr()+"','"
             * +requestParameter.getModule_id()+"','"
             * +requestParameter.getRelated()+"','"
             * +requestParameter.getElapsedtime()+"','"
             * +requestParameter.getCsStr()+"','"
             * +(int)requestParameter.getApp_version()+"')";
             * 
             * query = session.createSQLQuery(sql); query.executeUpdate();
             */

            // String sql = "insert into t_browse_app_log_"
            // + date
            // +
            // "(imei,model,ip,cfrom,app_id,module_id,related,elapsedtime,cs,version) "
            // + "value('" + requestParameter.getImei() + "','"
            // + requestParameter.getModel() + "',INET_ATON('"
            // + requestParameter.getIp() + "'),'"
            // + requestParameter.getCfrom() + "','"
            // + requestParameter.getIdStr() + "','"
            // + requestParameter.getModule_id() + "','"
            // + requestParameter.getRelated() + "','"
            // + requestParameter.getElapsedtime() + "','"
            // + requestParameter.getCsStr() + "','"
            // + (int) requestParameter.getApp_version() + "')";
            if (Constants.USE_LOGCACHE) {
                // UpgradeQueryLogCache.getIns().add(sql);
                saveBrowseAppLogCache.add(requestParameter.getImei(),
                        requestParameter.getModel(), requestParameter.getIp(),
                        String.valueOf(requestParameter.getCfrom()),
                        requestParameter.getIdStr(),
                        requestParameter.getModule_id(),
                        requestParameter.getRelated(),
                        requestParameter.getElapsedtime(),
                        requestParameter.getCsStr(),
                        String.valueOf(requestParameter.getApp_version()));
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar day = Calendar.getInstance();
                String date = sdf.format(day.getTime()).replace("-", "");
                String sql = "insert into t_browse_app_log_"
                        + date
                        + "(imei,model,ip,cfrom,app_id,module_id,related,elapsedtime,cs,version)value(?,?,INET_ATON(?),?,?,?,?,?,?,?) ";
                getSession().createSQLQuery(sql)
                        .setParameter(0, requestParameter.getImei())
                        .setParameter(1, requestParameter.getModel())
                        .setParameter(2, requestParameter.getIp())
                        .setParameter(3, requestParameter.getCfrom())
                        .setParameter(4, requestParameter.getIdStr())
                        .setParameter(5, requestParameter.getModule_id())
                        .setParameter(6, requestParameter.getRelated())
                        .setParameter(7, requestParameter.getElapsedtime())
                        .setParameter(8, requestParameter.getCsStr())
                        .setParameter(9, requestParameter.getApp_version())
                        .executeUpdate();
            }
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean saveBrowseBaiduAppLog(RequestParameter requestParameter)
            throws Exception {
        try {
            Session session = getSession();
            Query query = null;
            String sql;

            sql = "insert into t_browse_baidu_app_log(imei,model,ip,cfrom,app_id,module_id,related,elapsedtime,cs,version)"
                    + "value(?,?,INET_ATON(?),?,?,?,?,?,?,?) ";
            // + "value('"
            // + requestParameter.getImei()
            // + "','"
            // + requestParameter.getModel()
            // + "','"
            // + requestParameter.getIp()
            // + "','"
            // + requestParameter.getCfrom()
            // + "','"
            // + requestParameter.getIdStr()
            // + "','"
            // + requestParameter.getModule_id()
            // + "','"
            // + requestParameter.getRelated()
            // + "','"
            // + requestParameter.getElapsedtime()
            // + "','"
            // + requestParameter.getCsStr()
            // + "','"
            // + (int) requestParameter.getApp_version() + "')";

            query = session.createSQLQuery(sql)
                    .setString(0, requestParameter.getImei())
                    .setString(1, requestParameter.getModel())
                    .setString(2, requestParameter.getIp())
                    .setShort(3, requestParameter.getCfrom())
                    .setString(4, requestParameter.getIdStr())
                    .setString(5, requestParameter.getModule_id())
                    .setString(6, requestParameter.getRelated())
                    .setString(7, requestParameter.getElapsedtime())
                    .setString(8, requestParameter.getCsStr())
                    .setFloat(9, requestParameter.getApp_version());
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean saveBrowseAppPackageLog(RequestParameter requestParameter)
            throws Exception {
        try {
            Session session = getSession();
            Query query = null;
            String sql;

            sql = "insert into t_browse_app_package_log(imei,model,ip,cfrom,app_id,app_package,module_id,related,elapsedtime,cs,version)"
                    + "value(?,?,INET_ATON(?),?,?,?,?,?,?,?,?) ";
            // + "value('"
            // + requestParameter.getImei()
            // + "','"
            // + requestParameter.getModel()
            // + "','"
            // + requestParameter.getIp()
            // + "','"
            // + requestParameter.getCfrom()
            // + "','"
            // + requestParameter.getIdStr()
            // + "','"
            // + requestParameter.getPackages()
            // + "','"
            // + requestParameter.getModule_id()
            // + "','"
            // + requestParameter.getRelated()
            // + "','"
            // + requestParameter.getElapsedtime()
            // + "','"
            // + requestParameter.getCsStr()
            // + "','"
            // + (int) requestParameter.getApp_version() + "')";

            query = session.createSQLQuery(sql)
                    .setString(0, requestParameter.getImei())
                    .setString(1, requestParameter.getModel())
                    .setString(2, requestParameter.getIp())
                    .setShort(3, requestParameter.getCfrom())
                    .setString(4, requestParameter.getIdStr())
                    .setString(5, requestParameter.getPackages())
                    .setString(6, requestParameter.getModule_id())
                    .setString(7, requestParameter.getRelated())
                    .setString(8, requestParameter.getElapsedtime())
                    .setString(9, requestParameter.getCsStr())
                    .setFloat(10, requestParameter.getApp_version());
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public BaiduAppId getLocalBaiduAppIdById(Long id) throws Exception {
        List<BaiduAppId> baiduAppIds = getHibernateTemplate().find(
                "from BaiduAppId where id = ?", id);
        if (null != baiduAppIds && baiduAppIds.size() > 0) {
            return baiduAppIds.get(0);
        } else {
            return null;
        }
    }

    @Override
    public AppInfo findOnSaleAppByPackage(String appPackage) throws Exception {
        try {
            String queryString = "from AppInfo where appPackage=? and appStatus!=?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setString(0, appPackage);
            queryObject.setShort(1, (short) 12);
            List list = queryObject.list();
            if (null != list && list.size() > 0) {
                return (AppInfo) list.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            Lg.error(LgType.APPINFO, "根据包名查找app信息时出错，error=" + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean appIsValidForModel(Long app_id, Model model)
            throws Exception {

        String sql = "";
        if (null != model) {
            sql = "select id from t_app_info where id="
                    + app_id
                    + " and (appStatus=0 or (appStatus=13 and filter_model not like '%,"
                    + model.getId() + ",%'))";
        } else {
            sql = "select id from t_app_info where id=" + app_id
                    + " and appStatus!=12";
        }

        List list = getSession().createSQLQuery(sql)
                .addScalar("id", Hibernate.LONG).list();
        if (null != list && list.size() > 0) {
            return true;
        }
        return false;

    }

    @Override
    public AppInfo findOnSaleApp(final RequestParameter requestParameter)
            throws Exception {
        List list = getHibernateTemplate().execute(
                new HibernateCallback<List>() {

                    @Override
                    public List doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Criteria criteria = session.createCriteria(
                                AppInfo.class, "appInfo");

                        criteria.setProjection(setJoinAppsProList());
                        if (requestParameter.getPackages() != null) {
                            criteria.add(Restrictions.eq("appInfo.appPackage",
                                    requestParameter.getPackages()));
                        }
                        if (requestParameter.getIdStr() != null) {
                            criteria.add(Restrictions.eq("appInfo.id",
                                    Long.valueOf(requestParameter.getIdStr())));
                        }
                        criteria.add(Restrictions.ne("appInfo.appStatus",
                                (short) 12));
                        criteria.setResultTransformer(Transformers
                                .aliasToBean(AppInfo.class));
                        List<AppInfo> list = criteria.list();
                        return list;
                    }
                });
        if (list != null && list.size() > 0) {
            return (AppInfo) list.get(0);
        }
        return null;
    }

    @Override
    public List<AppInfo> getAppsByParentTypeIdWithFilterModel(
            final List<AppType> appTypesList, final int apps_per_page,
            final int page_index, final int order_type, final Short model_id,
            final Integer sdkVersion, final String drawable_dpi,
            final String CPU_ABI) throws Exception {

        @SuppressWarnings({ "rawtypes", "unchecked" })
        List<AppInfo> list = this.getHibernateTemplate().executeFind(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException {
                        Criteria criteria = session
                                .createCriteria(AppInfo.class);

                        criteria.add(Restrictions.in("appType", appTypesList));

                        if (null != model_id) {
                            criteria.add(Restrictions.or(Restrictions.le(
                                    "minSdkVersion", sdkVersion), Restrictions
                                    .isNull("minSdkVersion")));

                            criteria.add(Restrictions.or(Restrictions.ge(
                                    "maxSdkVersion", sdkVersion), Restrictions
                                    .isNull("maxSdkVersion")));
                            /*
                             * if(CPU_ABI!=null){ String sql="(CPU_ABI is null";
                             * String cpuabis[]=CPU_ABI.split(","); for(int i=0;
                             * i<cpuabis.length; i++) { String
                             * cpuabi=cpuabis[i];
                             * sql=sql+" or CPU_ABI like '%,"+cpuabi+",%'"; }
                             * sql=sql+")";
                             * criteria.add(Restrictions.sqlRestriction(sql)); }
                             */
                            criteria.add(Restrictions
                                    .sqlRestriction("(appStatus=0 or (appStatus=13 and filter_model not like '%,"
                                            + model_id + ",%'))"));
                        } else {
                            criteria.add(Restrictions.ne("appStatus",
                                    (short) 12));
                        }

                        switch (order_type) {
                        case Constants.TYPE_ONSALEDATE_ASC:
                            criteria.addOrder(Order.asc("updateDate"));// onSaleDate
                            break;
                        case Constants.TYPE_ONSALEDATE_DESC:
                            criteria.addOrder(Order.desc("updateDate"));

                            break;
                        case Constants.TYPE_DOWNLOADCOUNT_ASC:
                            criteria.addOrder(Order.asc("downloadCount"));
                            break;
                        case Constants.TYPE_DOWNLOADCOUNT_DESC:
                            criteria.addOrder(Order.desc("downloadCount"));
                            break;
                        case Constants.TYPE_AVGCOMMENT_ASC:
                            criteria.addOrder(Order.asc("avgComment"));
                            break;
                        case Constants.TYPE_AVGCOMMENT_DESC:
                            criteria.addOrder(Order.desc("avgComment"));
                            break;

                        default:
                            break;
                        }

                        criteria.setFirstResult((page_index - 1)
                                * apps_per_page);
                        criteria.setMaxResults(apps_per_page + 1);

                        List<AppInfo> result = criteria.list();
                        return result;
                    }
                });
        if (null != list && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    // liuhaiyan@统计：按叶子类型id获取所有app列表并按机型过滤
    @Override
    public int countAppsByLeafTypeIdWithFilterModel(final AppType appType,
            final Model modelClass) throws Exception {

        Criteria criteria = getSession().createCriteria(AppInfo.class);

        criteria.add(Restrictions.eq("appType", appType));

        if (null != modelClass) {
            criteria.add(Restrictions.or(Restrictions.le("minSdkVersion",
                    modelClass.getSdkVersion()), Restrictions
                    .isNull("minSdkVersion")));
            criteria.add(Restrictions.or(Restrictions.ge("maxSdkVersion",
                    modelClass.getSdkVersion()), Restrictions
                    .isNull("maxSdkVersion")));
            /*
             * if (CPU_ABI != null) { String sql = "(CPU_ABI is null"; String
             * cpuabis[] = CPU_ABI.split(","); for (int i = 0; i <
             * cpuabis.length; i++) { String cpuabi = cpuabis[i]; sql = sql +
             * " or CPU_ABI like '%," + cpuabi + ",%'"; } sql = sql + ")";
             * criteria.add(Restrictions.sqlRestriction(sql)); }
             */

            criteria.add(Restrictions
                    .sqlRestriction("(appStatus=0 or (appStatus=13 and filter_model not like '%,"
                            + modelClass.getId() + ",%'))"));
        } else {
            criteria.add(Restrictions.eq("appStatus", (short) 0));
        }

        int totalRows = ((Integer) criteria.setProjection(
                Projections.rowCount()).uniqueResult()).intValue(); // 是否为null
        return totalRows;

    }

    // liuhaiyan@按叶子类型id获取所有app列表并按机型过滤
    @Override
    public List<AppInfo> getAppsByLeafTypeIdWithFilterModel(
            final AppType appType, final int apps_per_page,
            final int page_index, final int order_type, final Short model_id,
            final Integer sdkVersion, final String drawable_dpi,
            final String CPU_ABI) throws Exception {

        @SuppressWarnings({ "rawtypes", "unchecked" })
        List<AppInfo> list = this.getHibernateTemplate().executeFind(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException {
                        Criteria criteria = session
                                .createCriteria(AppInfo.class);

                        criteria.add(Restrictions.eq("appType", appType));

                        if (null != model_id) {
                            criteria.add(Restrictions.or(Restrictions.le(
                                    "minSdkVersion", sdkVersion), Restrictions
                                    .isNull("minSdkVersion")));

                            criteria.add(Restrictions.or(Restrictions.ge(
                                    "maxSdkVersion", sdkVersion), Restrictions
                                    .isNull("maxSdkVersion")));
                            /*
                             * if(CPU_ABI!=null){ String sql="(CPU_ABI is null";
                             * String cpuabis[]=CPU_ABI.split(","); for(int i=0;
                             * i<cpuabis.length; i++) { String
                             * cpuabi=cpuabis[i];
                             * sql=sql+" or CPU_ABI like '%,"+cpuabi+",%'"; }
                             * sql=sql+")";
                             * criteria.add(Restrictions.sqlRestriction(sql)); }
                             */

                            criteria.add(Restrictions
                                    .sqlRestriction("(appStatus=0 or (appStatus=13 and filter_model not like '%,"
                                            + model_id + ",%'))"));
                        } else {
                            criteria.add(Restrictions.ne("appStatus",
                                    (short) 12));
                        }

                        switch (order_type) {
                        case Constants.TYPE_ONSALEDATE_ASC:
                            // criteria.addOrder(Order.asc("onSaleDate"));
                            criteria.addOrder(Order.asc("updateDate"));
                            break;
                        case Constants.TYPE_ONSALEDATE_DESC:
                            // criteria.addOrder(Order.desc("onSaleDate"));
                            criteria.addOrder(Order.desc("updateDate"));
                            break;
                        case Constants.TYPE_DOWNLOADCOUNT_ASC:
                            criteria.addOrder(Order.asc("downloadCount"));
                            break;
                        case Constants.TYPE_DOWNLOADCOUNT_DESC:
                            criteria.addOrder(Order.desc("downloadCount"));
                            break;
                        case Constants.TYPE_AVGCOMMENT_ASC:
                            criteria.addOrder(Order.asc("avgComment"));
                            break;
                        case Constants.TYPE_AVGCOMMENT_DESC:
                            criteria.addOrder(Order.desc("avgComment"));
                            break;
                        default:
                            break;
                        }
                        criteria.setFirstResult((page_index - 1)
                                * apps_per_page);
                        criteria.setMaxResults(apps_per_page + 1);

                        List<AppInfo> result = criteria.list();
                        return result;
                    }
                });
        if (null != list && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    // liuhaiyan@统计：按父类型id获取所有子类型app列表并按机型过滤
    @Override
    public int countAppsByParentTypeIdWithFilterModel(
            final List<AppType> appTypesList, final int apps_per_page,
            final int page_index, final int order_type, final Short model_id,
            final Integer sdkVersion, final String drawable_dpi,
            final String CPU_ABI) throws Exception {

        Criteria criteria = getSession().createCriteria(AppInfo.class);

        criteria.add(Restrictions.in("appType", appTypesList));

        if (null != model_id) {
            criteria.add(Restrictions.or(
                    Restrictions.le("minSdkVersion", sdkVersion),
                    Restrictions.isNull("minSdkVersion")));

            criteria.add(Restrictions.or(
                    Restrictions.ge("maxSdkVersion", sdkVersion),
                    Restrictions.isNull("maxSdkVersion")));
            /*
             * if (CPU_ABI != null) { String sql = "(CPU_ABI is null"; String
             * cpuabis[] = CPU_ABI.split(","); for (int i = 0; i <
             * cpuabis.length; i++) { String cpuabi = cpuabis[i]; sql = sql +
             * " or CPU_ABI like '%," + cpuabi + ",%'"; } sql = sql + ")";
             * criteria.add(Restrictions.sqlRestriction(sql)); }
             */
            criteria.add(Restrictions
                    .sqlRestriction("(appStatus=0 or (appStatus=13 and filter_model not like '%,"
                            + model_id + ",%'))"));
        } else {
            criteria.add(Restrictions.eq("appStatus", (short) 0));
        }

        int totalRows = ((Integer) criteria.setProjection(
                Projections.rowCount()).uniqueResult()).intValue(); // 是否为null
        return totalRows;

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CommonResultAppInfo> getAppInfoList(
            final List<String> appTypeIdsList, final int typeId,
            final PageVO page, Model model, final Integer order_type)
            throws Exception {
        Session session = getSession();
        return AppInfoFilter.execute(session, page, model, new SqlBuffer() {
            @Override
            public String preSql() {
                return "select ";
            }

            @Override
            public String postSql() {
                if (appTypeIdsList != null && appTypeIdsList.size() > 0) {
                    return " from t_app_info appinfo1_ where  appinfo1_.appType in(:appType)";
                }
                return " from t_app_info appinfo1_ where  appinfo1_.appType=:appType";
            }

            @Override
            public String orderSq() {
                String orderSq = " order by appinfo1_.updateDate desc ";
                switch (order_type) {
                case Constants.TYPE_ONSALEDATE_ASC:
                    // criteria.addOrder(Order.asc("onSaleDate"));
                    orderSq = " order by appinfo1_.updateDate asc ";
                    break;
                case Constants.TYPE_ONSALEDATE_DESC:
                    // criteria.addOrder(Order.desc("onSaleDate"));
                    orderSq = " order by appinfo1_.updateDate desc ";
                    break;
                case Constants.TYPE_DOWNLOADCOUNT_ASC:
                    orderSq = " order by appinfo1_.downloadCount asc ";
                    break;
                case Constants.TYPE_DOWNLOADCOUNT_DESC:
                    orderSq = " order by appinfo1_.downloadCount desc ";
                    break;
                case Constants.TYPE_AVGCOMMENT_ASC:
                    orderSq = " order by appinfo1_.avgComment asc ";
                    break;
                case Constants.TYPE_AVGCOMMENT_DESC:
                    orderSq = " order by appinfo1_.avgComment desc ";
                    break;
                default:
                    break;
                }
                return orderSq;
            }

            @Override
            public void queryAssign(PreparedStatement statement,
                    Map<String, Integer> placeholder) throws SQLException {
                if (appTypeIdsList != null && appTypeIdsList.size() > 0) {
                    StringBuffer appType = new StringBuffer();
                    for (String type : appTypeIdsList) {
                        appType.append(type).append(",");
                    }
                    statement.setString(placeholder.get("appType"),
                            appType.substring(0, appType.length() - 1));
                } else {
                    statement.setInt(placeholder.get("appType"), typeId);
                }
                if (page != null) {
                    statement.setInt(placeholder.get("size"),
                            page.getNumPerPage() + 1);
                }
            }
        });
    }

    @Override
    public BaiduAppId getLocalBaiduAppId(BaiduAppId baiduAppId)
            throws Exception {
        List<BaiduAppId> baiduAppIds = getHibernateTemplate().find(
                "from BaiduAppId where baidu_id = ?", baiduAppId.getBaidu_id());
        if (null != baiduAppIds && baiduAppIds.size() > 0) {
            return baiduAppIds.get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean saveBaiduAppUrl(Long id, String url) throws Exception {

        String queryString = "update BaiduAppId set url=? where id=?";
        Session session = getSession();
        Query queryObj = session.createQuery(queryString);
        queryObj.setString(0, url);
        queryObj.setLong(1, id);
        queryObj.executeUpdate();

        return true;
    }

    // @Override
    // public boolean packageIsExist(String appPackage) throws Exception {
    // try {
    // String queryString = "select appPackage from AppInfo where appPackage=?";
    // Session session = getSession();
    // Query queryObject = session.createQuery(queryString);
    // queryObject.setString(0, appPackage);
    // List list = queryObject.list();
    // if (null != list && list.size() > 0) {
    // return true;
    // }
    // if (isSystemPackage(appPackage)) {
    // return true;
    // }
    // return false;
    //
    // } catch (Exception e) {
    // Lg.error(LgType.APPINFO,
    // "查找数据库中是否有相同包名信息时出错，error=" + e.getMessage());
    // throw e;
    // }
    // }

    @Override
    public AppInfo getAppInfoByPackageName(SearchResultForm requestParas)
            throws Exception {
        AppInfo appInfo = null;
        String key = "%" + requestParas.getWord() + "%";
        String queryString = "select id,appCnName,appEnName,appAuthor,appVersion,appIcon,patchs,apkSize,appVersionCode,appPackage,tag,commentCount,avgComment,downloadCount,official,appStatus from t_app_info"
                + " where appPackage= ?"
                + " and appCnName not like ?"
                + " and appKeyWord not like ?";

        Query query = getSession().createSQLQuery(queryString)
                .addScalar("id", Hibernate.LONG)
                .addScalar("appCnName", Hibernate.STRING)
                .addScalar("appEnName", Hibernate.STRING)
                .addScalar("appAuthor", Hibernate.STRING)
                .addScalar("appVersion", Hibernate.STRING)
                .addScalar("appIcon", Hibernate.STRING)
                .addScalar("patchs", Hibernate.STRING)
                .addScalar("apkSize", Hibernate.INTEGER)
                .addScalar("appVersionCode", Hibernate.STRING)
                .addScalar("appPackage", Hibernate.STRING)
                .addScalar("tag", Hibernate.SHORT)
                .addScalar("commentCount", Hibernate.INTEGER)
                .addScalar("avgComment", Hibernate.FLOAT)
                .addScalar("downloadCount", Hibernate.INTEGER)
                .addScalar("official", Hibernate.CHARACTER)
                .addScalar("appStatus", Hibernate.SHORT)
                .setString(0, requestParas.getAppPackageName())
                .setString(1, key).setString(2, key)
                .setResultTransformer(Transformers.aliasToBean(AppInfo.class));
        @SuppressWarnings("unchecked")
        List<AppInfo> list = query.list();
        if (null != list && list.size() > 0) {
            appInfo = (AppInfo) list.get(0);
        }
        return appInfo;
    }

    @Override
    public BaiduAppId saveBaiduAppId(BaiduAppId baiduAppId) throws Exception {
        getSession().setFlushMode(FlushMode.AUTO);
        getHibernateTemplate().save(baiduAppId);
        return baiduAppId;
    }

    // @Override
    // public boolean isSystemPackage(String appPackage) throws Exception {
    // try {
    // String queryString =
    // "select systemPackage from SystemPackage where systemPackage=?";
    // Session session = getSession();
    // Query queryObject = session.createQuery(queryString);
    // queryObject.setString(0, appPackage);
    // List list = queryObject.list();
    // if (null != list && list.size() > 0) {
    // return true;
    // } else {
    // return false;
    // }
    // } catch (Exception e) {
    // Lg.error(LgType.APPINFO, "判断是否为系统包名时出错，error=" + e.getMessage());
    // throw e;
    // }
    // }

    public int countAppsByKeyNameForModel(final SearchKey searchKey,
            final Model modelClass) throws Exception {

        String sql = "";
        String key = searchKey.getKey_name().trim().replaceAll(" ", "%");

        if (null == modelClass) {
            sql = "select count(*) from t_app_info" + " where"
                    + " (appCnName like '%" + key + "%'"
                    + " or appKeyWord like '%" + searchKey.getKey_name().trim()
                    + "%')" + " and appStatus!=" + Constants.APP_OFFSALE;
        } else {
            /*
             * String subquery= ""; if(null!=modelClass.getCPU_ABI()){ subquery
             * = "(CPU_ABI is null"; String
             * cpuabis[]=modelClass.getCPU_ABI().split(","); for(int i=0;
             * i<cpuabis.length; i++) { String cpuabi=cpuabis[i];
             * subquery=subquery+" or CPU_ABI like '%,"+cpuabi+",%'"; }
             * subquery=subquery+")";
             * 
             * }
             */
            sql = "select count(*) from t_app_info" + " where"
                    + " (appCnName like '%" + key + "%'"
                    + " or appKeyWord like '%" + searchKey.getKey_name().trim()
                    + "%')" + " and (appStatus=" + Constants.APP_ONSALE
                    + " or (appStatus=" + Constants.APP_PART_ONSALE
                    + " and filter_model not like '%," + modelClass.getId()
                    + ",%'))"
                    + " and (minSdkVersion is null or minSdkVersion<="
                    + modelClass.getSdkVersion() + ")"
                    + " and (maxSdkVersion is null or maxSdkVersion>="
                    + modelClass.getSdkVersion() + ")";
            // subquery ;
        }

        @SuppressWarnings("unchecked")
        BigInteger count = (BigInteger) getSession().createSQLQuery(sql)
                .uniqueResult();

        return count.intValue();
    }

    @Override
    public AppInfo findAppByPackage(String appPackage) throws Exception {
        AppInfo appInfo = null;
        try {
            String queryString = "from AppInfo where appPackage=?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setString(0, appPackage);
            List list = queryObject.list();
            if (null != list && list.size() > 0) {
                appInfo = (AppInfo) list.get(0);
            }
        } catch (Exception e) {
            Lg.error(LgType.APPINFO, "根据包名" + appPackage + "查找app信息时出错，error="
                    + e.getMessage());
            throw e;
        }
        return appInfo;
    }

    // liuhaiyan@相关推荐：获取某一个类型的应用
    @Override
    public List<AppInfo> getAppsByLeafType(final AppType appType,
            final PageVO page, final Model modelClass) throws Exception {

        @SuppressWarnings({ "rawtypes", "unchecked" })
        List<AppInfo> list = this.getHibernateTemplate().executeFind(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException {
                        Criteria criteria = session.createCriteria(
                                AppInfo.class, "appInfo");

                        criteria.add(Restrictions
                                .eq("appInfo.appType", appType));

                        if (null != modelClass) {
                            criteria.add(Restrictions.or(Restrictions.le(
                                    "appInfo.minSdkVersion",
                                    modelClass.getSdkVersion()), Restrictions
                                    .isNull("appInfo.minSdkVersion")));

                            criteria.add(Restrictions.or(Restrictions.ge(
                                    "appInfo.maxSdkVersion",
                                    modelClass.getSdkVersion()), Restrictions
                                    .isNull("appInfo.maxSdkVersion")));
                            /*
                             * if(CPU_ABI!=null){ String sql="(CPU_ABI is null";
                             * String cpuabis[]=CPU_ABI.split(","); for(int i=0;
                             * i<cpuabis.length; i++) { String
                             * cpuabi=cpuabis[i];
                             * sql=sql+" or CPU_ABI like '%,"+cpuabi+",%'"; }
                             * sql=sql+")";
                             * criteria.add(Restrictions.sqlRestriction(sql)); }
                             */
                            criteria.add(Restrictions.or(
                                    Restrictions.eq("appInfo.appStatus",
                                            (short) 0),
                                    Restrictions.and(
                                            Restrictions.eq(
                                                    "appInfo.appStatus",
                                                    (short) 13),
                                            Restrictions.not(Restrictions
                                                    .like("appInfo.filter_model",
                                                            "%,"
                                                                    + String.valueOf(modelClass
                                                                            .getId())
                                                                    + ",%")))));
                        } else {
                            criteria.add(Restrictions.ne("appInfo.appStatus",
                                    (short) 12));
                        }
                        criteria.setProjection(getProjection());
                        criteria.setFirstResult(page.getCurrentPageNum());
                        criteria.setMaxResults(page.getNumPerPage());
                        List<AppInfo> result = criteria.setResultTransformer(
                                Transformers.aliasToBean(AppInfo.class)).list();
                        return result;
                    }
                });
        if (null != list && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public List<AppInfo> getAppsOrder(final AppType appType, final PageVO page,
            final Model modelClass) throws Exception {
        @SuppressWarnings({ "rawtypes", "unchecked" })
        List<AppInfo> list = this.getHibernateTemplate().executeFind(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException {
                        Criteria criteria = session.createCriteria(
                                AppInfo.class, "appInfo");
                        // criteria.add(Restrictions
                        // .eq("appInfo.appType", appType));

                        if (null != modelClass) {
                            criteria.add(Restrictions.or(Restrictions.le(
                                    "appInfo.minSdkVersion",
                                    modelClass.getSdkVersion()), Restrictions
                                    .isNull("appInfo.minSdkVersion")));

                            criteria.add(Restrictions.or(Restrictions.ge(
                                    "appInfo.maxSdkVersion",
                                    modelClass.getSdkVersion()), Restrictions
                                    .isNull("appInfo.maxSdkVersion")));
                            /*
                             * if(CPU_ABI!=null){ String sql="(CPU_ABI is null";
                             * String cpuabis[]=CPU_ABI.split(","); for(int i=0;
                             * i<cpuabis.length; i++) { String
                             * cpuabi=cpuabis[i];
                             * sql=sql+" or CPU_ABI like '%,"+cpuabi+",%'"; }
                             * sql=sql+")";
                             * criteria.add(Restrictions.sqlRestriction(sql)); }
                             */
                            criteria.add(Restrictions.or(
                                    Restrictions.eq("appInfo.appStatus",
                                            (short) 0),
                                    Restrictions.and(
                                            Restrictions.eq(
                                                    "appInfo.appStatus",
                                                    (short) 13),
                                            Restrictions.not(Restrictions
                                                    .like("appInfo.filter_model",
                                                            "%,"
                                                                    + String.valueOf(modelClass
                                                                            .getId())
                                                                    + ",%")))));
                        } else {
                            criteria.add(Restrictions.ne("appInfo.appStatus",
                                    (short) 12));
                        }
                        criteria.addOrder(Order.desc("appInfo.downloadCount"));
                        criteria.setProjection(getProjection());
                        criteria.setFirstResult(page.getCurrentPageNum());
                        criteria.setMaxResults(page.getNumPerPage());
                        List<AppInfo> result = criteria.setResultTransformer(
                                Transformers.aliasToBean(AppInfo.class)).list();
                        return result;
                    }
                });
        if (null != list && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public AppInfo getApppInfoWithPackage(String appPackage) throws Exception {
        AppInfo appInfo = null;
        String queryString = "select id,appCnName,appEnName,appAuthor,appVersion,appIcon,patchs,apkSize,appVersionCode,appPackage,tag,commentCount,avgComment,official from t_app_info"
                + " where appPackage=? and appStatus!=12";

        Query query = getSession().createSQLQuery(queryString)
                .addScalar("id", Hibernate.LONG)
                .addScalar("appCnName", Hibernate.STRING)
                .addScalar("appEnName", Hibernate.STRING)
                .addScalar("appAuthor", Hibernate.STRING)
                .addScalar("appVersion", Hibernate.STRING)
                .addScalar("appIcon", Hibernate.STRING)
                .addScalar("patchs", Hibernate.STRING)
                .addScalar("apkSize", Hibernate.INTEGER)
                .addScalar("appVersionCode", Hibernate.STRING)
                .addScalar("appPackage", Hibernate.STRING)
                .addScalar("tag", Hibernate.SHORT)
                .addScalar("commentCount", Hibernate.INTEGER)
                .addScalar("avgComment", Hibernate.FLOAT)
                .addScalar("official", Hibernate.CHARACTER)
                .setString(0, appPackage)
                .setResultTransformer(Transformers.aliasToBean(AppInfo.class));
        @SuppressWarnings("unchecked")
        List<AppInfo> list = query.list();
        if (null != list && list.size() > 0) {
            appInfo = (AppInfo) list.get(0);
        }
        return appInfo;
        /*
         * try { String queryString =
         * "select * from t_app_info where appPackage='" + appPackage +
         * "' and appStatus!=12";
         * 
         * Query query =
         * getSession().createSQLQuery(queryString).addEntity(AppInfo.class);
         * 
         * List<AppInfo> list = query.list(); if (null != list && list.size() >
         * 0) { return list.get(0); } return null; } catch (Exception e) {
         * //Lg.error(LgType.APPINFO, "getApppInfoWithPackage出错，error=" +
         * e.getMessage()); throw e; }
         */
    }

    // liuhaiyan@20120619@获取排行榜的app列表并按机型过滤
    @Override
    public List<AppInfo> getTopAppsWithFilterModel(final int order_type,
            final int apps_per_page, final int page_index,
            final Short model_id, final Integer sdkVersion,
            final String drawable_dpi, final String CPU_ABI) throws Exception {

        @SuppressWarnings({ "rawtypes", "unchecked" })
        List<AppInfo> list = this.getHibernateTemplate().executeFind(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException {
                        Criteria criteria = session
                                .createCriteria(AppInfo.class);

                        if (null != model_id) {
                            criteria.add(Restrictions.or(Restrictions.le(
                                    "minSdkVersion", sdkVersion), Restrictions
                                    .isNull("minSdkVersion")));

                            criteria.add(Restrictions.or(Restrictions.ge(
                                    "maxSdkVersion", sdkVersion), Restrictions
                                    .isNull("maxSdkVersion")));
                            /*
                             * if(CPU_ABI!=null){ String sql="(CPU_ABI is null";
                             * String cpuabis[]=CPU_ABI.split(","); for(int i=0;
                             * i<cpuabis.length; i++) { String
                             * cpuabi=cpuabis[i];
                             * sql=sql+" or CPU_ABI like '%,"+cpuabi+",%'"; }
                             * sql=sql+")";
                             * criteria.add(Restrictions.sqlRestriction(sql)); }
                             */
                            criteria.add(Restrictions
                                    .sqlRestriction("(appStatus=0 or (appStatus=13 and filter_model not like '%,"
                                            + model_id + ",%'))"));
                        } else {
                            criteria.add(Restrictions.ne("appStatus",
                                    (short) 12));
                        }

                        if (0 != apps_per_page && 0 != page_index) { // apps_per_page设为0表示查找所有app，便于统计app数量
                            switch (order_type) {
                            case Constants.TOP_DOWNLOADCOUNT_DESC:
                                criteria.addOrder(Order.desc("downloadCount"));
                                break;
                            case Constants.TOP_AVGCOMMENT_DESC:
                                criteria.addOrder(Order.desc("avgComment"));
                                break;
                            case Constants.TOP_ONSALEDATE_DESC:
                                criteria.addOrder(Order.desc("onSaleDate"));
                                break;
                            case Constants.TOP_DOWNLOADCOUNT_ASC:
                                criteria.addOrder(Order.asc("downloadCount"));
                                break;
                            case Constants.TOP_AVGCOMMENT_ASC:
                                criteria.addOrder(Order.asc("avgComment"));
                                break;
                            case Constants.TOP_ONSALEDATE_ASC:
                                criteria.addOrder(Order.asc("onSaleDate"));
                                break;

                            default:
                                break;
                            }

                            criteria.setFirstResult((page_index - 1)
                                    * apps_per_page);
                            criteria.setMaxResults(apps_per_page);
                        }

                        List<AppInfo> result = criteria.list();
                        return result;
                    }
                });
        return list;
    }

    // liuhaiyan@保存手机评论后，相应更改app的总分数、平均分和总评级数
    @Override
    public boolean saveAppInfoForSaveComment(AppInfo appInfo, Float score)
            throws Exception {
        boolean result = false;
        try {
            Float commmentSum = appInfo.getCommentSum();
            appInfo.setCommentSum(commmentSum + score);
            appInfo.setCommentCount(appInfo.getCommentCount() + 1);
            Float avg_comment = (commmentSum + score)
                    / (appInfo.getCommentCount());
            appInfo.setAvgComment(avg_comment);
            getHibernateTemplate().save(appInfo);
            result = true;
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    @Override
    public AppInfo getAppInfoById(Long app_id) throws Exception {
        AppInfo appInfo = null;
        String queryString = "select id,appCnName,appEnName,appVersion,appIcon,patchs,apkSize,appVersionCode,appPackage,tag,commentCount,avgComment,official from t_app_info where id="
                + app_id;

        Query query = getSession().createSQLQuery(queryString)
                .addScalar("id", Hibernate.LONG)
                .addScalar("appCnName", Hibernate.STRING)
                .addScalar("appEnName", Hibernate.STRING)
                .addScalar("appVersion", Hibernate.STRING)
                .addScalar("appIcon", Hibernate.STRING)
                .addScalar("patchs", Hibernate.STRING)
                .addScalar("apkSize", Hibernate.INTEGER)
                .addScalar("appVersionCode", Hibernate.STRING)
                .addScalar("appPackage", Hibernate.STRING)
                .addScalar("tag", Hibernate.SHORT)
                .addScalar("commentCount", Hibernate.INTEGER)
                .addScalar("avgComment", Hibernate.FLOAT)
                .addScalar("official", Hibernate.CHARACTER)
                .setResultTransformer(Transformers.aliasToBean(AppInfo.class));
        @SuppressWarnings("unchecked")
        List<AppInfo> list = query.list();
        if (null != list && list.size() > 0) {
            appInfo = (AppInfo) list.get(0);
        }
        return appInfo;
    }

    @Override
    public boolean packageIsExist(String appPackage) throws Exception {
        try {
            String queryString = "select appPackage from AppInfo where appPackage=?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setString(0, appPackage);
            List list = queryObject.list();
            if (null != list && list.size() > 0) {
                return true;
            }
            return false;

        } catch (Exception e) {
            Lg.error(LgType.APPINFO,
                    "查找数据库中是否有相同包名信息时出错，error=" + e.getMessage());
            throw e;
        }
    }

    public Projection getProjection() {
        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("appInfo.id"), "id");
        proList.add(Projections.property("appInfo.appPackage"), "appPackage");
        proList.add(Projections.property("appInfo.appCnName"), "appCnName");
        proList.add(Projections.property("appInfo.appEnName"), "appEnName");
        proList.add(Projections.property("appInfo.appIcon"), "appIcon");
        proList.add(Projections.property("appInfo.appAuthor"), "appAuthor");
        proList.add(Projections.property("appInfo.downloadCount"),
                "downloadCount");
        proList.add(Projections.property("appInfo.appApk"), "appApk");
        proList.add(Projections.property("appInfo.official"), "official");

        proList.add(Projections.property("appInfo.apkSize"), "apkSize");
        proList.add(Projections.property("appInfo.appVersion"), "appVersion");
        proList.add(Projections.property("appInfo.appVersionCode"),
                "appVersionCode");
        proList.add(Projections.property("appInfo.avgComment"), "avgComment");
        return proList;
    }

    @Override
    public boolean saveLogs(List<String> logs) throws Exception {
        if (null != logs && logs.size() > 0) {
            Session session = getSession();
            for (int i = 0; i < logs.size(); i++) {
                try {
                    session.createSQLQuery(logs.get(i)).executeUpdate();
                } catch (Exception e) {
                    Lg.error(LgType.APPINFO, "插入日志失败：[" + logs.get(i) + "]");
                }
                if (i % 100 == 0) {
                    session.flush();
                    session.clear();
                }
            }
        }
        return true;
    }
}
