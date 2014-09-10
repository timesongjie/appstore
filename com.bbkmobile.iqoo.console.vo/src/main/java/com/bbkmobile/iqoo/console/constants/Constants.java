/**
 * 
 */
package com.bbkmobile.iqoo.console.constants;

import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.bbkmobile.iqoo.common.util.cfgfile.PropertiesFileManager;

/**
 * @author wangbo
 * @version 1.0.0.0/2011-12-22
 */
public class Constants {
    public static boolean SAVE_BROWSE_LOG = true;
    public static boolean SAVE_BROWSE_APP_LOG = true;
    public static boolean SAVE_PACKAGE_UPDATE_LOG = true;
    public static boolean SAVE_SEARCH_KEY_LOG = true;
    public static boolean SAVE_APP_DOWNLOAD_LOG = true;
    public static boolean SAVE_APP_BAIDU_DOWNLOAD_LOG = true;
    public static boolean UPDATE_PACKAGES_SWITCH = true;
    public static boolean USE_EHCACHE = false;
    public static boolean USE_LOGCACHE = false;

    public final static String PRO_SAVE_BROWSE_LOG = "save_browse_log";
    public final static String PRO_SAVE_BROWSE_APP_LOG = "save_browse_app_log";
    public final static String PRO_SAVE_PACKAGE_UPDATE_LOG = "save_package_update_log";
    public final static String PRO_SAVE_SEARCH_KEY_LOG = "save_search_key_log";
    public final static String PRO_SAVE_APP_DOWNLOAD_LOG = "save_app_download_log";
    public final static String PRO_SAVE_APP_BAIDU_DOWNLOAD_LOG = "save_app_baidu_download_log";
    public final static String PRO_UPDATE_PACKAGES_SWITCH = "update_packages_switch";

