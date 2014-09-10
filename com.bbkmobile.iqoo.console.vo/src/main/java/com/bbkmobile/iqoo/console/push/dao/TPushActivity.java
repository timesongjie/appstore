package com.bbkmobile.iqoo.console.push.dao;

import java.io.File;
import java.util.Date;
/**
 * 
 * @author time
 *
 */
public class TPushActivity {

	public static final char RELATION_APP = '0';
	public static final char RELATION_ACT = '1';
	public static final char RELATION_TOPIC = '2';
	
	private Integer id;
	private String pid;
	private String title;
	private String content;
	private String icon;
	private String relationId;
	private Character relationType;//应用详情、活动详情、专题详情对应的类别分别为0，1，2
	private Date createtime;
	private Date stime;
	private Date etime;
	private Character status;//0 默认 不启用 1 启用
	private Integer pushType;//0 全量 1 IP 2 IMEI
	
	private File picture;
	private String pictureFileName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
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
		return icon;
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
	public Character getRelationType() {
		return relationType;
	}
	public void setRelationType(Character relationType) {
		this.relationType = relationType;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getStime() {
		return stime;
	}
	public void setStime(Date stime) {
		this.stime = stime;
	}
	public Date getEtime() {
		return etime;
	}
	public void setEtime(Date etime) {
		this.etime = etime;
	}
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
	public Integer getPushType() {
		return pushType;
	}
	public void setPushType(Integer pushType) {
		this.pushType = pushType;
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
}
