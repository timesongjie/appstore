package com.bbkmobile.iqoo.interfaces.userfeedback.dao;

import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.errorreport.ErrorReport;

public interface ErrorReportDAO {

    boolean saveErrorReport(AppInfo appInfo, String user_name, String model,
            String content, String client_ip, String imei, String errCode,
            Character type) throws Exception;

    boolean saveBaiduErrorReport(Long app_id, String user_name, String model,
            String content, String client_ip, String imei, String errCode,
            Character type) throws Exception;

    boolean saveAppInstallSucessLog(ErrorReport errorReport, String target,
            String elapsedtime, String appVersion) throws Exception;
}
