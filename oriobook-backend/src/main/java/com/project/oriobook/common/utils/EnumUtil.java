package com.project.oriobook.common.utils;

import com.project.oriobook.common.enums.ErrorCodeEnum;
import com.project.oriobook.common.enums.ErrorMessage;

public class EnumUtil {
    public static <T extends Enum<T>> T fromValue(Class<T> enumType, String value) {
        for (T enumConstant : enumType.getEnumConstants()) {
            if (enumConstant.toString().equalsIgnoreCase(value)) {
                return enumConstant;
            }
        }
        throw new IllegalArgumentException(ErrorMessage.get(ErrorCodeEnum.COMMON_ERROR_ENUM_TYPE) + value);
    }
}
