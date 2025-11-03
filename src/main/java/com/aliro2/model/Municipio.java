package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Municipios")
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMunicipio")
    private Integer idMunicipio;

    @Column(name = "idProvincia")
    private Integer idProvincia;

    @Column(name = "codMunicipio")
    private Integer codMunicipio;

    @Column(name = "DC")
    private Integer dc;

    @Column(name = "Municipio")
    private String municipio;
}