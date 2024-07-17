package com.project.oriobook.common.exceptions.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.oriobook.common.constants.CommonConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtExceptionBase extends Exception {
    private int statusCode;

    private String message;

    private ErrorDetails errorDetails;

    private String path;

    private LocalDateTime time = LocalDateTime.now();

    public JwtExceptionBase(int statusCode, String message, ErrorDetails errorDetails, String path) {
        super(message);
        this.errorDetails = errorDetails;
        this.statusCode = statusCode;
        this.message = message;
        this.path = path;
    }

    public Map<String, Object> getErrorResponse() {
        Map<String, Object> responseDetails = new LinkedHashMap<>();
        responseDetails.put("statusCode", this.statusCode);
        responseDetails.put("message", this.message);
        responseDetails.put("error", this.errorDetails);
        responseDetails.put("path", this.path);
        responseDetails.put("time", time.toString());
        return responseDetails;
    }
}
