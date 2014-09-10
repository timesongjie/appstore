/**
 * UtilTool.java
 * com.bbkmobile.iqoo.common.util
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-1-4 		dengkehai
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/

package com.bbkmobile.iqoo.console.constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import com.bbkmobile.iqoo.common.util.cfgfile.PropertiesFileManager;

/**
 * ClassName:UtilTool
 * Function: TODO ADD FUNCTION
 *
 * @author   dengkehai
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-1-4		上午11:31:29
 *
 */
public class UtilTool {

	public static boolean checkStringNull(String str) {
		if (null == str || str.trim().length() == 0) {
			return true;
		}

		return false;
	}

	public static byte[] intToByteArray(int value) {
		byte[] b = new byte[4];
		int j = 0;
		for (int i = 3; i >= 0; i--) {
			int offset = (b.length - 1 - j) * 8;
			b[i] = (byte) ((value >>> offset) & 0xFF);
			j++;
		}
		return b;
	}

	public static byte[] PadByte(byte[] source, byte[] des) {
		for (int j = 0; j < 32; j++) {
			if (j < des.length) {
				source[j] = des[j];
			} else
				source[j] = 0;
		}
		return source;
	}

	public static String getClientIP(HttpServletRequest req) {

		String ip = req.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}

		return ip;
	}

	public static long IP2Long(String ip) {
		String[] seg = ip.split("\\.");
		return Long.parseLong(seg[0]) << 24 | Long.parseLong(seg[1]) << 16
				| Long.parseLong(seg[2]) << 8 | Long.parseLong(seg[3]);
	}

	/**
	 * 
	 * @param from
	 * @param to
	 * @throws IOException
	 */
	public static void copyFile(File from, String to) throws IOException {

		FileInputStream srcIn = null;
		FileOutputStream destOut = null;

		try {
			srcIn = new FileInputStream(from);
			destOut = new FileOutputStream(to);

			byte[] buff = new byte[1024];
			int len;
			while (Constants.FILE_EOF != (len = srcIn.read(buff))) {
				destOut.write(buff, 0, len);
			}

		} catch (IOException e) {
			throw e;
		} finally {
			try {
				srcIn.close();
				destOut.close();
			} catch (IOException e) {
			}
		}
	}

	public static String changeFileName(String fileName) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssS");
		int index=fileName.lastIndexOf(".");
		return dateFormat.format(Calendar.getInstance().getTime()) + fileName.substring(index);
	}
	
	//定义广告图片的名称
	public static String changeAdIconName(Long ad_id,String fileName, String screen) {

		String str=Constants.PH_ICON+"_"+Constants.ADVERTISEMENT_ICON+"_"+ad_id.toString()+"_"+screen;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssS");
		int index=fileName.lastIndexOf(".");
		return str+"_"+dateFormat.format(Calendar.getInstance().getTime())+fileName.substring(index);
		//dateFormat.format(Calendar.getInstance().getTime()) + 
	}
	
	//定义图片的名称
	public static String changeIconName(String typeName, Long id,String fileName, String screenId) {

		//1.typeName识别是哪种类别，如广告：adimg,专题topicimg ; 2. ad_id,screen通过id和分辨率ID找到相应的图片
		
		String str=typeName+"_"+id.toString()+"_"+screenId;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssS");
		int index=fileName.lastIndexOf(".");
		return str+"_"+dateFormat.format(Calendar.getInstance().getTime())+fileName.substring(index);
		//dateFormat.format(Calendar.getInstance().getTime()) + 
	}
	
	//定义图片的名称
	public static String changeModelIconName(String typeName, Short id,String fileName) {

		//1.typeName识别是哪种类别，如广告：adimg,专题topicimg ; 2. ad_id,screen通过id和分辨率ID找到相应的图片
		
		String str=typeName+"_"+id.toString();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssS");
		int index=fileName.lastIndexOf(".");
		return str+"_"+dateFormat.format(Calendar.getInstance().getTime())+fileName.substring(index);
		//dateFormat.format(Calendar.getInstance().getTime()) + 
	}

	public static String getHttpURL(String uri) {
		return PropertiesFileManager.getInstance()
				.getProperty(Constants.APP_MAIN_URL).trim()
				+ uri.trim();
	}
	
	public static String getDownloadHttpURL(String uri) throws Exception{
		if(null!=uri){
			return PropertiesFileManager.getInstance()
			.getProperty(Constants.DOWNLOAD_MAIN_URL).trim()
			+ uri.trim();
		}else{
			return null;
		}
		
    }
	
	public static String getDownloadImageHttpURL(String uri) throws Exception{
        if(null!=uri){
            return PropertiesFileManager.getInstance()
            .getProperty(Constants.DOWNLOAD_IMAGE_URL).trim()
            + uri.trim();
        }else{
            return null;
        }
        
    }
	
	public static String getDownloadApkHttpURL(String uri) throws Exception{
        if(null!=uri){
            return PropertiesFileManager.getInstance()
            .getProperty(Constants.DOWNLOAD_APK_URL).trim()
            + uri.trim();
        }else{
            return null;
        }
        
    }

    public static String getDownloadPath(String uri) throws Exception {
        return PropertiesFileManager.getInstance().getProperty(Constants.PRO_WEBAPP_WEBROOT).trim() + uri.trim();
    }
    
    public static boolean isNumberStr(String numberStr){ //判断是否时int型字符串
	    try {
	        Integer.parseInt(numberStr);
	    }catch(Exception e) {
	        return false;
	    }
	    return true;
    }
    
    /**
     * 把数字转成IP
     * @Description:  
     * @param add
     * @return  
     * @Author:leez
     * @see:
     * @since: 1.0
     * @Create Date:2014年8月14日
     */
    public static String inet_ntoa(long add) {  
        return ((add & 0xff000000) >> 24) + "." + ((add & 0xff0000) >> 16)  
                + "." + ((add & 0xff00) >> 8) + "." + ((add & 0xff));  
    }  
      
    public static long inet_aton(Inet4Address add) {  
        byte[] bytes = add.getAddress();  
        long result = 0;  
        for (byte b : bytes) {  
            if ((b & 0x80L) != 0) {  
                result += 256L + b;  
            } else {  
                result += b;  
            }  
            result <<= 8;  
        }  
        result >>= 8;  
        return result;  
    }  
      
    /** 
     * 把ip地址转换成 数字
     */  
    public static long inet_aton(String ipAdress) {  
        long result = 0;  
        // number between a dot  
        long section = 0;  
        // which digit in a number  
        int times = 1;  
        // which section  
        int dots = 0;  
        for (int i = ipAdress.length() - 1; i >= 0; --i) {  
            if (ipAdress.charAt(i) == '.') {  
                times = 1;  
                section <<= dots * 8;  
                result += section;  
                section = 0;  
                ++dots;  
            } else {  
                section += (ipAdress.charAt(i) - '0') * times;  
                times *= 10;  
            }  
        }  
        section <<= dots * 8;  
        result += section;  
        return result;  
    }
    
    public static void main(String args []) {
        System.out.println(UtilTool.inet_aton("127.0.0.1"));
        //-- 2130706433
        System.out.println(UtilTool.inet_ntoa(2130706433));
    }
}
