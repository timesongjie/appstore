/**
 * 
 */
package com.bbkmobile.iqoo.common.util;

/**
 * @author wangbo
 * @version 1.0.0.0/2011-9-10
 */
public class VersionToInteger {

	public static Integer toInt(String version){
		//1.5.5
		Integer ver = 0;
		String[] verSeg = version.trim().split("\\.");		
		for(int i=0; i<3; i++){
			ver = ver*1000 + Integer.valueOf(verSeg[i]);
		}
		return ver;
	}
}
