package com.project.oriobook.common.utils;

import java.util.Collection;

public class ValidationUtil {
    public static <T> boolean isNullOrBlankStr(T str) {
        return str == null || str.toString().isBlank();
    }

    public static <T> boolean isNullOrEmptyList(Collection<T> list) {
        return list == null || list.isEmpty();
    }
}
