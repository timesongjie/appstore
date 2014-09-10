package com.bbkmobile.iqoo.console.dao.sysmanage;

import java.util.ArrayList;
import java.util.List;

import com.bbkmobile.iqoo.common.util.MD5;

public class SystemAdminVO {

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
     * @return the trueName
     */
    public String getTrueName() {
        return trueName;
    }

    /**
     * @param trueName
     *            the trueName to set
     */
    public void setTrueName(String trueName) {
        this.trueName = trueName;
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

    /**
     * @return the priLs
     */
    public List<PrivilegeVO> getPriLs() {
        return priLs;
    }

    /**
     * @param priLs
     *            the priLs to set
     */
    public void setPriLs(List<PrivilegeVO> priLs) {
        this.priLs = priLs;
    }

    /**
     * @return the hasPri
     */
    public List<Integer> getHasPri() {
        // priLs.add(new PrivilegeVO(1, "权限a"));
        // priLs.add(new PrivilegeVO(2, "权限b"));
        // priLs.add(new PrivilegeVO(3, "权限c"));
        // priLs.add(new PrivilegeVO(4, "权限d"));
        return hasPri;
    }

    /**
     * @param hasPri
     *            the hasPri to set
     */
    public void setHasPri(List<Integer> hasPri) {
        this.hasPri = hasPri;
    }

    public List<PrivilegeVO> getAllPri() {
        return allPri;
    }

    public void setAllPri(List<PrivilegeVO> allPri) {
        this.allPri = allPri;
    }

    public List<String> getSelectPri() {
        return selectPri;
    }

    public void setSelectPri(List<String> selectPri) {
        this.selectPri = selectPri;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    private Integer id;
    private String name;
    private String pwd;
    private String trueName;
    private String dept;

    private List<PrivilegeVO> priLs = new ArrayList<PrivilegeVO>(); // 管理员拥有的权限对象的集合
    private List<Integer> hasPri = new ArrayList<Integer>(); // 管理员拥有的权限ID的集合
    private List<PrivilegeVO> allPri = new ArrayList<PrivilegeVO>(); // 所有权限对象集合
    private List<String> selectPri = new ArrayList<String>(); // 管理员拥有的权限名称的集合
}
