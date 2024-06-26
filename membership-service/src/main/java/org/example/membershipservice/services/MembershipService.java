package org.example.membershipservice.services;

import org.example.membershipservice.entities.Membership;
import org.example.membershipservice.repos.MembershipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;

    public MembershipService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

    public Membership getMembershipById(Long id) {
        return membershipRepository.findById(id).orElse(null);
    }

    public Membership saveMembership(Membership membership) {
        return membershipRepository.save(membership);
    }

    public void deleteMembership(Long id) {
        membershipRepository.deleteById(id);
    }
}
