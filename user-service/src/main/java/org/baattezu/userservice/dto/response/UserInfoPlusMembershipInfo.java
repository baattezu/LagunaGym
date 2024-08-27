package org.baattezu.userservice.dto.response;

import java.time.LocalDate;

public record UserInfoPlusMembershipInfo(
        Long userId,
        String email,
        String phoneNumber,
        LocalDate endDate,
        Boolean isFrozen
) {

}
