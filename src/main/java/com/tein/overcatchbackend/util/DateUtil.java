package com.tein.overcatchbackend.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_FORMAT2 = "yyyyMMddHHmmss";

    public static LocalDateTime toDateTime(String stringDate, String pattern) {
        return LocalDateTime.parse(stringDate, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate toDate(String stringDate, String pattern) {
        return LocalDate.parse(stringDate, DateTimeFormatter.ofPattern(pattern));
    }

    public static String toString(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String toString(LocalDateTime date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String toString(LocalDateTime date) {
        return toString(date, DEFAULT_FORMAT2);
    }

    public static String toString2(LocalDateTime date) {
        return toString(date, DEFAULT_FORMAT);
    }

    public static LocalDateTime toDateTime(String date) {
        return toDateTime(date, DEFAULT_FORMAT);
    }
}
