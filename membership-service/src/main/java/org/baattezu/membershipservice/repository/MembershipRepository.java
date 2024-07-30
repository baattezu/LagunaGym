package org.baattezu.membershipservice.repository;

import org.baattezu.membershipservice.entities.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Optional<Membership> findMembershipById(Long id);
}
