package org.baattezu.membershipservice.configs;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class MyAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String token; // just for feign.RequestInterceptor

    private Long userId;
    public MyAuthenticationToken(
            Object principal, Object credentials,
            Collection<? extends GrantedAuthority> authorities,
            Long userId,
            String token
    ) {
        super(principal, credentials, authorities);
        this.userId = userId;
        this.token = token;
    }
}
