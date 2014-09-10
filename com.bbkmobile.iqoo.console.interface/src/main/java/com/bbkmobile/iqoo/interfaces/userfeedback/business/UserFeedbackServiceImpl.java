package com.bbkmobile.iqoo.interfaces.userfeedback.business;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.UtilTool;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.userfeedback.UserFeedback;
import com.bbkmobile.iqoo.interfaces.review.dao.CommentReviewDAO;
import com.bbkmobile.iqoo.interfaces.userfeedback.dao.UserFeedbackDAO;

@Service("iUserFeedbackService")
public class UserFeedbackServiceImpl implements UserFeedbackService {
    @Resource(name = "iUserFeedbakDAO")
    private UserFeedbackDAO userFeedbackDAO;
    @Resource(name = "iCommentReviewDAO")
    private CommentReviewDAO commentReviewDAO;

    // begin:接口
    public String saveUserFeedBack(UserFeedback userFeedback) throws Exception {
        String reponse = "";
        // 判断联系方式是否填写正确
        /*
         * String userContact = userFeedback.getUser_contact();
         * if(!RequestUtil.isEmail(userContact) &&
         * !RequestUtil.isMobileNumber(userContact)){ reponse = "联系方式必须为邮箱或手机号";
         * return reponse; }
         */
        if (!UtilTool.checkStringNull(userFeedback.getUser_contact())) {
            int length = userFeedback.getUser_contact().length();
            userFeedback.setUser_contact(userFeedback.getUser_contact().trim()
                    .substring(0, length <= 30 ? length : 30));
        }
        // todo 判断评论内容是否合法
        if (userFeedbackDAO.addUserFeedBack(userFeedback)) {
            reponse = "1";
        }
        return reponse;
    }

    // end:接口

    public String saveComment(RequestParameter requestParameter)
            throws Exception {
        String result = "0";
        int statusCode = 1;
        String prompt1 = "";
        String prompt2 = "";

        try {
            if (0 != requestParameter.getApp_version()) {

                boolean isCommented = false;

                if (510 > requestParameter.getApp_version()) {
                    boolean isForbidComment = false;

                    // 账户是否异常
                    isForbidComment = userFeedbackDAO
                            .isForbidComment(requestParameter);
                    if (isForbidComment) {
                        result = Constants.COMMENT_TERMINAL_FORBID;
                        return result;
                    }
                    // 是否发表过评论
                    isCommented = commentReviewDAO.isCommented(
                            requestParameter, "local");
                    if (isCommented) {
                        result = Constants.COMMENT_IS_PUBLISHED;
                        return result;
                    }
                } else { // 510 <= requestParameter.getApp_version()
                    // 是否发表过评论
                    isCommented = commentReviewDAO.isCommented(
                            requestParameter, "local");
                    if (isCommented) {
                        prompt1 = Constants.COMMENT_IS_PUBLISHED;
                        prompt2 = Constants.COMMENT_IS_PUBLISHED;
                        return responseComment(statusCode, prompt1, prompt2,
                                requestParameter.getApp_version());// by time
                    }
                }
            }

            result = userFeedbackDAO.saveComment(requestParameter);

            if (0 != requestParameter.getApp_version()
                    && 510 <= requestParameter.getApp_version()) {

                if (result.equals("1")) {
                    prompt1 = "评论成功";
                    prompt2 = Constants.COMMENT_IS_PUBLISHED;
                } else if (result.equals(Constants.COMMENT_IS_ILLEGAL)) {
                    statusCode = 2;
                    prompt1 = Constants.COMMENT_IS_ILLEGAL;
                    prompt2 = Constants.COMMENT_TERMINAL_FORBID;
                }

                return responseComment(statusCode, prompt1, prompt2,
                        requestParameter.getApp_version());// by time
            }

        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    private String responseComment(int statusCode, String prompt1,
            String prompt2, Float app_Version) {

        if (app_Version != null && app_Version >= Constants.APPVERSION530) {
            return "{\"result\":\"true\",\"statusCode\":\"" + statusCode
                    + "\",\"prompt1\":\"" + prompt1 + "\",\"prompt2\":\""
                    + prompt2 + "\"}";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><comment_response>");
            sb.append("<statuscode>");
            sb.append(statusCode);
            sb.append("</statuscode><prompt1><![CDATA[");
            sb.append(prompt1);
            sb.append("]]></prompt1><prompt2><![CDATA[");
            sb.append(prompt2);
            sb.append("]]></prompt2>");
            sb.append("</comment_response>");
            return sb.toString();
        }
    }

    public boolean saveDownloadAppStatusLog(RequestParameter requestParameter)
            throws Exception {
        try {
            userFeedbackDAO.saveDownloadAppStatusLog(requestParameter);
            return true;
        } catch (Exception e) {
            throw e;
            // e.printStackTrace();
            // Lg.error(LgType.STDOUT,
            // "saveDownloadAppStatusLog出错，error:"+e.getMessage());
        }
    }
}
