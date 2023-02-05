package com.example.tally.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @PackageName: com.example.tally.utils
 * @ClassName: DateUtils
 * @Author: winwa
 * @Date: 2023/2/3 8:24
 * @Description:
 **/
public class DateUtils {

    public static String getDateString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isToday(int year, int month, int day) {
        Date date = new Date();
        if (year == getYear(date) && month == getMonth(date) && day == getDay(date)) {
            return true;
        }

        return false;
    }
}
