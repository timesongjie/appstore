package com.bbkmobile.iqoo.console.dao.apptype;

import java.io.File;
import java.util.Date;

/**
 * PersonalType 个性化类型
 * 
 * @author time
 * 
 */
public class PersonalType {

    private Integer id;
    private String name;
    private String icon;
    private Character tag;
    private Date addTime;
    private Character status;// 0不启用 1启用
    private Integer order;

    private File picture;
    private String pictureFileName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Character getTag() {
        return tag;
    }

    public void setTag(Character tag) {
        this.tag = tag;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public File getPicture() {
        return picture;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    public String getPictureFileName() {
        return pictureFileName;
    }

    public void setPictureFileName(String pictureFileName) {
        this.pictureFileName = pictureFileName;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

}
