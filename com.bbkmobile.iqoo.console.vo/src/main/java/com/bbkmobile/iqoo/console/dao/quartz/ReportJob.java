package com.bbkmobile.iqoo.console.dao.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bbkmobile.iqoo.console.dao.appinfo.AppInfo;

public class ReportJob {

    private Long id; // 定时任务的ID
    private AppInfo appInfo = new AppInfo(); // 定时任务的操作对象（应用对象）
    private Short app_status; // 要将对应的应用改成appStatus状态（如：“已上架”，“已下架”）
    private Date task_time; // 定时任务执行时间
    private String cron_expression; // 定时任务执行时间（quartz定时表达式）
    private char enabled; // 定时任务的状态（0：定时任务已经加入触发器，并已启动 1：添加定时任务到数据库
                          // 2：定时任务已经执行成功 3：已经取消了的定时任务）

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public Short getApp_status() {
        return app_status;
    }

    public void setApp_status(Short appStatus) {
        app_status = appStatus;
    }

    public Date getTask_time() {
        return task_time;
    }

    public void setTask_time(Date taskTime) {
        // SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.task_time = taskTime;
    }

    public String getCron_expression() {
        return cron_expression;
    }

    public void setCron_expression(String cronExpression) {
        cron_expression = cronExpression;
    }

    public void setEnabled(char enabled) {
        this.enabled = enabled;
    }

    public char getEnabled() {
        return enabled;
    }
}
