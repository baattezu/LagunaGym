package org.baattezu.authservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baattezu.authservice.configs.MyAuthenticationToken;
import org.baattezu.authservice.dto.AuthenticationRequest;
import org.baattezu.authservice.dto.AuthenticationResponse;
import org.baattezu.authservice.dto.RegisterRequest;
import org.baattezu.authservice.dto.UserInfoDto;
import org.baattezu.authservice.model.User;
import org.baattezu.authservice.services.AuthenticationService;
import org.baattezu.authservice.services.JwtService;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    @PreAuthorize("@authController.hasRoleAdminOrIsTheSameUser(#id)")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(authenticationService.deleteUser(id));
    }

    @PostMapping("/assign-role")
    @PreAuthorize("@authController.hasRoleAdminOrIsTheSameUser(#userId)")
    public ResponseEntity<?> assignRole(
            @RequestParam Long userId,
            @RequestParam String roleName
    ) {
        authenticationService.assignRole(userId, roleName);
        return ResponseEntity.ok("Role assigned successfully");
    }

    public Boolean hasRoleAdminOrIsTheSameUser(Long userId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        boolean isSameUser = authentication instanceof MyAuthenticationToken customToken
                && userId.equals(customToken.getUserId()); // wanted ternary operator but IDE thought differently
        if (!isAdmin) {
            throw new AccessDeniedException("You must have ADMIN role to perform this action");
        }
        if (isSameUser) {
            throw new AccessDeniedException("You cannot perform this action on yourself.");
        }

        return true;
    }

}
