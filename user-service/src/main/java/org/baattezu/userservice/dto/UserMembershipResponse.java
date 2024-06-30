package org.baattezu.userservice.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class UserMembershipResponse {

    private String name;
    private LocalDate endDate;
    private int daysLeft;
    private boolean isFrozen;
    private LocalDate frozenUntilDate;

}
