package com.bbkmobile.iqoo.interfaces.push.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.common.util.DateUtil;
import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;
import com.bbkmobile.iqoo.interfaces.push.vo.PushActivity;

@Repository
public class IPushDaoImpl extends AnnotationBaseDao implements IPushDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<PushActivity> getPushActivites() throws Exception {
        String sql = "select id,push_title as title,push_content as content,"
                + " push_icon as icon,relation_id as relationId,relation_type as relationType,"
                + " pid from t_push_activity where status = '1' and (now() between stime and etime)";
        Session session = getSession();
        return session
                .createSQLQuery(sql)
                .addScalar("id")
                .addScalar("pid")
                .addScalar("title")
                .addScalar("content")
                .addScalar("icon")
                .addScalar("relationId")
                .addScalar("relationType", Hibernate.CHARACTER)
                .setResultTransformer(
                        Transformers.aliasToBean(PushActivity.class)).list();
    }

    @Override
    public List getAppInfoRelations(Integer id) throws Exception {
        String sql = "select appVersionCode,appPackage from t_app_info,t_push_activity"
                + " where t_push_activity.id = ? and t_push_activity.relation_id = t_app_info.id";
        return getSession().createSQLQuery(sql).setInteger(0, id).list();
    }

    @Override
    public List getActAppInfoRelations(Integer id) throws Exception {
        String sql = "select appVersionCode,appPackage from t_app_info,t_push_activity,t_model_activity"
                + " where t_push_activity.id = ? and t_push_activity.relation_id = t_model_activity.act_id and t_model_activity.app_id = t_app_info.id";
        return getSession().createSQLQuery(sql).setInteger(0, id).list();
    }

    @Override
    public String getPid() throws Exception {
        String sql = "select pid from t_push_activity where status = '1' limit 1";
        Object obj = getSession().createSQLQuery(sql).uniqueResult();
        if (obj != null) {
            return String.valueOf(obj);
        }
        return "";
    }

    @Override
    public void savePushLogTable(String appVersion, String model, String pid,
            String pushid, long slapsedtime, String cs, String imei, String ip)
            throws Exception {
        String date = DateUtil.getYYYYMMDD();
        String sql = "insert into t_push_log_"
                + date
                + "(imei,pid,pushid,ip,model,elapsedtime,cs,appversion)value(?,?,?,INET_ATON(?),?,?,?,?) ";
        // + "value('"
        // + imei + "','" + pid + "',INET_ATON('" + ip + "'),'" + model
        // + "'," + slapsedtime + ",'" + cs + "','" + appVersion + "')";
        // if (Constants.USE_LOGCACHE) {
        // UpgradeQueryLogCache.getIns().add(sql);
        // } else {
        //
        // }
        getSession().createSQLQuery(sql).setString(0, imei).setString(1, pid)
                .setString(2, pushid).setString(3, ip).setString(4, model)
                .setLong(5, slapsedtime).setString(6, cs)
                .setString(7, appVersion).executeUpdate();
    }
}
