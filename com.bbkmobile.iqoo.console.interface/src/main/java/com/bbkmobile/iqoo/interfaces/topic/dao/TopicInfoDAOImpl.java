package com.bbkmobile.iqoo.interfaces.topic.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.modelmgr.ConsoleConstant;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.dao.topic.ModelTopic;
import com.bbkmobile.iqoo.console.dao.topic.TopicApp;
import com.bbkmobile.iqoo.console.dao.topic.TopicIcon;
import com.bbkmobile.iqoo.console.dao.topic.TopicInfo;
import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;
import com.bbkmobile.iqoo.interfaces.common.AppInfoFilter;
import com.bbkmobile.iqoo.interfaces.common.SqlBuffer;
import com.bbkmobile.iqoo.interfaces.topic.vo.TopicBasicInfo;
import com.bbkmobile.iqoo.interfaces.topic.vo.TopicResultAppInfo;

@Repository("iTopicInfoDAO")
public class TopicInfoDAOImpl extends AnnotationBaseDao implements TopicInfoDAO {

    // begin:手机接口@haiyan

    // 根据系列获取专题
    /* 修改人：zhangyi （推荐的专题按照show_order排序，未推荐的专题按照add_date排序） */
    @SuppressWarnings("unchecked")
    public List<ModelTopic> getModelTopicsBySeries(Model model)
            throws Exception {
        Short seriesId = (short) 0;
        if (null != model) {
            seriesId = model.getSeries_id();
        }
        Short recommend = (short) 1;
        // List<TopicInfo> list = new ArrayList<TopicInfo>();
        /*
         * List<ModelTopic> modelTopics = getHibernateTemplate().find(
         * "from ModelTopic where series_id=? order by show_order", seriesId);
         */

        List<ModelTopic> modelTopics = getHibernateTemplate()
                .find("from ModelTopic where series_id=? and recommend=? order by show_order",
                        seriesId, recommend);
        recommend = 0;
        modelTopics
                .addAll(getHibernateTemplate()
                        .find("from ModelTopic where series_id=? and recommend=? order by  topicInfo.add_date desc",
                                seriesId, recommend));
        /*
         * if (null != modelTopics && modelTopics.size() > 0) { for (ModelTopic
         * modelTopic : modelTopics) { list.add(modelTopic.getTopicInfo()); } }
         */
        return modelTopics;
        /*
         * return (List<TopicInfo>) getHibernateTemplate().find(
         * "from TopicInfo where id in (select topicInfo.id from ModelTopic where series_id=?)"
         * , seriesId);
         */
    }

    @SuppressWarnings("unchecked")
    public List<TopicInfo> getTopicsBySeries(Model model) throws Exception {
        Short seriesId = (short) 0;
        if (null != model) {
            seriesId = model.getSeries_id();
        }
        Short recommend = (short) 1;
        List<TopicInfo> list = new ArrayList<TopicInfo>();
        /*
         * List<ModelTopic> modelTopics = getHibernateTemplate().find(
         * "from ModelTopic where series_id=? order by show_order", seriesId);
         */

        List<ModelTopic> modelTopics = getHibernateTemplate()
                .find("from ModelTopic where series_id=? and recommend=? order by show_order",
                        seriesId, recommend);
        recommend = 0;
        modelTopics
                .addAll(getHibernateTemplate()
                        .find("from ModelTopic where series_id=? and recommend=? order by  topicInfo.add_date desc",
                                seriesId, recommend));

        if (null != modelTopics && modelTopics.size() > 0) {
            for (ModelTopic modelTopic : modelTopics) {
                list.add(modelTopic.getTopicInfo());
            }
        }

        return list;
        /*
         * return (List<TopicInfo>) getHibernateTemplate().find(
         * "from TopicInfo where id in (select topicInfo.id from ModelTopic where series_id=?)"
         * , seriesId);
         */
    }

