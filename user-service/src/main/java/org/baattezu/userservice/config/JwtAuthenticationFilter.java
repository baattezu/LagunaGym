package org.baattezu.userservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
import org.baattezu.userservice.exception.ErrorResponse;
import org.baattezu.userservice.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        };
        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);

        try {
            if (username != null || SecurityContextHolder.getContext().getAuthentication() == null){
                MyAuthenticationToken auth = new MyAuthenticationToken(
                        jwtService.extractUserId(jwt),
                        null,
                        jwtService.extractRoles(jwt),
                        jwtService.extractUserId(jwt),
                        jwt
                );
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
                logger.info("user authenticated successfully");
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e){
            logger.error("JWT expired: {}", e.getMessage());
            setErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, "JWT has expired", response);
        } catch (JwtException e) {
            logger.error("JWT Token is invalid: {}", e.getMessage());
            setErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token is invalid", response);
        } catch (Exception e) {
            logger.error("Error occurred during JWT processing: {}", e.getMessage());
            setErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the JWT token", response);
        }
    }

    private void setErrorResponse(int status, String message, HttpServletResponse response) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(status, message);
        ObjectMapper op = new ObjectMapper();
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String responseMessage = op.writeValueAsString(errorResponse);
        response.getWriter().write(responseMessage);
    }
}
