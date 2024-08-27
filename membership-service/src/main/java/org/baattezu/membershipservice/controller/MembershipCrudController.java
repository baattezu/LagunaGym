package org.baattezu.membershipservice.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baattezu.membershipservice.entities.Membership;
import org.baattezu.membershipservice.service.MembershipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/memberships")
@Validated
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Membership CRUD", description = "The Membership CRUD. Contains operations like creating, getting, updating, deleting memberships")
public class MembershipCrudController {

    private static final Logger logger = LoggerFactory.getLogger(MembershipCrudController.class);
    private final MembershipService membershipService;

    @GetMapping
    public ResponseEntity<List<Membership>> getAllMemberships(Authentication authentication) {
        logger.info(authentication.getAuthorities().toString());
        List<Membership> memberships = membershipService.getAllMemberships();
        return ResponseEntity.ok(memberships);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Membership> getMembershipById(@PathVariable Long id) {
        Membership membership = membershipService.getMembershipById(id);
        if (membership != null) {
            return ResponseEntity.ok(membership);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Membership> createMembership(@Valid @RequestBody Membership membership) {
        Membership savedMembership = membershipService.saveMembership(membership);
        return ResponseEntity.ok(savedMembership);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Membership> updateMembership(@PathVariable Long id, @Valid @RequestBody Membership membership) {
        return ResponseEntity.ok(membershipService.updateMembership(id, membership));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteMembership(@PathVariable Long id) {
        membershipService.deleteMembership(id);
        return ResponseEntity.ok(String.format("Membership is deleted with id: %d", id));
    }
}