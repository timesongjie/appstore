package com.bbkmobile.iqoo.console.dao.login;

/**
 * 
 */

public class BbkAdminUser implements java.io.Serializable {

    // Fields

    /**
	 * 
	 */
    private static final long serialVersionUID = 7108665930738699131L;
    private Integer admUserId;
    private String admUserName;
    private String admUserPass;
    private String admUserPower;
    private String admUserTrueName;
    private String admUserSafe;
    private String admUserDep;

    // Constructors

    /** default constructor */
    public BbkAdminUser() {
    }

    /** minimal constructor */
    public BbkAdminUser(Integer admUserId, String admUserPass) {
        this.admUserId = admUserId;
        this.admUserPass = admUserPass;
    }

    /** full constructor */
    public BbkAdminUser(Integer admUserId, String admUserName,
            String admUserPass, String admUserPower, String admUserTrueName,
            String admUserSafe, String admUserDep) {
        this.admUserId = admUserId;
        this.admUserName = admUserName;
        this.admUserPass = admUserPass;
        this.admUserPower = admUserPower;
        this.admUserTrueName = admUserTrueName;
        this.admUserSafe = admUserSafe;
        this.admUserDep = admUserDep;
    }

    // Property accessors

    public Integer getAdmUserId() {
        return this.admUserId;
    }

    public void setAdmUserId(Integer admUserId) {
        this.admUserId = admUserId;
    }

    public String getAdmUserName() {
        return this.admUserName;
    }

    public void setAdmUserName(String admUserName) {
        this.admUserName = admUserName;
    }

    public String getAdmUserPass() {
        return this.admUserPass;
    }

    public void setAdmUserPass(String admUserPass) {
        this.admUserPass = admUserPass;
    }

    public String getAdmUserPower() {
        return this.admUserPower;
    }

    public void setAdmUserPower(String admUserPower) {
        this.admUserPower = admUserPower;
    }

    public String getAdmUserTrueName() {
        return this.admUserTrueName;
    }

    public void setAdmUserTrueName(String admUserTrueName) {
        this.admUserTrueName = admUserTrueName;
    }

    public String getAdmUserSafe() {
        return this.admUserSafe;
    }

    public void setAdmUserSafe(String admUserSafe) {
        this.admUserSafe = admUserSafe;
    }

    public String getAdmUserDep() {
        return this.admUserDep;
    }

    public void setAdmUserDep(String admUserDep) {
        this.admUserDep = admUserDep;
    }

}