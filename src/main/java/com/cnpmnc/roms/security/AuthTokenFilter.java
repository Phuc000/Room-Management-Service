package com.cnpmnc.roms.security;

import com.cnpmnc.roms.service.UserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateToken(jwt)) {
                String username = jwtUtils.extractUsername(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        if(request.getCookies() != null) {
            logger.info("Found " + request.getCookies().length + " cookies");
            
            for (var cookie : request.getCookies()) {
                logger.info("Cookie name: " + cookie.getName() + ", value: " + cookie.getValue().substring(0, Math.min(10, cookie.getValue().length())) + "...");
                
                if (cookie.getName().equals("CredentialCookie")) {
                    logger.info("Found CredentialCookie");
                    return cookie.getValue();
                }
            }
            logger.info("CredentialCookie not found");
        } else {
            logger.info("No cookies in request");
        }
        return null;
    }
}
