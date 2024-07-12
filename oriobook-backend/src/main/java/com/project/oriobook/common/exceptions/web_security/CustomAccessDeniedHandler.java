package com.project.oriobook.common.exceptions.web_security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.oriobook.common.exceptions.base.JwtExceptionBase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        String originalUri = request.getRequestURI();

        JwtExceptionBase error = new JwtExceptionBase();
        error.setStatusCode(HttpServletResponse.SC_FORBIDDEN);
        error.setMessage("Access denied");

        error.setPath(originalUri);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(error.getErrorResponse()));
    }
}
