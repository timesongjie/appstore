package com.bbkmobile.iqoo.interfaces.userfeedback.business;

public interface ErrorReportService {

    String saveErrorReport(String app_id, String user_name, String model,
            String content, String client_ip, String imei, String errCode,
            String type, String target, String elapsedtime, String appVersion)
            throws Exception;

}
