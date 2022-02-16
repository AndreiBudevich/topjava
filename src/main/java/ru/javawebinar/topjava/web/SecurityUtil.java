package ru.javawebinar.topjava.web;

public class SecurityUtil {
    private static Integer userId;

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static void setAuthUserId(int userId) {
        SecurityUtil.userId = userId;
    }

    public static Integer authUserId() {
        return userId;
    }
}