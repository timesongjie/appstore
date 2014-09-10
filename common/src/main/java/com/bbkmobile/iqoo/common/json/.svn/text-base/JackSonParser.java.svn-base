package com.bbkmobile.iqoo.common.json;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;

public class JackSonParser {

	private static ObjectMapper objectMapper =  new ObjectMapper();
	static{
		objectMapper.getSerializationConfig().disable(Feature.WRITE_NULL_PROPERTIES);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		objectMapper.getSerializationConfig().setDateFormat(df );
	}
	public static <T>String bean2Json(T t){
		try {
			return objectMapper.writeValueAsString(t);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
//	public static void main(String[] args) {
//		User user = new User();
//		
//		List<User> users = new 
//				ArrayList<User>();
//		users.add(user);
//		System.out.println(bean2Json(user));
//		System.out.println(bean2Json(users));
//		
//		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("username", "jetty");
//		map.put("password", "name");
//		list.add(map);
//		System.out.println(bean2Json(list));
//	}
}
