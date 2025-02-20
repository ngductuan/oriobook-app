package com.project.oriobook.common.exceptions;

import com.project.oriobook.common.enums.ErrorCodeEnum;
import com.project.oriobook.common.enums.ErrorMessage;
import com.project.oriobook.common.exceptions.base.ErrorDetails;
import com.project.oriobook.common.exceptions.base.LogicExceptionBase;

public class CategoryException {
    public static class NotFound extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.CATEGORY_NOT_FOUND;
        private static final String message = ErrorMessage.get(code);

        public NotFound() {
            super(404, message, new ErrorDetails(code.toString(), message));
        }
    }

    public static class DuplicateName extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.CATEGORY_DUPLICATE_NAME;
        private static final String message = ErrorMessage.get(code);

        public DuplicateName() {
            super(400, message, new ErrorDetails(code.toString(), message));
        }
    }

    public static class InvalidType extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.CATEGORY_INVALID_TYPE;
        private static final String message = ErrorMessage.get(code);

        public InvalidType() {
            super(400, message, new ErrorDetails(code.toString(), message));
        }
    }

    public static class InvalidParent extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.CATEGORY_INVALID_PARENT;
        private static final String message = ErrorMessage.get(code);

        public InvalidParent() {
            super(400, message, new ErrorDetails(code.toString(), message));
        }
    }
}
