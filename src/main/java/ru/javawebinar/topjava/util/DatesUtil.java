package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DatesUtil {
    private DatesUtil() {
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
        return localDateTime.format(formatter);
    }
}