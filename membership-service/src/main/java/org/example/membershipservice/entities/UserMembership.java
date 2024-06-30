package org.example.membershipservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class UserMembership {

    @EmbeddedId
    private UserMembershipId id;

    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "freeze_until")
    private LocalDate frozenUntil;
    @Column(name = "last_freeze")
    private LocalDate lastFreeze;

}
