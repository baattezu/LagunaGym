package org.baattezu.membershipservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FreezeRequest {

    @JsonFormat(pattern="dd.MM.yyyy")
    private LocalDate freezeUntil;

}