    public static boolean SWITCH_CLOSE_BAIDU_SEARCH = false;
    public static boolean SWITCH_CLOSE_BAIDU_RELATED = false;
    public static boolean SWITCH_CLOSE_BAIDU_SUG = false;
    public static boolean SWITCH_CLOSE_BAIDU_APP = false;
    // 关联查询开关
    public static boolean SWITCH_CLOSE_RELATION_SUG = false;
    public static boolean SWITCH_CLOSE_MATCH_SUG = false;
    public static boolean SWITCH_CLOSE_BAIDU_SUG2 = false;
    // 配置搜索屏蔽站点
    public static String SEARCH_BLOCKED_SITES = "";
    static {
        if (!PropertiesFileManager.getInstance()
                .getProperty(PRO_SAVE_BROWSE_LOG).trim().equals("true")) {
            SAVE_BROWSE_LOG = false;
        }
        if (!PropertiesFileManager.getInstance()
                .getProperty(PRO_SAVE_BROWSE_APP_LOG).trim().equals("true")) {
            SAVE_BROWSE_APP_LOG = false;
        }
        if (!PropertiesFileManager.getInstance()
                .getProperty(PRO_SAVE_PACKAGE_UPDATE_LOG).trim().equals("true")) {
            SAVE_PACKAGE_UPDATE_LOG = false;
        }
        if (!PropertiesFileManager.getInstance()
                .getProperty(PRO_SAVE_SEARCH_KEY_LOG).trim().equals("true")) {
            SAVE_SEARCH_KEY_LOG = false;
        }
        if (!PropertiesFileManager.getInstance()
                .getProperty(PRO_SAVE_APP_DOWNLOAD_LOG).trim().equals("true")) {
            SAVE_APP_DOWNLOAD_LOG = false;
        }
        if (!PropertiesFileManager.getInstance()
                .getProperty(PRO_SAVE_APP_BAIDU_DOWNLOAD_LOG).trim()
                .equals("true")) {
            SAVE_APP_BAIDU_DOWNLOAD_LOG = false;
        }
        if (!PropertiesFileManager.getInstance()
                .getProperty(PRO_UPDATE_PACKAGES_SWITCH).trim().equals("true")) {
            UPDATE_PACKAGES_SWITCH = false;
        }
        if (PropertiesFileManager.getInstance().getProperty("use_ehcache")
                .trim().equals("true")) {
            USE_EHCACHE = true;
        }
        if (PropertiesFileManager.getInstance().getProperty("use_logcache")
                .trim().equals("true")) {
            USE_LOGCACHE = true;
        }
        if (PropertiesFileManager.getInstance()
                .getProperty("switch_close_baidu_search").trim().equals("1")) {
            SWITCH_CLOSE_BAIDU_SEARCH = true;
        }
        if (PropertiesFileManager.getInstance()
                .getProperty("switch_close_baidu_related").trim().equals("1")) {
            SWITCH_CLOSE_BAIDU_RELATED = true;
        }
        if (PropertiesFileManager.getInstance()
                .getProperty("switch_close_baidu_bug").trim().equals("1")) {
            SWITCH_CLOSE_BAIDU_SUG = true;
        }
        if (PropertiesFileManager.getInstance()
                .getProperty("switch_close_baidu_app").trim().equals("1")) {
            SWITCH_CLOSE_BAIDU_APP = true;
        }

        if (PropertiesFileManager.getInstance().getProperty(
                "switch_close_relation_sug") != null
                && PropertiesFileManager.getInstance()
                        .getProperty("switch_close_relation_sug").trim()
                        .equals("1")) {
            SWITCH_CLOSE_RELATION_SUG = true;
        }
        if (PropertiesFileManager.getInstance().getProperty(
                "switch_close_match_sug") != null
                && PropertiesFileManager.getInstance()
                        .getProperty("switch_close_match_sug").trim()
                        .equals("1")) {
            SWITCH_CLOSE_MATCH_SUG = true;
        }
        if (PropertiesFileManager.getInstance().getValueFromPropFile(
                "switch_close_baidu_sug") != null
                && PropertiesFileManager.getInstance()
                        .getValueFromPropFile("switch_close_baidu_sug").trim()
                        .equals("1")) {
            SWITCH_CLOSE_BAIDU_SUG2 = true;
        }
        if (StringUtils.isNotEmpty(PropertiesFileManager.getInstance()
                .getProperty("search_blocked_sites"))) {
            SEARCH_BLOCKED_SITES = PropertiesFileManager.getInstance()
                    .getProperty("search_blocked_sites").trim();
        }
    }

    public static final Float APPSTORE_VERSION_5_1 = (float) 510;

    public static final String SESSION_AUTH_USER = "session_auth_user";

    public final static int FILE_EOF = -1;

    public final static String PRO_WEBAPP_WEBROOT = "pro.path";
    public final static String APP_MAIN_URL = "app.main.url";

    public final static String DOWNLOAD_MAIN_URL = "download.main.url";

    public final static String DOWNLOAD_IMAGE_URL = "download.image.url";
    public final static String DOWNLOAD_APK_URL = "download.apk.url";

    public final static String CONSOLE_USER_LOGIN = "登录";
    public final static String CONSOLE_USER_LOGOUT = "退出";

    public final static int HUB_HOT_MAX_PAGE = 3;

    // 拍行榜设置
    public final static int APP = 1; // 类型为应用类
    public final static int GAME = 12; // 类型为游戏类

    public final static char FREE = '1'; // sellType=1免费应用
    public final static char PAY = '2'; // sellType=2收费应用

    public final static int FREE_APP = 1; // 免费应用
    public final static int PAY_APP = 2; // 付费应用
    public final static int FREE_GAME = 3; // 免费游戏
    public final static int PAY_GAME = 4; // 付费游戏

    public final static String USER_COMMENT = "user_comment"; // 用户评论
    public final static String ERROR_REPORT = "error_report"; // 错误报告

    public final static char COMMENT = '1'; // 用户评论
    public final static char REPORT = '2'; // 错误报告

