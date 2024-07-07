package com.project.oriobook.common.exceptions;

import com.project.oriobook.common.exceptions.base.BusinessExceptionBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

// Handle global exceptions
@RestControllerAdvice
public class GlobalExceptionHandler {
    // @ExceptionHandler(Exception.class)
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // public ResponseEntity<?> handleGeneralException(Exception exception) {
    //     return ResponseEntity.internalServerError().body(exception.getMessage());
    //     // return ResponseEntity.internalServerError().body(
    //     //    ResponseObject.builder()
    //     //            .status(HttpStatus.INTERNAL_SERVER_ERROR)
    //     //            .message(exception.getMessage())
    //     //            .build()
    //     // );
    //
    // }
    @ExceptionHandler(BusinessExceptionBase.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleBusinessExceptionBase(BusinessExceptionBase exception) {
        Map<String, Object> responseDetails = exception.getErrorResponse();
        return responseDetails;
    }
}
