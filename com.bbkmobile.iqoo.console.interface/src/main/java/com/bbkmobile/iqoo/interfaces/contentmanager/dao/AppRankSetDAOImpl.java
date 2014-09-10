package com.bbkmobile.iqoo.interfaces.contentmanager.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.contentmanage.ApplicationTop;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.top.dao.TopBase;
import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;
import com.bbkmobile.iqoo.interfaces.common.AppInfoFilter;
import com.bbkmobile.iqoo.interfaces.common.SqlBuffer;

@Repository("iAppRankSetDAO")
public class AppRankSetDAOImpl extends AnnotationBaseDao implements
        AppRankSetDAO {

    public List<TopBase> getRankTops(final int order_type_int,
            final int apps_per_page_int, final int page_index_int,
            final Short model_id, final Integer sdkVersion,
            final String drawable_dpi, final String CPU_ABI,
            final String tableName) throws Exception {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<TopBase> list = this.getHibernateTemplate().executeFind(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException {
                        Class topClass = null;
                        try {
                            topClass = Class
                                    .forName("com.bbkmobile.iqoo.console.top.dao."
                                            + tableName);
                        } catch (ClassNotFoundException e) {
                            // e.printStackTrace();
                        }
                        Criteria criteria = session.createCriteria(topClass);
                        Criteria downloadRankApp_criteria = criteria
                                .createCriteria("appInfo", Criteria.LEFT_JOIN);
                        if (null != model_id) {

                            downloadRankApp_criteria.add(Restrictions.or(
                                    Restrictions
                                            .le("minSdkVersion", sdkVersion),
                                    Restrictions.isNull("minSdkVersion")));

                            downloadRankApp_criteria.add(Restrictions.or(
                                    Restrictions
                                            .ge("maxSdkVersion", sdkVersion),
                                    Restrictions.isNull("maxSdkVersion")));
                            /*
                             * if (CPU_ABI != null) { String sql =
                             * "(CPU_ABI is null"; String cpuabis[] =
                             * CPU_ABI.split(","); for (int i = 0; i <
                             * cpuabis.length; i++) { String cpuabi =
                             * cpuabis[i]; sql = sql + " or CPU_ABI like '%," +
                             * cpuabi + ",%'"; } sql = sql + ")";
                             * downloadRankApp_criteria.add(Restrictions
                             * .sqlRestriction(sql)); }
                             */
                            downloadRankApp_criteria.add(Restrictions
                                    .sqlRestriction("(appStatus=0 or (appStatus=13 and filter_model not like '%,"
                                            + model_id + ",%'))"));
                        } else {
                            downloadRankApp_criteria.add(Restrictions.ne(
                                    "appStatus", (short) 12));
                        }

                        criteria.setFirstResult((page_index_int - 1)
                                * apps_per_page_int);
                        criteria.setMaxResults(apps_per_page_int);
                        criteria.addOrder(Order.asc("show_order"));
                        List<ApplicationTop> result = criteria.list();
                        return result;
                    }
                });
        if (null != list && list.size() > 0) {
            return list;
        } else {
            return null;
        }

    }

    public List<AppInfo> getScoreTopApps(final int order_type_int,
            final int apps_per_page_int, final int page_index_int,
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
                             * if (CPU_ABI != null) { String sql =
                             * "(CPU_ABI is null"; String cpuabis[] =
                             * CPU_ABI.split(","); for (int i = 0; i <
                             * cpuabis.length; i++) { String cpuabi =
                             * cpuabis[i]; sql = sql + " or CPU_ABI like '%," +
                             * cpuabi + ",%'"; } sql = sql + ")";
                             * criteria.add(Restrictions.sqlRestriction(sql)); }
                             */
                            criteria.add(Restrictions
                                    .sqlRestriction("(appStatus=0 or (appStatus=13 and filter_model not like '%,"
                                            + model_id + ",%'))"));
                        } else {
                            criteria.add(Restrictions.ne("appStatus",
                                    (short) 12));
                        }

                        criteria.add(Restrictions.ge("downloadCount", 10000));
                        criteria.addOrder(Order.desc("avgComment"));
                        criteria.addOrder(Order.desc("downloadCount"));
                        criteria.setFirstResult((page_index_int - 1)
                                * apps_per_page_int);
                        criteria.setMaxResults(apps_per_page_int);

                        List<AppInfo> result = criteria.list();
                        return result;
                    }
                });
        return list;
    }

    @Override
    public List<CommonResultAppInfo> getTopApps(final PageVO page,
            final TopBase top, final Model model, final String topName)
            throws Exception {
        List<CommonResultAppInfo> list = getHibernateTemplate().execute(
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
                                        return " from "
                                                + topName
                                                + " this_ , t_app_info appinfo1_ where  this_.app_id=appinfo1_.id ";
                                    }

                                    @Override
                                    public String orderSq() {
                                        return "order by this_.show_order asc ";
                                    }
                                });
                    }

                });
        return list;
    }

    @Override
    public List<CommonResultAppInfo> getScoreTopApps(final PageVO page,
            AppInfo appInfo, final Model model) throws Exception {
        List<CommonResultAppInfo> list = getHibernateTemplate().execute(
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
                                        return " from  t_app_info appinfo1_ where  appinfo1_.downloadCount >= 10000 ";
                                    }

                                    @Override
                                    public String orderSq() {
                                        return "order by appinfo1_.avgComment desc,appinfo1_.downloadCount desc ";
                                    }
                                });
                    }

                });
        return list;
    }

    @Override
    public List<CommonResultAppInfo> getTopAppsWithFilterModel(
            final int order_type, final PageVO page, final Model model)
            throws Exception {

        @SuppressWarnings({ "rawtypes", "unchecked" })
        List<CommonResultAppInfo> list = this.getHibernateTemplate()
                .executeFind(new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException {
                        return AppInfoFilter.execute(session, page, model,
                                new SqlBuffer() {
                                    @Override
                                    public String preSql() {
                                        return "select ";
                                    }

                                    @Override
                                    public String postSql() {
                                        return " from  t_app_info appinfo1_ where  1 = 1 ";
                                    }

                                    @Override
                                    public String orderSq() {
                                        String orderSql = null;
                                        if (0 != page.getCurrentPageNum()
                                                && 0 != page.getNumPerPage()) { // apps_per_page设为0表示查找所有app，便于统计app数量
                                            switch (order_type) {
                                            case Constants.TOP_DOWNLOADCOUNT_DESC:
                                                orderSql = " order by appinfo1_.downloadCount desc ";
                                                break;
                                            case Constants.TOP_AVGCOMMENT_DESC:
                                                orderSql = " order by appinfo1_.avgComment desc ";
                                                break;
                                            case Constants.TOP_ONSALEDATE_DESC:
                                                orderSql = " order by appinfo1_.onSaleDate desc ";
                                                break;
                                            case Constants.TOP_DOWNLOADCOUNT_ASC:
                                                orderSql = " order by appinfo1_.downloadCount asc ";
                                                break;
                                            case Constants.TOP_AVGCOMMENT_ASC:
                                                orderSql = " order by appinfo1_.avgComment asc ";
                                                break;
                                            case Constants.TOP_ONSALEDATE_ASC:
                                                orderSql = " order by appinfo1_.onSaleDate asc ";
                                                break;
                                            default:
                                                break;
                                            }
                                        }
                                        return orderSql;
                                    }
                                });
                    }
                });
        return list;
    }
}
