package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Garaje")
public class Garaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GrjId")
    private Integer grjId;

    @Column(name = "GrjFecha")
    private String grjFecha; // VARCHAR(8)

    @Column(name = "GrjNombre")
    private String grjNombre;

    @Column(name = "GrjEmpresa")
    private String grjEmpresa;

    @Column(name = "GrjMarca")
    private String grjMarca;

    @Column(name = "GrjModelo")
    private String grjModelo;

    @Column(name = "GrjColor")
    private String grjColor;

    @Column(name = "GrjMatricula")
    private String grjMatricula;
}