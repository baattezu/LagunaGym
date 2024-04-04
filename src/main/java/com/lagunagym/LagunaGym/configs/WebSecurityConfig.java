package com.lagunagym.LagunaGym.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.http.HttpRequest;

@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                requests -> requests
                        .requestMatchers("/","/login").permitAll()
                        .anyRequest().authenticated()
        ).addFilterAt(new MyFilter(), UsernamePasswordAuthenticationFilter.class)
                    .build();
    }
}
