package org.example.membershipservice.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "memberships")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "duration_days")
    private int durationDays;

    @Column(name = "price")
    private double price;

    // Getters and setters
}

