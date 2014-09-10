package com.bbkmobile.iqoo.interfaces.common;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JDBC SQL 占位符参数 :xxx 增强，占位符格式要求为字母和下弧线_
 * 
 * @author time
 * 
 */
public class SqlPlaceholderHandler {

    private static String _regexp = ":[a-zA-Z_]+";
    private static Pattern pattern = Pattern.compile(_regexp);// Thread safe

    public static Map<String, Integer> handleParams(String orginSQL) {
        Matcher matcher = pattern.matcher(orginSQL);
        int index = 0;
        Map<String, Integer> result = new HashMap<String, Integer>();
        while (matcher.find()) {
            result.put(matcher.group().substring(1), ++index);
        }
        return result;
    };

    public static String handlePlaces(String orginSQL) {
        return orginSQL.replaceAll(_regexp, "?");

    }
}
