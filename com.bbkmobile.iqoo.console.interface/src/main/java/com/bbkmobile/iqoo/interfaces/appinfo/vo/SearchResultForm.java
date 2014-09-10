/**
 * 
 */
package com.bbkmobile.iqoo.interfaces.appinfo.vo;

/**
 * @author wangbo
 * 
 */
public class SearchResultForm {

    private String imei;// imei码
    private String model;// 机型号
    private String word;// 关键词
    private int version;// 通信协议版本
    private String osversion;// OS版本
    private String dpi;// 分辨率
    private int recordNum;// 每页记录数
    private int pageNum;// 页数
    private String cs; // 请求来源
    private String target; // 请求目标 本地数据 或 百度数据
    private String cls; // 请求类别，0表示所有的 分类

    private String appPackageName;

    private String ip;

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getOsversion() {
        return osversion;
    }

    public void setOsversion(String osversion) {
        this.osversion = osversion;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public int getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(int recordNum) {
        this.recordNum = recordNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAppPackageName() {
        return this.appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

}
