package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Mezua")
public class Mezua {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MezId")
    private Integer mezId;

    @Column(name = "MezEmail")
    private String mezEmail;

    @Column(name = "MezNombre")
    private String mezNombre;

    @Column(name = "MezApellidos")
    private String mezApellidos;
}