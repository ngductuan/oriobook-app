package com.project.oriobook.common.exceptions;

import com.project.oriobook.common.enums.ErrorCodeEnum;
import com.project.oriobook.common.enums.ErrorMessage;
import com.project.oriobook.common.exceptions.base.ErrorDetails;
import com.project.oriobook.common.exceptions.base.LogicExceptionBase;

public class OrderException {
    public static class EmptyCart extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.ORDER_EMPTY_CART;
        private static final String message = ErrorMessage.get(code);

        public EmptyCart() {
            super(404, message, new ErrorDetails(code.toString(), message));
        }
    }

    public static class CreateOrderDetailsFailed extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.ORDER_DETAILS_CREATE_FAILED;
        private static final String message = ErrorMessage.get(code);

        public CreateOrderDetailsFailed() {
            super(404, message, new ErrorDetails(code.toString(), message));
        }
    }

    public static class OrderNotFound extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.ORDER_NOT_FOUND;
        private static final String message = ErrorMessage.get(code);

        public OrderNotFound(String orderId) {
            super(404, message, new ErrorDetails(code.toString(), message + ": " + orderId));
        }
    }
}
