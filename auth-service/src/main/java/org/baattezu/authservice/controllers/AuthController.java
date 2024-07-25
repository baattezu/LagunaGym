package org.baattezu.authservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baattezu.authservice.dto.AuthenticationRequest;
import org.baattezu.authservice.dto.AuthenticationResponse;
import org.baattezu.authservice.dto.RegisterRequest;
import org.baattezu.authservice.dto.UserInfoDto;
import org.baattezu.authservice.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id
            ){
        return ResponseEntity.ok(authenticationService.deleteUser(id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assign-role")
    public ResponseEntity<?> assignRole(@RequestParam Long userId, @RequestParam String roleName) {
        authenticationService.assignRole(userId, roleName);
        return ResponseEntity.ok("Role assigned successfully");
    }
}
