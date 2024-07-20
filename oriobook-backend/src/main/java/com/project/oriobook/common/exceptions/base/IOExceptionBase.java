package com.project.oriobook.common.exceptions.base;

import com.project.oriobook.common.utils.ErrorUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IOExceptionBase extends IOException {
    private ErrorDetails errorDetails;
    private int statusCode;
    private String message;

    public IOExceptionBase(int statusCode, String message, ErrorDetails errorDetails) {
        super(message);
        this.errorDetails = errorDetails;
        this.statusCode = statusCode;
        this.message = message;
    }

    public Map<String, Object> getErrorResponse() {
        Map<String, Object> responseDetails = new LinkedHashMap<>();
        responseDetails.put("statusCode", this.statusCode);
        responseDetails.put("message", this.message);
        responseDetails.put("error", this.errorDetails);
        responseDetails.put("stackTrace", ErrorUtil.getStackTraceElement(this));
        return responseDetails;
    }
}
