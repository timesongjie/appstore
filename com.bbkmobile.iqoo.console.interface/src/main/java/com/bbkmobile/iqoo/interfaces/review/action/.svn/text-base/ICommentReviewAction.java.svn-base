package com.bbkmobile.iqoo.interfaces.review.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bbkmobile.iqoo.common.json.NullResultObject;
import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.interfaces.review.business.CommentReviewService;
import com.bbkmobile.iqoo.platform.base.BaseAction;


/**
 * @author zhangyi
 *
 */
@Component("iCommentReviewAction")
@Scope("prototype")
public class ICommentReviewAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    @Resource(name="iCommentReviewService")
    private CommentReviewService commentReviewService;
    
    //begin:手机接口@haiyan
    
    //获取app的评论列表
    public String comments() throws Exception{
        String app_id = null;
        String target = null;
        String cs = null;
        String appVersion = null;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            
            app_id = request.getParameter("id");
            String apps_per_page = StringUtils.defaultIfEmpty(getHttpServletRequest().getParameter("apps_per_page"),"10");
            String page_index = StringUtils.defaultIfEmpty(getHttpServletRequest().getParameter("page_index"),"1");
            
            String model = request.getParameter("model");
            cs = request.getParameter("cs");
            String imei = request.getParameter("imei");
            //String client_ip = UtilTool.getClientIP(request);       //获取ip
            
            String user_name = request.getParameter("user_name");
            target = request.getParameter("target");
            appVersion = request.getParameter("app_version");
            
            String appversion = request.getParameter("appversion");
            String appversioncode = request.getParameter("appversioncode");
            
            RequestParameter requestParameter = new RequestParameter();
           
            if(null!=app_id && !"".equals(app_id)){
                requestParameter.setIdlong(Long.parseLong(app_id));
            }
            if(null!=apps_per_page && !"".equals(apps_per_page)){
                requestParameter.setApps_per_page(Integer.parseInt(apps_per_page));
            }
            if(null!=page_index && !"".equals(page_index)){
                requestParameter.setPage_index(Integer.parseInt(page_index));
            }   
            
            requestParameter.setModel(model);
            requestParameter.setCsStr(cs);
 
            requestParameter.setImei(imei);
            requestParameter.setUser_name(user_name);
            requestParameter.setTarget(target);
            
            requestParameter.setAppVersion(appversion);
            requestParameter.setAppVersionCode(appversioncode);
            
            if(appVersion != null  && StringUtils.isNumeric(appVersion)){
                requestParameter.setApp_version(Float.parseFloat(appVersion));
            }
            String data = commentReviewService.getAppCommentListForXml(requestParameter);
            if(appVersion != null && StringUtils.isNumeric(appVersion) && Float.parseFloat(appVersion) >= Constants.APPVERSION530){
            	outwrite(data, "text/plain;charset=utf-8");   
            }else{
            	outwrite(data, "text/xml;charset=utf-8");   
            }
        } catch (Exception e) {
        	outwrite(NullResultObject.print(), "text/xml;charset=utf-8");
            Lg.error(LgType.COMMENT_REVIEW, "手机获取app用户评论信息时出错，id=" + app_id + ",target=" + target + ",cs=" + cs
                    + ",appVersion=" + appVersion + ",error=" + e.getMessage());
            
        }
        return null;
    } 
    
    //end:手机接口
}
