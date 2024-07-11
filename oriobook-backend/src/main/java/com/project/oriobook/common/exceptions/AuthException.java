package com.project.oriobook.common.exceptions;

import com.project.oriobook.common.enums.ErrorCodeEnum;
import com.project.oriobook.common.enums.ErrorMessage;
import com.project.oriobook.common.exceptions.base.ErrorDetails;
import com.project.oriobook.common.exceptions.base.LogicExceptionBase;

public class AuthException {
    public static class UnAuthorize extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.AUTH_UNAUTHORIZED;
        private static final String message = ErrorMessage.get(code);

        public UnAuthorize() {
            super(401, message, new ErrorDetails(code.toString(), message));
        }
    }

    public static class EmailExist extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.AUTH_EMAIL_EXISTED;
        private static final String message = ErrorMessage.get(code);

        public EmailExist() {
            super(401, message, new ErrorDetails(code.toString(), message));
        }
    }
}
