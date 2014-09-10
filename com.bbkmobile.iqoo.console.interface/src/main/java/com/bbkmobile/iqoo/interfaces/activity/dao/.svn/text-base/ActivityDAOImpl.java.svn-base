package com.bbkmobile.iqoo.interfaces.activity.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.console.activity.dao.ActivityInfo;
import com.bbkmobile.iqoo.console.activity.dao.ModelActivity;
import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;
@Repository("iActivityDAO")
public class ActivityDAOImpl extends AnnotationBaseDao implements ActivityDAO{

    public List<ModelActivity> getModelActivities() throws Exception {
        String sql="select act_id,name,img,pc_img,app_id from t_model_activity order by show_order";
        Query query = getSession().createSQLQuery(sql)
                .addScalar("act_id", Hibernate.INTEGER)
                .addScalar("name", Hibernate.STRING)
                .addScalar("img", Hibernate.STRING)
                .addScalar("pc_img", Hibernate.STRING)
                .addScalar("app_id", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(ModelActivity.class));
        @SuppressWarnings("unchecked")
        List<ModelActivity> modelActivitys = query.list();
        return modelActivitys;
    }
    
    public ActivityInfo getModelActivityById(Integer id) throws Exception {
        String sql="select id,name,img,pc_img,description,app_id from t_activity_info where id = ?";
        Query query = getSession().createSQLQuery(sql)
                .addScalar("id", Hibernate.INTEGER)
                .addScalar("name", Hibernate.STRING)
                .addScalar("img", Hibernate.STRING)
                .addScalar("pc_img", Hibernate.STRING)
                .addScalar("description", Hibernate.STRING)
                .addScalar("app_id", Hibernate.LONG)
                .setInteger(0, id).setResultTransformer(Transformers.aliasToBean(ActivityInfo.class));
        @SuppressWarnings("unchecked")
        List<ActivityInfo> activityInfos = query.list();
        
        if(null!=activityInfos && activityInfos.size()>0){
            return activityInfos.get(0);
        }
        return null;
    }

	@Override
	public List<ActivityInfo> getModelActivityIdByAppId(Long app_id) throws Exception {
		String sql = "select id,name from t_activity_info where app_id = ? ";
		 return getSession().createSQLQuery(sql).setLong(0, app_id).setResultTransformer(Transformers.aliasToBean(ActivityInfo.class)).list();
	}
    
}
