package org.baattezu.userservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;


    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }
    public List<SimpleGrantedAuthority> extractRoles(String jwt){
        Claims claims = extractAllClaims(jwt);
        ObjectMapper op = new ObjectMapper();
        List<String> roles = op.convertValue(claims.get("roles"), new TypeReference<List<String>>() {
        });
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    public Long extractUserId(String jwt){
        return extractAllClaims(jwt).get("userId",Long.class);
    }

    private Claims extractAllClaims(String jwt){
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(getSignInKey()).build();
        return jwtParser.parseClaimsJws(jwt).getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
