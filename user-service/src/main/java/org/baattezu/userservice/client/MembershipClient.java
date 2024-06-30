package org.baattezu.userservice.client;

import org.baattezu.userservice.dto.MembershipRequest;
import org.baattezu.userservice.dto.UserMembershipResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "membership-service")
public interface MembershipClient {

    @PostMapping("/api/memberships/users/{id}")
    void addMembershipToUser(@PathVariable Long id, @RequestBody MembershipRequest request);

    @GetMapping("/api/memberships/users/{id}")
    UserMembershipResponse getUserMembership(@PathVariable Long id);

}
