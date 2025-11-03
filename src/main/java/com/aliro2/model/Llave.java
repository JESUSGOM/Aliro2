package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Llaves")
public class Llave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LlvOrden")
    private Integer llvOrden;

    @Column(name = "LlvCodigo")
    private String llvCodigo;

    @Column(name = "LlvCentro")
    private Integer llvCentro;

    @Column(name = "LlvPuerta")
    private String llvPuerta;

    @Column(name = "LlvPlanta")
    private String llvPlanta;

    @Column(name = "LlvCajetin")
    private Integer llvCajetin;

    @Column(name = "LlvRestriccion")
    private String llvRestriccion;
}