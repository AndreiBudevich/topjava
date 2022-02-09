package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DatesUtil {
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    private DatesUtil() {
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(FORMATTER);
    }
}