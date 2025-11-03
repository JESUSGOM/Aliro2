package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Incidencias")
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IncId")
    private Integer incId;

    @Column(name = "IncCentro")
    private Integer incCentro;

    @Column(name = "IncFecha")
    private String incFecha; // VARCHAR(8)

    @Column(name = "IncHora")
    private String incHora; // VARCHAR(6)

    @Column(name = "IncTexto")
    private String incTexto;

    @Column(name = "IncComunicadoA")
    private String incComunicadoA;

    @Column(name = "IncModoComunica")
    private String incModoComunica;

    @Column(name = "IncEmailComunica")
    private String incEmailComunica;

    @Column(name = "IncUsuario")
    private String incUsuario;
}