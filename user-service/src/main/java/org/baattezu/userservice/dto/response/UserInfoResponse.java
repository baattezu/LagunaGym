package org.baattezu.userservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthDate;
    private UserMembershipResponse membership;
}
