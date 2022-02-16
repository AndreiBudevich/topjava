package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public static final LocalDate START_LOKAL_DATE_DEDEFAULT = LocalDate.of(-999999999, 1, 1);
    public static final LocalDate END_LOKAL_DATE_DEDEFAULT = LocalDate.of(999999999, 12, 31);
    public static final LocalTime START_TIME = LocalTime.of(0, 0);
    public static final LocalTime END_TIME = LocalTime.of(0, 0);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<? super T>> boolean isBetweenHalfOpen(T lt, T startTime, T endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

