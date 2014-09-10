package com.bbkmobile.iqoo.interfaces.userfeedback.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.console.constants.UtilTool;
import com.bbkmobile.iqoo.console.dao.appinfo.AppComment;
import com.bbkmobile.iqoo.console.dao.appinfo.BaiduAppComment;
import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.console.dao.keyword.CensorWord;
import com.bbkmobile.iqoo.console.dao.userfeedback.ForbidComment;
import com.bbkmobile.iqoo.console.dao.userfeedback.ForbidCommentReason;
import com.bbkmobile.iqoo.console.dao.userfeedback.UserFeedback;
import com.bbkmobile.iqoo.explorer.logcache.UpgradeQueryLogCache;
import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;
import com.bbkmobile.iqoo.interfaces.keyword.dao.KeywordInfoDAO;
import com.bbkmobile.iqoo.interfaces.userfeedback.vo.ContentFilterResult;

@Repository("iUserFeedbakDAO")
public class UserFeedbackDAOImpl extends AnnotationBaseDao implements
        UserFeedbackDAO {
    @Resource(name = "iKeywordInfoDAO")
    private KeywordInfoDAO keywordInfoDAO;

    @Override
    public boolean addUserFeedBack(UserFeedback userFeedback) throws Exception {
        userFeedback.setPost_date(new Date());
        getHibernateTemplate().save(userFeedback);
        return true;
    }

    /*
     * 如果查询类型从 应用类型提交 则type_flag为ture，如果从标题栏提交则为false
     * 主要解决：当应用类型提交查询类型时，标题栏应用状态显示为全部类型。当从标题栏提交查询类型时，应用类型为“请选择”，两者不同步。
     */
    boolean type_flag = false;

    public List<AppComment> getAppCommentList(final int start,
            final AppComment appComment) throws Exception {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<AppComment> list = this.getHibernateTemplate().executeFind(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException {
                        Criteria criteria = session
                                .createCriteria(AppComment.class);

                        Criteria app_criteria = criteria.createCriteria( // 关联appInfo
                                "appInfo", Criteria.LEFT_JOIN);

                        // 应用类型查询
                        if (null != appComment.getAppInfo().getAppType()
                                .getId()
                                && 0 != appComment.getAppInfo().getAppType()
                                        .getId() && type_flag) { // type_flag为ture
                                                                 // 则查询类型从
                                                                 // 应用类型提交
                            app_criteria.add(Restrictions.eq("appType",
                                    appComment.getAppInfo().getAppType()));
                            appComment.getAppInfo().setType(0); // 标题栏类型置为
                                                                // 全部分类，下面从标题栏查询就不会执行了

                        }
                        // 从标题栏类型查询
                        if ((appComment.getAppInfo().getType() != 0)) { // 标题栏：分类
                            appComment.getAppInfo().getAppType()
                                    .setId(appComment.getAppInfo().getType());
                            app_criteria.add(Restrictions.eq("appType",
                                    appComment.getAppInfo().getAppType()));
                        }
                        // 状态查询

                        if (null != appComment.getComment_status()
                                && !appComment.getComment_status().equals(
                                        Constants.COMMENT_REVIEW_ALL)) {
                            criteria.add(Restrictions.eq("comment_status",
                                    appComment.getComment_status()));
                        }

                        // 应用名称查询
                        if (null != appComment.getAppInfo().getAppCnName()
                                && !UtilTool.checkStringNull(appComment
                                        .getAppInfo().getAppCnName())) {
                            app_criteria.add(Restrictions.like("appCnName", "%"
                                    + appComment.getAppInfo().getAppCnName()
                                    + "%"));
                        }
                        // 评论时间段查询
                        if (null != appComment.getFrom_date()) {
                            criteria.add(Restrictions.between("comment_date",
                                    appComment.getFrom_date(),
                                    appComment.getTo_date()));
                        }

                        criteria.setFirstResult(start);
                        criteria.setMaxResults(10);
                        List<AppComment> result = criteria.list();
                        return result;
                    }
                });
        return list;
    }

    @Override
    public boolean isForbidComment(RequestParameter requestParameter)
            throws Exception {
        if (null != requestParameter.getImei()
                && !requestParameter.getImei().equals("")) {
            String sql = "select imei from t_comment_forbid where imei= ?";// +
                                                                           // requestParameter.getImei();
            List list = getSession().createSQLQuery(sql)
                    .setString(0, requestParameter.getImei()).list();
            if (null != list && list.size() > 0) {
                return true;
            }
        }
        return false;
    }

    // 保存手机发送的用户评论
    @Override
    @Transactional
    public String saveComment(RequestParameter requestParameter)
            throws Exception {

        String result = "1";
        Long app_id_long = requestParameter.getIdlong();
        String target = requestParameter.getTarget();

        String user_name = requestParameter.getUser_name();
        String user_id = requestParameter.getUser_id();

        char login_type = filterUserName(requestParameter);// by time

        if (app_id_long < 0 || (null != target && "baidu".equals(target))) { // 保存百度app评论
            BaiduAppComment appComment = new BaiduAppComment();
            appComment.setApp_id(app_id_long);
            appComment.setUser_name(user_name); // user_name
            appComment.setUser_id(user_id);
            appComment.setLogin_type(login_type);
            appComment.setModel(requestParameter.getModel());
            appComment.setUser_ip(requestParameter.getIp());
            appComment.setUser_imei(requestParameter.getImei());
            appComment.setComment_date(new Date());
            // by time
            ContentFilterResult contentFilterResult = filterCensorInfo(requestParameter);
            if (contentFilterResult.getResult() == null) {
                appComment.setComment_status(contentFilterResult
                        .getComment_status()); // 未审核
                appComment.setComment(contentFilterResult.getContent());
            } else {
                return contentFilterResult.getResult();
            }
            appComment.setScore(requestParameter.getScore());
            getHibernateTemplate().save(appComment);
            return result;
        }
        // AppInfo appInfo = appInfoDAO.findAppById(app_id_long); // 不要select *
        // Float score_float = Float.parseFloat(score);
        // if (null != appInfo) {
        AppComment appComment = new AppComment();
        // appComment.setAppInfo(appInfo);
        appComment.getAppInfo().setId(app_id_long);
        appComment.setUser_name(user_name); // user_name
        appComment.setUser_id(user_id);
        appComment.setLogin_type(login_type);
        appComment.setModel(requestParameter.getModel());
        appComment.setUser_ip(requestParameter.getIp());
        appComment.setUser_imei(requestParameter.getImei());
        appComment.setComment_date(new Date());
        appComment.setAppversion(requestParameter.getAppVersion());
        appComment.setAppversioncode(requestParameter.getAppVersionCode());

        ContentFilterResult contentFilterResult = filterCensorInfo(requestParameter);// by
                                                                                     // time
        if (contentFilterResult.getResult() == null) {
            appComment.setComment_status(contentFilterResult
                    .getComment_status()); // 未审核
            appComment.setComment(contentFilterResult.getContent());
        } else {
            return contentFilterResult.getResult();
        }
        appComment.setScore(requestParameter.getScore());
        saveOrUpdateComment(appComment);
        // AppInfoDAO appInfoDAO;
        // appInfoDAO.saveAppInfoForSaveComment(appInfo,
        // requestParameter.getScore());
        updateAppInfoScore(app_id_long, requestParameter.getScore());
        updateCommentGrade(app_id_long, requestParameter.getScore()); // 评论等级统计
        // }
        return result;
    }

    private ContentFilterResult filterCensorInfo(
            RequestParameter requestParameter) throws Exception {
        String content = requestParameter.getContent();
        // System.out.println("content=" + content);
        // \s 匹配的是任意的空白符，包括空格，制表符(Tab)，换行符，中文全角空格
        // content = content.replaceAll("\\s{1,}", " ");
        content = content.replaceAll("[ \\f\\t\\v]{1,}", " "); // 去掉空格
        content = content.replaceAll("\\n{1,}", "\n"); // 去掉多余换行符号
        List<CensorWord> censorWords = keywordInfoDAO
                .getHitCensorWoreList(content);
        char comment_status = Constants.COMMENT_REVIEW_NO;
        ContentFilterResult result = new ContentFilterResult();
        if (null != censorWords) {
            String filterSwitch = ""; // 过滤替换
            for (CensorWord censorWord : censorWords) {
                int grade = censorWord.getGrade();
                if (Constants.CENSORWORD_GRADE_BANNED == grade) {
                    result.setResult(Constants.COMMENT_IS_ILLEGAL); // 代表有违禁内容，禁止显示
                    // 禁评当天
                    saveForbidComment(requestParameter);
                    return result;
                } else if (Constants.CENSORWORD_GRADE_MOD == grade) { // 待审核
                    comment_status = Constants.COMMENT_REVIEW_MOD;
                    break;
                } else if (Constants.CENSORWORD_GRADE_FILTER == grade) { // 替换
                    if (comment_status != Constants.COMMENT_REVIEW_MOD) { // 待审核级别
                                                                          // 高于
                                                                          // 已过滤级别
                        comment_status = Constants.COMMENT_REVIEW_FILTER;
                    }
                    filterSwitch = filterSwitch + censorWord.getWord().trim()
                            + "|";
                }
            }
            if (!"".equals(filterSwitch)) { // 去掉字符串中最后的"|"符号
                filterSwitch = filterSwitch.substring(0,
                        filterSwitch.length() - 1);
                content = content.replaceAll(filterSwitch, "**");
            }
        }
        result.setComment_status(comment_status);
        result.setContent(content);
        return result;
    }

    private char filterUserName(RequestParameter requestParameter) {
        char login_type = '0';
        String login_type_str = requestParameter.getLogin_type();
        // String user_name = requestParameter.getUser_name();
        if (null != login_type_str && !"".equals(login_type_str)) {
            if (login_type_str.equals(Constants.TAG_PHONE_NUM)) {
                login_type = '2';
                // user_name = user_name.substring(0, 3) + "****"
                // + user_name.substring(7, user_name.length());
                // requestParameter.setUser_name(user_name);
            } else if (login_type_str.equals(Constants.TAG_EMAIL)) {
                login_type = '3';
            } else if (login_type_str.equals(Constants.TAG_NAME)) {
                login_type = '1';
            }
        }
        return login_type;
    }

    private boolean saveForbidComment(RequestParameter requestParameter)
            throws Exception {
        ForbidComment forbidComment = new ForbidComment();
        forbidComment.setImei(null == requestParameter.getImei() ? ""
                : requestParameter.getImei());
        forbidComment.setForbid_status(Constants.COMMENT_FORBID_1);
        forbidComment.setAdd_date(new Date());
        ForbidCommentReason forbidCommentSeason = new ForbidCommentReason();
        forbidCommentSeason.setUser("vivo");
        forbidCommentSeason.setForbid_opinion(requestParameter.getContent());
        forbidComment.setForbidCommentSeason(forbidCommentSeason);
        getHibernateTemplate().save(forbidComment);
        return true;
    }

    private boolean updateCommentGrade(Long app_id, Float score)
            throws Exception {
        String sql = "";
        int grade = 0;
        if (5 == score) {
            grade = 5;
        } else if (4 == score) {
            grade = 4;
        } else if (3 == score) {
            grade = 3;
        } else if (2 == score) {
            grade = 2;
        } else if (1 == score) {
            grade = 1;
        }
        if (0 != grade) {
            if (isExistCommentGrade(app_id)) {
                sql = "update t_comment_grade set score" + grade + "=score"
                        + grade + "+1 where app_id=" + app_id;
            } else {
                sql = "insert into t_comment_grade(app_id,score" + grade
                        + ") value(" + app_id + ",1)";
            }
            getSession().createSQLQuery(sql).executeUpdate();
        }
        return true;
    }

    private boolean isExistCommentGrade(Long app_id) throws Exception {
        String sql = "select id from t_comment_grade where app_id=" + app_id;
        Integer id = (Integer) getSession().createSQLQuery(sql).uniqueResult();
        if (null != id) {
            return true;
        } else {
            return false;
        }

    }

    // 根据id查询用户评论
    public AppComment findAppCommentById(Long appComment_id) throws Exception {

        return getHibernateTemplate().load(AppComment.class, appComment_id);

    }

    @Override
    public boolean saveDownloadAppStatusLog(RequestParameter requestParameter)
            throws Exception {
        long elapsedtime = 0;
        short related = -1;
        short updated = -1;
        short cs = -1;
        String imei = ""; // modified by chenlizhi , 电信imei有字符

        if (null != requestParameter.getElapsedtime()
                && !requestParameter.getElapsedtime().equals("")) {
            elapsedtime = Long.parseLong(requestParameter.getElapsedtime());
        }
        if (null != requestParameter.getRelated()
                && !requestParameter.getRelated().equals("")) {
            related = Short.parseShort(requestParameter.getRelated());
        }
        if (null != requestParameter.getUpdate()
                && !requestParameter.getUpdate().equals("")) {
            updated = Short.parseShort(requestParameter.getUpdate());
        }
        if (null != requestParameter.getCsStr()
                && !requestParameter.getCsStr().equals("")) {
            cs = Short.parseShort(requestParameter.getCsStr());
        }
        if (null != requestParameter.getImei()
                && !requestParameter.getImei().equals("")) {
            imei = requestParameter.getImei();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar day = Calendar.getInstance();
        String date = sdf.format(day.getTime()).replace("-", "");

        String sql = "";
        if (null != requestParameter.getType()) {
            if (requestParameter.getType().equals("complete")) {
                sql = "insert into t_app_download_complete_log_"
                        + date
                        + "(imei,model,ip,cfrom,app_id,module_id,related,updated,elapsedtime,cs,version) "
                        + "value('" + imei + "','"
                        + requestParameter.getModel() + "',INET_ATON('"
                        + requestParameter.getIp() + "'),"
                        + requestParameter.getCfrom() + ","
                        + Long.parseLong(requestParameter.getIdStr()) + ",'"
                        + requestParameter.getModule_id() + "'," + related
                        + "," + updated + "," + elapsedtime + "," + cs + ",'"
                        + requestParameter.getVersion() + "')";
            } else if (requestParameter.getType().equals("cancel")) {
                sql = "insert into t_app_download_log_"
                        + date
                        + "(imei,model,ip,cfrom,app_id,module_id,related,updated,elapsedtime,cs,version,status) "
                        + "value('" + imei + "','"
                        + requestParameter.getModel() + "',INET_ATON('"
                        + requestParameter.getIp() + "'),"
                        + requestParameter.getCfrom() + ","
                        + Long.parseLong(requestParameter.getIdStr()) + ",'"
                        + requestParameter.getModule_id() + "'," + related
                        + "," + updated + "," + elapsedtime + "," + cs + ",'"
                        + requestParameter.getVersion() + "','" + "2'" + ")";

            } else {
                Lg.info(LgType.USERFEEDBACK,
                        "saveDownloadAppStatusLog时为空:status="
                                + requestParameter.getType() + ",id="
                                + requestParameter.getIdStr() + ",target="
                                + requestParameter.getTarget() + ",cfrom="
                                + requestParameter.getCfrom() + ",module_id="
                                + requestParameter.getModule_id() + ",related="
                                + requestParameter.getRelated() + ",update="
                                + requestParameter.getUpdate() + ",appVersion="
                                + requestParameter.getAppVersion() + ",model="
                                + requestParameter.getModel() + ",cs="
                                + requestParameter.getCsStr() + ",imei="
                                + requestParameter.getImei());
            }
        }

        if (Constants.USE_LOGCACHE) {
            UpgradeQueryLogCache.getIns().add(sql);
        } else {
            getSession().createSQLQuery(sql).executeUpdate();
        }
        return true;
    }

    @Override
    public void saveOrUpdateComment(final AppComment comment) throws Exception {
        if (comment != null) {
            getHibernateTemplate().execute(new HibernateCallback<Object>() {
                @Override
                public Object doInHibernate(Session session)
                        throws HibernateException, SQLException {
                    String sql = " insert into t_app_comment (user_name, user_id, login_type, user_imei, model, app_id, appversion, appversioncode,"
                            + " score, comment, agent, comment_date, comment_status, good_count, bad_count, report_count, backup_comment)"
                            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    session.createSQLQuery(sql)
                            .setString(0, comment.getUser_name())
                            .setString(1, comment.getUser_id())
                            .setCharacter(2, comment.getLogin_type())
                            .setString(3, comment.getUser_imei())
                            .setString(4, comment.getModel())
                            .setLong(5, comment.getAppInfo().getId())
                            .setString(6, comment.getAppversion())
                            .setString(7, comment.getAppversioncode())
                            .setFloat(8, comment.getScore())
                            .setString(9, comment.getComment())
                            .setString(10, comment.getAgent())
                            .setTimestamp(11, comment.getComment_date())
                            .setCharacter(12, comment.getComment_status())
                            .setInteger(13, comment.getGood_count())
                            .setInteger(14, comment.getBad_count())
                            .setInteger(15, comment.getReport_count())
                            .setString(16, comment.getBackup_comment())
                            .executeUpdate();
                    return null;
                }
            });
        }
    }

    @Override
    public void saveOrUpdateComment(BaiduAppComment comment) throws Exception {
        if (comment != null) {
            getHibernateTemplate().save(comment);
        }
    }

    public void updateAppInfoScore(final Long app_id, final float score)
            throws Exception {
        getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                String sql = "update t_app_info set commentCount = commentCount + 1  , "
                        + " avgComment = (commentSum + :commentSum)/commentCount  ,"// 次数已经加1
                        + " commentSum = commentSum + :commentSum where id=:id ";
                session.createSQLQuery(sql).setFloat("commentSum", score)
                        .setLong("id", app_id).executeUpdate();
                return null;
            }
        });
    }
}
