package org.example.membershipservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