    public final static String TAG_NAME = "name";
    public final static String TAG_EMAIL = "email";
    public final static String TAG_PHONE_NUM = "phonenum";

    // 开发者资料审核
    public final static int PERSONAL_DEVELOPER = 0; // 个人开发者
    public final static int COMPANY_DEVELOPER = 1; // 企业开发者

    // 应用汇类型
    public final static int SOCIAL_HUB = 1; // 社交汇
    public final static int READ_HUB = 2; // 阅读汇
    public final static int GAME_HUB = 3; // 游戏汇
    public final static int MOVIE_HUB = 4; // 影视汇
    public final static int INFORMATION_HUB = 5; // 资讯汇
    public final static int MUSIC_HUB = 6; // 音乐汇
    public final static int LIFE_HUB = 7; // 音乐汇

    // 设置手机首页广告的方式
    public final static short DEFAULT_TYPE = 0; // 默认值方式
    public final static short SERIES_TYPE = 1; // 按手机系列
    public final static short MODEL_TYPE = 2; // 按手机机型

    // 广告图片前缀，区分是广告图片还是专题,活动
    public final static String ADVERTISEMENT_ICON = "adimg"; // 按手机机型
    public final static String TOPIC_ICON = "topicimg"; // 按手机机型
    public final static String ACTIVITY_ICON = "activityimg"; // 按手机机型

    // 广告图片前缀区分是PC端还是手机端
    public final static String PH_ICON = "ph"; // 按手机机型
    public final static String PC_ICON = "pc"; // 按手机机型

    public final static char RECOMMEND = '1'; // 汇设置应用时设置为主推
    public final static char CANCEL_RECOMMEND = '0'; // 汇设置应用时取消设置为主推

    public final static int APPS_PER_PAGE_CS = 25; // 终端请求时默认每页app数量
    public final static int APPS_PER_PAGE = 10; // 终端请求时默认每页app数量_
    public final static int APPS_PER_PAGE_PC_TOP = 10; // 终端请求时默认每页app数量_

    public final static int APPS_PER_MAX_PAGE = 220; // 后台请求的每大页app数量
    public final static int APPS_PER_PAGE_2 = 15; // 终端请求新品推荐

    public final static int APPS_PER_PAGE_20 = 20; // 后台请求的每大页app数量

    // 排行榜 排序
    public final static int TOP_DOWNLOADCOUNT_DESC = 0; // 排行榜按下载降序排序
    public final static int TOP_AVGCOMMENT_DESC = 1; // 按评分
    public final static int TOP_ONSALEDATE_DESC = 2; // 按发布日期
    public final static int TOP_DOWNLOADCOUNT_ASC = 3;
    public final static int TOP_AVGCOMMENT_ASC = 4;
    public final static int TOP_ONSALEDATE_ASC = 5;
    public final static int TOP_APPLICATION = 6;
    public final static int TOP_GAME = 7;
    public final static int TOP_RISE = 8;

    // 类型、汇 排序
    public final static int TYPE_ONSALEDATE_ASC = 0; // 类型、汇：按发布日期升序排序
    public final static int TYPE_ONSALEDATE_DESC = 1;
    public final static int TYPE_DOWNLOADCOUNT_ASC = 2; // 按排行榜
    public final static int TYPE_DOWNLOADCOUNT_DESC = 3;
    public final static int TYPE_AVGCOMMENT_ASC = 4; // 按评分
    public final static int TYPE_AVGCOMMENT_DESC = 5;

    // 评论状态
    public final static char COMMENT_REVIEW_ALL = '0'; // 评论全部状态
    public final static char COMMENT_REVIEW_NO = '1'; // 评论未审核
    public final static char COMMENT_REVIEW_PASS = '2'; // 评论审核通过
    public final static char COMMENT_REVIEW_NO_PASS = '3'; // 评论审核不通过
    public final static char COMMENT_REVIEW_MOD = '4'; // 待审核
    public final static char COMMENT_REVIEW_FILTER = '5'; // 评论已过滤
    public final static char COMMENT_REVIEW_BANNED = '6'; // 评论禁止显示

