package com.project.oriobook.common.exceptions;

import com.project.oriobook.common.exceptions.base.BusinessExceptionBase;
import com.project.oriobook.common.exceptions.base.LogicExceptionBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

// Handle global exceptions
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception) {
        if (exception instanceof LogicExceptionBase logicException) {
            return ResponseEntity.status(logicException.getStatusCode()).body(logicException.getErrorResponse());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @ExceptionHandler(BusinessExceptionBase.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBusinessException(BusinessExceptionBase exception) {
        Map<String, Object> responseDetails = exception.getErrorResponse();
        return responseDetails;
    }
}
