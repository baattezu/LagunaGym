package org.baattezu.membershipservice.service;

import org.baattezu.membershipservice.entities.Membership;
import org.baattezu.membershipservice.exception.NotFoundException;
import org.baattezu.membershipservice.repository.MembershipRepository;
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
        return membershipRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Membership not found with id: %d", id))
        );
    }

    public Membership saveMembership(Membership membership) {
        return membershipRepository.save(membership);
    }

    public void deleteMembership(Long id) {
        membershipRepository.deleteById(id);
    }

    public Membership updateMembership(Long id, Membership membership) {
        Membership updMembership = getMembershipById(id);
        updMembership.setName(membership.getName());
        updMembership.setDescription(membership.getDescription());
        updMembership.setPrice(membership.getPrice());
        updMembership.setMonths(membership.getMonths());
        return membershipRepository.save(membership);
    }
}
