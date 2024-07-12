package com.project.oriobook.common.exceptions.web_security;

import com.project.oriobook.common.enums.ErrorCodeEnum;
import com.project.oriobook.common.enums.ErrorMessage;
import com.project.oriobook.common.exceptions.base.ErrorDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.InsufficientAuthenticationException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class FixJwtException extends InsufficientAuthenticationException {
    private static final int statusCode = 401;

    private static final String message = ErrorMessage.get(ErrorCodeEnum.AUTH_UNAUTHORIZED);

    private ErrorDetails errorDetails;

    private String path;

    public FixJwtException(ErrorCodeEnum errorCodeEnum, String path) {
        super(message);
        this.errorDetails = new ErrorDetails(errorCodeEnum.toString(), ErrorMessage.get(errorCodeEnum));
        this.path = path;
    }

    public FixJwtException(ErrorCodeEnum errorCodeEnum, Throwable cause) {
        super(message, cause);
        this.errorDetails = new ErrorDetails(errorCodeEnum.toString(), ErrorMessage.get(errorCodeEnum));
    }

    public Map<String, Object> getErrorResponse() {
        Map<String, Object> responseDetails = new LinkedHashMap<>();
        responseDetails.put("statusCode", statusCode);
        responseDetails.put("message", message);
        responseDetails.put("error", this.errorDetails);
        responseDetails.put("path", this.path);
        responseDetails.put("time", LocalDateTime.now());
        return responseDetails;
    }
}
