package org.baattezu.userservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;


    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthDate;

    @Column(name = "membership_id")
    private Long membershipId;


    public UserInfo(String email){
        this.email = email;
    }
    public UserInfo (){
    }
}