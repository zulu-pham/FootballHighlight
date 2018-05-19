package com.ciuciu.footballhighlight.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date yesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static Date tomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static Date after(int numDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, numDate);
        return calendar.getTime();
    }

    public static Date after(Date date, int numDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, numDate);
        return calendar.getTime();
    }
}
