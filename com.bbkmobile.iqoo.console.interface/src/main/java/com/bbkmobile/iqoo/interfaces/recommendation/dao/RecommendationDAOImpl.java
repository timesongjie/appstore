package com.bbkmobile.iqoo.interfaces.recommendation.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.common.vo.NewAppsResultAppInfo;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.appinfo.TAppScreenshot;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.dao.recommendation.CellphoneRecommendApp;
import com.bbkmobile.iqoo.console.dao.recommendation.NewApp;
import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;
import com.bbkmobile.iqoo.interfaces.common.AppInfoFilter;
import com.bbkmobile.iqoo.interfaces.common.SqlBuffer;

@Repository("iRecommendationDAO")
public class RecommendationDAOImpl extends AnnotationBaseDao implements
        RecommendationDAO {

    @Override
    public int countAllCellphoneRecommendAppsWithFilterModel(
            final Short series_id, final Short model_id,
            final Integer sdkVersion, final String drawable_dpi,
            final String CPU_ABI) {

        Criteria criteria = getSession().createCriteria(
                CellphoneRecommendApp.class);

        criteria.add(Restrictions.eq("series_id", series_id));

        Criteria adApp_criteria = criteria.createCriteria("appInfo",
                Criteria.LEFT_JOIN);

        if (null != model_id) {

            adApp_criteria.add(Restrictions.or(
                    Restrictions.le("minSdkVersion", sdkVersion),
                    Restrictions.isNull("minSdkVersion")));

            adApp_criteria.add(Restrictions.or(
                    Restrictions.ge("maxSdkVersion", sdkVersion),
                    Restrictions.isNull("maxSdkVersion")));
            /*
             * if (CPU_ABI != null) { String sql = "(CPU_ABI is null"; String
             * cpuabis[] = CPU_ABI.split(","); for (int i = 0; i <
             * cpuabis.length; i++) { String cpuabi = cpuabis[i]; sql = sql +
             * " or CPU_ABI like '%," + cpuabi + ",%'"; } sql = sql + ")";
             * adApp_criteria.add(Restrictions.sqlRestriction(sql)); }
             */
            adApp_criteria
                    .add(Restrictions
                            .sqlRestriction("(appStatus=0 or (appStatus=13 and filter_model not like '%,"
                                    + model_id + ",%'))"));

        } else {
            adApp_criteria.add(Restrictions.eq("appStatus", (short) 0));
        }

        int totalRows = ((Integer) criteria.setProjection(
                Projections.rowCount()).uniqueResult()).intValue(); // 是否为null
        return totalRows;

    }

    @Override
    public int countNewApps(final RequestParameter requestParameter,
            final Model model) throws Exception {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Criteria criteria = getSession().createCriteria(NewApp.class);

        Criteria app_criteria = criteria.createCriteria("appInfo",
                Criteria.LEFT_JOIN);

        if (null != model) {
            app_criteria.add(Restrictions.or(
                    Restrictions.le("minSdkVersion", model.getSdkVersion()),
                    Restrictions.isNull("minSdkVersion")));

            app_criteria.add(Restrictions.or(
                    Restrictions.ge("maxSdkVersion", model.getSdkVersion()),
                    Restrictions.isNull("maxSdkVersion")));
            /*
             * String CPU_ABI = model.getCPU_ABI();
             * 
             * if (CPU_ABI != null) { String sql = "(CPU_ABI is null"; String
             * cpuabis[] = CPU_ABI.split(","); for (int i = 0; i <
             * cpuabis.length; i++) { String cpuabi = cpuabis[i]; sql = sql +
             * " or CPU_ABI like '%," + cpuabi + ",%'"; } sql = sql + ")";
             * app_criteria.add(Restrictions.sqlRestriction(sql)); }
             */
            app_criteria
                    .add(Restrictions
                            .sqlRestriction("(appStatus=0 or (appStatus=13 and filter_model not like '%,"
                                    + model.getId() + ",%'))"));
        } else {
            app_criteria.add(Restrictions.eq("appStatus", (short) 0));
        }
        int totalRows = ((Integer) criteria.setProjection(
                Projections.rowCount()).uniqueResult()).intValue(); // 是否为null
        return totalRows;
    }

    @Override
    public List<CommonResultAppInfo> getCellphoneRecommendAppsWithFilterModel(
            final PageVO page, final Model model) throws Exception {
        return getHibernateTemplate().execute(
                new HibernateCallback<List<CommonResultAppInfo>>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public List<CommonResultAppInfo> doInHibernate(
                            Session session) throws HibernateException,
                            SQLException {
                        return AppInfoFilter.execute(session, page, model,
                                new SqlBuffer() {
                                    @Override
                                    public String preSql() {
                                        return "select ";
                                    }

                                    @Override
                                    public String postSql() {
                                        if (model != null
                                                && model.getSeries_id() > 0) {
                                            return " from t_cellphone_recommend this_ ,t_app_info appinfo1_ "
                                                    + " where this_.app_id = appinfo1_.id and this_.series_id=:series_id ";
                                        } else {
                                            return " from t_cellphone_recommend this_ ,t_app_info appinfo1_ "
                                                    + " where this_.app_id = appinfo1_.id and this_.series_id=0 ";
                                        }
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
                                        if (model != null
                                                && model.getSeries_id() > 0) {
                                            statement.setInt(placeholder
                                                    .get("series_id"), model
                                                    .getSeries_id());
                                        }
                                        statement.setInt(
                                                placeholder.get("size"),
                                                page.getNumPerPage() + 1);
                                    }
                                });
                    }
                });
    }

    @Override
    public List<NewAppsResultAppInfo> getNewsAppsWithFilterModel(
            final PageVO page, final Model model, final boolean showScreen)
            throws Exception {
        return getHibernateTemplate().execute(
                new HibernateCallback<List<NewAppsResultAppInfo>>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public List<NewAppsResultAppInfo> doInHibernate(
                            Session session) throws HibernateException,
                            SQLException {
                        List<NewAppsResultAppInfo> list = AppInfoFilter
                                .execute(session, page, model,
                                        NewAppsResultAppInfo.class,
                                        new SqlBuffer() {
                                            @Override
                                            public String preSql() {
                                                return "select appinfo1_.updateDate as upload_time , appinfo1_.app_remark,";
                                            }

                                            @Override
                                            public String postSql() {
                                                if (model != null
                                                        && model.getSeries_id() > 0) {
                                                    return " from t_newapp_recommend this_ ,t_app_info appinfo1_ "
                                                            + " where this_.app_id = appinfo1_.id  ";
                                                } else {
                                                    return " from t_newapp_recommend this_ ,t_app_info appinfo1_ "
                                                            + " where this_.app_id = appinfo1_.id ";
                                                }
                                            }

                                            @Override
                                            public String orderSq() {
                                                // return
                                                // " order by this_.show_order asc ";
                                                return " order by appinfo1_.updateDate asc ";
                                            }

                                            @Override
                                            public void queryAssign(
                                                    PreparedStatement statement,
                                                    Map<String, Integer> placeholder)
                                                    throws SQLException {
                                                statement.setInt(
                                                        placeholder.get("size"),
                                                        page.getNumPerPage() + 1);
                                            }
                                        });
                        if (list != null && list.size() > 0) {
                            if (showScreen) {
                                for (NewAppsResultAppInfo app : list) {
                                    Long appId = app.getId();
                                    app.setScreenshotList(getAppScreenshot(getAppScreenshot(appId)));
                                }
                            }
                        } else {
                            return Collections.emptyList();
                        }

                        return list;
                    }
                });
    }

    private List<TAppScreenshot> getAppScreenshot(Long id) {
        Session session = getSession();
        String sql = "select tappscreen0_.screenshot as screenshot from t_app_screenshot tappscreen0_  where  tappscreen0_.app_id = ? ";
        List list = session
                .createSQLQuery(sql)
                .addScalar("screenshot")
                .setLong(0, id)
                .setResultTransformer(
                        Transformers.aliasToBean(TAppScreenshot.class)).list();
        // if(list != null && list.size() >0){
        // Set<TAppScreenshot> set = new HashSet<TAppScreenshot>(list.size());
        // for(int i=0; i<list.size(); i++){
        // set.add((TAppScreenshot) list.get(i));
        // }
        // return set;
        // }
        return list;
    }

    private List<String> getAppScreenshot(List<TAppScreenshot> shots) {
        if (shots != null) {
            List<String> list = new ArrayList<String>(shots.size());
            for (TAppScreenshot shot : shots) {
                list.add(shot.getScreenshot());
            }
            return list;
        }
        return null;
    }

    @Override
    public List<CommonResultAppInfo> getInstallAppsWithFilterModel(
            final Model model) throws Exception {
        return getHibernateTemplate().execute(
                new HibernateCallback<List<CommonResultAppInfo>>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public List<CommonResultAppInfo> doInHibernate(
                            Session session) throws HibernateException,
                            SQLException {
                        return AppInfoFilter.execute(session, null, model,
                                new SqlBuffer() {
                                    @Override
                                    public String preSql() {
                                        return "select ";
                                    }

                                    @Override
                                    public String postSql() {
                                        return " from t_editor_recommend this_  ,t_app_info appinfo1_ "
                                                + " where this_.app_id = appinfo1_.id";
                                    }

                                    @Override
                                    public String orderSq() {
                                        return " order by this_.show_order asc ";
                                    }
                                });
                    }
                });
    }
}
