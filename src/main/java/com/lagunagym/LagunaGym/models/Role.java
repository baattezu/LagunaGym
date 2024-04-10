package com.lagunagym.LagunaGym.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "role_title")
    private String role_title;


}
