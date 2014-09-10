package com.bbkmobile.iqoo.interfaces.advertisement.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.dao.advertisement.Advertisement;
import com.bbkmobile.iqoo.console.dao.advertisement.StartPage;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;
import com.bbkmobile.iqoo.interfaces.common.AppInfoFilter;
import com.bbkmobile.iqoo.interfaces.common.SqlBuffer;

@Repository("iAdInfoDAOImpl")
public class AdInfoDAOImpl extends AnnotationBaseDao implements AdInfoDAO {

    // 据系列获取手机广告
    @SuppressWarnings("unchecked")
    @Override
    public List<Advertisement> getPhoneAdInfoBySeries(final Short series_id,
            final Short model_id) throws Exception {

        // return
        // (List<Advertisement>)getHibernateTemplate().find("from Advertisement where id in (select advertisement.id from ModelAdvertisement where series_id=? order by show_order)",series_id);
        // List<Advertisement> list = new ArrayList<Advertisement>();
        // List<ModelAdvertisement> modelAdList = getHibernateTemplate().find(
        // "from ModelAdvertisement where series_id=? order by show_order",
        // series_id);
        // if (null != modelAdList && modelAdList.size() > 0) {
        // for (ModelAdvertisement md : modelAdList) {
        // list.add(md.getAdvertisement());
        // }
        // }
        String sql = "select * from t_ad_info where id in( select ad_id from t_model_ad  where series_id = ? order by show_order)";
        return getSession().createSQLQuery(sql).addEntity(Advertisement.class)
                .setShort(0, series_id).list();
        // return list;
    }

