package com.social.server.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatterUtil {
    private final static DateTimeFormatter withoutTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final static DateTimeFormatter withTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private final static DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String withoutTimeFormat(LocalDateTime date) {
        return format(date, withoutTimeFormat);
    }

    public static String withTimeFormat(LocalDateTime date) {
        return format(date, withTimeFormat);
    }

    public static String inputFormat(LocalDateTime date) {
        return format(date, inputFormat);
    }

    private static String format(LocalDateTime dateTime, DateTimeFormatter formatter) {
        return dateTime != null ? dateTime.format(formatter) : null;
    }

}
