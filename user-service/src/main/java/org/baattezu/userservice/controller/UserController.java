package org.baattezu.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baattezu.userservice.dto.MembershipRequest;
import org.baattezu.userservice.model.UserInfo;
import org.baattezu.userservice.dto.UserInfoRequest;
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
    public ResponseEntity<UserInfo> getUserProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserInfoById(id));
    }

    @PutMapping
    public ResponseEntity<UserInfo> updateUserProfileByEmail(
            @Valid @RequestBody UserInfoRequest userInfo
    ){
        return ResponseEntity.ok(userService.updateUserInfo(userInfo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfileById(@PathVariable Long id) {
        userService.deleteUserInfo(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{userId}/memberships")
    public ResponseEntity<UserInfo> addMembershipToUser(@PathVariable Long userId, @RequestBody MembershipRequest membershipRequest) {
        UserInfo updatedUser = userService.addMembershipToUser(userId, membershipRequest);
        return ResponseEntity.ok(updatedUser);
    }



}
