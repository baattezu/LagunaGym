package org.baattezu.authservice.services;


import lombok.RequiredArgsConstructor;
import org.baattezu.authservice.client.UserServiceClient;
import org.baattezu.authservice.dto.AuthenticationRequest;
import org.baattezu.authservice.dto.AuthenticationResponse;
import org.baattezu.authservice.dto.RegisterRequest;
import org.baattezu.authservice.model.*;
import org.baattezu.authservice.repositories.RoleRepository;
import org.baattezu.authservice.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserServiceClient userServiceClient;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private void emailIsTaken(String email){
        if (userRepository.existsByEmail(email)){
            throw new IllegalStateException("Email is taken: " + email );
        }
    }
    public AuthenticationResponse register(RegisterRequest request) {
        emailIsTaken(request.getEmail());
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new NoSuchElementException("Role 'USER' not found"));
        roles.add(userRole);
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();
        userServiceClient.saveUser(new UserInfoDto(user.getEmail()));
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user: " + request.getEmail());
            throw new UsernameNotFoundException("Invalid email or password");
        }
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", request.getEmail());
                    return new UsernameNotFoundException("User not found");
                });
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
