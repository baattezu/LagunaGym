package org.baattezu.membershipservice.repository;


import org.baattezu.membershipservice.entities.UserMembership;
import org.baattezu.membershipservice.entities.UserMembershipId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMembershipRepository extends JpaRepository<UserMembership, UserMembershipId> {
    Optional<UserMembership> findUserMembershipById_UserId(Long userId);
    void deleteUserMembershipById_UserId(Long userId);
}
