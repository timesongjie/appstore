package com.bbkmobile.iqoo.common.util;

/**
 * 
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.bbkmobile.iqoo.common.logging.Lg;

/**
 * 日期处理方法集合
 * 
 * @author leez
 * 
 */
public class DateUtil {

    /**
     * 把时间字符串 yyyyMMddhhmmss 转换成　yyyy-MM-dd hh:mm:ss
     * 
     * @param date
     *            　　　yyyyMMddhhmmss　的字符串
     * @return　yyyy-MM-dd hh:mm:ss的字符串
     */
    public static String getFullDateWithSecond(String dateStr) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddhhmmss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = (Date) sdf1.parse(dateStr);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            //Lg.error(LgType.SYSTEM, "转换" + dateStr + "时出错！");
        }
        return null;
    }

    /**
     * 转成yyyy-MM-dd HH:mm:ss日期格式的字符串,
     * 
     * @param time
     * @return
     */
    public static String getFullDateWithTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
    
    /**
     * 
     * @Description:  把时间对像转换成 YYYYMMDD的字符串
     * @param date
     * @return  
     * @Author:leez
     * @see:
     * @since: 1.0
     * @Create Date:2014年7月31日
     */
    public static String getYYYYMMDD(Date date) {
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
        return format.format(date);
    }
    
    public static String getYYYYMMDD() {
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
    }
    
    public static String[] getCreateMTTableDate() {
        String[] add_table = new String[5];
        Calendar cal=Calendar.getInstance();
        for(int i=0; i< add_table.length; i++){
            cal.add(Calendar.DAY_OF_MONTH, 1);
            add_table[i] = getYYYYMMDD(cal.getTime());
        }
        return add_table;
    }
    
    /**
     * 把时间对像转换成 YYYYMM的字符串
     * @Description:  
     * @param date
     * @return  
     * @Author:leez
     * @see:
     * @since: 1.0
     * @Create Date:2014年7月31日
     */
    public static String getYYYYMM(Date date) {
        SimpleDateFormat format=new SimpleDateFormat("yyyyMM");
        return format.format(date);
    }
}
