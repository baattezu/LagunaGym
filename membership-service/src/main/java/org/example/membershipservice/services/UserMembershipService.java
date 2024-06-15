package org.example.membershipservice.services;


import org.example.membershipservice.entities.UserMembership;
import org.example.membershipservice.entities.UserMembershipId;
import org.example.membershipservice.repos.UserMembershipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMembershipService {

    private final UserMembershipRepository userMembershipRepository;

    public UserMembershipService(UserMembershipRepository userMembershipRepository) {
        this.userMembershipRepository = userMembershipRepository;
    }

    public List<UserMembership> getAllUserMemberships() {
        return userMembershipRepository.findAll();
    }

    public UserMembership getUserMembership(UserMembershipId id) {
        return userMembershipRepository.findById(id).orElse(null);
    }

    public UserMembership saveUserMembership(UserMembership userMembership) {
        return userMembershipRepository.save(userMembership);
    }

    public void deleteUserMembership(UserMembershipId id) {
        userMembershipRepository.deleteById(id);
    }
}
