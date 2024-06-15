package org.example.membershipservice.controllers;


import org.example.membershipservice.entities.Membership;
import org.example.membershipservice.services.MembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @GetMapping
    public ResponseEntity<List<Membership>> getAllMemberships() {
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
    public ResponseEntity<Membership> createMembership(@RequestBody Membership membership) {
        Membership savedMembership = membershipService.saveMembership(membership);
        return ResponseEntity.ok(savedMembership);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Membership> updateMembership(@PathVariable Long id, @RequestBody Membership membership) {
        Membership existingMembership = membershipService.getMembershipById(id);
        if (existingMembership != null) {
            membership.setId(id);
            Membership updatedMembership = membershipService.saveMembership(membership);
            return ResponseEntity.ok(updatedMembership);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembership(@PathVariable Long id) {
        Membership existingMembership = membershipService.getMembershipById(id);
        if (existingMembership != null) {
            membershipService.deleteMembership(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}