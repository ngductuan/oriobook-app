package com.project.oriobook.common.enums;

public enum ErrorCodeEnum {
    // COMMON
    COMMON_ERROR_ENUM_TYPE("COM_0010"),
    // AUTH
    AUTH_EMAIL_EXISTED("ATH_0010"),
    AUTH_EMAIL_NOT_EXISTED("ATH_0011"),
    AUTH_WRONG_CREDENTIALS("ATH_0012");

    // PRODUCT
    // ORDER
    // AUTHOR
    // CATEGORY
    // REVIEW

    private final String value;

    ErrorCodeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
