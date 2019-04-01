package com.wujia.lib_common.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
public class DateUtil {


    public static String formatMsgDate(String m) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM.dd HH:mm", Locale.ENGLISH);
        Date date = new Date(Long.parseLong(m));
        String curDate = formatter.format(date);
        return curDate;
    }

    public static String formathhMMdd(long m) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:MM:dd");
        Date date = new Date(m);
        String curDate = formatter.format(date);
        return curDate;
    }

    //获取系统当前日期时间
    public static String getCurrentyyyymmddhh() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH");
        Date date = new Date(System.currentTimeMillis());
        String curDate = formatter.format(date);
        return curDate;
    }

    //获取系统当前日期时间
    public static String getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String curDate = formatter.format(date);
        return curDate;
    }

    //获取系统当前日期
    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        String curDate = formatter.format(date);
        return curDate;
    }

    //获取系统当前日期(英文格式)
    public static String getCurrentDateEnglish() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
        Date date = new Date(System.currentTimeMillis());
        String curDate = formatter.format(date);
        return curDate;
    }

    //获取系统当前时间
    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String curDate = formatter.format(date);
        return curDate;
    }


    //获取系统当前时间
    public static String getCurrentTimeHHMM() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String curDate = formatter.format(date);
        return curDate;
    }


    //获取系统当前是星期几
    public static String getCurrentWeekDay() {
        String week = "";
        Calendar c1 = Calendar.getInstance();
        int day = c1.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }

    //获取系统当前是星期几
    public static String getCurrentWeekDayEn() {
        String week = "";
        Calendar c1 = Calendar.getInstance();
        int day = c1.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 1:
                week = "Sunday";
                break;
            case 2:
                week = "Monday";
                break;
            case 3:
                week = "Tuesdays";
                break;
            case 4:
                week = "Wednesday";
                break;
            case 5:
                week = "Thursday";
                break;
            case 6:
                week = "Fridays";
                break;
            case 7:
                week = "Saturday";
                break;
        }
        return week;
    }

}
