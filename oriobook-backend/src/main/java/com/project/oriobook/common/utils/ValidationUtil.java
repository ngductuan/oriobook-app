package com.project.oriobook.common.utils;


public class ValidationUtil {
    public static <T> boolean diffNullOrBlank(T str) {
        return str != null && !str.toString().isBlank();
    }
}
