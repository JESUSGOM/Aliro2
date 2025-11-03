package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Alquileres")
public class Alquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AlqId")
    private Integer alqId;

    @Column(name = "AlqCentro")
    private Integer alqCentro;

    @Column(name = "AlqEmpresa")
    private String alqEmpresa;
}
