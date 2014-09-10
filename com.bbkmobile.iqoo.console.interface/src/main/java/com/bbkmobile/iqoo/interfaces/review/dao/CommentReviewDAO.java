package com.bbkmobile.iqoo.interfaces.review.dao;

import java.util.List;

import com.bbkmobile.iqoo.console.dao.appinfo.AppComment;
import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.appinfo.BaiduAppComment;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.userfeedback.CommentGrade;

public interface CommentReviewDAO {
    boolean isCommented(final RequestParameter requestParameter,
            final String target) throws Exception;

    CommentGrade getCommentGrade(Long app_id) throws Exception;

    //
    List<AppComment> findAppCommentList(Long app_id, int apps_per_page,
            int page_index) throws Exception; // add by haiyan

    int countAppCommentList(final Long app_id) throws Exception;

    //
    List<BaiduAppComment> findBaiduAppCommentList(final Long app_id,
            final int apps_per_page, final int page_index) throws Exception;

    AppInfo getAppInfo(Long app_id) throws Exception;

    // int countBaiduAppCommentList(final Long app_id) throws Exception;
    //
    // int countAppCommentRecords(AppComment appComment) throws Exception;
    // int countAppCommentRecords1(final AppComment appComment) throws
    // Exception;
    //
    // List<AppComment> getAppCommentList(int start, final AppComment
    // appComment) throws Exception;
    // List<AppComment> getAppCommentList1(final int start, final AppComment
    // appComment) throws Exception;
    //
    // List<AppComment> getAllAppCommentList(AppComment appComment) throws
    // Exception ;
    // List<AppComment> getAllAppCommentList1() throws Exception;
    //
    // boolean deleteAppCommentByID(Long[] ids) throws Exception ;
    //
    // boolean deleteCommentReviewRecordByID(Long[] ids) throws Exception ;
    //
    //
    // List<CommentReviewRecords> findCommentReviewRecord(AppComment appComment)
    // throws Exception;
    //
    // AppComment findAppCommentById(Long app_id) throws Exception;
    //
    // boolean updateForCommentReview(CommentReviewRecords review,Integer
    // adminUserId) throws Exception;
    //
    // boolean updateForCommentReview(Long[] ids, CommentReviewRecords review,
    // Integer adminUserId) throws Exception;
    //
    // boolean saveComments(AppInfo appInfo, AppComment appComment,String []
    // strArray) throws Exception;

}
