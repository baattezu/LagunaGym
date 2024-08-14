package org.baattezu.authservice.configs;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class MyAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final Long userId;
    public MyAuthenticationToken(
            Object principal, Object credentials,
            Collection<? extends GrantedAuthority> authorities,
            Long userId
    ) {
        super(principal, credentials, authorities);
        this.userId = userId;
    }
}
