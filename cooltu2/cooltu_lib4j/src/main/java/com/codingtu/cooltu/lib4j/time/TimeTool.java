package com.codingtu.cooltu.lib4j.time;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTool {

    public static final String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YMD = "yyyy-MM-dd";

    private static Date nowDate() {
        return new Date();
    }

    /**************************************************
     *
     * format
     *
     **************************************************/
    public static String dateToStr(Date time) {
        return dateToStr(FORMAT_DEFAULT, time);
    }

    public static String dateToStr(String format, Date time) {
        if (time != null)
            return new SimpleDateFormat(format).format(time);
        return null;
    }

    /***************************************
     *
     * parse
     *
     ***************************************/

    public static Date strToDate(String dataStr) {
        return strToDate(FORMAT_DEFAULT, dataStr);
    }

    public static Date strToDate(String format, String dataStr) {
        try {
            return new SimpleDateFormat(format).parse(dataStr);
        } catch (Exception e) {
        }
        return null;
    }

    /***************************************
     *
     * now
     *
     ***************************************/
    public static String now() {
        return now(FORMAT_DEFAULT);
    }

    public static String now(String format) {
        return dateToStr(format, nowDate());
    }

    public static String nowYmd() {
        return ymd(nowDate());
    }

    /***************************************
     *
     * ymd
     *
     ***************************************/

    public static String ymd(Date time) {
        return dateToStr(FORMAT_YMD, time);
    }

    public static Date ymdDate(String ymd) {
        return strToDate(FORMAT_YMD, ymd);
    }

}
