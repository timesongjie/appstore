package com.bbkmobile.iqoo.interfaces.push.dao;

import java.util.List;

import com.bbkmobile.iqoo.interfaces.push.vo.PushActivity;

public interface IPushDao {

    public List<PushActivity> getPushActivites() throws Exception;

    List getAppInfoRelations(Integer id) throws Exception;

    List getActAppInfoRelations(Integer id) throws Exception;

    public String getPid() throws Exception;

    /**
     * 保存客户端返回的push的日志
     * 
     * @Description:
     * @param appVersion
     * @param model
     * @param pid
     * @param slapsedtime
     * @param cs
     * @param imei
     * @param ip
     * @throws Exception
     * @Author:leez
     * @see:
     * @since: 1.0
     * @Create Date:2014年8月8日
     */
    public void savePushLogTable(String appVersion, String model, String pid,
            String pushid, long elapsedtime, String cs, String imei, String ip)
            throws Exception;
    // app_version=530&model=vivo+Xplay3S&pid=550E8400E29B11D4A716446655440000&elapsedtime=20586399&cs=0&imei=012345678987654
}
