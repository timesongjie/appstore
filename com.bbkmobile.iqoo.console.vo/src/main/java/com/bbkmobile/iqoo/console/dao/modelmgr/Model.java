package com.bbkmobile.iqoo.console.dao.modelmgr;

import java.util.Date;

public class Model implements java.io.Serializable {

    private static final long serialVersionUID = -1535100742318988405L;

    private Short id;
    private String md_name; // 机型名字
    private String md_icon; // 机型图片
    private String md_sys; // 系统平台
    private Date release_date; // 发布时间
    private Integer show_order; // 显示顺序
    private String screen_size; // 屏幕大小
    private Date add_date; // 添加时期
    private Date modify_date; // 修改日期

    private Short series_id; // 系列id

    private Integer sdkVersion;
    private String drawable_dpi;
    private String platform;
    private String CPU_ABI;

    public Short getId() {
        return this.id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getMd_name() {
        return this.md_name;
    }

    public void setMd_name(String md_name) {
        this.md_name = md_name;
    }

    public String getMd_icon() {
        return this.md_icon;
    }

    public void setMd_icon(String md_icon) {
        this.md_icon = md_icon;
    }

    public String getMd_sys() {
        return this.md_sys;
    }

    public void setMd_sys(String md_sys) {
        this.md_sys = md_sys;
    }

    public Date getRelease_date() {
        return this.release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public Integer getShow_order() {
        return this.show_order;
    }

    public void setShow_order(Integer show_order) {
        this.show_order = show_order;
    }

    public String getScreen_size() {
        return this.screen_size;
    }

    public void setScreen_size(String screen_size) {
        this.screen_size = screen_size;
    }

    public Date getAdd_date() {
        return this.add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public Date getModify_date() {
        return this.modify_date;
    }

    public void setModify_date(Date modify_Date) {
        this.modify_date = modify_Date;
    }

    public Short getSeries_id() {
        return this.series_id;
    }

    public void setSeries_id(Short series_id) {
        this.series_id = series_id;
    }

    public String getDrawable_dpi() {
        return this.drawable_dpi;
    }

    public void setDrawable_dpi(String drawable_dpi) {
        this.drawable_dpi = drawable_dpi;
    }

    public String getCPU_ABI() {
        return this.CPU_ABI;
    }

    public void setCPU_ABI(String cPU_ABI) {
        CPU_ABI = cPU_ABI;
    }

    public Integer getSdkVersion() {
        return this.sdkVersion;
    }

    public void setSdkVersion(Integer sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

}
