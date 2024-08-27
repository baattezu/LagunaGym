package org.baattezu.authservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth", description = "The Auth API. Contains operations like authentication, registration, assigning roles, deletion of users")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "Register user", description = "Register user")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticate user")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@authController.hasRoleAdminOrIsTheSameUser(#id)")
    @Operation(
            summary = "Delete user",
            description = "Delete user while having ROLE_ADMIN",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(authenticationService.deleteUser(id));
    }

    @PatchMapping("/{id}/assign-role")
    @PreAuthorize("@authController.hasRoleAdminOrIsTheSameUser(#userId)")
    @Operation(
            summary = "Assign role",
            description = "Assign role to user while having ROLE_ADMIN",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<?> assignRole(
            @PathVariable Long id,
            @RequestParam String roleName
    ) {
        authenticationService.assignRole(id, roleName);
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
