package com.bbkmobile.iqoo.interfaces.userfeedback.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bbkmobile.iqoo.common.json.NullResultObject;
import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.common.util.RequestUtil;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.userfeedback.UserFeedback;
import com.bbkmobile.iqoo.interfaces.userfeedback.business.ErrorReportService;
import com.bbkmobile.iqoo.interfaces.userfeedback.business.UserFeedbackService;
import com.bbkmobile.iqoo.platform.base.BaseAction;

@SuppressWarnings("serial")
@Component("iUserFeedbackAction")
@Scope("prototype")
public class UserFeedbackAction extends BaseAction{
    
	@Resource(name="iUserFeedbackService")
	private UserFeedbackService userFeedbackService;
	@Resource(name="iErrorReportService")
    private ErrorReportService errorReportService;
    
//    private PageVO page = new PageVO();
////    private  Map<String,String>logTypes = new HashMap<String,String>();
//    private	 final String KEY_TYPE = "type";
//    private  final String KEY_IMEI = "imei";
//    private String imei;
//    private final String DEFAULT_TYPE = "t_app_download_log_";
//    private List<Object[]> logs = new ArrayList<Object[]> ();//应用下载日志集合
    
    public UserFeedbackAction(){
    	super();
//    	logTypes.put("t_app_download_log_", "本地应用下载日志");
//    	logTypes.put("t_app_download_complete_log_", "本地应用下载完成日志");
//    	//缺少update字段
//    	logTypes.put("t_browse_app_log_", "浏览本地应用日志");
//    	//缺少update字段,新增包字段
//    	logTypes.put("t_browse_app_package_log", "浏览本地应用安装包日志");
//    	//缺少update字段
//    	logTypes.put("t_browse_baidu_app_log", "浏览百度应用日志");
//    	//缺少update字段
//    	logTypes.put("t_app_baidu_download_log", "百度应用下载日志");
//    	
//    	logTypes.put("t_app_baidu_install_log", "百度应用安装日志");
//    	
//    	logTypes.put("t_browse_log_", "浏览日志");
//    	
//    	logTypes.put("t_app_download_count_yyyyMM", "本地应用下载次数日志");
//    	
//    	logTypes.put("t_packages_update_log_", "安装包更新日志");
//    	logTypes.put("t_search_word_log_", "关键字查询日志");
    }
    //begin:手机接口@haiyan
    public String postComments() throws Exception{
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
 
            String user_name = request.getParameter("user_name");
            String login_type = request.getParameter("login_type");
            String user_id = request.getParameter("user_id");
            String app_id = request.getParameter("id");
            String score = request.getParameter("score");
            String content = request.getParameter("content");
  
            String appversion = request.getParameter("appversion");
            String appversioncode = request.getParameter("appversioncode");
            
            String version = request.getParameter("app_version");
            String model=request.getParameter("model");
            String cs =request.getParameter("cs");
            String imei =request.getParameter("imei");
            String client_ip = RequestUtil.getClientIP(request);
            String target = request.getParameter("target");
            
            RequestParameter requestParameter = new RequestParameter();
            
            requestParameter.setUser_name(user_name);
            requestParameter.setUser_id(user_id);
            requestParameter.setLogin_type(login_type);
            
            if(null!=app_id && !"".equals(app_id)){
                requestParameter.setIdlong(Long.parseLong(app_id));
            }
            if(null!=score && !"".equals(score)){
                requestParameter.setScore(Float.parseFloat(score));
            }
            requestParameter.setContent(content);
            
            requestParameter.setModel(model);
            if(null!=cs && !"".equals(cs)){
                requestParameter.setCs(Integer.parseInt(cs));
            }
            
            if(null!=version && !version.equals("")){
                requestParameter.setApp_version(Float.parseFloat(version)) ;
            }
            requestParameter.setImei(imei);
            requestParameter.setIp(client_ip);
            requestParameter.setTarget(target);
            
            requestParameter.setAppVersion(appversion);
            requestParameter.setAppVersionCode(appversioncode);
            
            //成功保存，则返回1
            String xml_data =userFeedbackService.saveComment(requestParameter);
            if(null != version && ! version.equals("") && Float.valueOf(version) >= Constants.APPVERSION530){
            	outwrite(xml_data,"text/plain;charset=utf-8");
            }else{
            	outwrite(xml_data,"text/xml;charset=utf-8");
            }
        } catch (Exception e) {
        	outwrite(NullResultObject.print(), "text/xml;charset=utf-8");
            Lg.error(LgType.USERFEEDBACK, "保存手机用户评论信息时出错，error=" + e.getMessage());
        }
        return null;
    }
    
    public String reporterr() throws Exception{
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String user_name = request.getParameter("user");
            String app_id = request.getParameter("id");
            String content = request.getParameter("content");
            String client_ip = RequestUtil.getClientIP(request);
            //sucess = commentReviewService.saveComment(app_id,score,content,ip);
            String imei = request.getParameter("imei");
            String errCode = request.getParameter("errCode");
            String model = request.getParameter("model");
            String type = request.getParameter("type");
            String target = request.getParameter("target");
            String appVersion = request.getParameter("app_version");
            String elapsedtime = request.getParameter("elapsedtime");
            
            if (null == type || type.equals("1")) {
                type = "5";
            }
            else if(type.equals("2")){
                type = "6";
            }       
            String xml_data = errorReportService.saveErrorReport(app_id, user_name, model, content, client_ip, imei, errCode, type, target,elapsedtime,appVersion);
            outwrite(xml_data,"text/xml;charset=utf-8");
        } catch (Exception e) {
            //e.printStackTrace();
            Lg.error(LgType.USERFEEDBACK, "保存错误报告信息时出错，error=" + e.getMessage());
        }
        return null;
    }
    
    public String postFeedback() throws Exception{
        String reponseStr = "";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            
            String user = request.getParameter("user");                  //用户名
            String userContact = request.getParameter("contact");   //用户联系方式
            String type = request.getParameter("type");                    //反馈类型
            String content = request.getParameter("content");           //反馈内容
            //post_date
            String imei =request.getParameter("imei");                      
            String model=request.getParameter("model");
            String cs =request.getParameter("cs");
            String userIp = RequestUtil.getClientIP(request);
            String elapsedtime = request.getParameter("elapsedtime");   //开机时间
            String appVersion = request.getParameter("app_version");  //软件版本号

            UserFeedback userFeedback= new UserFeedback();
            
            userFeedback.setUser(user);
            userFeedback.setUser_contact(userContact);
    
            if(null!=type && !type.equals("")){
                userFeedback.setContent_type(type.charAt(0));
            }
            userFeedback.setContent(content);
            userFeedback.setImei(imei);
            userFeedback.setModel(model);
            userFeedback.setCs(cs);
            userFeedback.setUser_ip(userIp);
            if(null!=elapsedtime && !elapsedtime.equals("")){
                userFeedback.setElapsedtime(Long.parseLong(elapsedtime));
            }
            userFeedback.setApp_version(appVersion);
            //成功保存，则返回
            reponseStr =userFeedbackService.saveUserFeedBack(userFeedback);
        } catch (Exception e) {
            //e.printStackTrace();
            Lg.error(LgType.USERFEEDBACK, "保存用户反馈信息时出错，error=" + e.getMessage());
            reponseStr = "提交失败";
        }
        outwrite(reponseStr,"text/plain;charset=utf-8");
        return null;
    }
    
