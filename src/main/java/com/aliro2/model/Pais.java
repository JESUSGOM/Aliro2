package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Paises")
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaisId")
    private Integer paisId;

    @Column(name = "PaisIso")
    private String paisIso;

    @Column(name = "PaisNombre")
    private String paisNombre;
}