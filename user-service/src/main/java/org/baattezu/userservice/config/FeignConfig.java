package org.baattezu.userservice.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
@Configuration
public class FeignConfig {
    // https://www.springcloud.io/post/2022-01/feign-token-relay/#gsc.tab=0
    // Changed a logic many times , just to make sure I secured other services and
    // can use it via FeignClient
    @Bean
    public RequestInterceptor requestInterceptor(){
        return requestTemplate -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String token = authentication instanceof MyAuthenticationToken customToken ?
                    "Bearer " + customToken.getToken() : null;
            requestTemplate.header(HttpHeaders.AUTHORIZATION, token);
        };
    }
}
