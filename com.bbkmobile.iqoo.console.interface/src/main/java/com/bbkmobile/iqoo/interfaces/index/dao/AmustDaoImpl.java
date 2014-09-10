package com.bbkmobile.iqoo.interfaces.index.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.NewAppsResultAppInfo;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.index.dao.IndexModelApps;
import com.bbkmobile.iqoo.console.index.dao.IndexModelSubTitle;
import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;
import com.bbkmobile.iqoo.interfaces.common.AppInfoFilter;
import com.bbkmobile.iqoo.interfaces.common.ProjectQuery;
import com.bbkmobile.iqoo.interfaces.common.SqlBuffer;
import com.bbkmobile.iqoo.interfaces.index.vo.AmustAppsResultAppInfo;

@Repository
public class AmustDaoImpl extends AnnotationBaseDao implements AmustDao {

    @Override
    public List<NewAppsResultAppInfo> getAmustAppInfo(
            final IndexModelSubTitle title, final Model model, final PageVO page)
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
                                                // return
                                                // " from t_index_model_subtitle subtitle_,t_index_model_apps this_ ,t_app_info appinfo1_ "
                                                // +
                                                // " where this_.app_id = appinfo1_.id and this_.sub_id = subtitle_.id and subtitle_.subtitle = :subtitle and subtitle_.status = 1 ";
                                                return " from t_index_model_apps this_ ,t_app_info appinfo1_ "
                                                        + " where this_.app_id = appinfo1_.id and this_.sub_id = :id";
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
                                                        placeholder.get("id"),
                                                        title.getId());
                                                if (page != null) {
                                                    statement.setInt(
                                                            placeholder
                                                                    .get("size"),
                                                            page.getNumPerPage() + 1);
                                                }
                                            }
                                        });
                        return list;
                    }
                });
    }

    public List<AmustAppsResultAppInfo> projectQuery(Character tag,
            final Model model, final PageVO page) {
        Criteria criteria = getSession().createCriteria(IndexModelApps.class);

        Criteria subCriteria = criteria.createCriteria("model", "model",
                Criteria.LEFT_JOIN);
        // subCriteria.add(Restrictions.in("model.subTitle", titles.toArray()));
        subCriteria.add(Restrictions.eq("model.tag", tag));
        subCriteria.add(Restrictions.eq("model.status", '1'));

        criteria.createAlias("appInfo", "appInfo", Criteria.LEFT_JOIN);

        ProjectionList projectionList = ProjectQuery.setJoinAppsProList();
        projectionList.add(Projections.property("appInfo.updateDate"),
                "upload_time");
        projectionList.add(Projections.property("appInfo.app_remark"),
                "app_remark");
        projectionList.add(Projections.property("model.subTitle"), "title");
        // appCriteria.setProjection(projectionList);
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
        if (page != null) {
            criteria.setFirstResult((page.getCurrentPageNum() - 1)
                    * page.getNumPerPage());
            criteria.setMaxResults(page.getNumPerPage() + 1);
        }
        subCriteria.addOrder(Order.asc("model.show_order"));
        @SuppressWarnings("unchecked")
        List<AmustAppsResultAppInfo> list = criteria
                .setProjection(projectionList)
                .setResultTransformer(
                        Transformers.aliasToBean(AmustAppsResultAppInfo.class))
                .list();
        return list;
    }

    @Override
    public List<AmustAppsResultAppInfo> getAmustAppInfo(Character tag,
            Model model, PageVO page) throws Exception {
        return projectQuery(tag, model, page);
    }
}
