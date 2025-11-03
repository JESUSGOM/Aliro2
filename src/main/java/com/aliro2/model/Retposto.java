package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Retposto")
public class Retposto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RptId")
    private Integer rptId;

    @Column(name = "RptCentro")
    private Integer rptCentro;

    @Column(name = "RptNombre")
    private String rptNombre;

    @Column(name = "RptApellidoUno")
    private String rptApellidoUno;

    @Column(name = "RptApellidoDos")
    private String rptApellidoDos;

    @Column(name = "RptEmail")
    private String rptEmail;
}