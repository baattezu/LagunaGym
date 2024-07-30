package org.baattezu.membershipservice.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class UserMembership {

    @EmbeddedId
    private UserMembershipId id;

    @Column(name = "start_date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate startDate;

    @Column(name = "end_date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate endDate;

    @Column(name = "freeze_until")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate frozenUntil;

    @Column(name = "last_freeze")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate lastFreeze;

}
