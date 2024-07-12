package com.project.oriobook.common.exceptions;

import com.project.oriobook.common.enums.ErrorCodeEnum;
import com.project.oriobook.common.enums.ErrorMessage;
import com.project.oriobook.common.exceptions.base.ErrorDetails;
import com.project.oriobook.common.exceptions.base.JwtExceptionBase;
import com.project.oriobook.common.exceptions.base.LogicExceptionBase;

public class AuthException {
    private static final String globalAuthMsg = ErrorMessage.get(ErrorCodeEnum.AUTH_UNAUTHORIZED);
    private static final String globalAccessDeniedMsg = ErrorMessage.get(ErrorCodeEnum.AUTH_FORBIDDEN);

    // JwtExceptionBase for Authentication
    public static class UnAuthorization extends JwtExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.AUTH_UNAUTHORIZED;
        private static final String message = ErrorMessage.get(code);

        public UnAuthorization(String path) {
            super(401, globalAuthMsg, new ErrorDetails(code.toString(), message), path);
        }
    }

    public static class TokenEmpty extends JwtExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.AUTH_TOKEN_EMPTY;
        private static final String message = ErrorMessage.get(code);

        public TokenEmpty(String path) {
            super(401, globalAuthMsg, new ErrorDetails(code.toString(), message), path);
        }
    }

    public static class TokenExpired extends JwtExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.AUTH_TOKEN_EXPIRED;
        private static final String message = ErrorMessage.get(code);

        public TokenExpired(String path) {
            super(401, globalAuthMsg, new ErrorDetails(code.toString(), message), path);
        }
    }

    public static class TokenInvalid extends JwtExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.AUTH_INVALID_TOKEN;
        private static final String message = ErrorMessage.get(code);

        public TokenInvalid(String path) {
            super(401, globalAuthMsg, new ErrorDetails(code.toString(), message), path);
        }
    }

    // LogicExceptionBase for Authentication
    public static class ForbiddenResource extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.AUTH_INVALID_TOKEN;
        private static final String message = ErrorMessage.get(code);

        public ForbiddenResource() {
            super(403, globalAccessDeniedMsg, new ErrorDetails(code.toString(), message));
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
}
