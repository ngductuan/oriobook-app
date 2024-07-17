package com.project.oriobook.common.enums;

public enum ErrorCodeEnum {
    // COMMON
    COMMON_ERROR_ENUM_TYPE("COM_0010"),
    // AUTH
    AUTH_EMAIL_EXISTED("AUTH_0010"),
    AUTH_EMAIL_NOT_EXISTED("AUTH_0011"),
    AUTH_UNAUTHORIZED("AUTH_0012"),
    AUTH_WRONG_CREDENTIALS("AUTH_0013"),
    AUTH_TOKEN_EXPIRED("AUTH_0014"),
    AUTH_INVALID_TOKEN("AUTH_0015"),
    AUTH_TOKEN_EMPTY("AUTH_0016"),
    AUTH_FORBIDDEN("AUTH_0017"),
    AUTH_CANNOT_CREATE_TOKEN("AUTH_0018"),

    // USER
    USER_NOT_FOUND("USR_0010"),

    // PRODUCT
    PRODUCT_NOT_FOUND("PRO_0010"),
    // ORDER
    // AUTHOR
    AUTHOR_NOT_FOUND("AUT_0010"),
    // CATEGORY
    CATEGORY_NOT_FOUND("CAT_0010"),
    // REVIEW
    // UPLOAD FILE
    UPLOAD_NOT_EMPTY("UPL_0011"),
    UPLOAD_ONLY_IMAGE("UPL_0010"),

    // CART
    CART_ITEM_NOT_FOUND("CRT_0010"),
    CART_INVALID_ACTION("CRT_0011"),


    // DEFAULT
    DEFAULT_SEMICOLON("DEF_0000");
    private final String value;

    ErrorCodeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
