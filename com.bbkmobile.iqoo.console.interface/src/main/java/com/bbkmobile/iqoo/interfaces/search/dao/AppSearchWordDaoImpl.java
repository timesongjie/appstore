package com.bbkmobile.iqoo.interfaces.search.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.console.dao.word.AppSearchWord;
import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;
import com.bbkmobile.iqoo.interfaces.search.vo.HotAppsSearch;

@Repository("iAppSearchWordDao")
public class AppSearchWordDaoImpl extends AnnotationBaseDao implements
        AppSearchWordDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<HotAppsSearch> list() throws Exception {
        // package_name、version_name、version_code、icon_url、
        // title_zh、title_en、download_url、 id；
        // String sql =
        // "SELECT t2.appCnName appName,t2.appIcon appIcon,t2.id appId FROM t_search_app t,t_app_info t2 where t.appId = t2.id";
        /*
         * String sql =
         * "SELECT  t2.appCnName  as title_zh, t2.appEnName as title_en, t2.appIcon  as icon_url,"
         * +
         * " t2.id  as id, t2.appPackage as package_name, t2.appVersion as version_name, t2.appVersionCode as version_code ,t2.apkSize as size"
         * +
         * " FROM t_search_app t,t_app_info t2 where t.appId = t2.id order by t2.downloadCount desc"
         * ;
         */
        Session session = getSession();
        Criteria criteria = session.createCriteria(AppSearchWord.class);
        Criteria appCriteria = criteria.createAlias("appInfo", "appInfo",
                Criteria.LEFT_JOIN);

        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("appInfo.appCnName"), "title_zh");
        proList.add(Projections.property("appInfo.appEnName"), "title_en");
        proList.add(Projections.property("appInfo.appIcon"), "icon_url");
        proList.add(Projections.property("appInfo.id"), "id");
        proList.add(Projections.property("appInfo.appPackage"), "package_name");
        proList.add(Projections.property("appInfo.appVersion"), "version_name");
        proList.add(Projections.property("appInfo.appVersionCode"),
                "version_code");
        proList.add(Projections.property("appInfo.apkSize"), "size");
        appCriteria.setProjection(proList);
        appCriteria.addOrder(Order.desc("appInfo.downloadCount"));
        criteria.setResultTransformer(Transformers
                .aliasToBean(HotAppsSearch.class));
        return criteria.list();
        // SQLQuery query = session.createSQLQuery(sql)
        // .addScalar("title_zh", Hibernate.STRING)
        // .addScalar("title_en", Hibernate.STRING)
        // .addScalar("icon_url", Hibernate.STRING)
        // .addScalar("package_name", Hibernate.STRING)
        // .addScalar("version_name", Hibernate.STRING)
        // .addScalar("version_code", Hibernate.STRING)
        // .addScalar("id", Hibernate.LONG).addScalar("size",
        // Hibernate.INTEGER);
        // query.setResultTransformer(Transformers.aliasToBean(HotAppsSearch.class));
        // return query.list();
    }
}
