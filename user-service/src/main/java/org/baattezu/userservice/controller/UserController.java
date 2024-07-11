package org.baattezu.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baattezu.userservice.dto.UserInfoDto;
import org.baattezu.userservice.dto.response.UserInfoResponse;
import org.baattezu.userservice.model.UserInfo;
import org.baattezu.userservice.dto.request.UserInfoRequest;
import org.baattezu.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserInfo>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfoResponse> getUserProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserInfoResponse(id));
    }
    @GetMapping("/{id}/exists")
    public ResponseEntity<Void> checkUserExists(@PathVariable Long id){
        userService.checkUserExists(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}/email")
    public ResponseEntity<String> getEmail(@PathVariable Long id){
        return ResponseEntity.ok(userService.getEmail(id));
    }
    @PostMapping
    public ResponseEntity<Void> saveUser(@RequestBody UserInfoDto userInfoDto){
        userService.saveUser(userInfoDto.getEmail());
        return ResponseEntity.ok().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserInfo> updateUserProfileByEmail(
            @PathVariable Long id,
            @Valid @RequestBody UserInfoRequest userInfo
    ){
        return ResponseEntity.ok(userService.updateUserInfo(id, userInfo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfileById(@PathVariable Long id) {
        userService.deleteUserInfo(id);
        return ResponseEntity.noContent().build();
    }



}
