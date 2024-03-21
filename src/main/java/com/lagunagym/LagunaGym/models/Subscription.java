package com.lagunagym.LagunaGym.models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    private double price;

}