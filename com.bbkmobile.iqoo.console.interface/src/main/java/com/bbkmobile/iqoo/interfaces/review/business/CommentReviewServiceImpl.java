package com.bbkmobile.iqoo.interfaces.review.business;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.common.json.JackSonParser;
import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.util.CommonUtil;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.dao.appinfo.AppComment;
import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;
import com.bbkmobile.iqoo.console.dao.appinfo.BaiduAppComment;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.userfeedback.CommentGrade;
import com.bbkmobile.iqoo.interfaces.review.dao.CommentReviewDAO;
import com.bbkmobile.iqoo.interfaces.review.vo.CommentListResultObject;
import com.bbkmobile.iqoo.interfaces.userfeedback.dao.UserFeedbackDAO;

@Service("iCommentReviewService")
public class CommentReviewServiceImpl implements CommentReviewService {
    @Resource(name = "iCommentReviewDAO")
    private CommentReviewDAO commentReviewDAO;
    @Resource(name = "iUserFeedbakDAO")
    private UserFeedbackDAO userFeedbackDAO;

    // begin:手机接口@haiyan
    public String getAppCommentListForXml(RequestParameter requestParameter)
            throws Exception {

        String cs = requestParameter.getCsStr();
        int apps_per_page_int = requestParameter.getApps_per_page();
        int page_index_int = requestParameter.getPage_index();
        float appVersion = requestParameter.getApp_version();
        int total_count = 0;
        int maxpage = page_index_int;
        String commented = "N";
        String forbidComment = "N";
        String reponse = "";
        CommentGrade commentGrade = null;

        if (0 == requestParameter.getApps_per_page()) {
            requestParameter.setApps_per_page(Constants.APPS_PER_PAGE_CS);
        }

        if (0 == requestParameter.getPage_index()) {
            requestParameter.setPage_index(1);
            page_index_int = 1;
        }
        Long app_id = requestParameter.getIdlong();

        String target = requestParameter.getTarget();

        if (app_id < 0 || (null != target && "baidu".equals(target))) {
            try {
                // int total_count = commentReviewDAO.findAppCommentList(app_id,
                // 0, 0).size();
                List<BaiduAppComment> appComments = commentReviewDAO
                        .findBaiduAppCommentList(app_id,
                                requestParameter.getApps_per_page(),
                                requestParameter.getPage_index());
                int appCommentsSize = appComments.size();
                if (null != cs && cs.equals("0")) {
                    if (appCommentsSize == (apps_per_page_int + 1)) {
                        maxpage = page_index_int + 1;
                        appComments.remove(appCommentsSize - 1); // 去掉最后一个
                    }
                    total_count = appComments.size(); // 当前页个数
                } else {
                    if (appVersion >= 400 && page_index_int > 1) { // 第一页返回total_count为所有app数量
                        if (appCommentsSize == (apps_per_page_int + 1)) {
                            maxpage = page_index_int + 1;
                            appComments.remove(appCommentsSize - 1); // 去掉最后一个
                        }
                        total_count = appComments.size(); // 当前页个数
                    } else {
                        total_count = commentReviewDAO
                                .countAppCommentList(app_id);
                        maxpage = (int) Math.ceil((float) total_count
                                / apps_per_page_int);
                        if (appCommentsSize == (apps_per_page_int + 1)) {
                            appComments.remove(appCommentsSize - 1); // 去掉最后一个
                        }
                    }

                }

                if (1 == page_index_int) {
                    if (null != requestParameter.getImei()
                            && !requestParameter.getImei().equals("")) {
                        if (requestParameter.getApp_version() >= 510) {
                            // 先判断账户是否异常，再判断是否已经评论过
                            if (userFeedbackDAO
                                    .isForbidComment(requestParameter)) {
                                forbidComment = "Y";
                                reponse = Constants.COMMENT_TERMINAL_FORBID;
                            } else {
                                if (commentReviewDAO.isCommented(
                                        requestParameter, "baidu")) {
                                    commented = "Y";
                                    reponse = Constants.COMMENT_IS_PUBLISHED;
                                }
                            }
                        } else {
                            if (commentReviewDAO.isCommented(requestParameter,
                                    "baidu")) {
                                commented = "Y";
                            }
                        }
                    }

                }
                if (appVersion >= Constants.APPVERSION530) {
                    CommentListResultObject result = new CommentListResultObject();
                    result.setCommented(commented);
                    result.setForbidComment(forbidComment);
                    result.setMaxPage(maxpage);
                    result.setPageNo(page_index_int);
                    result.setPageSize(apps_per_page_int);
                    result.setReponse(reponse);
                    result.setTotalCount(total_count);
                    result.setResult(true);
                    result.setValue(bdAppCommentListTAppCommentList(
                            appComments, requestParameter));
                    return JackSonParser.bean2Json(result);
                } else {
                    PageVO page = new PageVO();
                    page.setPageCount(maxpage);
                    page.setRecordCount(total_count);
                    return getBaiduXmlResult(page, requestParameter,
                            appComments,
                            getXmlScore(forbidComment, commented, reponse));
                }
            } catch (Exception e) {
                throw e;
            }

        } else {
            try {
                // int total_count = commentReviewDAO.findAppCommentList(app_id,
                // 0, 0).size();
                List<AppComment> appComments = commentReviewDAO
                        .findAppCommentList(app_id,
                                requestParameter.getApps_per_page(),
                                requestParameter.getPage_index());

                if (null == appComments || appComments.size() == 0) {
                    if (appVersion >= Constants.APPVERSION530) {
                        CommentListResultObject result = new CommentListResultObject();//
                        result.setResult(true);
                        result.setMaxPage(0);
                        result.setPageNo(page_index_int);
                        result.setPageSize(apps_per_page_int);
                        return JackSonParser.bean2Json(result);
                    }
                    return getAppCommentHeadXml(requestParameter);
                }

                int appCommentsSize = appComments.size();
                if (null != cs && cs.equals("0")) {
                    if (appCommentsSize == (apps_per_page_int + 1)) {
                        maxpage = page_index_int + 1;
                        appComments.remove(appCommentsSize - 1); // 去掉最后一个
                    }
                    total_count = appComments.size(); // 当前页个数

                } else {
                    if (appVersion >= 400 && page_index_int > 1) { // 第一页返回total_count为所有app数量
                        if (appCommentsSize == (apps_per_page_int + 1)) {
                            maxpage = page_index_int + 1;
                            appComments.remove(appCommentsSize - 1); // 去掉最后一个
                        }
                        total_count = appComments.size(); // 当前页个数
                    } else {
                        total_count = commentReviewDAO
                                .countAppCommentList(app_id);
                        maxpage = (int) Math.ceil((float) total_count
                                / apps_per_page_int);
                        if (appCommentsSize == (apps_per_page_int + 1)) {
                            appComments.remove(appCommentsSize - 1); // 去掉最后一个
                        }
                    }

                }
                if (1 == page_index_int) {
                    if (requestParameter.getApp_version() >= 510) {
                        // 先判断账户是否异常，再判断是否已经评论过
                        if (userFeedbackDAO.isForbidComment(requestParameter)) {
                            forbidComment = "Y";
                            reponse = Constants.COMMENT_TERMINAL_FORBID;
                        } else {
                            if (commentReviewDAO.isCommented(requestParameter,
                                    "local")) {
                                commented = "Y";
                                reponse = Constants.COMMENT_IS_PUBLISHED;
                            }
                        }
                    } else {
                        if (commentReviewDAO.isCommented(requestParameter,
                                "local")) {
                            commented = "Y";
                        }
                    }
                    commentGrade = commentReviewDAO.getCommentGrade(app_id);
                }
                AppInfo appinfo = commentReviewDAO.getAppInfo(app_id);
                if (appVersion >= Constants.APPVERSION530 && appinfo != null) {
                    CommentListResultObject result = new CommentListResultObject();
                    result.setCommented(commented);
                    result.setForbidComment(forbidComment);
                    result.setMaxPage(maxpage);
                    result.setPageNo(page_index_int);
                    result.setPageSize(apps_per_page_int);
                    result.setReponse(reponse);
                    result.setTotalCount(appinfo.getCommentCount());
                    result.setResult(true);
                    result.setValue(customAppCommentList(appComments,
                            requestParameter));
                    result.setScore(appinfo.getAvgComment());
                    if (commentGrade != null) {
                        result.setScore1(commentGrade.getScore1());
                        result.setScore2(commentGrade.getScore2());
                        result.setScore3(commentGrade.getScore3());
                        result.setScore4(commentGrade.getScore4());
                        result.setScore5(commentGrade.getScore5());
                    }
                    return JackSonParser.bean2Json(result);
                } else {
                    PageVO page = new PageVO();
                    page.setPageCount(maxpage);
                    page.setCurrentPageNum(page_index_int);
                    return getXmlResult(page, requestParameter, appinfo,
                            commentGrade, appComments,
                            getXmlScore(forbidComment, commented, reponse));
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    private String getAppCommentHeadXml(RequestParameter requestParameter) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><CommentList maxpage=\"");
        sb.append(0);
        sb.append("\" PageNo=\"");
        sb.append(requestParameter.getPage_index());
        sb.append("\" pageSize=\"");
        sb.append(requestParameter.getApps_per_page());
        sb.append("\" TotalCount=\"");
        sb.append(0);
        sb.append("\" ForbidComment=\"");
        sb.append("N");
        sb.append("\" commented=\"");
        sb.append("N");
        sb.append("\" response=\"");
        sb.append("");
        sb.append("\" score=\"");
        sb.append(0);
        sb.append("\" score1=\"");
        sb.append(0);
        sb.append("\" score2=\"");
        sb.append(0);
        sb.append("\" score3=\"");
        sb.append(0);
        sb.append("\" score4=\"");
        sb.append(0);
        sb.append("\" score5=\"");
        sb.append(0);
        sb.append("\">");
        sb.append("</CommentList>");
        return sb.toString();
    }

    private String getAppCommentXml(List<AppComment> appComments,
            RequestParameter requestParameter) {
        StringBuilder sb = new StringBuilder();
        String user_name = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (AppComment appComment : appComments) {
            sb.append("<comment user_name=\"");
            user_name = appComment.getUser_name();
            // if(null != user_name && !"".equals(user_name) &&
            // !user_name.equalsIgnoreCase("null")){
            // sb.append(appComment.getUser_name());
            // } else {
            // user_name = "匿名用户";
            // if (null != appComment.getUser_imei() && null !=
            // requestParameter.getImei()
            // && appComment.getUser_imei().equals(requestParameter.getImei()))
            // {
            // user_name = "我";
            // }
            // sb.append(user_name);
            // }
            sb.append(customUserName(user_name, appComment.getUser_imei(),
                    requestParameter.getImei(), appComment.getLogin_type()));
            sb.append("\" comment_time=\"");
            sb.append(dateFormat.format(appComment.getComment_date()));
            sb.append("\" comment_score=\"");
            sb.append(appComment.getScore());
            sb.append("\" model=\"");
            String comment_model = appComment.getModel();
            if (null != comment_model && !"".equals(comment_model)) {
                if (comment_model.indexOf("@vivolauncher") != -1) {
                    comment_model = comment_model.split("@")[0];
                }
                sb.append(comment_model);
            } else {
                sb.append("vivo智能手机");
            }
            sb.append("\" appversion=\"");
            sb.append(appComment.getAppversion());
            sb.append("\"><![CDATA[");
            if (appComment.getComment_status().equals(
                    Constants.COMMENT_REVIEW_MOD)) {
                sb.append("评论正在审核...");
            } else {
                sb.append(appComment.getComment());
            }
            sb.append("]]></comment>");
        }
        return sb.toString();
    }

    private String getBaiduAppCommentXml(List<BaiduAppComment> appComments,
            RequestParameter requestParameter) {
        StringBuilder sb = new StringBuilder();
        String user_name = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (BaiduAppComment appComment : appComments) {
            sb.append("<comment user_name=\"");
            user_name = appComment.getUser_name();
            // if(null != user_name && !"".equals(user_name)){
            // sb.append(appComment.getUser_name());
            // } else {
            // user_name = "匿名用户";
            // if (null != appComment.getUser_imei() && null !=
            // requestParameter.getImei()
            // && appComment.getUser_imei().equals(requestParameter.getImei()))
            // {
            // user_name = "我";
            // }
            // sb.append(user_name);
            // }
            sb.append(customUserName(user_name, appComment.getUser_imei(),
                    requestParameter.getImei(), appComment.getLogin_type()));
            sb.append("\" comment_time=\"");
            sb.append(dateFormat.format(appComment.getComment_date()));
            sb.append("\" comment_score=\"");
            sb.append(appComment.getScore());
            sb.append("\" model=\"");
            String comment_model = appComment.getModel();
            if (null != comment_model && !"".equals(comment_model)) {
                sb.append(appComment.getModel());
            } else {
                sb.append("vivo智能手机");
            }
            sb.append("\"><![CDATA[");
            if (appComment.getComment_status().equals(
                    Constants.COMMENT_REVIEW_MOD)) {
                sb.append("评论正在审核...");
            } else {
                sb.append(appComment.getComment());
            }
            sb.append("]]></comment>");
        }
        return sb.toString();
    }

    private String getXmlResult(PageVO page, RequestParameter requestParameter,
            AppInfo appinfo, CommentGrade commentGrade,
            List<AppComment> appComments, String score) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><CommentList maxpage=\"");
        sb.append(page.getPageCount());
        sb.append("\" PageNo=\"");
        sb.append(requestParameter.getPage_index());
        sb.append("\" pageSize=\"");
        sb.append(requestParameter.getApps_per_page());
        sb.append("\" TotalCount=\"");
        sb.append(appinfo.getCommentCount());
        // sb.append("\" ForbidComment=\"");
        // sb.append(forbidComment);
        // sb.append("\" commented=\"");
        // sb.append(commented);
        // sb.append("\" response=\"");
        // sb.append(reponse);
        sb.append("\" score=\"");
        sb.append(appinfo.getAvgComment());
        sb.append(score);

        if (null != commentGrade) {
            sb.append("\" score1=\"");
            sb.append(commentGrade.getScore1());
            sb.append("\" score2=\"");
            sb.append(commentGrade.getScore2());
            sb.append("\" score3=\"");
            sb.append(commentGrade.getScore3());
            sb.append("\" score4=\"");
            sb.append(commentGrade.getScore4());
            sb.append("\" score5=\"");
            sb.append(commentGrade.getScore5());

        } else {
            if (1 == page.getCurrentPageNum()) {
                sb.append("\" score1=\"");
                sb.append(0);
                sb.append("\" score2=\"");
                sb.append(0);
                sb.append("\" score3=\"");
                sb.append(0);
                sb.append("\" score4=\"");
                sb.append(0);
                sb.append("\" score5=\"");
                sb.append(0);

            }
        }
        sb.append("\">");
        if (null != appComments && appComments.size() > 0) {
            sb.append(getAppCommentXml(appComments, requestParameter));
        }
        sb.append("</CommentList>");

        return sb.toString();
    }

