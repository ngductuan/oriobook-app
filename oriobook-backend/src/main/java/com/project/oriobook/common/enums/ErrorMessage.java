package com.project.oriobook.common.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ErrorMessage {
    private static final Map<String, String> errorMessages = new HashMap<>();

    static {
        // COMMON
        errorMessages.put(ErrorCodeEnum.COMMON_ERROR_ENUM_TYPE.toString(), "Unknown enum type.");

        // AUTH
        errorMessages.put(ErrorCodeEnum.AUTH_EMAIL_EXISTED.toString(), "Email already exists.");
        errorMessages.put(ErrorCodeEnum.AUTH_EMAIL_NOT_EXISTED.toString(), "Email does not exist.");
        errorMessages.put(ErrorCodeEnum.AUTH_WRONG_CREDENTIALS.toString(), "Incorrect username or password.");

        // PRODUCT
        // ORDER
        // AUTHOR
        // CATEGORY
        errorMessages.put(ErrorCodeEnum.CATEGORY_NOT_FOUND.toString(), "Category not found");

        // REVIEW
    }

    public static String get(ErrorCodeEnum code) {
        return errorMessages.get(code.toString());
    }
}
