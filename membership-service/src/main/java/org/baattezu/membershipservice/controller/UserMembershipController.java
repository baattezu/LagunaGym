package org.baattezu.membershipservice.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.baattezu.membershipservice.configs.MyAuthenticationToken;
import org.baattezu.membershipservice.dto.request.FreezeRequest;
import org.baattezu.membershipservice.dto.response.UserMembershipWithId;
import org.baattezu.membershipservice.entities.UserMembership;
import org.baattezu.membershipservice.service.UserMembershipService;
import org.baattezu.membershipservice.dto.request.MembershipRequest;
import org.baattezu.membershipservice.dto.response.UserMembershipResponse;
import org.slf4j.ILoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(
    name = "UserMembership",
    description = "The UserMembership API. Contains operations like " +
    "adding memberships to user, " +
    "getting information about user's membership, " +
    "freezing and unfreezing user's membership, " +
    "deleting user's membership"
)
public class UserMembershipController {

    private final UserMembershipService userMembershipService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @Hidden
    public ResponseEntity<List<UserMembershipWithId>> getUsersMemberships(){
        return ResponseEntity.ok(userMembershipService.getUsersMemberships());
    }
    @PostMapping("/{id}")
    @PreAuthorize("@userMembershipController.hasPermission(#id)")
    @Operation(summary = "Add membership to user")
    public ResponseEntity<UserMembership> addMembershipToUser(
            @PathVariable Long id,
            @RequestBody MembershipRequest request
        ){
        return ResponseEntity.ok(
            userMembershipService.addMembershipToUser(
                id, request.getMembershipId()
            )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("@userMembershipController.hasPermission(#id)")
    @Operation(summary = "Get user's membership")
    public ResponseEntity<UserMembershipResponse> getUserMembership(@PathVariable Long id){
        return ResponseEntity.ok(userMembershipService.getUserMembershipInfoResponse(id));
    }
    @PostMapping("/{id}/freeze")
    @PreAuthorize("@userMembershipController.hasPermission(#id)")
    @Operation(summary = "Freeze user's membership")
    public ResponseEntity<UserMembership> freezeMembership(
            @PathVariable Long id,
            @RequestBody FreezeRequest request
    ){
        return ResponseEntity.ok(
                userMembershipService.freezeMembership(id, request.getFreezeUntil())
        );
    }
    @DeleteMapping("/{id}/freeze")
    @PreAuthorize("@userMembershipController.hasPermission(#id)")
    @Operation(summary = "Unfreeze user's membership")
    public ResponseEntity<UserMembership> unfreezeMembership(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(
                userMembershipService.unfreezeMembership(id)
        );
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("@userMembershipController.hasPermission(#id)")
    @Operation(summary = "Delete user's membership")
    public ResponseEntity<Void> deleteUserMembership(
            @PathVariable Long id
    ){
        userMembershipService.deleteUserMembership(id);
        return ResponseEntity.ok().build();
    }

    public Boolean hasPermission(Long userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdminOrManager = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority ->
                        grantedAuthority.getAuthority().equals("ROLE_ADMIN") ||
                        grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
        boolean isSameUser = authentication instanceof MyAuthenticationToken currentToken &&
                userId.equals(currentToken.getUserId());

        if (isSameUser) {
            return true;
        } else if (!isAdminOrManager) {
            throw new AccessDeniedException("You must have ADMIN or MANAGER role to perform this action");
        }
        return true;
    }

}
