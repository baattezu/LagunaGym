package org.baattezu.membershipservice.client;

import org.baattezu.membershipservice.configs.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", configuration = FeignConfig.class)
public interface UserServiceClient {

    @GetMapping("/api/users/{id}/exists")
    boolean checkUserExists(@PathVariable Long id);

    @GetMapping("/api/users/{id}/email")
    String getEmail(@PathVariable Long id);
}
