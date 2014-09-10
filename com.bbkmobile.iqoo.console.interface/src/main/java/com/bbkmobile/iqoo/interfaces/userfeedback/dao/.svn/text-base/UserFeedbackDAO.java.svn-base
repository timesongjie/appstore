package com.bbkmobile.iqoo.interfaces.userfeedback.dao;

import com.bbkmobile.iqoo.console.dao.appinfo.AppComment;
import com.bbkmobile.iqoo.console.dao.appinfo.BaiduAppComment;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.userfeedback.UserFeedback;

public interface UserFeedbackDAO {
    
    boolean addUserFeedBack(UserFeedback userFeedback) throws Exception; 
//    
//    int countAppCommentRecords(AppComment appComment) throws Exception; 
//    
//    List<AppComment> getAppCommentList(int start, final AppComment appComment) throws Exception;    
//   
//    List<AppComment> getAllAppCommentList(AppComment appComment) throws Exception ;
//   
//    boolean deleteAppCommentByID(Long[] ids) throws Exception ;  
//    
//    public boolean deleteCommentReviewRecordByID(Long[] ids) throws Exception ;
//    
    String saveComment(RequestParameter requestParameter) throws Exception; //add by haiyan
    void saveOrUpdateComment(AppComment comment) throws Exception;
    void saveOrUpdateComment(BaiduAppComment comment) throws Exception;
//    
    boolean isForbidComment(RequestParameter requestParameter) throws Exception;
//
//    AppComment findAppCommentById(Long appComment_id) throws Exception;
//    
//    Integer countForbidComments(ForbidComment forbidComment) throws Exception;
//    List<ForbidComment> getForbidComments(ForbidComment forbidComment) throws Exception;
//
//    boolean deleteForbidComments(ForbidComment forbid_status, String ids) throws Exception;
//
//    boolean saveForbidCommnet(AppComment appComment, Short forbid_status, String ids, UserInfo user) throws Exception ;
//
//    ForbidComment getForbidComment(ForbidComment forbidComment) throws Exception;
//    
//    boolean deleteForbidCommentsWithTimed() throws Exception;
//
    boolean saveDownloadAppStatusLog(RequestParameter requestParameter) throws Exception;
//    
//    List<Object[]> getSystemLogList(int start,String ...tableName)throws Exception;
//    int countSystemLogs(String  ...tableName)throws Exception;
}
