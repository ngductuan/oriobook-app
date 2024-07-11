package com.project.oriobook.common.exceptions.web_security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.oriobook.common.exceptions.base.SecurityExceptionBase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AuthenticationException implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         org.springframework.security.core.AuthenticationException authException)
            throws IOException, IOException {
        String originalUri = request.getRequestURI();

        SecurityExceptionBase error = new SecurityExceptionBase();
        error.setStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
        error.setMessage("Unauthorized");

        error.setPath(originalUri);
        error.setTime(LocalDateTime.now());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        // objectMapper.writeValueAsString(error.getErrorResponse());
        response.getWriter().write(objectMapper.writeValueAsString(error.getErrorResponse()));
    }
}
