package org.example.membershipservice.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Membership {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private Integer days;
    private Double price;
}
