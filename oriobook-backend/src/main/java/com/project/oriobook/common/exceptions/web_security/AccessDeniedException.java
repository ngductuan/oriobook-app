package com.project.oriobook.common.exceptions.web_security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.oriobook.common.exceptions.base.SecurityExceptionBase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AccessDeniedException implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, IOException {
        String originalUri = request.getRequestURI();

        SecurityExceptionBase error = new SecurityExceptionBase();
        error.setStatusCode(HttpServletResponse.SC_FORBIDDEN);
        error.setMessage("Access denied");

        error.setPath(originalUri);
        error.setTime(LocalDateTime.now());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(error.getErrorResponse()));
    }
}
