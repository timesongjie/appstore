package com.bbkmobile.iqoo.interfaces.push.business;

import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.interfaces.push.vo.PushActivity;

public interface IPushService {
    public PushActivity getPushActivity(RequestParameter requestParameter,
            PushActivity push) throws Exception;

    String getNewestPid() throws Exception;

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
}
