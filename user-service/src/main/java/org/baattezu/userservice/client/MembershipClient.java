package org.baattezu.userservice.client;

import org.baattezu.userservice.dto.request.MembershipRequest;
import org.baattezu.userservice.dto.response.UserMembershipResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "membership-service")
public interface MembershipClient {

    @GetMapping("/api/memberships/users/{id}")
    UserMembershipResponse getUserMembership(@PathVariable Long id);

    @DeleteMapping("/api/memberships/users/{id}")
    ResponseEntity<Void> deleteUserMembership(@PathVariable Long id);
}
