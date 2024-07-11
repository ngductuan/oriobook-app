package com.project.oriobook.modules.auth.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.oriobook.common.constants.CommonConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;

    private String refreshToken;

    private String userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = CommonConst.DATETIME_FORMAT, timezone = CommonConst.TIME_ZONE)
    private LocalDateTime expiredAt;
}
