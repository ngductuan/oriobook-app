package com.project.oriobook.common.utils;

import java.util.Collection;
import java.util.List;

public class ValidationUtil {
    public static <T> boolean diffNullOrBlankStr(T str) {
        return str != null && !str.toString().isBlank();
    }

    public static <T> boolean diffNullOrEmptyList(Collection<T> list) {
        return list != null && !list.isEmpty();
    }
}
