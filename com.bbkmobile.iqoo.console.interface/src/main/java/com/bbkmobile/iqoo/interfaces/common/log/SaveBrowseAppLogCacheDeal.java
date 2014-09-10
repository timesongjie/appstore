package com.bbkmobile.iqoo.interfaces.common.log;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.console.constants.LgType;

@Repository("saveBrowseAppLogCacheDeal")
public class SaveBrowseAppLogCacheDeal extends Deal {

    @Override
    public void deal(List<String[]> logLs) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar day = Calendar.getInstance();
        String date = sdf.format(day.getTime()).replace("-", "");
        String sql = "insert into t_browse_app_log_"
                + date
                + "(imei,model,ip,cfrom,app_id,module_id,related,elapsedtime,cs,version)value(?,?,INET_ATON(?),?,?,?,?,?,?,?) ";
        // + "value('" + requestParameter.getImei() + "','"
        // + requestParameter.getModel() + "',INET_ATON('"
        // + requestParameter.getIp() + "'),'"
        // + requestParameter.getCfrom() + "','"
        // + requestParameter.getIdStr() + "','"
        // + requestParameter.getModule_id() + "','"
        // + requestParameter.getRelated() + "','"
        // + requestParameter.getElapsedtime() + "','"
        // + requestParameter.getCsStr() + "','"
        // + (int) requestParameter.getApp_version() + "')";
        if (logLs != null && logLs.size() > 0) {
            Session session = getSession();
            for (int i = 0; i < logLs.size(); i++) {

                String[] params = logLs.get(i);
                if (params != null && params.length == 10) {
                    try {
                        session.createSQLQuery(sql).setString(0, params[0])
                                .setString(1, params[1])
                                .setString(2, params[2])
                                .setString(3, params[3])
                                .setString(4, params[4])
                                .setString(5, params[5])
                                .setString(6, params[6])
                                .setString(7, params[7])
                                .setString(8, params[8])
                                .setString(9, params[9]).executeUpdate();
                    } catch (Exception e) {
                        Lg.error(
                                LgType.APPINFO,
                                "插入日志SaveBrowseAppLogCache失败：["
                                        + Arrays.toString(params) + "]");
                    }
                }
                if (i % 100 == 0) {
                    session.flush();
                    session.clear();
                }
            }
        }
    }
}
