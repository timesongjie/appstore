package com.bbkmobile.iqoo.console.dao.apptype;

import java.io.File;

public class AppType2 extends AppType{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File[] icons;
	private String[] iconsFileName;
	private String[] iconsContentType;
	
	private String []typeIcons = new String[3];
	
	public AppType2(){
		
	}
	public AppType2(AppType appType){
		if(appType != null){
			this.setId(appType.getId());
			this.setAppCnNames(appType.getAppCnNames());
			this.setTypeName(appType.getTypeName());
			this.setParentId(appType.getParentId());
			this.setTypeEnName(appType.getTypeEnName());
			this.setTypeIcon(appType.getTypeIcon());
			this.setTypeOrder(appType.getTypeOrder());
			this.setTypeDesc(appType.getTypeDesc());
			this.setTypeStatus(appType.getTypeStatus());
			this.setTypeEnName(appType.getTypeEnName());
		}
	}
	public File[] getIcons() {
		return icons;
	}
	public void setIcons(File[] icons) {
		this.icons = icons;
	}
	public String[] getIconsFileName() {
		return iconsFileName;
	}
	public void setIconsFileName(String[] iconsFileName) {
		this.iconsFileName = iconsFileName;
	}
	public String[] getIconsContentType() {
		return iconsContentType;
	}
	public void setIconsContentType(String[] iconsContentType) {
		this.iconsContentType = iconsContentType;
	}
	public String[] getTypeIcons() {
		return typeIcons;
	}
	public void setTypeIcons(String[] typeIcons) {
		this.typeIcons = typeIcons;
	}
	/*public void setParentType(AppType parentType) {
		super.setParentType(parentType);
	}*/
	public AppType cloneParent(){
		AppType temp = new AppType();
		temp.setTypeName(getTypeName());
		temp.setParentId(getParentId());
		temp.setTypeEnName(getTypeEnName());
		temp.setTypeIcon(getTypeIcon());
		temp.setTypeOrder(getTypeOrder());
		temp.setTypeDesc(getTypeDesc());
		temp.setTypeStatus(getTypeStatus());
		temp.setTypeEnName(getTypeEnName());
		return temp;
	}
	
}
