package org.baattezu.membershipservice.controller.manager;


import jakarta.validation.Valid;
import org.baattezu.membershipservice.entities.Membership;
import org.baattezu.membershipservice.service.MembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
@Validated
public class MembershipCrudController {

    private final MembershipService membershipService;

    public MembershipCrudController(MembershipService membershipService) {
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
    public ResponseEntity<Membership> createMembership(@Valid @RequestBody Membership membership) {
        Membership savedMembership = membershipService.saveMembership(membership);
        return ResponseEntity.ok(savedMembership);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Membership> updateMembership(@PathVariable Long id, @Valid @RequestBody Membership membership) {
        return ResponseEntity.ok(membershipService.updateMembership(id, membership));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMembership(@PathVariable Long id) {
        membershipService.deleteMembership(id);
        return ResponseEntity.ok(String.format("Membership is deleted with id: %d", id));
    }
}