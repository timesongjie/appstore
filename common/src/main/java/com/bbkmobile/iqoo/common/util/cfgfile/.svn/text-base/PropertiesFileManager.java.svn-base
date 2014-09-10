/**
 * 
 */
package com.bbkmobile.iqoo.common.util.cfgfile;


/**
 * 
 * 属性文件管理器
 * 以单态的形式构造
 * 提供一个对properties文件唯一的全局访问点
 * 
 * @author wangbo
 */
public class PropertiesFileManager extends FileManagerAbstract{
	

	/**
	 * 唯一私有构造器
	 */
	private PropertiesFileManager(){
		
	}
	
	/**
	 * 
	 * @return PropertiesFileManager
	 */
	public static PropertiesFileManager getInstance(){
		return InnerCls.pfm;
	}
	
	/**
	 * 静态私有内部类，将PropertiesFileManager的初始化延迟到第一次使用时
	 * @author wangbo
	 */
	private static class  InnerCls{
		private static PropertiesFileManager pfm = new PropertiesFileManager();
	}
	

	/* (non-Javadoc)
	 * @see com.bbkmobile.iqoo.common.util.FileManagerAbstract#createCfgFile()
	 */
	@Override
	protected CfgFileInterface createCfgFile() {
		// TODO Auto-generated method stub
		return new CfgFileProperties();
	}
	
}
