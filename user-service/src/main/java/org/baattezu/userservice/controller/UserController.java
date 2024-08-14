package org.baattezu.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baattezu.userservice.config.MyAuthenticationToken;
import org.baattezu.userservice.dto.UserInfoDto;
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
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<UserInfo>> getUsers(){
        logger.info("Getting users exists");
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<UserInfoResponse> getUserProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserInfoResponse(id));
    }
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkUserExists(@PathVariable Long id){
        logger.info("Checking user exists");
        return ResponseEntity.ok(userService.checkUserExistsForMicroservices(id));
    }
    @GetMapping("/{id}/email")
    public ResponseEntity<String> getEmail(@PathVariable Long id){
        return ResponseEntity.ok(userService.getEmailForMicroservice(id));
    }
    @PostMapping
    public ResponseEntity<Void> saveUser(@RequestBody UserInfoDto userInfoDto){
        userService.saveUser(userInfoDto.getEmail());
        return ResponseEntity.ok().build();

    }

    @PutMapping("/{id}")
    @PreAuthorize("@userController.hasPermission(#id)")
    public ResponseEntity<UserInfo> updateUserProfileByEmail(
            @PathVariable Long id,
            @Valid @RequestBody UserInfoRequest userInfo
    ){
        return ResponseEntity.ok(userService.updateUserInfo(id, userInfo));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@userController.hasPermission(#id)")
    public ResponseEntity<Void> deleteUserProfileById(@PathVariable Long id) {
        userService.deleteUserInfo(id);
        return ResponseEntity.ok().build();
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