    // 统计：根据id找到广告，广告中的app按机型过滤
    @Override
    public int countPhoneAdAppsWithModelFilter(final Long id,
            final Short model_id, final Integer sdkVersion,
            final String drawable_dpi, final String CPU_ABI) throws Exception {
        try {
            Criteria criteria = getSession()
                    .createCriteria(Advertisement.class);
            criteria.add(Restrictions.eq("id", id));
            Criteria adApp_criteria = criteria.createCriteria("appInfos",
                    Criteria.LEFT_JOIN);
            if (null != model_id) {
                adApp_criteria.add(Restrictions.or(
                        Restrictions.le("minSdkVersion", sdkVersion),
                        Restrictions.isNull("minSdkVersion")));

                adApp_criteria.add(Restrictions.or(
                        Restrictions.ge("maxSdkVersion", sdkVersion),
                        Restrictions.isNull("maxSdkVersion")));
                /*
                 * if (CPU_ABI != null) { String sql = "(CPU_ABI is null";
                 * String cpuabis[] = CPU_ABI.split(","); for (int i = 0; i <
                 * cpuabis.length; i++) { String cpuabi = cpuabis[i]; sql = sql
                 * + " or CPU_ABI like '%," + cpuabi + ",%'"; } sql = sql + ")";
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

        } catch (Exception e) {
            throw e;
        }
    }

    // 根据id找到广告，广告中的app按机型过滤
    @Override
    public Advertisement findPhoneAdByIdForFilterApps(final Long id,
            final int apps_per_page, final int page_index,
            final Short model_id, final Integer sdkVersion,
            final String drawable_dpi, final String CPU_ABI) throws Exception {

        // TODO Auto-generated method stub
        try {
            @SuppressWarnings({ "unchecked", "rawtypes" })
            List<Advertisement> list = this.getHibernateTemplate().executeFind(
                    new HibernateCallback() {
                        public Object doInHibernate(Session session)
                                throws HibernateException {
                            Criteria criteria = session
                                    .createCriteria(Advertisement.class);

                            criteria.add(Restrictions.eq("id", id));

                            Criteria adApp_criteria = criteria.createCriteria(
                                    "appInfos", Criteria.LEFT_JOIN);

                            if (null != model_id) {
                                adApp_criteria.add(Restrictions.or(Restrictions
                                        .le("minSdkVersion", sdkVersion),
                                        Restrictions.isNull("minSdkVersion")));

                                adApp_criteria.add(Restrictions.or(Restrictions
                                        .ge("maxSdkVersion", sdkVersion),
                                        Restrictions.isNull("maxSdkVersion")));
                                /*
                                 * if (CPU_ABI != null) { String sql =
                                 * "(CPU_ABI is null"; String cpuabis[] =
                                 * CPU_ABI.split(","); for (int i = 0; i <
                                 * cpuabis.length; i++) { String cpuabi =
                                 * cpuabis[i]; sql = sql +
                                 * " or CPU_ABI like '%," + cpuabi + ",%'"; }
                                 * sql = sql + ")";
                                 * adApp_criteria.add(Restrictions
                                 * .sqlRestriction(sql)); }
                                 */
                                adApp_criteria.add(Restrictions
                                        .sqlRestriction("(appStatus=0 or (appStatus=13 and filter_model not like '%,"
                                                + model_id + ",%'))"));

                            } else {
                                adApp_criteria.add(Restrictions.ne("appStatus",
                                        (short) 12));
                            }

                            adApp_criteria.setFirstResult((page_index - 1)
                                    * apps_per_page);
                            adApp_criteria.setMaxResults(apps_per_page + 1);
                            List<Advertisement> result = criteria.list();
                            return result;
                        }
                    });
            if (null != list && list.size() > 0) {
                return list.get(0);
            } else {
                return null;
            }

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<StartPage> getValidStartPages(RequestParameter requestParameter)
            throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            String today = sdf.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            String tomorrow = sdf.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            String dayAfterTomorrow = sdf.format(calendar.getTime());
            String sql = "select id,image,valid_date,invalid_date,default_view from t_start_page where"
                    + " (valid_date<='"
                    + today
                    + "' and invalid_date>='"
                    + today
                    + "')"
                    + " or "
                    + " (valid_date<='"
                    + tomorrow
                    + "' and invalid_date>='"
                    + tomorrow
                    + "')"
                    + " or "
                    + " (valid_date<='"
                    + dayAfterTomorrow
                    + "' and invalid_date>='"
                    + dayAfterTomorrow
                    + "')"
                    + " or "
                    + " default_view = '1' order by default_view desc,valid_date asc";
            @SuppressWarnings("unchecked")
            List<StartPage> list = getSession()
                    .createSQLQuery(sql)
                    .addScalar("id", Hibernate.INTEGER)
                    .addScalar("image", Hibernate.STRING)
                    .addScalar("valid_date", Hibernate.STRING)
                    .addScalar("invalid_date", Hibernate.STRING)
                    .addScalar("default_view", Hibernate.CHARACTER)
                    .setResultTransformer(
                            Transformers.aliasToBean(StartPage.class)).list();

            if (null != list && list.size() > 0) {
                return list;
            }
            return null;
        } catch (Exception e) {
            throw e;
        }
    }

    // end:手机接口

    @Override
    public String getAdImgUrlByAd(Long adId, String cs, Float version)
            throws Exception {
        String screenName = "phone";
        if ("1".equals(cs)) {
            screenName = "pc";
        } else {
            if (null != version && version >= 500) {
                screenName = "phone2";
            }
        }
        // List<AdvertisementIcon>
        // adImg=getHibernateTemplate().find("from AdvertisementIcon where advertisement.id=? and modelScreen.type=1 and modelScreen.value=?",adId,screenName);
        Session session = getSession();
        String queryString = "select advertisem0_.icon_url  from t_ad_icon advertisem0_, t_console_constant consolecon1_ where advertisem0_.screen_id=consolecon1_.id and advertisem0_.ad_id=? and consolecon1_.c_type=1 and consolecon1_.c_value=?";
        @SuppressWarnings("unchecked")
        List<String> adImg = session.createSQLQuery(queryString)
                .setLong(0, adId).setString(1, screenName).list();
        if (null != adImg && adImg.size() == 1) {
            return adImg.get(0);
            // return adImg.get(0).getIcon_url();
        }
        return null;
    }

    @Override
    public List<CommonResultAppInfo> getFiltedAppInfo(final PageVO page,
            final long id, final Model model) throws Exception {
        return getHibernateTemplate().execute(
                new HibernateCallback<List<CommonResultAppInfo>>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public List<CommonResultAppInfo> doInHibernate(
                            Session session) throws HibernateException,
                            SQLException {
                        // Criteria criteria =
                        // session.createCriteria(AdvertisementApp.class);
                        // Criteria appInfoCriteria =
                        // criteria.createCriteria("appInfo",
                        // "appInfo",Criteria.LEFT_JOIN);
                        // appInfoCriteria.setProjection(ProjectQuery.setJoinAppsProList());
                        // // appInfoCriteria.add(Restrictions.eq("appInfo.id",
                        // app_id));
                        //
                        // Criteria advertisementCriteria =
                        // criteria.createCriteria("advertisement","advertisement",
                        // Criteria.LEFT_JOIN);
                        // advertisementCriteria.add(Restrictions.eq("advertisement.id",
                        // id));
                        // criteria.setResultTransformer(Transformers.aliasToBean(CommonResultAppInfo.class));
                        // return criteria.list();
                        return AppInfoFilter.execute(session, page, model,
                                new SqlBuffer() {

                                    @Override
                                    public String preSql() {
                                        return "select appinfos3_.show_order  as sortOrder,";
                                    }

                                    @Override
                                    public String postSql() {
                                        return " from t_ad_info this_ ,t_ad_app appinfos3_, t_app_info appinfo1_ where this_.id=:id and this_.id=appinfos3_.ad_id and appinfos3_.app_id=appinfo1_.id ";
                                    }

                                    @Override
                                    public String orderSq() {
                                        return "order by appinfos3_.show_order ";
                                    }

                                    @Override
                                    public void queryAssign(
                                            PreparedStatement statement,
                                            Map<String, Integer> placeholder)
                                            throws SQLException {
                                        if (placeholder != null) {
                                            statement.setLong(
                                                    placeholder.get("id"), id);
                                            if (page != null) {
                                                statement.setInt(
                                                        placeholder.get("size"),
                                                        page.getNumPerPage() + 1);
                                            }
                                        }
                                    }

                                });
                    }

                });
    }
}
