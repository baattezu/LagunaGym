package org.example.membershipservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.membershipservice.dto.request.FreezeRequest;
import org.example.membershipservice.dto.request.MembershipRequest;
import org.example.membershipservice.dto.response.UserMembershipResponse;
import org.example.membershipservice.entities.UserMembership;
import org.example.membershipservice.service.UserMembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/memberships/users")
@RequiredArgsConstructor
public class UserMembershipController {

    private final UserMembershipService userMembershipService;

    @PostMapping("/{id}")
    public ResponseEntity<UserMembership> addMembershipToUser(
            @PathVariable("id") Long userId,
            @RequestBody MembershipRequest request
        ){
        return ResponseEntity.ok(
            userMembershipService.addMembershipToUser(
                userId, request.getMembershipId()
            )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserMembershipResponse> getUserMembership(@PathVariable Long id){
        return ResponseEntity.ok(userMembershipService.getUserMembershipInfoResponse(id));
    }
    @PostMapping("/{id}/freeze")
    public ResponseEntity<UserMembership> freezeMembership(
            @PathVariable("id") Long userId,
            @RequestBody FreezeRequest request
    ){
        return ResponseEntity.ok(
                userMembershipService.freezeMembership(userId, request.getFreezeUntil())
        );
    }
    @DeleteMapping("/{id}/freeze")
    public ResponseEntity<UserMembership> unfreezeMembership(
            @PathVariable("id") Long userId
    ){
        return ResponseEntity.ok(
                userMembershipService.unfreezeMembership(userId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserMembership(
            @PathVariable Long id
    ){
        userMembershipService.deleteUserMembership(id);
        return ResponseEntity.noContent().build();
    }

}
