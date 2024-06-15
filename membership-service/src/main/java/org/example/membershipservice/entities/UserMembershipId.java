package org.example.membershipservice.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class UserMembershipId implements Serializable {

    private Long userId;
    private Long membershipId;

    public UserMembershipId() {
    }

    public UserMembershipId(Long userId, Long membershipId) {
        this.userId = userId;
        this.membershipId = membershipId;
    }

    // Getters, setters, equals, and hashCode
}
