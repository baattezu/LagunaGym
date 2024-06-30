package org.example.membershipservice.repository;

import org.example.membershipservice.entities.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Optional<Membership> findMembershipById(Long id);
}
