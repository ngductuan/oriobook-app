package com.project.oriobook.common.components.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.oriobook.common.exceptions.base.JwtExceptionBase;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CommonHelper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String transformMapToJson(Map<String, Object> object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static void responseFilterException(Exception e, HttpServletResponse response) {
        try {
            response.setContentType("application/json");
            if (e instanceof JwtExceptionBase jwtE) {
                response.setStatus(jwtE.getStatusCode());
                response.getWriter().write(CommonHelper.transformMapToJson(jwtE.getErrorResponse()));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(e.getMessage());
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}