package com.bbkmobile.iqoo.common.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;


public class JsonParser {

	public static <T> T readJSON2Bean(Object json,Class<T> clazz){
		return (T) JSONObject.toBean(JSONObject.fromObject(json),clazz);
	}
	public static <T> T[] readJSON2Array(Object json,Class<T> clazz){
		JSONArray array = JSONArray.fromObject(json);
		return (T[]) JSONArray.toArray(array, clazz);
	}
	
	public static <T> List<T> readJSON2List(Object json,Class<T> clazz){
		JSONArray array = JSONArray.fromObject(json);
		return JSONArray.toList(array,clazz);
	}
	
	public static Object readJSON2Bean(Object json){
		return  JSONObject.toBean(JSONObject.fromObject(json));
	}
	public static Object[] readJSON2Array(Object json){
		JSONArray array = JSONArray.fromObject(json);
		return (Object[]) JSONArray.toArray(array);
	}
	
	public static List<Object > readJSON2List(Object json){
		JSONArray array = JSONArray.fromObject(json);
		return JSONArray.toList(array);
	}
	
	public static String object2Json(Object obj){
		return JSONObject.fromObject(obj).toString();
	}
	
	public static String array2Json(Object obj){
		return JSONArray.fromObject(obj).toString();
	}
	public static Object getValue(Object obj,String key) throws Exception{
		if(obj instanceof JSONObject){
			return ((JSONObject)obj).get(key);
		}
		return PropertyUtils.getProperty(obj, key);
	}
//	public static void main(String[] args) throws Exception {
//		
//		User user = new User();
//		user.setAge(1);
//		user.setName("name");
//		
//		List<User> users = new ArrayList<User>();
//		users.add(user);
//		
//		Map<String,User> users2 = new HashMap<String, User>();
//		users2.put("first", user);
//		
//		System.out.println(object2Json(user));
//		System.out.println(object2Json(users2));
//		System.out.println(array2Json(users));
//		
//		
//		//JSON - OBJECT 数组格式 需要jsonArray 转 获取
//		String paramStr = "[{\"package_name\":\"com.tencent.international.mtt\"},{\"package_name\":\"com.tencent.game.rhythmmaster\"}]";
//		Object[] obj = readJSON2Array(paramStr);
//		System.out.println(getValue(obj[0],"package_name"));
//		
//		paramStr = "[{\"age\":1,\"name\":\"name\"}]";
//		System.out.println(readJSON2Array(paramStr, User.class)[0].getAge());
//		
//		
//		//OBJECT - JSON
//		//MAP,OBJECT(JSONOBJECT) LIST(JSONARRAY)  
//		
//	}
}
