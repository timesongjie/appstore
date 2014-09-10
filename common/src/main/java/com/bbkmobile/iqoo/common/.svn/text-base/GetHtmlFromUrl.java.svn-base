package com.bbkmobile.iqoo.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetHtmlFromUrl {
	 public static String getHtmlStr(String strUrl,String searchStr) {
	       try {
	           URL url = new URL(strUrl);
	           BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
               String s = "";
	           StringBuffer sb = new StringBuffer("");
	           while ((s = br.readLine()) != null) {
	        	   if(s.indexOf(searchStr)>-1){
	        		   sb.append(s+"\r\n");
	        		   }
	           }
	           br.close();
	           return sb.toString();
	       } catch (Exception e) {
	           return null;
	       } 
	    }

	    public static List<String> getCompileStr(String str,String str1,String str2){
	    	List<String> matchStr=new ArrayList<String>();
	    	String [] strs = str.split("\r\n");
	    	String compileStr = "("+str1+")([^<]+?)("+str2+")";
	    	Pattern pattern = Pattern.compile(compileStr);
	    	for(String s : strs){
		        Matcher matcher = pattern.matcher(s);        
		        while (matcher.find()) {
		        	String sMatch=matcher.group(2);
		        	matchStr.add(sMatch);
		        }		        
	    	}
	        return matchStr;
	    }
}
