package org.baattezu.userservice.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baattezu.userservice.config.MyAuthenticationToken;
import org.baattezu.userservice.dto.UserInfoDto;
import org.baattezu.userservice.dto.response.UserInfoPlusMembershipInfo;
import org.baattezu.userservice.dto.response.UserInfoResponse;
import org.baattezu.userservice.model.UserInfo;
import org.baattezu.userservice.dto.request.UserInfoRequest;
import org.baattezu.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(
    name = "User",
    description = "The User API. Contains operations like " +
            "getting all users," +
            "getting aggregated information about user and user's membership" +
            "updating information about user.")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @Operation(summary = "Get users")
    public ResponseEntity<List<UserInfo>> getUsers(){
        logger.info("Getting users exists");
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/with-memberships")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @Operation(summary = "Get users with their memberships")
    public ResponseEntity<List<UserInfoPlusMembershipInfo>> getUsersNMembershipProfileById() {
        return ResponseEntity.ok(userService.getUserInfosResponse());
    }
    @GetMapping("/{id}")
    @PreAuthorize("@userController.hasPermission(#id)")
    @Operation(summary = "Get user with their membership")
    public ResponseEntity<UserInfoResponse> getUserProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserInfoResponse(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("@userController.hasPermission(#id)")
    @Operation(summary = "Update user")
    public ResponseEntity<UserInfo> updateUserProfileByEmail(
            @PathVariable Long id,
            @Valid @RequestBody UserInfoRequest userInfo
    ){
        return ResponseEntity.ok(userService.updateUserInfo(id, userInfo));
    }

    public boolean hasPermission(Long userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdminOrManager = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority ->
                    grantedAuthority.getAuthority().equals("ROLE_ADMIN") ||
                    grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
        boolean isSameUser = authentication instanceof MyAuthenticationToken customToken &&
                userId.equals(customToken.getUserId());
        if (isSameUser){
            return true;
        } else if (!isAdminOrManager){
            logger.error("You have no permissions");
            throw new AccessDeniedException("You must have ADMIN or MANAGER role to perform this action");
        }
        return true;
    }



}
