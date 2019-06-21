package com.social.server.util;

import java.time.format.DateTimeFormatter;

public class DateFormatterUtil {
    public final static DateTimeFormatter viewFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public final static DateTimeFormatter viewMessageFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public final static DateTimeFormatter viewInputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

}
