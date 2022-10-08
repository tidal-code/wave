package com.tidal.wave.date;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("all")
public class FormattedDate {

    public static String getLocalDate(String pattern) {
        return getLocalDate(pattern, "Pacific/Auckland");
    }

    public static String getLocalDate(String pattern, String timeZone) {
        ZoneId zoneId = ZoneId.of(timeZone);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime nzZoneTime = LocalDateTime.now(zoneId);
        return nzZoneTime.format(dateFormatter);
    }

    public String getXSDFormattedDate(LocalDateTime date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return date.format(dateFormatter);
    }
}
