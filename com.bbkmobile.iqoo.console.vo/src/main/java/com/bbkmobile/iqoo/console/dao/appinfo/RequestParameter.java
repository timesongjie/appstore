package com.bbkmobile.iqoo.console.dao.appinfo;

import java.util.Date;

public class RequestParameter {

    private static final int MODELSIZE = 50;
    private static final int IMEISIZE = 20;
    private static final int IPSIZE = 10;
    private static final int USERNAMESIZE = 100;
    private static final int USERIDSIZE = 50;
    private static final int KEYSIZE = 50;
    private static final int CSSIZE = 5;
    private static final int PACAKGESIZE = 100;
    private static final int VERSIONSIZE = 5;

    private long idlong;

    private int apps_per_page; // 每页请求的个数
    private int page_index; // 请求的页码
    private String model; // 机型，例如：vivo Y1
    private int cs; // 请求终端：0-手机 1-pc
    private String imei; // 手机imei码
    private float app_version; // PC端特有的参数，表示软件商店的版本。（用来区分服务器端百度垂搜，大于200可以调用百度垂搜，小于200不能调用百度垂搜）
    private int order_type; // 排序方式
    private String key; // 汇英文名
    private int id;
    private String word; // 搜索关键词
    private String dbversion;

    private String user_name;
    private String ip;
    private String content;
    private float score;

    private String target; // baidu or local

    private String type; // 焦点图类型 下载状态

    // 获取app详情相关
    private String search_type; // 根据id或package_name获取app详情
    private String content_complete; // 是否需要完整的详细
    private String need_comment; // 是否评论
    private String versioncode;
    // private String signmd5;

    // 以下变量为记录日志相关
    private String idStr;
    private Short cfrom;
    private String csStr;
    private String pageIndexStr;
    private Date add_date;
    private String packages; // 热门汇推荐用此参数 根据包名获取详情
    private String version; // 汇、专题接口用此参数
    private String elapsedtime; // 开机时间
    private String module_id; // 广告/汇/专题id
    private String related; // 相关推荐标识 1
    private String update;
    private String cp_name;

    private String user_id;
    private String login_type;

    private String appVersion;
    private String appVersionCode;
    // OTA（默认）为1
    // 自升级 为2
    // 商店推广 为3
    private String channle;

    public int getApps_per_page() {
        return this.apps_per_page;
    }

    public void setApps_per_page(int apps_per_page) {
        this.apps_per_page = apps_per_page;
    }

    public int getPage_index() {
        return this.page_index;
    }

    public void setPage_index(int page_index) {
        this.page_index = page_index;
    }

    public String getModel() {
        if (model != null && model.length() > MODELSIZE) {
            return model.substring(0, MODELSIZE);
        }
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCs() {
        return this.cs;
    }

    public void setCs(int cs) {
        this.cs = cs;
    }

    public String getImei() {
        if (imei != null && imei.length() > IMEISIZE) {
            return imei.substring(0, IMEISIZE);
        }
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public float getApp_version() {
        return this.app_version;
    }

    public void setApp_version(float app_version) {
        this.app_version = app_version;
    }

    public int getOrder_type() {
        return this.order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        if (user_name != null && user_name.trim().length() > USERNAMESIZE) {
            return user_name.substring(0, USERNAMESIZE);
        }
        return this.user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getIp() {
        if (ip != null && ip.length() > IPSIZE) {
            return ip.substring(0, IPSIZE);
        }
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getScore() {
        return this.score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getIdStr() {
        return this.idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public Short getCfrom() {
        return this.cfrom;
    }

    public void setCfrom(Short cfrom) {
        this.cfrom = cfrom;
    }

    public String getCsStr() {
        if (csStr != null && csStr.length() > CSSIZE) {
            return csStr.substring(0, CSSIZE);
        }
        return this.csStr;
    }

    public void setCsStr(String csStr) {
        this.csStr = csStr;
    }

    public String getPageIndexStr() {
        return this.pageIndexStr;
    }

    public void setPageIndexStr(String pageIndexStr) {
        this.pageIndexStr = pageIndexStr;
    }

    public long getIdlong() {
        return this.idlong;
    }

    public void setIdlong(long idlong) {
        this.idlong = idlong;
    }

    public Date getAdd_date() {
        return this.add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public String getPackages() {
        if (this.packages != null && this.packages.length() > PACAKGESIZE) {
            return packages.substring(0, PACAKGESIZE);
        }
        return this.packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getVersion() {
        if (this.version != null && this.version.length() > VERSIONSIZE) {
            return this.version.substring(0, VERSIONSIZE);
        }
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getWord() {
        if (word != null && word.length() > KEYSIZE) {
            return word.substring(0, KEYSIZE);
        }
        return this.word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSearch_type() {
        return this.search_type;
    }

    public void setSearch_type(String search_type) {
        this.search_type = search_type;
    }

    public String getContent_complete() {
        return this.content_complete;
    }

    public void setContent_complete(String content_complete) {
        this.content_complete = content_complete;
    }

    public String getVersioncode() {
        return this.versioncode;
    }

    public void setVersioncode(String versioncode) {
        this.versioncode = versioncode;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getDbversion() {
        return this.dbversion;
    }

    public void setDbversion(String dbversion) {
        this.dbversion = dbversion;
    }

    public String getUser_id() {
        if (user_id != null && user_id.length() > USERIDSIZE) {
            return user_id.substring(0, USERIDSIZE);
        }
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLogin_type() {
        return this.login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public String getElapsedtime() {
        return this.elapsedtime;
    }

    public void setElapsedtime(String elapsedtime) {
        this.elapsedtime = elapsedtime;
    }

    public String getModule_id() {
        return this.module_id;
    }

    public void setModule_id(String module_id) {
        this.module_id = module_id;
    }

    public String getRelated() {
        return this.related;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppVersionCode() {
        return this.appVersionCode;
    }

    public void setAppVersionCode(String appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getUpdate() {
        return this.update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNeed_comment() {
        return need_comment;
    }

    public void setNeed_comment(String need_comment) {
        this.need_comment = need_comment;
    }

    public String getCp_name() {
        return cp_name;
    }

    public void setCp_name(String cp_name) {
        this.cp_name = cp_name;
    }

    public String getChannle() {
        return channle;
    }

    public void setChannle(String channle) {
        this.channle = channle;
    }

}
