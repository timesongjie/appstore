package com.bbkmobile.iqoo.interfaces.userfeedback.business;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.errorreport.ErrorReport;
import com.bbkmobile.iqoo.interfaces.appinfo.dao.AppInfoDAO;
import com.bbkmobile.iqoo.interfaces.userfeedback.dao.ErrorReportDAO;

@Service("iErrorReportService")
public class ErrorReportServiceImpl implements ErrorReportService {
    @Resource(name = "iErrorReportDAO")
    private ErrorReportDAO errorReportDAO;
    @Resource(name = "iAppInfoDAO")
    private AppInfoDAO appInfoDAO;

    @Override
    public String saveErrorReport(String app_id, String user_name,
            String model, String content, String client_ip, String imei,
            String errCode, String type, String target, String elapsedtime,
            String appVersion) throws Exception {
        String succes = Constants.ERROR_REPORT_FAIL;
        try {
            Long app_id_long = Long.parseLong(app_id);

            if (type.equals("6") && null != errCode && errCode.equals("0")) { // errcode=0为表示安装成功
                ErrorReport errorReport = new ErrorReport();
                errorReport.setApp_id(app_id_long);
                errorReport.setUser(user_name); // user_name
                errorReport.setModel(model);
                errorReport.setUser_ip(client_ip);
                errorReport.setUser_imei(imei);
                errorReport.setReport_date(new Date());

                errorReportDAO.saveAppInstallSucessLog(errorReport, target,
                        elapsedtime, appVersion);
                succes = "保存成功";
            } else { // errcode !=0 为表示错误日志
                if (null != target && target.equals("baidu")) {
                    Character typeChar = type.toCharArray()[0];
                    boolean result = errorReportDAO.saveBaiduErrorReport(
                            app_id_long, user_name, model, content, client_ip,
                            imei, errCode, typeChar);
                    if (result) {
                        succes = "1";
                    }
                } else {
                    if (app_id_long < 0) { // 处理百度app错误报告
                        Character typeChar = type.toCharArray()[0];
                        boolean result = errorReportDAO.saveBaiduErrorReport(
                                app_id_long, user_name, model, content,
                                client_ip, imei, errCode, typeChar);
                        if (result) {
                            succes = "1";
                        }
                    } else {
                        AppInfo appInfo = appInfoDAO.findAppById(app_id_long);
                        Character typeChar = type.toCharArray()[0];
                        boolean result = errorReportDAO.saveErrorReport(
                                appInfo, user_name, model, content, client_ip,
                                imei, errCode, typeChar);
                        if (result) {
                            succes = "1";
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw e;
        }
        return succes;
    }
}
