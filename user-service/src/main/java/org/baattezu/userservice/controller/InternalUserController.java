package org.baattezu.userservice.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.baattezu.userservice.dto.UserInfoDto;
import org.baattezu.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Hidden
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class InternalUserController {
    private final UserService userService;
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkUserExists(@PathVariable Long id){
        return ResponseEntity.ok(userService.checkUserExistsForMicroservices(id));
    }
    @GetMapping("/{id}/email")
    public ResponseEntity<String> getEmail(@PathVariable Long id){
        return ResponseEntity.ok(userService.getEmailForMicroservice(id));
    }
    @PostMapping("/save-user-from-auth-service")
    public ResponseEntity<Void> saveUserFromAuthService(@RequestBody UserInfoDto userInfoDto){
        userService.saveUser(userInfoDto.getEmail());
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfileByIdFromAuthService(@PathVariable Long id) {
        userService.deleteUserInfo(id);
        return ResponseEntity.ok().build();
    }
}
