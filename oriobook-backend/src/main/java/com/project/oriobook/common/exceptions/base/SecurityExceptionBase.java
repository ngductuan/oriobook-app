package com.project.oriobook.common.exceptions.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.oriobook.common.constants.CommonConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityExceptionBase {
    private int statusCode;

    private String message;

    private String path;

    @JsonFormat(shape=JsonFormat.Shape.STRING,
            pattern= CommonConst.DATETIME_FORMAT, timezone=CommonConst.TIME_ZONE)
    private LocalDateTime time;

    public Map<String, Object> getErrorResponse() {
        Map<String, Object> responseDetails = new LinkedHashMap<>();
        responseDetails.put("statusCode", this.statusCode);
        responseDetails.put("message", this.message);
        responseDetails.put("path", this.path);
        responseDetails.put("time", time);
        return responseDetails;
    }
}
