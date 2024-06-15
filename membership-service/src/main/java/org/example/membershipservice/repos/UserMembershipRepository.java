package org.example.membershipservice.repos;


import org.example.membershipservice.entities.UserMembership;
import org.example.membershipservice.entities.UserMembershipId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMembershipRepository extends JpaRepository<UserMembership, UserMembershipId> {
}
