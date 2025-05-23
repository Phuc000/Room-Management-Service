package com.cnpmnc.roms.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        String errorMessage;
        int statusCode;

        if (authException.getCause() instanceof IllegalArgumentException) {
            errorMessage = "Error: Bad Request";
            statusCode = HttpServletResponse.SC_BAD_REQUEST;
        } else if (authException.getCause() instanceof NullPointerException) {
            errorMessage = "Error: Internal Server Error";
            statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        } else {
            errorMessage = "Error: Unauthorized";
            statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        }

        response.sendError(statusCode, errorMessage);
    }
}
