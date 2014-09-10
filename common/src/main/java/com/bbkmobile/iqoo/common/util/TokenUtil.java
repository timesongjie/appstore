package com.bbkmobile.iqoo.common.util;

public class TokenUtil {
    /**
     * 获取下载地址的token
     * 
     * @param app_id
     * @return
     */
    public static String getDownloadApkToken(String app_id, String key) {
        String token = MD5.encode32(key + app_id);
        return token;
    }
}
