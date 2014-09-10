package com.bbkmobile.iqoo.interfaces.common.log;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.console.constants.LgType;

@Repository("saveDownloadAppLogCacheDeal")
public class SaveDownloadAppLogCacheDeal extends Deal {

    @Override
    public void deal(List<String[]> logLs) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar day = Calendar.getInstance();
        String date = sdf.format(day.getTime()).replace("-", "");
        String sql = "insert into t_app_download_log_"
                + date
                + "(imei,model,ip,cfrom,app_id,module_id,related,updated,elapsedtime,cs,version,uuid,user_name,status)value(?,?,INET_ATON(?),?,?,?,?,?,?,?,?,?,?,?)";
        if (logLs != null && logLs.size() > 0) {
            Session session = getSession();
            for (int i = 0; i < logLs.size(); i++) {

                String[] params = logLs.get(i);
                if (params != null && params.length == 14) {
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
                                .setString(9, params[9])
                                .setString(10, params[10])
                                .setString(11, params[11])
                                .setString(12, params[12])
                                .setString(13, params[13]).executeUpdate();
                    } catch (Exception e) {
                        Lg.error(LgType.APPINFO, "插入SaveDownloadAppLog日志失败：["
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