    // 敏感词汇等级：
    public final static int CENSORWORD_GRADE_BANNED = 1; // 敏感词汇等级：禁止
    public final static int CENSORWORD_GRADE_MOD = 2; // 审核
    public final static int CENSORWORD_GRADE_FILTER = 3; // 过滤

    public final static int numPerPage = 10; // 每页显示条数

    public final static int HOTHUBCOUNT = 4;

    public final static int TOP_COUNT = 200; // 限制排行榜条数

    public final static int RELATED_REC_COUNT = 9;
    public final static int RELATED_REC_COUNT_2 = 8;

    public final static String COMMENT_IS_ILLEGAL = "该评论无效，包含违禁信息"; // 您提交的评论含违禁信息，请核对后再提交！
    public final static String COMMENT_IS_PUBLISHED = "已评论过该应用";
    public final static String COMMENT_TERMINAL_FORBID = "该终端涉及违规评论，暂时无法评论";

    public final static Short COMMENT_FORBID_1 = 1; // 禁当天
    public final static Short COMMENT_FORBID_2 = 2; // 禁2天
    public final static Short COMMENT_FORBID_3 = 3; // 长期禁止

    public final static int COMMENT_COUNT_FOR_PACKAGE = 5;

    public final static String DOWNLOAD_APK_FILE_ACTION_URL = "/appinfo/downloadApkFile";// 下载apk的action
                                                                                         // url

    public final static String ERROR_REPORT_FAIL = "提交失败！";

    public final static short FROM_RECOMMEND = 1;
    public final static short FROM_ADVERTISMENT = 2;
    public final static short FROM_TYPE = 3;
    public final static short FROM_TOP = 4;
    public final static short FROM_SEARCH = 5;
    public final static short FROM_HUB = 6;
    public final static short FROM_TOPIC = 7;
    /*
     * public final static short FROM_RECOMMEND_APP=8; public final static short
     * FROM_ADVERTISMENT_APP=9; public final static short FROM_TYPE_APP=10;
     * public final static short FROM_TOP_APP=11; public final static short
     * FROM_SEARCH_APP=12; public final static short FROM_HUB_APP=13; public
     * final static short FROM_HOTHUB_APP=14; public final static short
     * FROM_TOPIC_APP=15;
     */

    public final static short FROM_BAIDU_APP = 16;

    // public final static short FROM_PACKAGE_UPDATE=17;
    public final static short FROM_HOT_HUB = 18;
    // public final static short FROM_THIRD_APP=19;
    public final static short FROM_SELF_UPDATE_APP = 20;

    public final static short FROM_HUB_SEACH = 21;
    // public final static short FROM_HUB_SEACH_APP = 22;
    //
    // public final static short FROM_RELATED_REC_APP = 23;

    public final static short FROM_HOT_HUB_SELF = 26; // 热门推荐汇自动刷新
    public final static short FROM_HOT_HUB_MAN = 27; // 热门推荐汇手动刷新

    public final static short FROM_NEW = 28; // 新品速递
    public final static short FROM_INSTALLED = 29; // 装机必备
    /*
     * public final static short FROM_RECOMMEND_APP_DOWNLOAD=8*10; public final
     * static short FROM_ADVERTISMENT_APP_DOWNLOAD=9*10; public final static
     * short FROM_TYPE_APP_DOWNLOAD=10*10; public final static short
     * FROM_TOP_APP_DOWNLOAD=11*10; public final static short
     * FROM_SEARCH_APP_DOWNLOAD=12*10; public final static short
     * FROM_HUB_APP_DOWNLOAD=13*10; public final static short
     * FROM_HOTHUB_APP_DOWNLOAD=14*10; public final static short
     * FROM_TOPIC_APP_DOWNLOAD=15*10;
     */
    public final static short FROM_BAIDU_APP_DOWNLOAD = 16 * 10;

