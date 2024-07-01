package org.baattezu.userservice.client;

import org.baattezu.userservice.dto.request.MembershipRequest;
import org.baattezu.userservice.dto.response.UserMembershipResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "membership-service")
public interface MembershipClient {

    @PostMapping("/api/memberships/users/{id}")
    Long addMembershipToUser(@PathVariable Long id, @RequestBody MembershipRequest request);

    @GetMapping("/api/memberships/users/{id}")
    UserMembershipResponse getUserMembership(@PathVariable Long id);

    @DeleteMapping("/api/memberships/users/{id}")
    void deleteUserMembership(@PathVariable Long id);
}
