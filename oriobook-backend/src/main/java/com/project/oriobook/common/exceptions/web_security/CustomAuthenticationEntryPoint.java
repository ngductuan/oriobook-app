package com.project.oriobook.common.exceptions.web_security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        FixJwtException detail = (authException instanceof FixJwtException)
                ? ((FixJwtException) authException)
                : null;
        System.out.print(authException.getClass().getName());
        if (authException instanceof FixJwtException jwtException) {
            response.getWriter().write(objectMapper.writeValueAsString(jwtException));
        } else {
            response.getWriter().write(authException.getMessage());
        }

        // String originalUri = request.getRequestURI();
        // String pathReq = (String) request.getAttribute("pathReq");
        //
        // SecurityExceptionBase error = new SecurityExceptionBase();
        // error.setStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
        // error.setMessage(message);
        // error.setErrorDetails(new ErrorDetails(errorCodeEnum.toString(), message));
        // error.setPath(pathReq);
        //
        // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // response.setContentType("application/json");
        // // objectMapper.writeValueAsString(error.getErrorResponse());
        // response.getWriter().write(objectMapper.writeValueAsString(error.getErrorResponse()));
    }
}
