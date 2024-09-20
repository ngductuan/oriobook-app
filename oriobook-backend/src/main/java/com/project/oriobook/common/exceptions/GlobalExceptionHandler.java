package com.project.oriobook.common.exceptions;

import com.project.oriobook.common.exceptions.base.BusinessExceptionBase;
import com.project.oriobook.common.exceptions.base.LogicExceptionBase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

// Handle global exceptions
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessExceptionBase.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBusinessException(BusinessExceptionBase exception) {
        log.error(exception.getMessage());

        Map<String, Object> responseDetails = exception.getErrorResponse();
        return responseDetails;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException exception) {
        log.error(exception.getMessage());

        Map<String, Object> responseDetails = (new AuthException.ForbiddenResource(
                request.getRequestURI()
        )).getErrorResponse();
        return responseDetails;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception) {
        log.error(exception.getMessage());

        if (exception instanceof LogicExceptionBase logicException) {
            return ResponseEntity.status(logicException.getStatusCode()).body(logicException.getErrorResponse());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }
}