//    public String downloadlog() throws Exception {
//        String status = "";
//        String id = "";
//        String target = "";
//        String cfrom = "";
//        String module_id = "";
//        String related =  "";
//        String update = "";
//        String appVersion = "";
//        
//        String model = "";
//        String cs = "";
//        String imei = "";
//        
//        
//        try {
//            HttpServletRequest request = ServletActionContext.getRequest();
//            status = request.getParameter("status");
//            id = request.getParameter("id");
//            target = request.getParameter("target");
//            cfrom = request.getParameter("cfrom");
//            module_id = request.getParameter("module_id");
//            related = request.getParameter("related");
//            update = request.getParameter("update");
//            appVersion = request.getParameter("app_version");
//            model = request.getParameter("model");
//            cs = request.getParameter("cs");
//            imei = request.getParameter("imei");
//            
//            RequestParameter requestParameter = new RequestParameter();
//            
//            requestParameter.setType(status);
//            requestParameter.setIdStr(id);
//            requestParameter.setTarget(target);
//            requestParameter.setCfrom(Short.parseShort(cfrom));
//            requestParameter.setModule_id(module_id);
//            requestParameter.setRelated(related);
//            requestParameter.setVersion(appVersion);
//            requestParameter.setModel(model);
//            requestParameter.setCsStr(cs);
//            requestParameter.setImei(imei);
//            requestParameter.setElapsedtime(request.getParameter("elapsedtime"));
//            
//            String ip = RequestUtil.getClientIP(request);
//            requestParameter.setIp(ip);
//            //requestParameter.setType(request.getParameter("type"));  //扩展参数，app类型
//
//            if (Constants.SAVE_APP_DOWNLOAD_LOG) {
//                userFeedbackService.saveDownloadAppStatusLog(requestParameter);
//            }
//            
//            outwrite("ok", "text/plain;charset=utf-8");  
//        } catch (Exception e) {
//            Lg.error(LgType.USERFEEDBACK, "记录downloadlog时出错:status=" + status + 
//                    ",id=" + id + 
//                    ",target=" + target + 
//                    ",cfrom=" + cfrom +
//                    ",module_id=" + module_id +
//                    ",related=" + related +
//                    ",update=" + update +
//                    ",appVersion=" + appVersion + 
//                    ",model=" + model +
//                    ",cs=" + cs +
//                    ",imei=" + imei +
//                    ",error=" + e.getMessage());
//        }
//        return null;
//    }
//	/**
//	 * 查询系统日志
//	 * @return
//	 */
//	public String searchSystemLog()
//	{
//		String forward="show_system_logs";
//		try
//		{
//			String type = getHttpServletRequest().getParameter(KEY_TYPE);
//			type = (StringUtils.hasText(type) ? type : DEFAULT_TYPE);
//			
//			imei = getHttpServletRequest().getParameter(KEY_IMEI);
//			//表明以时间戳生成的表结构
//			String tableName = type;
//			if(type.charAt(type.length()-1) == '_'){
//				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
//				String postfix = sf.format( new Date());
//				tableName += postfix;
//			}else if(type.endsWith("yyyyMM")){
//				SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
//				String postfix = sf.format( new Date());
//				tableName = tableName.substring(0, tableName.indexOf("yyyyMM")) + postfix;
//			}
//			if(StringUtils.hasText(imei)){
//				logs = userFeedbackService.getSystemLogs(page, tableName,imei);
//			}else{
//				logs = userFeedbackService.getSystemLogs(page, tableName);
//			}
//			if(null == logs || logs.size() == 1)
//			{
//				addActionMessage("无任何符合条件的记录，请重新查询");
//			}
//		}catch (Exception e) {
//			// TODO: handle exception
//			Lg.error(LgType.USERFEEDBACK, "根据条件查找后台管理员登录日志失败。", e);
//		}
//		
//		return forward;
//	}
	
    /*
    public String reporterr() throws Exception{
        try {
            HttpServletResponse reponse = ServletActionContext.getResponse();
            HttpServletRequest request = ServletActionContext.getRequest();
            String user_name = request.getParameter("user_name");
            String app_id = request.getParameter("id");
            String content = request.getParameter("content");
            String client_ip = RequestUtil.getClientIP(request);
            //sucess = commentReviewService.saveComment(app_id,score,content,ip);
            String imei= request.getParameter("imei");        
            String errCode = request.getParameter("errCode");
         
            String xml_data = userFeedbackService.saveErrorReport(app_id, user_name, null, content, client_ip, imei, errCode);
            reponse.setContentType("text/xml;charset=utf-8");
            reponse.setHeader("Cache-Control", "no-cache");
            reponse.setContentLength(xml_data.getBytes("utf-8").length);
            ServletOutputStream out = reponse.getOutputStream();
            out.write(xml_data.getBytes("utf-8"));
            out.flush();
            out.close(); 
            
            
        } catch (Exception e) {
            e.printStackTrace();
            Lg.error(LgType.USERFEEDBACK, "保存错误报告信息时出错，error=" + e.getMessage());
        }
        return null;
    }
    */
    //end:手机接口
}
