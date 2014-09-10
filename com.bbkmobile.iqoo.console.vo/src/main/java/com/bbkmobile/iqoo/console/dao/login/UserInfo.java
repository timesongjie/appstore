/**
 * 
 */
package com.bbkmobile.iqoo.console.dao.login;

import com.bbkmobile.iqoo.common.util.MD5;

/**
 * @author wangbo
 * @version 1.0.0.0/2011-7-16
 */
public class UserInfo { // 管理员用户信息的JavaBean,对应于数据库的bbk_adminuser表

    private String name;
    private String pwd;
    private Integer userid;
    private String power;
    private String dept;

    private Short operation_id;
    private String operation_target;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the pwd
     */
    public String getPwd() {
        return MD5.encode32(pwd);
    }

    /**
     * @param pwd
     *            the pwd to set
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return the userid
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * @param userid
     *            the userid to set
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * @return the power
     */
    public String getPower() {
        return power;
    }

    /**
     * @param power
     *            the power to set
     */
    public void setPower(String power) {
        this.power = power;
    }

    /**
     * @return the dept
     */
    public String getDept() {
        return dept;
    }

    /**
     * @param dept
     *            the dept to set
     */
    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getOperation_target() {
        return this.operation_target;
    }

    public void setOperation_target(String operation_target) {
        this.operation_target = operation_target;
    }

    public Short getOperation_id() {
        return this.operation_id;
    }

    public void setOperation_id(Short operation_id) {
        this.operation_id = operation_id;
    }

}
