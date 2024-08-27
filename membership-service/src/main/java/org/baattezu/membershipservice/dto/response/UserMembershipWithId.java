package org.baattezu.membershipservice.dto.response;

import java.time.LocalDate;

public record UserMembershipWithId(
        Long userId,
        LocalDate endDate,
        Boolean isFrozen
) { }
