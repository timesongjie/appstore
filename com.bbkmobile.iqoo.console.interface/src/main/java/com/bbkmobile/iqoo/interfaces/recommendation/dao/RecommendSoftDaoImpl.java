package com.bbkmobile.iqoo.interfaces.recommendation.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.dao.recommendation.RecommendApp;
import com.bbkmobile.iqoo.console.dao.recommendation.RecommendGame;
import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;
import com.bbkmobile.iqoo.interfaces.common.AppInfoFilter;
import com.bbkmobile.iqoo.interfaces.common.SqlBuffer;

@Repository("iRecommendSoftDao")
public class RecommendSoftDaoImpl extends AnnotationBaseDao implements
        RecommendSoftDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<CommonResultAppInfo> getRecommendAppListForInterface(
            final PageVO page, RecommendApp recommendApp, final Model model)
            throws Exception {
        return getHibernateTemplate().execute(
                new HibernateCallback<List<CommonResultAppInfo>>() {

                    @Override
                    public List<CommonResultAppInfo> doInHibernate(
                            Session session) throws HibernateException,
                            SQLException {
                        return AppInfoFilter.execute(session, page, model,
                                new SqlBuffer() {
                                    @Override
                                    public String preSql() {
                                        return "select this_.show_order as sortOrder,appinfo1_.app_remark,";
                                    }

                                    @Override
                                    public String postSql() {
                                        return " from t_app_recommend this_ ,t_app_info appinfo1_ where this_.app_id=appinfo1_.id ";
                                    }

                                    @Override
                                    public String orderSq() {
                                        return " order by this_.show_order asc ";
                                    }

                                    @Override
                                    public void queryAssign(
                                            PreparedStatement statement,
                                            Map<String, Integer> placeholder)
                                            throws SQLException {
                                        statement.setInt(
                                                placeholder.get("size"),
                                                page.getNumPerPage() + 1);// 接口分页
                                    }
                                });
                    }

                });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CommonResultAppInfo> getRecommendGameListForInterface(
            final PageVO page, final RecommendGame recommendGame,
            final Model model) throws Exception {
        /**/
        return getHibernateTemplate().execute(
                new HibernateCallback<List<CommonResultAppInfo>>() {

                    @Override
                    public List<CommonResultAppInfo> doInHibernate(
                            Session session) throws HibernateException,
                            SQLException {
                        // AppInfo appInfo = null;
                        // if (recommendGame != null) {
                        // appInfo = recommendGame.getAppInfo();
                        // }
                        return AppInfoFilter.execute(session, page, model,
                                new SqlBuffer() {
                                    @Override
                                    public String preSql() {
                                        return "select this_.show_order as sortOrder,appinfo1_.app_remark,";
                                    }

                                    @Override
                                    public String postSql() {
                                        return " from t_game_recommend this_ ,t_app_info appinfo1_ where this_.app_id=appinfo1_.id ";
                                    }

                                    @Override
                                    public String orderSq() {
                                        return " order by this_.show_order asc ";
                                    }

                                    @Override
                                    public void queryAssign(
                                            PreparedStatement statement,
                                            Map<String, Integer> placeholder)
                                            throws SQLException {
                                        statement.setInt(
                                                placeholder.get("size"),
                                                page.getNumPerPage() + 1);// 接口分页
                                    }
                                });
                    }

                });
    }

    @Override
    public int countRecommendAppRecords(RecommendApp recommendApp, Model model)
            throws Exception {
        Criteria criteria = getSession().createCriteria(RecommendApp.class,
                "recommendApp");
        if (recommendApp != null) {
            AppInfo info = recommendApp.getAppInfo();
            if (info != null) {
                filter(info, model, criteria);
            }
        }
        return ((Integer) (criteria.setProjection(Projections.rowCount())
                .uniqueResult())).intValue();
    }

    @Override
    public int countRecommendGameRecords(RecommendGame recommendGame,
            Model model) throws Exception {
        Criteria criteria = getSession().createCriteria(RecommendGame.class);
        if (recommendGame != null) {
            AppInfo info = recommendGame.getAppInfo();
            if (info != null) {
                filter(info, model, criteria);
            }
        }
        return ((Integer) (criteria.setProjection(Projections.rowCount())
                .uniqueResult())).intValue();
    }

    // //////统计时统一过滤////////
    private void filter(AppInfo info, Model model, Criteria criteria) {
        Criteria app_criteria = criteria.createCriteria("appInfo", "appInfo");

        if (model != null && model.getId() > 0) {// (StringUtils.isNotBlank(info.getFilter_model()))
                                                 // {
            if (model.getSdkVersion() != null) {
                app_criteria
                        .add(Restrictions.or(
                                Restrictions.le("minSdkVersion",
                                        model.getSdkVersion()),
                                Restrictions.isNull("minSdkVersion")));
                app_criteria
                        .add(Restrictions.or(
                                Restrictions.ge("maxSdkVersion",
                                        model.getSdkVersion()),
                                Restrictions.isNull("maxSdkVersion")));
            }
            // if (StringUtils.isNotBlank(model.getCPU_ABI())) {
            // String sql = "(CPU_ABI is null";
            // String cpuabis[] = model.getCPU_ABI().split(",");
            // for (int i = 0; i < cpuabis.length; i++) {
            // String cpuabi = cpuabis[i];
            // sql = sql + " or CPU_ABI like '%," + cpuabi + ",%'";
            // }
            // sql = sql + ")";
            // app_criteria.add(Restrictions.sqlRestriction(sql));
            // }
            app_criteria
                    .add(Restrictions
                            .sqlRestriction("(appStatus=0 or (appStatus=13 and filter_model not like '%,"
                                    + model.getId() + ",%'))"));
        } else {
            app_criteria.add(Restrictions.ne("appStatus", (short) 12));
        }
    }
    // appInfo按需查询
    // private final String appInfoSql = "  appinfo1_.appAuthor as developer, "
    // +
    // " appinfo1_.downloadCount as download_count, appinfo1_.appApk as download_url, appinfo1_.appIcon as icon_url, "
    // +
    // " appinfo1_.id as id, appinfo1_.official as offical, appinfo1_.appPackage as package_name, "
    // +
    // " appinfo1_.patchs as patchs, appinfo1_.commentCount as raters_count, appinfo1_.avgComment as score,"
    // +
    // " appinfo1_.apkSize as size, appinfo1_.tag as tag, appinfo1_.appCnName as title_zh,"
    // +
    // " appinfo1_.appEnName as title_en, appinfo1_.appVersion as version_name, appinfo1_.appVersionCode as version_code ";
    //
    // private StringBuffer sqlFilter(StringBuffer sql,AppInfo info){
    // if (StringUtils.isNotBlank(info.getFilter_model())) {
    // if(info.getMinSdkVersion() != null){
    // sql.append(" and (appinfo1_.minSdkVersion<= :minSdkVersion or appinfo1_.minSdkVersion is null) ");
    // }
    // if(info.getMaxSdkVersion() != null){
    // sql.append(" and (appinfo1_.maxSdkVersion>= :maxSdkVersion or appinfo1_.maxSdkVersion is null)");
    // }
    // if(StringUtils.isNotBlank(info.getCPU_ABI())){
    // sql.append(" and (CPU_ABI is null or CPU_ABI like :CPU_ABI)");
    // }
    // sql.append(" and (appStatus=0 or (appStatus=13 and filter_model not like :filter_model)) ");
    // }
    // else{
    // sql.append(" and (appStatus != 12");
    // }
    // return sql;
    // }
    // private void queryAssign(SQLQuery query,AppInfo info){
    // if (StringUtils.isNotBlank(info.getFilter_model())) {
    // if(info.getMinSdkVersion() != null){
    // query.setInteger("minSdkVersion", info.getMinSdkVersion());
    // }
    // if(info.getMaxSdkVersion() != null){
    // query.setInteger("maxSdkVersion", info.getMaxSdkVersion());
    // }
    // if(StringUtils.isNotBlank(info.getCPU_ABI())){
    // query.setString("CPU_ABI", transLike(info.getCPU_ABI()));
    // }
    // query.setString("filter_model", transLike(info.getFilter_model()));
    // }
    // }
    // private void queryAddScalar(SQLQuery query){
    // query.addScalar("sortOrder",Hibernate.INTEGER)
    // .addScalar("developer", Hibernate.STRING)
    // .addScalar("download_count", Hibernate.LONG)
    // .addScalar("download_url", Hibernate.STRING)
    // .addScalar("icon_url", Hibernate.STRING)
    // .addScalar("id", Hibernate.LONG)
    // .addScalar("offical", Hibernate.CHARACTER)
    // .addScalar("package_name", Hibernate.STRING)
    // .addScalar("patchs", Hibernate.STRING)
    // .addScalar("raters_count", Hibernate.INTEGER)
    // .addScalar("score", Hibernate.FLOAT)
    // .addScalar("size", Hibernate.INTEGER)
    // .addScalar("tag", Hibernate.SHORT)
    // .addScalar("title_zh", Hibernate.STRING)
    // .addScalar("title_en", Hibernate.STRING)
    // .addScalar("version_name", Hibernate.STRING)
    // .addScalar("version_code", Hibernate.STRING);
    // }
    // private String transLike(String paramter){
    // return "%,"+paramter+",%";
    // }
}