    public final static short FROM_UPDATE_APP_DOWNLOAD = 17 * 10; // 无效
    // public final static short FROM_SELF_UPDATE_APP_DOWNLOAD=20*10;
    /*
     * public final static short FROM_BAIDU_APP_LIST_DOWNLOAD=21*10;
     * 
     * public final static short FROM_HUB_SEACH_APP_DOWNLOAD = 22*10; public
     * final static short FROM_RELATED_REC_APP_DOWNLOAD = 23*10; public final
     * static short FROM_RELATED_REC_BAIDUAPP_DOWNLOAD = 240; public final
     * static short FROM_MANAGE_DOWNLOAD = 25; //实际保存25*10
     * 
     * public final static short FROM_RECOMMEND_LIST_APP_DOWNLOAD=31*10; public
     * final static short FROM_ADVERTISMENT_LIST_APP_DOWNLOAD=32*10; public
     * final static short FROM_TYPE_LIST_APP_DOWNLOAD=33*10; public final static
     * short FROM_TOP_LIST_APP_DOWNLOAD=34*10; public final static short
     * FROM_SEARCH_LIST_APP_DOWNLOAD=35*10; public final static short
     * FROM_HUB_APP_LIST_DOWNLOAD=36*10; public final static short
     * FROM_TOPIC_LIST_APP_DOWNLOAD=37*10; public final static short
     * FROM_HOTHUB_LIST_APP_DOWNLOAD=38*10; public final static short
     * FROM_BAIDU_LIST_APP_DOWNLOAD=39*10;
     * 
     * public final static short FROM_HUB_SEACH_LIST_APP_DOWNLOAD = 40*10;
     * 
     * public final static short FROM_HUB_APP_GAME=41; public final static short
     * FROM_HUB_APP_SNS=42; public final static short FROM_HUB_APP_LIFE=43;
     * public final static short FROM_HUB_APP_MOVIE=44; public final static
     * short FROM_HUB_APP_EBOOK=45; public final static short
     * FROM_HUB_APP_INFORMATION=46; public final static short
     * FROM_HUB_APP_MUSIC=47; public final static short FROM_HUB_APP_AUDIO=48;
     * public final static short FROM_HUB_APP_READ=49;
     * 
     * public final static short FROM_HOTHUB_APP_GAME=51; public final static
     * short FROM_HOTHUB_APP_SNS=52; public final static short
     * FROM_HOTHUB_APP_LIFE=53; public final static short
     * FROM_HOTHUB_APP_MOVIE=54; public final static short
     * FROM_HOTHUB_APP_EBOOK=55; public final static short
     * FROM_HOTHUB_APP_INFORMATION=56; public final static short
     * FROM_HOTHUB_APP_MUSIC=57; public final static short
     * FROM_HOTHUB_APP_AUDIO=58; public final static short
     * FROM_HOTHUB_APP_READ=59;
     * 
     * public final static short FROM_HUB_APP_LIST_DOWNLOAD_GAME=61; public
     * final static short FROM_HUB_APP_LIST_DOWNLOAD_SNS=62; public final static
     * short FROM_HUB_APP_LIST_DOWNLOAD_LIFE=63; public final static short
     * FROM_HUB_APP_LIST_DOWNLOAD_MOVIE=64; public final static short
     * FROM_HUB_APP_LIST_DOWNLOAD_EBOOK=65; public final static short
     * FROM_HUB_APP_LIST_DOWNLOAD_INFORMATION=66; public final static short
     * FROM_HUB_APP_LIST_DOWNLOAD_MUSIC=67; public final static short
     * FROM_HUB_APP_LIST_DOWNLOAD_AUDIO=68; public final static short
     * FROM_HUB_APP_LIST_DOWNLOAD_READ=69;
     * 
     * public final static short FROM_HOTHUB_APP_LIST_DOWNLOAD_GAME=71; public
     * final static short FROM_HOTHUB_APP_LIST_DOWNLOAD_SNS=72; public final
     * static short FROM_HOTHUB_APP_LIST_DOWNLOAD_LIFE=73; public final static
     * short FROM_HOTHUB_APP_LIST_DOWNLOAD_MOVIE=74; public final static short
     * FROM_HOTHUB_APP_LIST_DOWNLOAD_EBOOK=75; public final static short
     * FROM_HOTHUB_APP_LIST_DOWNLOAD_INFORMATION=76; public final static short
     * FROM_HOTHUB_APP_LIST_DOWNLOAD_MUSIC=77; public final static short
     * FROM_HOTHUB_APP_LIST_DOWNLOAD_AUDIO=78; public final static short
     * FROM_HOTHUB_APP_LIST_DOWNLOAD_READ=79;
     * 
     * public final static short FROM_HUB_APP_DOWNLOAD_GAME=41*10; public final
     * static short FROM_HUB_APP_DOWNLOAD_SNS=42*10; public final static short
     * FROM_HUB_APP_DOWNLOAD_LIFE=43*10; public final static short
     * FROM_HUB_APP_DOWNLOAD_MOVIE=44*10; public final static short
     * FROM_HUB_APP_DOWNLOAD_EBOOK=45*10; public final static short
     * FROM_HUB_APP_DOWNLOAD_INFORMATION=46*10; public final static short
     * FROM_HUB_APP_DOWNLOAD_MUSIC=47*10; public final static short
     * FROM_HUB_APP_DOWNLOAD_AUDIO=48*10; public final static short
     * FROM_HUB_APP_DOWNLOAD_READ=49*10;
     * 
     * public final static short FROM_HOTHUB_APP_DOWNLOAD_GAME=51*10; public
     * final static short FROM_HOTHUB_APP_DOWNLOAD_SNS=52*10; public final
     * static short FROM_HOTHUB_APP_DOWNLOAD_LIFE=53*10; public final static
     * short FROM_HOTHUB_APP_DOWNLOAD_MOVIE=54*10; public final static short
     * FROM_HOTHUB_APP_DOWNLOAD_EBOOK=55*10; public final static short
     * FROM_HOTHUB_APP_DOWNLOAD_INFORMATION=56*10; public final static short
     * FROM_HOTHUB_APP_DOWNLOAD_MUSIC=57*10; public final static short
     * FROM_HOTHUB_APP_DOWNLOAD_AUDIO=58*10; public final static short
     * FROM_HOTHUB_APP_DOWNLOAD_READ=59*10;
     * 
     * public final static short FROM_HUB_APP_LIST_DOWNLOAD_GAME=61*10; public
     * final static short FROM_HUB_APP_LIST_DOWNLOAD_SNS=62*10; public final
     * static short FROM_HUB_APP_LIST_DOWNLOAD_LIFE=63*10; public final static
     * short FROM_HUB_APP_LIST_DOWNLOAD_MOVIE=64*10; public final static short
     * FROM_HUB_APP_LIST_DOWNLOAD_EBOOK=65*10; public final static short
     * FROM_HUB_APP_LIST_DOWNLOAD_INFORMATION=66*10; public final static short
     * FROM_HUB_APP_LIST_DOWNLOAD_MUSIC=67*10; public final static short
     * FROM_HUB_APP_LIST_DOWNLOAD_AUDIO=68*10; public final static short
     * FROM_HUB_APP_LIST_DOWNLOAD_READ=69*10;
     * 
     * public final static short FROM_HOTHUB_APP_LIST_DOWNLOAD_GAME=71*10;
     * public final static short FROM_HOTHUB_APP_LIST_DOWNLOAD_SNS=72*10; public
     * final static short FROM_HOTHUB_APP_LIST_DOWNLOAD_LIFE=73*10; public final
     * static short FROM_HOTHUB_APP_LIST_DOWNLOAD_MOVIE=74*10; public final
     * static short FROM_HOTHUB_APP_LIST_DOWNLOAD_EBOOK=75*10; public final
     * static short FROM_HOTHUB_APP_LIST_DOWNLOAD_INFORMATION=76*10; public
     * final static short FROM_HOTHUB_APP_LIST_DOWNLOAD_MUSIC=77*10; public
     * final static short FROM_HOTHUB_APP_LIST_DOWNLOAD_AUDIO=78*10; public
     * final static short FROM_HOTHUB_APP_LIST_DOWNLOAD_READ=79*10;
     * 
     * public final static short FROM_NEW_APP=81; //新品速递app详情 public final
     * static short FROM_INSTALLED_APP=82; //装机必备app详情 public final static short
     * FROM_NEW_APP_DOWNLOAD=81*10; //详情中下载 public final static short
     * FROM_INSTALLED_APP_DOWNLOAD=82*10; //详情中下载 public final static short
     * FROM_NEW_LIST_APP_DOWNLOAD=83*10; //列表中下载 public final static short
     * FROM_INSTALLED_LIST_APP_DOWNLOAD=84*10; //列表中下载
     */
    public final static short FROM_MANAGEMENT_INSTALLED_APP = 85;

