package com.bbkmobile.iqoo.interfaces.userfeedback.dao;


import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.errorreport.BaiduErrorReport;
import com.bbkmobile.iqoo.console.dao.errorreport.ErrorReport;
import com.bbkmobile.iqoo.console.dao.modelmgr.ConsoleConstant;
import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;
@Repository("iErrorReportDAO")
public class ErrorReportDAOImpl extends AnnotationBaseDao implements
		ErrorReportDAO {

	boolean type_flag = false;
	public boolean saveAppInstallSucessLog(ErrorReport errorReport, String target,String elapsedtime,String appVersion) throws Exception {
	    try {
            String table_name = "";
            if (null != target && target.equals("baidu")) {
                table_name = "t_app_baidu_install_log";
            } else {
                if (errorReport.getApp_id()<0){    
                    table_name = "t_app_baidu_install_log";
                }else{
                    table_name = "t_app_install_log";
                }
            }
            
            String sql = "insert into " +table_name+"(imei,model,ip,app_id,elapsedtime,version) " + "value('"
                    + errorReport.getUser_imei() + "','" + errorReport.getModel() + "','"
                    + errorReport.getUser_ip() + "','" + errorReport.getApp_id()  + "','"
                    + elapsedtime + "','" + appVersion  + "')";
            getSession().createSQLQuery(sql).executeUpdate();;
            return true;
        } catch (Exception e) {
            throw e;
        }
    }
	@Override
	public boolean saveErrorReport(AppInfo appInfo, String user_name,
			String model, String content, String client_ip, String imei,
			String errCode, Character type) throws Exception {

		boolean result = false;
		ErrorReport errorReport = new ErrorReport();
		if (null != appInfo) {
		    
			List list=getHibernateTemplate().find("from ConsoleConstant where type=? and value=?",Short.parseShort(type.toString()),errCode);
			if(null!=list && list.size()>0){
				ConsoleConstant constant=(ConsoleConstant)list.get(0);
				errorReport.setError_content(constant.getDescribe());
			}else{
				errorReport.setError_content(content);
			}
			
			errorReport.setAppInfo(appInfo);
			errorReport.setUser(user_name); // user_name
			errorReport.setModel(model);
			
			errorReport.setUser_ip(client_ip);
			errorReport.setUser_imei(imei);

			errorReport.setReport_date(new Date());

			errorReport.setError_code(errCode);
			errorReport.setError_type(type);

			getHibernateTemplate().save(errorReport);
			result = true;
		}
		return result;
	}
	 @Override
	    public boolean saveBaiduErrorReport(Long app_id, String user_name, String model, String content, String client_ip,
	            String imei, String errCode, Character type) throws Exception {
	        boolean result = false;
	        BaiduErrorReport errorReport = new BaiduErrorReport();

	        List list=getHibernateTemplate().find("from ConsoleConstant where type=? and value=?",Short.parseShort(type.toString()),errCode);
	        if(null!=list && list.size()>0){
	            ConsoleConstant constant=(ConsoleConstant)list.get(0);
	            errorReport.setError_content(constant.getDescribe());
	        }else{
	            errorReport.setError_content(content);
	        }
	        errorReport.setApp_id(app_id);
	        errorReport.setUser(user_name); // user_name
	        errorReport.setModel(model);
	        
	        errorReport.setUser_ip(client_ip);
	        errorReport.setUser_imei(imei);

	        errorReport.setReport_date(new Date());

	        errorReport.setError_code(errCode);
	        errorReport.setError_type(type);

	        getHibernateTemplate().save(errorReport);
	        result = true;
	   
	        return result;
	    }
}
