package com.project.oriobook.common.enums;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessage {
    private static final Map<String, String> errorMessages = new HashMap<>();

    static {
        // COMMON
        errorMessages.put(ErrorCodeEnum.COMMON_ERROR_ENUM_TYPE.toString(), "Unknown enum type");

        // AUTH
        errorMessages.put(ErrorCodeEnum.AUTH_EMAIL_EXISTED.toString(), "Email already exists");
        errorMessages.put(ErrorCodeEnum.AUTH_EMAIL_NOT_EXISTED.toString(), "Email does not exist");
        errorMessages.put(ErrorCodeEnum.AUTH_WRONG_CREDENTIALS.toString(), "Wrong email or password");
        errorMessages.put(ErrorCodeEnum.AUTH_UNAUTHORIZED.toString(), "Unauthorized");
        errorMessages.put(ErrorCodeEnum.AUTH_TOKEN_EXPIRED.toString(), "Token is expired");
        errorMessages.put(ErrorCodeEnum.AUTH_INVALID_TOKEN.toString(), "Invalid token");

        // USER
        errorMessages.put(ErrorCodeEnum.USER_NOT_FOUND.toString(), "User not found");

        // PRODUCT
        errorMessages.put(ErrorCodeEnum.PRODUCT_NOT_FOUND.toString(), "Product not found");
        // ORDER
        // AUTHOR
        errorMessages.put(ErrorCodeEnum.AUTHOR_NOT_FOUND.toString(), "Author not found");
        // CATEGORY
        errorMessages.put(ErrorCodeEnum.CATEGORY_NOT_FOUND.toString(), "Category not found");

        // REVIEW

        // UPLOAD FILE
        errorMessages.put(ErrorCodeEnum.UPLOAD_NOT_EMPTY.toString(), "File is not empty");
        errorMessages.put(ErrorCodeEnum.UPLOAD_ONLY_IMAGE.toString(), "Only upload image (png, jpg, jpeg)");
    }

    public static String get(ErrorCodeEnum code) {
        return errorMessages.get(code.toString());
    }
}
