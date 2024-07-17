package com.project.oriobook.common.exceptions;

import com.project.oriobook.common.enums.ErrorCodeEnum;
import com.project.oriobook.common.enums.ErrorMessage;
import com.project.oriobook.common.exceptions.base.ErrorDetails;
import com.project.oriobook.common.exceptions.base.LogicExceptionBase;

public class CartException {
    public static class ItemNotExists extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.CART_ITEM_NOT_FOUND;
        private static final String message = ErrorMessage.get(code);

        public ItemNotExists() {
            super(404, message, new ErrorDetails(code.toString(), message));
        }
    }

    public static class InvalidAction extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.CART_INVALID_ACTION;
        private static final String message = ErrorMessage.get(code);

        public InvalidAction() {
            super(400, message, new ErrorDetails(code.toString(), message));
        }
    }
}