    private String getXmlScore(String forbidComment, String commented,
            String reponse) {
        StringBuffer score = new StringBuffer();
        score.append("\" ForbidComment=\"");
        score.append(forbidComment);
        score.append("\" commented=\"");
        score.append(commented);
        score.append("\" response=\"");
        score.append(reponse);
        return score.toString();
    }

    private String getBaiduXmlResult(PageVO page,
            RequestParameter requestParameter,
            List<BaiduAppComment> appComments, String score) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><CommentList maxpage=\"");
        sb.append(page.getPageCount());
        sb.append("\" PageNo=\"");
        sb.append(requestParameter.getPage_index());
        sb.append("\" pageSize=\"");
        sb.append(requestParameter.getApps_per_page());
        sb.append("\" TotalCount=\"");
        sb.append(page.getRecordCount());
        sb.append(score);
        sb.append("\">");
        if (null != appComments && appComments.size() > 0) {
            sb.append(getBaiduAppCommentXml(appComments, requestParameter));

        }
        sb.append("</CommentList>");
        return sb.toString();
    }

    private List<AppComment> bdAppCommentListTAppCommentList(
            List<BaiduAppComment> comments, RequestParameter requestParameter) {
        if (comments == null) {
            return Collections.emptyList();
        }
        List<AppComment> appComments = new ArrayList<AppComment>(
                comments.size());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (BaiduAppComment comment : comments) {
            AppComment appComment = new AppComment();
            appComment.setComment(dateFormat.format(appComment
                    .getComment_date()));
            String user_name = comment.getUser_name();
            appComment.setUser_name(customUserName(user_name,
                    comment.getUser_imei(), requestParameter.getImei(),
                    comment.getLogin_type()));
            appComment.setScore(comment.getScore());
            String comment_model = comment.getModel();
            if (null != comment_model && !"".equals(comment_model)) {
                appComment.setModel(comment_model);
            } else {
                appComment.setModel("vivo智能手机");
            }

            if (comment.getComment_status()
                    .equals(Constants.COMMENT_REVIEW_MOD)) {
                appComment.setComment("评论正在审核...");
            } else {
                appComment.setComment(comment.getComment());
            }
            appComments.add(appComment);
        }
        return appComments;
    }

    private List<AppComment> customAppCommentList(List<AppComment> comments,
            RequestParameter requestParameter) {
        if (comments != null && comments.size() > 0) {
            for (AppComment comment : comments) {
                String user_name = comment.getUser_name();
                comment.setUser_name(customUserName(user_name,
                        comment.getUser_imei(), requestParameter.getImei(),
                        comment.getLogin_type()));
            }
        }
        return comments;
    }

    private String customUserName(String userName, String imei, String reqImei,
            Character login_type) {
        if (imei != null && imei.equals(reqImei)) {
            userName = "我";
        } else if (userName == null || StringUtils.isEmpty(userName)
                || "null".equals(userName)) {
            userName = "匿名用户";
        }
        // if (login_type != null && login_type == '2')
        // {CommonUtil.isMobileNO(userName.trim())
        if (userName != null && userName.trim().length() == 11
                && StringUtils.isNumeric(userName.trim())) {
            userName = userName.substring(0, 3) + "****"
                    + userName.substring(7, userName.length());
        }
        // }
        return userName;
    }

    @Override
    public String getAppCommentListForJson(RequestParameter requestParameter)
            throws Exception {
        return null;
    }

    /*
     * public String getAppCommentListForXml(RequestParameter requestParameter)
     * throws Exception { StringBuilder sb = new StringBuilder();
     * 
     * if(0==requestParameter.getApps_per_page()){
     * requestParameter.setApps_per_page(Constants.APPS_PER_PAGE_CS); }
     * 
     * if(0==requestParameter.getPage_index()){
     * requestParameter.setPage_index(1); } Long app_id =
     * (long)requestParameter.getId();
     * 
     * String target = requestParameter.getTarget();
     * 
     * if(app_id<0 || (null!=target && "baidu".equals(target))){ try { int
     * total_count = 0; //int total_count =
     * commentReviewDAO.findAppCommentList(app_id, 0, 0).size();
     * List<BaiduAppComment> appComments =
     * commentReviewDAO.findBaiduAppCommentList(app_id,
     * requestParameter.getApps_per_page(), requestParameter.getPage_index());
     * total_count = appComments.size();
     * 
     * int maxpage = (int) Math.ceil((float) total_count /
     * requestParameter.getApps_per_page()); DateFormat dateFormat = new
     * SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     * 
     * sb.append(
     * "<?xml version=\"1.0\" encoding=\"utf-8\" ?><CommentList maxpage=\"");
     * sb.append(maxpage); sb.append("\" PageNo=\"");
     * sb.append(requestParameter.getPage_index()); sb.append("\" pageSize=\"");
     * sb.append(requestParameter.getApps_per_page());
     * sb.append("\" TotalCount=\""); sb.append(total_count); sb.append("\">");
     * 
     * String user_name = requestParameter.getUser_name();
     * 
     * int apps_per_page_int = requestParameter.getApps_per_page(); int
     * page_index_int = requestParameter.getPage_index(); int startPosion =
     * apps_per_page_int*(page_index_int-1); int currentPosion=0;
     * BaiduAppComment appComment=null; if(null != appComments &&
     * appComments.size()>0){ for(int k=0;k<apps_per_page_int;k++){
     * currentPosion=startPosion+k; if(currentPosion>total_count-1){ break; }
     * else{ appComment = appComments.get(currentPosion);
     * sb.append("<comment user_name=\""); if(null!=user_name &&
     * !"".equals(user_name)){ sb.append(appComment.getUser_name()); }else{
     * String userName = "匿名用户"; if(null!=appComment.getUser_imei() &&
     * null!=requestParameter.getImei() &&
     * appComment.getUser_imei().equals(requestParameter.getImei())){ userName =
     * "我"; } sb.append(userName); } sb.append("\" comment_time=\"");
     * sb.append(dateFormat.format(appComment.getComment_date()));
     * sb.append("\" comment_score=\""); sb.append(appComment.getScore());
     * sb.append("\" model=\""); sb.append(appComment.getModel());
     * sb.append("\"><![CDATA[");
     * if(appComment.getComment_status().equals(Constants.COMMENT_REVIEW_MOD)){
     * sb.append("评论正在审核..."); } else{ sb.append(appComment.getComment()); }
     * sb.append("]]></comment>"); } } } sb.append("</CommentList>");
     * 
     * } catch (Exception e) { throw e; }
     * 
     * }else{ try { int total_count = 0; //int total_count =
     * commentReviewDAO.findAppCommentList(app_id, 0, 0).size();
     * List<AppComment> appComments =
     * commentReviewDAO.findAppCommentList(app_id,
     * requestParameter.getApps_per_page(), requestParameter.getPage_index());
     * total_count = appComments.size();
     * 
     * int maxpage = (int) Math.ceil((float) total_count /
     * requestParameter.getApps_per_page()); DateFormat dateFormat = new
     * SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     * 
     * sb.append(
     * "<?xml version=\"1.0\" encoding=\"utf-8\" ?><CommentList maxpage=\"");
     * sb.append(maxpage); sb.append("\" PageNo=\"");
     * sb.append(requestParameter.getPage_index()); sb.append("\" pageSize=\"");
     * sb.append(requestParameter.getApps_per_page());
     * sb.append("\" TotalCount=\""); sb.append(total_count); sb.append("\">");
     * 
     * String user_name = requestParameter.getUser_name();
     * 
     * int apps_per_page_int = requestParameter.getApps_per_page(); int
     * page_index_int = requestParameter.getPage_index(); int startPosion =
     * apps_per_page_int*(page_index_int-1); int currentPosion=0; AppComment
     * appComment=null; if(null != appComments && appComments.size()>0){ for(int
     * k=0;k<apps_per_page_int;k++){ currentPosion=startPosion+k;
     * if(currentPosion>total_count-1){ break; } else{ appComment =
     * appComments.get(currentPosion); sb.append("<comment user_name=\"");
     * if(null!=user_name && !"".equals(user_name)){
     * sb.append(appComment.getUser_name()); }else{ String userName = "匿名用户";
     * if(null!=appComment.getUser_imei() && null!=requestParameter.getImei() &&
     * appComment.getUser_imei().equals(requestParameter.getImei())){ userName =
     * "我"; } sb.append(userName); } sb.append("\" comment_time=\"");
     * sb.append(dateFormat.format(appComment.getComment_date()));
     * sb.append("\" comment_score=\""); sb.append(appComment.getScore());
     * sb.append("\" model=\""); sb.append(appComment.getModel());
     * sb.append("\"><![CDATA[");
     * if(appComment.getComment_status().equals(Constants.COMMENT_REVIEW_MOD)){
     * sb.append("评论正在审核..."); } else{ sb.append(appComment.getComment()); }
     * sb.append("]]></comment>"); } } } sb.append("</CommentList>");
     * 
     * } catch (Exception e) { throw e; } }
     * 
     * return sb.toString(); }
     */
    // end:手机接口

    // public List<AppComment> getAppCommentList(AppComment appComment, PageVO
    // page) throws Exception {
    // List<AppComment> appComments = null;
    // try {
    // Integer start = (page.getCurrentPageNum() - 1) *
    // Constants.APPS_PER_PAGE_20;
    // page.setRecordCount(commentReviewDAO.countAppCommentRecords1(appComment));
    // appComments = commentReviewDAO.getAppCommentList1(start, appComment);
    // if (null != appComments) {
    // ForbidComment forbidComment = new ForbidComment();
    // for (int i = 0; i < appComments.size(); i++) {
    // AppComment appCommentTmp = appComments.get(i);
    // if (null != appCommentTmp.getUser_imei() &&
    // !appCommentTmp.getUser_imei().trim().equals("")) {
    // forbidComment.setImei(Long.parseLong(appCommentTmp.getUser_imei()));
    // ForbidComment forbidComment2 =
    // userFeedbackDAO.getForbidComment(forbidComment);
    // if (null != forbidComment2) {
    // appComments.get(i).setForbid_status(forbidComment2.getForbid_status());
    // } else {
    // appComments.get(i).setForbid_status((short) 0);
    // }
    // }
    // }
    // }
    // } catch (Exception e) {
    // throw e;
    // }
    // return appComments;
    // }
    //
    // public List<AppComment> getAllAppCommentList(AppComment appComment)
    // throws Exception {
    // List<AppComment> list = null;
    // try {
    // if (null != appComment.getFrom_date() || null != appComment.getTo_date())
    // {
    // if (null == appComment.getFrom_date()) {
    // appComment.setFrom_date(new Date());
    // }
    // if (null == appComment.getTo_date()) {
    // appComment.setTo_date(new Date());
    // }
    // if (appComment.getFrom_date().after(appComment.getTo_date())) {
    // Date temp = appComment.getFrom_date();
    // appComment.setFrom_date(appComment.getTo_date());
    // appComment.setTo_date(temp);
    // }
    // }
    // list = commentReviewDAO.getAllAppCommentList(appComment);
    // } catch (Exception e) {
    // throw e;
    // }
    // return list;
    // }
    //
    // public List<AppComment> getAllAppCommentList1(AppComment appComment)
    // throws Exception {
    // List<AppComment> list = null;
    // try {
    // list = commentReviewDAO.getAllAppCommentList1();
    // } catch (Exception e) {
    // throw e;
    // }
    // return list;
    // }
    //
    // public boolean deleteAppCommentByID(Long id) throws Exception {
    // boolean result = false;
    // try
    // {
    // result = commentReviewDAO.deleteAppCommentByID(new Long[]{id});
    // return result;
    // }catch(Exception e){
    // throw e;
    // }
    // }
    //
    // public boolean deleteMoreAppCommentByID(Long[] ids) throws Exception {
    // boolean result = false;
    // try
    // {
    // result =commentReviewDAO.deleteAppCommentByID(ids);
    // return result;
    // }catch(Exception e){
    // throw e;
    // }
    // }
    //
    // public boolean deleteCommentReviewRecordByID(Long id) throws Exception {
    // boolean result = false;
    // try
    // {
    // result = commentReviewDAO.deleteCommentReviewRecordByID(new Long[]{id});
    // return result;
    // }catch(Exception e){
    // throw e;
    // }
    // }
    //
    // public boolean deleteMoreCommentReviewRecordByID(Long[] ids) throws
    // Exception {
    // boolean result = false;
    // try
    // {
    // result =commentReviewDAO.deleteCommentReviewRecordByID(ids);
    // return result;
    // }catch(Exception e){
    // throw e;
    // }
    // }
    //
    // // 查询用户评论审核记录
    //
    // @Override
    // public List<CommentReviewRecords> getCommentReviewRecords(AppComment
    // appComment)
    // throws Exception {
    //
    // return commentReviewDAO.findCommentReviewRecord(appComment);
    //
    // }
    //
    //
    // // 查询应用信息，初始化审核页面
    //
    // @Override
    // public AppComment toReviewComment(Long app_id) throws Exception {
    // AppComment appComment = null;
    // try {
    // appComment = commentReviewDAO.findAppCommentById(app_id);
    // } catch (Exception e) {
    // throw e;
    // }
    // return appComment;
    // }
    //
    // // 保存审核记录
    //
    // @Override
    // public boolean commentReview(CommentReviewRecords review,Integer
    // adminUserId) throws Exception {
    // boolean result = false;
    // try {
    // result = commentReviewDAO.updateForCommentReview(review,adminUserId);
    // } catch (Exception e) {
    // throw e;
    // }
    // return result;
    // }
    //
    // // 保存批量审核记录
    // @Override
    // public boolean reviewMoreAppComment(Long[] ids, CommentReviewRecords
    // review, Integer adminUserId) throws Exception {
    // boolean result = false;
    // try {
    // result = commentReviewDAO.updateForCommentReview(ids, review,
    // adminUserId);
    // } catch (Exception e) {
    // throw e;
    // }
    // return result;
    // }
    //
    // //批量导入评论
    // public boolean saveComments(AppInfo appInfo, AppComment appComment,String
    // comments_str) throws Exception{
    // boolean result = false;
    // String strArray[] = comments_str.split("\n") ;
    // result = commentReviewDAO.saveComments(appInfo, appComment,strArray);
    // return result;
    // }
    //
    // public CommentReviewDAO getCommentReviewDAO() {
    // return this.commentReviewDAO;
    // }
    //
    // public void setCommentReviewDAO(CommentReviewDAO commentReviewDAO) {
    // this.commentReviewDAO = commentReviewDAO;
    // }
    //
    // public UserFeedbackDAO getUserFeedbackDAO() {
    // return this.userFeedbackDAO;
    // }
    //
    // public void setUserFeedbackDAO(UserFeedbackDAO userFeedbackDAO) {
    // this.userFeedbackDAO = userFeedbackDAO;
    // }

}
