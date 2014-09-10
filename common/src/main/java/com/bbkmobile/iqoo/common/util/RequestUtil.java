/**
 * 
 */
package com.bbkmobile.iqoo.common.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author wangbo
 * @version 1.0.0.0/2011-8-18
 */
public class RequestUtil {

    /**
     * 
     * @param req
     * @return 返回客户的真实IP地址
     */
    public static String getClientIP(HttpServletRequest req) {

        String ips = getClientIPs(req);
        return getFirstIPFromIPs(ips);
    }

    /**
     * 
     * 获取客户端的IP地址，若经过代理或负载均衡服务器处理 IP可能会多个，形式如：ip1,ip2,ip3 第一个非unknown串是真实的client
     * ip
     * 
     * @param req
     * @return String 形式如：ip1,ip2,ip3
     */
    public static String getClientIPs(HttpServletRequest req) {

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

    protected static String getFirstIPFromIPs(String ips) {
        String firstIP = null;

        Pattern ipPat = Pattern
                .compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})");
        Matcher ipMat = ipPat.matcher(ips);

        if (ipMat.find()) {
            firstIP = ipMat.group(1);
        } else {
            firstIP = ips;
        }

        return firstIP;
    }

    public static boolean isMobileNumber(String mobile) {
        Pattern pat = Pattern.compile("^1[3-9]\\d{9}$");
        Matcher mat = pat.matcher(mobile);
        if (mat.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmail(String email) {
        /*
         * ^ ：匹配输入的开始位置。 \：将下一个字符标记为特殊字符或字面值。 ：匹配前一个字符零次或几次。 + ：匹配前一个字符一次或多次。
         * (pattern) 与模式匹配并记住匹配。 x|y：匹配 x 或 y。 [a-z] ：表示某个范围内的字符。与指定区间内的任何字符匹配。
         * \w ：与任何单词字符匹配，包括下划线。 $ ：匹配输入的结尾。
         */
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern pat = Pattern.compile(check);
        Matcher mat = pat.matcher(email);
        if (mat.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> map : parameterMap.entrySet()) {
            String key = map.getKey();
            String[] values = map.getValue();
            if (ArrayUtils.isEmpty(values)) {
                params.put(key, StringUtils.EMPTY);
            } else {
                params.put(key, values[0]);
            }
        }
        return params;
    }
}
