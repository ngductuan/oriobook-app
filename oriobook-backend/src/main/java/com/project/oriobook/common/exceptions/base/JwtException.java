package com.project.oriobook.common.exceptions.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.enums.ErrorCodeEnum;
import com.project.oriobook.common.enums.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtException {
    private int statusCode = 401;

    private String message;

    private ErrorDetails errorDetails;

    private String path;

    @JsonFormat(shape=JsonFormat.Shape.STRING,
            pattern= CommonConst.DATETIME_FORMAT, timezone=CommonConst.TIME_ZONE)
    private LocalDateTime time = LocalDateTime.now();

    public JwtException(ErrorCodeEnum errorCodeEnum, String path) {
        this.path = path;
        init(errorCodeEnum);
    }

    private void init(ErrorCodeEnum errorCodeEnum){
        message = ErrorMessage.get(errorCodeEnum);
        errorDetails = new ErrorDetails(errorCodeEnum.toString(), message);
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
