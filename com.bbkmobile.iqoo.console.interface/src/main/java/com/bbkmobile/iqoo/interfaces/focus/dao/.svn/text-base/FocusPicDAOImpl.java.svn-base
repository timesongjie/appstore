package com.bbkmobile.iqoo.interfaces.focus.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.console.focus.dao.FocusPic;
import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;

@Repository("iFocusPicDAO")
public class FocusPicDAOImpl extends AnnotationBaseDao implements FocusPicDAO{

    public List<FocusPic> getModelFocusPictures() throws Exception{

        String sql="select type,object_id,name,img,app_count,app_id from t_focus_picture order by show_order";
        Query query = getSession().createSQLQuery(sql)
                .addScalar("type", Hibernate.SHORT)
                .addScalar("object_id", Hibernate.LONG)
                .addScalar("name", Hibernate.STRING)
                .addScalar("img", Hibernate.STRING)
                .addScalar("app_count", Hibernate.INTEGER)
                .addScalar("app_id", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(FocusPic.class));
        @SuppressWarnings("unchecked")
        List<FocusPic> focusPics = query.list();
        return focusPics;
    }
}
