package com.project.oriobook.common.exceptions;

import com.project.oriobook.common.enums.ErrorCodeEnum;
import com.project.oriobook.common.enums.ErrorMessage;
import com.project.oriobook.common.exceptions.base.ErrorDetails;
import com.project.oriobook.common.exceptions.base.IOExceptionBase;
import com.project.oriobook.common.exceptions.base.LogicExceptionBase;

public class CommonException {
    public static class ConvertRedisData extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.COMMON_GET_REDIS_DATA;
        private static final String message = ErrorMessage.get(code);

        public ConvertRedisData(String message) {
            super(500, ConvertRedisData.message, new ErrorDetails(code.toString(), message));
        }
    }

    public static class InvalidDateDTO extends IOExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.COMMON_INVALID_DATE_DTO_FORMAT;
        private static final String message = ErrorMessage.get(code);

        public InvalidDateDTO(String message) {
            super(400, InvalidDateDTO.message, new ErrorDetails(code.toString(), message));
        }
    }
}
