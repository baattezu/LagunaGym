package org.baattezu.membershipservice.dto;

import lombok.Data;

@Data
public class UserInfoDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;
    private java.util.Date birthDate;

    private Long membershipId;
}
