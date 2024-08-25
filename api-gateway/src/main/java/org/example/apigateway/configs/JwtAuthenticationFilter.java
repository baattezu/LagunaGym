package org.example.apigateway.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            String path = exchange.getRequest().getURI().getPath();

            if (path.startsWith("/api/auth/")) {
                return chain.filter(exchange);
            }
            if (path.endsWith("/api-docs")) {
                return chain.filter(exchange);
            }

            HttpHeaders headers = exchange.getRequest().getHeaders();
            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                return handleError(exchange, "Missing Authorization Header");
            }

            String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return handleError(exchange, "Invalid Authorization Header");
            }

            String token = authHeader.substring(7);
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            logger.info("JWT Token Valid: User ID - {}, Roles - {}", claims.getSubject(), claims.get("roles").toString());
        } catch (ExpiredJwtException e) {
            return handleError(exchange, "JWT Token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            return handleError(exchange, "JWT Token is unsupported: " + e.getMessage());
        } catch (JwtException e) {
            return handleError(exchange, "JWT Token is invalid: " + e.getMessage());
        }
        return chain.filter(exchange);
    }
    private Mono<Void> handleError(ServerWebExchange exchange, String message) {
        logger.warn(message);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), message);
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        try {
            String responseBody = serializeErrorResponse(errorResponse);
            DataBuffer buffer = exchange.getResponse()
                    .bufferFactory()
                    .wrap(responseBody.getBytes(StandardCharsets.UTF_8));
            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (Exception e) {
            logger.error("Error writing response", e);
            return exchange.getResponse().setComplete();
        }
    }

    private String serializeErrorResponse(ErrorResponse errorResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            logger.error("Error serializing ErrorResponse", e);
            return "{\"status\":500,\"message\":\"Internal server error\"}";
        }
    }
}
