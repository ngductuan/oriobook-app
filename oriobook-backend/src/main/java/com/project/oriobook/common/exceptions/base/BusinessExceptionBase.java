package com.project.oriobook.common.exceptions.base;

import com.project.oriobook.common.utils.ErrorUtil;
import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessExceptionBase extends RuntimeException{
    private Map<String, List<String>> errorDetails;
    private int statusCode;
    private String message;

    public BusinessExceptionBase(int statusCode, String message, Map<String, List<String>> errorDetails) {
        super(message);
        this.errorDetails = errorDetails;
        this.statusCode = statusCode;
        this.message = message;
    }

    public Map<String, Object> getErrorResponse() {
        Map<String, Object> responseDetails = new LinkedHashMap<>();
        responseDetails.put("statusCode", this.statusCode);
        responseDetails.put("message", this.message);
        responseDetails.put("errors", this.errorDetails);
        responseDetails.put("stackTrace", ErrorUtil.getStackTraceElement(this));
        return responseDetails;
    }
}
