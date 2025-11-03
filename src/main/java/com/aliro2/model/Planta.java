package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Plantas")
public class Planta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PltId")
    private Integer pltId;

    @Column(name = "PltCentro")
    private Integer pltCentro;

    @Column(name = "PltPlanta")
    private String pltPlanta;
}