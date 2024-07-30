package org.baattezu.authservice.services;


import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.baattezu.authservice.client.UserServiceClient;
import org.baattezu.authservice.dto.*;
import org.baattezu.authservice.model.*;
import org.baattezu.authservice.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserServiceClient userServiceClient;
    private final KafkaTemplate<String, EmailMessage> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private void emailIsTaken(String email){
        if (userRepository.existsByEmail(email)){
            throw new IllegalStateException("Email is taken: " + email );
        }
    }
    public AuthenticationResponse register(RegisterRequest request) {
        emailIsTaken(request.getEmail());

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        userServiceClient.saveUser(new UserInfoDto(user.getEmail()));
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);

        AuthenticationResponse response = AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

        sendMessage(request.getEmail(), "Congratulations! You were registered!");

        return response;
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

        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", roles);

        String jwtToken = jwtService.generateToken(claims, user);

        AuthenticationResponse response = AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

        sendMessage(request.getEmail(), "You were logged in at " + LocalTime.now().toString());
        return response;
    }

    public void assignRole(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", userId);
                    return new UsernameNotFoundException("User not found");
                });

        Role role = getRoleByName(roleName).orElseThrow(() -> {
            logger.error("Role not found: {}", roleName);
            return new IllegalArgumentException("Role not found: " + roleName);
        });

        user.setRole(role);
        userRepository.save(user);
        sendMessage(user.getEmail(), "You were given new role : " + roleName);
    }

    private Optional<Role> getRoleByName(String roleName) {
        try {
            return Optional.of(Role.valueOf(roleName));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
    private void sendMessage(String email, String message){
        kafkaTemplate.send("verification-events", new EmailMessage(email, message));
    }

    public String deleteUser(Long userId) {
        String userEmail = getUserEmail(userId);

        deleteUserInUserService(userId);
        deleteUserFromRepository(userId);

        sendMessage(userEmail, "We deleted your account");

        return "User deleted successfully.";
    }

    private void deleteUserInUserService(Long userId) {
        try {
            ResponseEntity<Void> response = userServiceClient.deleteUserById(userId);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new IllegalStateException("Failed to delete user in <USER-SERVICE>. User deletion aborted.");
            }
        } catch (FeignException e) {
            if (e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new IllegalStateException("Failed to delete user in <USER-SERVICE>. User deletion aborted.");
            }
            throw e;
        }
    }

    private void deleteUserFromRepository(Long userId) {
        userRepository.deleteById(userId);
    }

    private String getUserEmail(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("No such user exists.")
        );
        return user.getEmail();
    }
}
