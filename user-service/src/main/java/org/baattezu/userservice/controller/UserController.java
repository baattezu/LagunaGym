package org.baattezu.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.baattezu.userservice.model.UserInfo;
import org.baattezu.userservice.service.UserInfoExistsException;
import org.baattezu.userservice.service.UserService;
import org.springframework.http.HttpStatus;
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
    @PostMapping
    public ResponseEntity<UserInfo> saveUser(@RequestBody UserInfo userInfo) throws UserInfoExistsException {
        return ResponseEntity.ok(userService.createUserInfo(userInfo));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserInfo> getUserProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserInfoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserInfo> updateUserProfile(@PathVariable Long id , @RequestBody UserInfo userInfo){
        userInfo.setId(id);
        return ResponseEntity.ok(userService.updateUserInfo(userInfo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfileById(@PathVariable Long id) {
        userService.deleteUserInfo(id);
        return ResponseEntity.noContent().build();
    }



}