    public int countTopicAppsByIdWihtFilterModel(final TopicInfo topicInfo,
            final RequestParameter requestParameter, final Model model)
            throws Exception {
        try {

            Criteria criteria = getSession().createCriteria(TopicApp.class);

            criteria.add(Restrictions.eq("topicInfo", topicInfo));

            Criteria app_criteria = criteria.createCriteria("appInfo",
                    Criteria.LEFT_JOIN);

            if (null != model) {

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
                /*
                 * String CPU_ABI = model.getCPU_ABI(); if (CPU_ABI != null) {
                 * String sql = "(CPU_ABI is null"; String cpuabis[] =
                 * CPU_ABI.split(","); for (int i = 0; i < cpuabis.length; i++)
                 * { String cpuabi = cpuabis[i]; sql = sql +
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

        } catch (Exception e) {
            throw e;
        }
    }

    public List<TopicApp> getTopicAppsByIdWihtFilterModel(
            final TopicInfo topicInfo, final RequestParameter requestParameter,
            final Model model) throws Exception {
        try {
            // Session session=getSession();
            // session.clear();
            @SuppressWarnings({ "unchecked", "rawtypes" })
            List<TopicApp> list = this.getHibernateTemplate().executeFind(
                    new HibernateCallback() {
                        public Object doInHibernate(Session session)
                                throws HibernateException {
                            Criteria criteria = session
                                    .createCriteria(TopicApp.class);

                            criteria.add(Restrictions
                                    .eq("topicInfo", topicInfo));

                            Criteria app_criteria = criteria.createCriteria(
                                    "appInfo", Criteria.LEFT_JOIN);

                            if (null != model) {

                                app_criteria.add(Restrictions.or(
                                        Restrictions.le("minSdkVersion",
                                                model.getSdkVersion()),
                                        Restrictions.isNull("minSdkVersion")));

                                app_criteria.add(Restrictions.or(
                                        Restrictions.ge("maxSdkVersion",
                                                model.getSdkVersion()),
                                        Restrictions.isNull("maxSdkVersion")));
                                /*
                                 * String CPU_ABI=model.getCPU_ABI();
                                 * if(CPU_ABI!=null){ String
                                 * sql="(CPU_ABI is null"; String
                                 * cpuabis[]=CPU_ABI.split(","); for(int i=0;
                                 * i<cpuabis.length; i++) { String
                                 * cpuabi=cpuabis[i];
                                 * sql=sql+" or CPU_ABI like '%,"+cpuabi+",%'";
                                 * } sql=sql+")";
                                 * app_criteria.add(Restrictions.sqlRestriction
                                 * (sql)); }
                                 */

                                app_criteria.add(Restrictions
                                        .sqlRestriction("(appStatus=0 or (appStatus=13 and filter_model not like '%,"
                                                + model.getId() + ",%'))"));

                            } else {
                                app_criteria.add(Restrictions.ne("appStatus",
                                        (short) 12));
                            }

                            app_criteria.setFirstResult((requestParameter
                                    .getPage_index() - 1)
                                    * requestParameter.getApps_per_page());
                            app_criteria.setMaxResults(requestParameter
                                    .getApps_per_page() + 1);

                            criteria.addOrder(Order.asc("show_order"));
                            List<TopicApp> result = criteria.list();
                            return result;
                        }
                    });

            if (null != list && list.size() > 0) {
                return list;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public TopicInfo getModelTopicById(long id) throws Exception {
        String sql = "select id,topic_name,topic_describe from t_topic_info where id = ?";
        @SuppressWarnings("unchecked")
        List<TopicInfo> topicInfos = getSession()
                .createSQLQuery(sql)
                .addScalar("id", Hibernate.LONG)
                .addScalar("topic_name", Hibernate.STRING)
                .addScalar("topic_describe", Hibernate.STRING)
                .setLong(0, id)
                .setResultTransformer(Transformers.aliasToBean(TopicInfo.class))
                .list();
        if (null != topicInfos && topicInfos.size() > 0) {
            return topicInfos.get(0);
        } else {
            return null;
        }

    }

    public String getTopicImgUrlByIdWithScreen(Long topicId, String cs)
            throws Exception {
        String screenName = "phone";
        if ("1".equals(cs)) {
            screenName = "pc";
        }
        List<TopicIcon> topicImg = getHibernateTemplate()
                .find("from TopicIcon where topicInfo.id=? and modelScreen.type=1 and modelScreen.value=?",
                        topicId, screenName);
        if (null != topicImg && topicImg.size() == 1) {
            return topicImg.get(0).getIcon_url();
        }
        return null;
    }

    // end:手机接口

    public List<TopicBasicInfo> getTopicInfo(Model model,
            RequestParameter requestParameter) throws Exception {
        String sql = "select modeltopic0_.recommend, "
                + " topicinfo1_.topic_name,topicinfo1_.app_id,topicinfo1_.app_count,"
                + "topicinfo1_.id as topic_list_id,topicinfo1_.topic_describe as introduction,"
                + "topicinfo1_.add_date as update_time,"
                + " topicicon0_.icon_url as image_url from "
                + " t_model_topic modeltopic0_ ,"
                + " t_topic_info topicinfo1_  , t_topic_icon topicicon0_ ,t_console_constant consolecon1_"
                + " where modeltopic0_.topic_id = topicinfo1_.id "
                + " and modeltopic0_.series_id = ? and modeltopic0_.recommend= 1 "
                + " and topicicon0_.screen_id = consolecon1_.id "
                + " and topicicon0_.topic_id=topicinfo1_.id"
                + " and consolecon1_.c_type = 1 "
                + " and consolecon1_.c_value = ? order by modeltopic0_.show_order";
        String cs = requestParameter.getCsStr();
        String screenName = "phone";
        if ("1".equals(cs)) {
            screenName = "pc";
        }
        @SuppressWarnings("unchecked")
        List<TopicBasicInfo> result = getSession()
                .createSQLQuery(sql)
                .addScalar("recommend")
                .addScalar("topic_name")
                .addScalar("app_id")
                .addScalar("introduction")
                .addScalar("app_count")
                .addScalar("update_time")
                .addScalar("topic_list_id")
                .addScalar("image_url")
                .setShort(0, model == null ? 0 : model.getSeries_id())
                .setString(1, screenName)
                .setResultTransformer(
                        Transformers.aliasToBean(TopicBasicInfo.class)).list();
        sql = "select modeltopic0_.recommend, "
                + " topicinfo1_.topic_name,topicinfo1_.app_id,topicinfo1_.app_count,"
                + " topicinfo1_.id as topic_list_id,topicinfo1_.topic_describe  as introduction,"
                + " topicinfo1_.add_date as update_time,"
                + " topicicon0_.icon_url as image_url from "
                + " t_model_topic modeltopic0_ ,"
                + " t_topic_info topicinfo1_  , t_topic_icon topicicon0_ ,t_console_constant consolecon1_"
                + " where modeltopic0_.topic_id = topicinfo1_.id "
                + " and modeltopic0_.series_id = ? and modeltopic0_.recommend = 0"
                + " and topicicon0_.screen_id = consolecon1_.id "
                + " and topicicon0_.topic_id=topicinfo1_.id"
                + " and consolecon1_.c_type = 1 "
                + " and consolecon1_.c_value = ? order by topicinfo1_.add_date desc ";
        @SuppressWarnings("unchecked")
        List<TopicBasicInfo> result2 = getSession()
                .createSQLQuery(sql)
                .addScalar("recommend")
                .addScalar("topic_name")
                .addScalar("app_id")
                .addScalar("introduction")
                .addScalar("app_count")
                .addScalar("update_time")
                .addScalar("topic_list_id")
                .addScalar("image_url")
                .setShort(0, model == null ? 0 : model.getSeries_id())
                .setString(1, screenName)
                .setResultTransformer(
                        Transformers.aliasToBean(TopicBasicInfo.class)).list();
        if (result == null) {
            result = new ArrayList<TopicBasicInfo>(0);
        }
        if (result2 != null && result2.size() > 0) {
            result.addAll(result2);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TopicResultAppInfo> getTopicAppList(final Model model,
            final TopicInfo topic, final PageVO page) throws Exception {
        Session session = getSession();
        return AppInfoFilter.execute(session, null, model,
                TopicResultAppInfo.class, new SqlBuffer() {
                    @Override
                    public String preSql() {
                        if ("tag".equals(topic.getTag())) {
                            return "select  topicinfo_.topic_name as topic_name, topicinfo_.topic_icon as image_url,"
                                    + "topicinfo_.topic_describe as introduction,appinfo1_.app_remark as remark,";
                        }
                        return "select topicinfo_.topic_describe as introduction,appinfo1_.app_remark as remark,";
                    }

                    @Override
                    public String postSql() {
                        return "from t_topic_app this_ ,t_topic_info topicinfo_, t_app_info appinfo1_ "
                                + " where topicinfo_.id = :id and this_.topic_id = topicinfo_.id and this_.app_id=appinfo1_.id ";
                    }

                    @Override
                    public String orderSq() {
                        return "order by this_.show_order";
                    }

                    @Override
                    public void queryAssign(PreparedStatement statement,
                            Map<String, Integer> placeholder)
                            throws SQLException {
                        statement.setLong(placeholder.get("id"), topic.getId());
                    }
                });
    }

    @Override
    public TopicInfo findTopicInfoById(Long id) throws Exception {
        // TODO Auto-generated method stub
        try {
            List<ConsoleConstant> constantList = getHibernateTemplate().find(
                    "from ConsoleConstant where value=? and type=1", "phone");
            short phone = 0;
            if (null != constantList && constantList.size() == 1) {
                phone = constantList.get(0).getId();
            }

            TopicInfo topic = (TopicInfo) getHibernateTemplate().find(
                    "from TopicInfo where id=?", id).get(0);

            List<TopicIcon> icon = getHibernateTemplate().find(
                    "from TopicIcon where topicInfo.id=? and modelScreen.id=?",
                    topic.getId(), phone);
            if (null != icon && icon.size() == 1) {
                topic.setTopic_icon(icon.get(0).getIcon_url());
            }
            return topic;

        } catch (Exception e) {
            throw e;
        }
    }
}
