/**
 * 
 */
package com.bbkmobile.iqoo.platform.auth;

import java.io.Serializable;

/**
 * @author wangbo
 * @version 1.0.0.0/2011-12-21
 */
public abstract class User implements Serializable {

    private static final long serialVersionUID = 4668955568442099536L;

    private String name;
    private String pwd;
    private String authCode;
    private Integer userId;

    /**
     * 获取验证码
     * 
     * @return
     */
    public String getAuthCode() {
        return authCode;
    }

    /**
     * 获取用户ID
     * 
     * @return
     */
    public Integer getUserID() {
        return userId;
    }

    /**
     * 获取何种第三方账号类型登陆 列入：sina\sohu\baidu账号等
     * 
     * @return
     */
    public abstract String getThirdPartyType();

    public abstract void setThirdPartyType(String type);

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
