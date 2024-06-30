package org.example.membershipservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMembershipResponse {

    private String name;
    private String endDate;
    private String remainingDays;
    private String frozenUntilDate;

    /*
    Eng: I thought frozenUntilDate field needs to be included in JSON,
    because front-end developer could use it in his DTO and check if its null
    Rus: Я думал сделать этот филд не включенным в JSON ,
    т.к фронтендер может добавить это в свой дто на проверку если это ноль или нет
    */

}
