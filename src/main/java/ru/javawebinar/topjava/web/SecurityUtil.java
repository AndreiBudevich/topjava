package ru.javawebinar.topjava.web;

public class SecurityUtil {
    private static Integer userId;

    public static void setAuthUserId(int userId) {
        SecurityUtil.userId = userId;
    }

    public static Integer authUserId() {
        return userId;
    }
}