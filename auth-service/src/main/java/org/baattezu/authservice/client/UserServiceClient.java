package org.baattezu.authservice.client;

import org.baattezu.authservice.dto.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping("/api/users")
    String saveUser(@RequestBody UserInfoDto user);

    @DeleteMapping("/api/users/{id}")
    void deleteUserById(@PathVariable Long id);

}
