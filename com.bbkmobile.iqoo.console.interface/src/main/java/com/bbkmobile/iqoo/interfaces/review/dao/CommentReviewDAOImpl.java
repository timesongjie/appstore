package com.bbkmobile.iqoo.interfaces.review.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.dao.appinfo.AppComment;
import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.appinfo.BaiduAppComment;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.userfeedback.CommentGrade;
import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;

@Repository("iCommentReviewDAO")
public class CommentReviewDAOImpl extends AnnotationBaseDao implements
        CommentReviewDAO {

    public CommentGrade getCommentGrade(Long app_id) throws Exception {
        String sql = "select * from t_comment_grade where app_id=" + app_id;
        List<CommentGrade> commentGrades = getSession().createSQLQuery(sql)
                .addEntity(CommentGrade.class).list();
        if (null != commentGrades && commentGrades.size() > 0) {
            return commentGrades.get(0);
        } else {
            return null;
        }
    }

    public boolean isCommented(final RequestParameter requestParameter,
            final String target) throws Exception {
        try {
            Long appId = requestParameter.getIdlong();
            String imei = requestParameter.getImei();
            String appVersionCode = requestParameter.getAppVersionCode();
            Float version = requestParameter.getApp_version();
            int count = 0;

            String queryString = "";
            String tableName = "";

            if (target.equals("local")) {
                tableName = "t_app_comment";
                if (version >= Constants.APPSTORE_VERSION_5_1
                        && null != appVersionCode && !appVersionCode.equals("")) {
                    queryString = "select count(*) from " + tableName
                            + " where app_id=" + appId
                            + " and appversioncode='" + appVersionCode
                            + "' and user_imei='" + imei + "'";
                    count = ((BigInteger) getSession().createSQLQuery(
                            queryString).uniqueResult()).intValue();
                    return (count >= 1) ? true : false;
                }
            } else {
                tableName = "t_baidu_app_comment";
            }

            queryString = "select count(*) from " + tableName
                    + " where app_id=" + appId + " and user_imei='" + imei
                    + "'";
            count = ((BigInteger) getSession().createSQLQuery(queryString)
                    .uniqueResult()).intValue();
            return (count > 2) ? true : false;
            /*
             * if (null!=user_name && !"".equals(user_name)) { queryString =
             * "select id from " + table_name + " where app_id=" + app_id +
             * " and user_name='" + user_name + "'"; }else{ queryString =
             * "select id from " + table_name + " where app_id=" + app_id +
             * " and user_imei='" + imei + "'"; }
             */
            /*
             * query = getSession().createSQLQuery(queryString); List<?> list =
             * query.list(); if (null != list && list.size() > 2) { return true;
             * }else{ return false; }
             */
        } catch (Exception e) {
            throw e;
        }

    }

    public int countAppCommentList(final Long app_id) throws Exception {

        Criteria criteria = getSession().createCriteria(AppComment.class);

        Criteria app_criteria = criteria.createCriteria( // 关联appInfo
                "appInfo", Criteria.LEFT_JOIN);

        app_criteria.add(Restrictions.eq("id", app_id));

        criteria.add(Restrictions.ne("comment_status",
                Constants.COMMENT_REVIEW_NO_PASS)); // Constants.COMMENT_REVIEW_NO_PASS为审核没有通过

        int totalRows = ((Integer) criteria.setProjection(
                Projections.rowCount()).uniqueResult()).intValue(); // 是否为null
        return totalRows;
    }

    public List<AppComment> findAppCommentList(final Long app_id,
            final int apps_per_page, final int page_index) throws Exception {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<AppComment> list = this.getHibernateTemplate().executeFind(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException {
                        Criteria criteria = session.createCriteria(
                                AppComment.class, "comment");
                        Criteria app_criteria = criteria.createCriteria( // 关联appInfo
                                "comment.appInfo", Criteria.LEFT_JOIN);
                        app_criteria.add(Restrictions.eq("comment.appInfo.id",
                                app_id));

                        criteria.setProjection(setCommentProList());

                        criteria.add(Restrictions.ne("comment.comment_status",
                                Constants.COMMENT_REVIEW_NO_PASS)); // Constants.COMMENT_REVIEW_NO_PASS为审核没有通过
                        criteria.addOrder(Order.desc("comment.comment_date"));
                        /*
                         * if(0!=apps_per_page){
                         * criteria.addOrder(Order.desc("comment_date"));
                         * criteria
                         * .setFirstResult((page_index-1)*apps_per_page);
                         * criteria.setMaxResults(apps_per_page);
                         * 
                         * }
                         */
                        if (apps_per_page == Constants.COMMENT_COUNT_FOR_PACKAGE) {
                            criteria.setFirstResult((page_index - 1)
                                    * apps_per_page);
                            criteria.setMaxResults(apps_per_page);

                        } else {
                            criteria.setFirstResult((page_index - 1)
                                    * apps_per_page);
                            criteria.setMaxResults(apps_per_page + 1);
                        }
                        List<AppComment> result = criteria
                                .setResultTransformer(
                                        Transformers
                                                .aliasToBean(AppComment.class))
                                .list();
                        return result;
                    }
                });
        return list;

    }

    private ProjectionList setCommentProList() {
        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("comment.id"), "id");
        proList.add(Projections.property("comment.user_name"), "user_name");
        proList.add(Projections.property("comment.user_imei"), "user_imei");
        proList.add(Projections.property("comment.model"), "model");
        proList.add(Projections.property("comment.score"), "score");
        proList.add(Projections.property("comment.comment"), "comment");
        proList.add(Projections.property("comment.comment_date"),
                "comment_date");
        proList.add(Projections.property("comment.comment_status"),
                "comment_status");
        proList.add(Projections.property("comment.login_type"), "login_type");
        proList.add(Projections.property("comment.appversion"), "appversion");
        return proList;
    }

    private ProjectionList setAppsProList() {
        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("appInfo.avgComment"), "avgComment");
        proList.add(Projections.property("appInfo.commentCount"),
                "commentCount");
        return proList;
    }

    //
    public List<BaiduAppComment> findBaiduAppCommentList(final Long app_id,
            final int apps_per_page, final int page_index) throws Exception {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<BaiduAppComment> list = this.getHibernateTemplate().executeFind(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException {
                        Criteria criteria = session.createCriteria(
                                BaiduAppComment.class, "comment");

                        criteria.add(Restrictions.eq("comment.app_id", app_id));

                        criteria.add(Restrictions.ne("comment.comment_status",
                                Constants.COMMENT_REVIEW_NO_PASS)); // Constants.COMMENT_REVIEW_NO_PASS为审核没有通过

                        criteria.setProjection(setCommentProList());
                        criteria.addOrder(Order.desc("comment_date"));
                        /*
                         * if(0!=apps_per_page){
                         * criteria.addOrder(Order.desc("comment_date"));
                         * criteria
                         * .setFirstResult((page_index-1)*apps_per_page);
                         * criteria.setMaxResults(apps_per_page);
                         * 
                         * }
                         */
                        if (apps_per_page == Constants.COMMENT_COUNT_FOR_PACKAGE) {
                            criteria.setFirstResult((page_index - 1)
                                    * apps_per_page);
                            criteria.setMaxResults(apps_per_page);

                        } else {
                            criteria.setFirstResult((page_index - 1)
                                    * apps_per_page);
                            criteria.setMaxResults(apps_per_page + 1);
                        }

                        List<BaiduAppComment> result = criteria
                                .setResultTransformer(
                                        Transformers
                                                .aliasToBean(BaiduAppComment.class))
                                .list();
                        return result;
                    }
                });
        return list;

    }

    @Override
    public AppInfo getAppInfo(final Long app_id) throws Exception {
        // TODO Auto-generated method stub
        return getHibernateTemplate().execute(new HibernateCallback<AppInfo>() {
            @Override
            public AppInfo doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(AppInfo.class,
                        "appInfo");
                criteria.setProjection(setAppsProList());
                criteria.add(Restrictions.eq("appInfo.id", app_id));
                List<AppInfo> info = criteria.setResultTransformer(
                        Transformers.aliasToBean(AppInfo.class)).list();
                if (info != null && info.size() > 0) {
                    return info.get(0);
                }
                return new AppInfo();
            }
        });
    }

}