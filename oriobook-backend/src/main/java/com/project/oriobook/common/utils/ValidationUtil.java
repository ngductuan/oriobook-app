package com.project.oriobook.common.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Collection;

public class ValidationUtil {
    public static <T> boolean isNullOrBlankString(T str) {
        return str == null || str.toString().isBlank();
    }

    public static <T> boolean isNullOrEmptyList(Collection<T> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNullJsonNode(JsonNode node) {
        return node == null || node.isNull();
    }
}
