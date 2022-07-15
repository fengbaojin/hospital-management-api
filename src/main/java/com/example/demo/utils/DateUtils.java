package com.example.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat();
    public static final String defaultDatePattern = "yyyy-MM-dd";
    public static final String defaultTimePattern = "HH:mm:ss";
    public static final String defaultDateTimePattern = "yyyy-MM-dd HH:mm:ss";
    public static final String defaultDateTimeCodePattern = "yyyyMMddHHmmssSSSS";

    public DateUtils() {
    }

    public static String getDate(String pattern) {
        dateFormat.applyPattern(pattern);
        dateFormat.setTimeZone(TimeZone.getTimeZone(java.time.ZoneId.systemDefault()));
        return dateFormat.format(new Date());
    }

    public static String getDate() {
        return getDate(new Date());
    }

    public static String getTime() {
        return getTime(new Date());
    }

    public static String getDateTime() {
        return getDateTime(new Date());
    }

    public static String getDateTime(boolean isDetails) {
        return isDetails?getDate(new Date(), "yyyy-MM-dd hh:mm:ss:SSS"):getDateTime(new Date());
    }

    public static String getDate(Date date) {
        return getDate(date, "yyyy-MM-dd");
    }

    public static String getTime(Date date) {
        return getDate(date, "HH:mm:ss");
    }

    public static String getDateTime(Date date) {
        return getDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getDate(Date date, String pattern) {
        dateFormat.applyPattern(pattern);
        dateFormat.setTimeZone(TimeZone.getTimeZone(java.time.ZoneId.systemDefault()));
        return dateFormat.format(date);
    }

    public static Date parse(String dateStr, String pattern) {
        if(BeanUtils.isEmpty(dateStr)) {
            return null;
        } else {
            dateFormat.applyPattern(pattern);
            Date date = null;

            try {
                date = dateFormat.parse(dateStr);
            } catch (ParseException var4) {
            }

            return date;
        }
    }

    public static LocalDate convertToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

    public static Date convertToDate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static LocalDateTime convertToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

    public static Date convertToDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }
}
