package com.lagunagym.LagunaGym.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String f_name;
    private String l_name;

    private String email;
    private String password;
    public Person(Long id){
        this.id = id;
    }
}