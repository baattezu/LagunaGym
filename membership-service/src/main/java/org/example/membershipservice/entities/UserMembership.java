package org.example.membershipservice.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class UserMembership {

    @EmbeddedId
    private UserMembershipId id;

    private LocalDate startDate;
    private LocalDate endDate;

    // Getters and setters
}
