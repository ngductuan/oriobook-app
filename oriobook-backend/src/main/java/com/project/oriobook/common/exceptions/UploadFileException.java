package com.project.oriobook.common.exceptions;

import com.project.oriobook.common.enums.ErrorCodeEnum;
import com.project.oriobook.common.enums.ErrorMessage;
import com.project.oriobook.common.exceptions.base.ErrorDetails;
import com.project.oriobook.common.exceptions.base.LogicExceptionBase;

public class UploadFileException {
    public static class NotEmpty extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.UPLOAD_NOT_EMPTY;
        private static final String message = ErrorMessage.get(code);

        public NotEmpty() {
            super(400, message, new ErrorDetails(code.toString(), message));
        }
    }

    public static class OnlyUploadImage extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.UPLOAD_ONLY_IMAGE;
        private static final String message = ErrorMessage.get(code);

        public OnlyUploadImage() {
            super(400, message, new ErrorDetails(code.toString(), message));
        }
    }
}
