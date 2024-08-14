package org.baattezu.membershipservice.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.baattezu.membershipservice.exception.ErrorResponse;
import org.baattezu.membershipservice.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;



@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        try {
            // Проверка наличия и формата заголовка Authorization
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            // Извлечение JWT токена из заголовка Authorization
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);


            // Проверка наличия пользователя и аутентификации в SecurityContext
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                MyAuthenticationToken authToken = new MyAuthenticationToken(
                        userEmail,
                        null,
                        jwtService.extractRoles(jwt), // roles
                        jwtService.extractUserId(jwt), // userId
                        jwt
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                logger.info("a");
            }

            // Продолжение выполнения цепочки фильтров
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            logger.error("JWT Token has expired: {}", e.getMessage());
            setErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token has expired", response);
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
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
    }
}
