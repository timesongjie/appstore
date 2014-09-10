package com.bbkmobile.iqoo.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    /**
     * 手机号码验证
     * 
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean sql_inj(String str) {
        String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,|sleep|alter";
        // 这里的东西还可以自己添加
        String[] inj_stra = inj_str.split("\\|");
        for (int i = 0; i < inj_stra.length; i++) {
            if (str.indexOf(inj_stra[i]) >= 0) {
                return true;
            }
        }
        return false;
    }
}
