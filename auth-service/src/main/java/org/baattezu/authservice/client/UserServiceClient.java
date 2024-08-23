package org.baattezu.authservice.client;

import org.baattezu.authservice.dto.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping("/api/users/save-user-from-auth-service")
    String saveUser(@RequestBody UserInfoDto user);

    @DeleteMapping("/api/users/{id}")
    ResponseEntity<Void> deleteUserById(@PathVariable Long id);

}
