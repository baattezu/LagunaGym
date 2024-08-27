package org.baattezu.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class UserMembershipWithId {
    private Long userId;
    private LocalDate endDate;
    private Boolean isFrozen;

    @Override
    public String toString() {
        return "UserMembershipWithId{" +
                "userId=" + userId +
                ", endDate=" + endDate +
                ", isFrozen=" + isFrozen +
                '}';
    }
}
