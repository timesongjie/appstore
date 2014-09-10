package com.bbkmobile.iqoo.interfaces.common.log;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.console.constants.LgType;

@Repository("saveBrowseLogCacheDeal")
public class SaveBrowseLogCacheDeal extends Deal {

    @Override
    public void deal(List<String[]> logLs) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar day = Calendar.getInstance();
        String date = sdf.format(day.getTime()).replace("-", "");
        // String sql = "insert into t_browse_log_"
        // + date
        // +
        // "(imei,model,ip,cfrom,req_id,cs,page_index,elapsedtime,version,channel)value(?,?,INET_ATON(?),?,?,?,?,?,?,?)";
        if (logLs != null && logLs.size() > 0) {
            Session session = getSession();
            long stime = System.currentTimeMillis();
            for (int i = 0; i < logLs.size(); i++) {
                String[] params = logLs.get(i);
                String sql = "insert into t_browse_log_"
                        + date
                        + "(imei,model,ip,cfrom,req_id,cs,page_index,elapsedtime,version,channel)"
                        + " value('" + params[0] + "','" + params[1]
                        + "',INET_ATON('" + params[2] + "'),'" + params[3]
                        + "','" + params[4] + "','" + params[5] + "','"
                        + params[6] + "','" + params[7] + "','" + params[8]
                        + "','" + params[9] + "')";
                SQLQuery query = session.createSQLQuery(sql);
                // if (params != null && params.length == 10) {
                // try {
                // query.setString(0, String.valueOf(params[0]))
                // .setString(1, params[1])
                // .setString(2, params[2])
                // .setString(3, params[3])
                // .setString(4, params[4])
                // .setString(5, params[5])
                // .setString(6, params[6])
                // .setString(7, params[7])
                // .setString(8, params[8])
                // .setString(9, params[9]).executeUpdate();
                // } catch (Exception e) {
                // Lg.error(LgType.APPINFO, "插入日志SaveBrowseLog失败：["
                // + Arrays.toString(params) + "]");
                // }
                // }
                query.executeUpdate();
                if (i % 100 == 0) {
                    session.flush();
                    session.clear();
                }
            }
            System.out.println("花费" + (System.currentTimeMillis() - stime)
                    + "ms;");

        }
    }
}
