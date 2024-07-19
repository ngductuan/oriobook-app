package com.project.oriobook.common.utils;

import com.project.oriobook.common.exceptions.base.JwtExceptionBase;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CommonUtil {
    private final MapperUtil mapperUtil;

    public void responseFilterException(Exception e, HttpServletResponse response) {
        try {
            response.setContentType("application/json");
            if (e instanceof JwtExceptionBase jwtE) {
                response.setStatus(jwtE.getStatusCode());
                response.getWriter().write(mapperUtil.convertMapToJson(jwtE.getErrorResponse()));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(e.getMessage());
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}