/**
 * AppTypeDAOImpl.java
 * com.bbkmobile.iqoo.console.dao.apptype
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2011-12-29 		dengkehai
 *
 * Copyright (c) 2011, TNT All Rights Reserved.
 */

package com.bbkmobile.iqoo.interfaces.apptype.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.console.dao.apptype.AppType;
import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;

/**
 * ClassName:AppTypeDAOImpl Function: TODO ADD FUNCTION
 * 
 * @author dengkehai
 * @version
 * @since Ver 1.1
 * @Date 2011-12-29 上午10:27:30
 * 
 */
@Repository("iAppTypeDAO")
public class AppTypeDAOImpl extends AnnotationBaseDao implements AppTypeDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<AppType> findAllSubType(Integer parentId) throws Exception {
        // TODO Auto-generated method stub
        // id parent_id tititle_cn tititle_zh icon_url icon_url2 icon_url3
        return getHibernateTemplate()
                .find("from AppType where parentType.id=? and typeStatus=0 order by typeOrder",
                        parentId);

    }

    public Object[] getTypeIcon(Integer type_id) throws Exception {
        String sql = "select icon1,icon2,icon3 from t_type_icon where type_id="
                + type_id;
        List<Object[]> list = getSession().createSQLQuery(sql).list();
        if (null != list && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Object[]> findAllType(Integer id) throws Exception {
        String sql = "select apptype0_.id as id0_,apptype0_.parentId as parentId0_ , apptype0_.typeName as typeName0_,  "
                + " apptype0_.typeEnName as typeEnName0_,"
                + " icon.icon1,icon.icon2,icon.icon3,apptype0_.appCnNames as appCnNames0_"
                + " from t_app_type apptype0_ , t_type_icon icon "
                + " where apptype0_.parentId= ? and apptype0_.id = icon.type_id and apptype0_.TypeStatus = 0 order by apptype0_.typeOrder ";
        return getSession().createSQLQuery(sql).addScalar("id0_")
                .addScalar("parentId0_").addScalar("typeName0_")
                .addScalar("typeEnName0_").addScalar("icon1")
                .addScalar("icon2").addScalar("icon3")
                .addScalar("appCnNames0_").setInteger(0, id).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AppType> findSubType(Integer parentId) throws Exception {

        return getHibernateTemplate()
                .find("from AppType where parentType.id=? and typeStatus=0 order by typeOrder",
                        parentId);

    }

    @Override
    public AppType findById(Integer id) throws Exception {

        return getHibernateTemplate().load(AppType.class, id);

    }
}
