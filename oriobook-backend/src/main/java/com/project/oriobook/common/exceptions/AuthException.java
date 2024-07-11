package com.project.oriobook.common.exceptions;

import com.project.oriobook.common.enums.ErrorCodeEnum;
import com.project.oriobook.common.enums.ErrorMessage;
import com.project.oriobook.common.exceptions.base.ErrorDetails;
import com.project.oriobook.common.exceptions.base.LogicExceptionBase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthException {
    public static class UnAuthorization extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.AUTH_UNAUTHORIZED;
        private static final String message = ErrorMessage.get(code);

        public UnAuthorization(HttpServletRequest request, HttpServletResponse response) {
            super(401, message, new ErrorDetails(code.toString(), message));
        }
    }

    public static class WrongCredentials extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.AUTH_WRONG_CREDENTIALS;
        private static final String message = ErrorMessage.get(code);

        public WrongCredentials() {
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

    public static class TokenExpired extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.AUTH_TOKEN_EXPIRED;
        private static final String message = ErrorMessage.get(code);

        public TokenExpired() {
            super(401, message, new ErrorDetails(code.toString(), message));
        }
    }

    public static class TokenInvalid extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.AUTH_INVALID_TOKEN;
        private static final String message = ErrorMessage.get(code);

        public TokenInvalid() {
            super(401, message, new ErrorDetails(code.toString(), message));
        }
    }
}
