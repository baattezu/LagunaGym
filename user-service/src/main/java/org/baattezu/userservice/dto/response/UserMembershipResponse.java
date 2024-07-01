package org.baattezu.userservice.dto.response;


import lombok.Data;

@Data
public class UserMembershipResponse {

    private String name;
    private String endDate;
    private String remainingDays;
    private String frozenUntilDate;

}
