package com.erp.common;

import java.time.LocalDate;

public class DateHandle {
    // 获得当前日期
    private static final LocalDate today = LocalDate.now();
    // 获取当年号 yyyy-mm-dd
    public static String getYear(Integer year){
        return today.plusYears(year).toString(); }
    // 获取当月日号
    public static String getDate(Integer day){
        return today.plusDays(day).toString();
    }
    // 获取当月月份
    public static String getMonth(Integer month){
        return today.plusMonths(month).toString();
    }

    public static String getTargetDate(String date, String format) {
        String targetDate = "";
        switch (format) {
            case "T":
                targetDate = date + "T23:59:59+08:00";
                break;
            case "Z":
                targetDate = date + "T14:30:45.123Z";
                break;
            case "D":
                targetDate = date + "14:30:45";
                break;
            case "SS":
                targetDate = date + "14:30:45 00:00:00";
        }
        return targetDate;
    }

}
