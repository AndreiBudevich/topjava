package ru.javawebinar.topjava.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    public static <T> boolean isBetweenHalfOpen(T current, T start, T end) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = DateTimeUtil.getMethod(current.getClass());
        if (method != null) {
            return (int) method.invoke(current, start) >= 0 && (int) method.invoke(current, end) <= 0;
        }
        return false;
    }

    private static <T> Method getMethod(Class<T> clazz) throws NoSuchMethodException {
        Method method = null;
        for (Method m : clazz.getMethods()) {
            if (m.getName().equals("compareTo")) {
                method = m;
                return clazz.getMethod("compareTo", method.getParameterTypes()[0]);
            }
        }
        return null;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

