package com.project.oriobook.common.exceptions.web_security;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        String originalUri = request.getRequestURI();

        // JwtException error = new JwtException();
        // error.setStatusCode(HttpServletResponse.SC_FORBIDDEN);
        // error.setMessage("Access denied");
        //
        // error.setPath(originalUri);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        // objectMapper.writeValueAsString(error.getErrorResponse()
        response.getWriter().write("123 " + accessDeniedException.getMessage());
    }
}