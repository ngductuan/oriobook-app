package com.project.oriobook.common.utils;

public class ValidationUtil {
    public static <T> boolean isNullOrBlank(T str) {
        return str != null && !str.toString().isBlank();
    }
}
