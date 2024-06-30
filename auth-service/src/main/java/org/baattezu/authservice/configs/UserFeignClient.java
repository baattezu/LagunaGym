package org.baattezu.authservice.configs;

import org.baattezu.authservice.model.User;
import org.baattezu.authservice.model.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @PostMapping("/api/users")
    String saveUser(@RequestBody UserInfoDto user);

}
