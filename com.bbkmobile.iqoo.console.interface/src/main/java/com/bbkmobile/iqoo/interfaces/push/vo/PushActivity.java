package com.bbkmobile.iqoo.interfaces.push.vo;

import com.bbkmobile.iqoo.console.constants.UtilTool;

/**
 * push活动
 * 
 * @author time
 * 
 */
public class PushActivity {

    private String pid;
    private String title;
    private String content;
    private String icon;
    private Character relationType;// 应用详情、活动详情、专题详情对应的类别分别为0，1，2
    private String relationId;
    private String packageName;
    private String versionCode;
    private Integer id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        try {
            return UtilTool.getDownloadImageHttpURL(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Character getRelationType() {
        return relationType;
    }

    public void setRelationType(Character relationType) {
        this.relationType = relationType;
    }
}
