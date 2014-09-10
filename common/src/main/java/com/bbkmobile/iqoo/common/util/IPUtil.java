/**
 * 
 */
package com.bbkmobile.iqoo.common.util;

/**
 * @author wangbo
 * @version 1.0.0.0/2011-8-22
 */
public class IPUtil {

    public static long IP2Long(String ip) {
        String[] seg = ip.split("\\.");
        return Long.parseLong(seg[0]) << 24 | Long.parseLong(seg[1]) << 16
                | Long.parseLong(seg[2]) << 8 | Long.parseLong(seg[3]);
    }

}