    public final static short FROM_ACTIVITY = 33;

    public final static short FROM_FOCUS_PICTURE = 8;

    public final static int ONE_COUNT_DELETE_LOG = 10000 * 10;
    public final static boolean DELETE_BROWSE_LOG = true;

    // public final static String
    // BAIDU_APP_DISCLAIMER=" ★★★★★★★★★★★★★★★★★★★★★\n" +
    // " ★     该软件由vivo合作伙伴“百度应用”提供     ★\n ★    由于未逐一适配,可能无法正常安装使用   ★\n" +
    // " ★★★★★★★★★★★★★★★★★★★★★\n\n";
    public final static String BAIDU_APP_DISCLAIMER = "该应用由vivo合作伙伴“百度应用”提供<br/><br/>";
    // public final static String BAIDU_APP_DISCLAIMER_FORMAT="<br/>" +
    // "&nbsp&nbsp&nbsp&nbsp 该软件由vivo合作伙伴“百度应用”提供&nbsp&nbsp&nbsp&nbsp&nbsp<br/>"
    // +
    // "&nbsp&nbsp&nbsp 由于未逐一适配,可能无法正常安装使用&nbsp&nbsp&nbsp<br/>" +
    // "<br/><br/>";
    public final static String BAIDU_APP_DISCLAIMER_FORMAT = "该应用由vivo合作伙伴“百度应用”提供<br/><br/>";

    public final static short OPERATION_USER_LOGIN = 1;
    public final static short OPERATION_APP_ONSALE = 201; // 上架
    public final static short OPERATION_APP_OFFSALE = 202; // 下架
    public final static short OPERATION_APP_DELETE = 203;
    public final static short OPERATION_APP_ORDER = 204;

    public final static short OPERATION_RECOMMEND_ADD = 301;
    public final static short OPERATION_RECOMMEND_CANSEL = 302;
    public final static short OPERATION_RECOMMEND_ORDER = 303;
    public final static short OPERATION_RECOMMEND_ORDER_DRAG = 304;
    public final static short OPERATION_RECOMMEND_INIT = 305;

    public static String token = "vivo@iqoo";

    public final static short APP_ONSALE = 0;
    public final static short APP_PART_ONSALE = 13;
    public final static short APP_OFFSALE = 12;

    public static Vector<String> packageTread = new Vector<String>(); // 线程安全，不允许两个人同时抓取一个包

    public final static int APPVERSION530 = 530;

    public final static String TOKEN_KEY = "bbk19950918@vivo#iqoo$";
}
