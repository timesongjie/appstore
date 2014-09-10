package com.bbkmobile.iqoo.common.util;

import java.util.Calendar;
import java.util.Date;


public class WeekdayUtil {
    private static final String TAG = "WeekdayUtil";

    public static int getWeekday() {

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekday = cal.get(Calendar.DAY_OF_WEEK);
        return weekday-1;
    }
}
