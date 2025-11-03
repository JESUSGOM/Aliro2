package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "KeyMoves")
public class KeyMove {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KeyOrden")
    private Integer keyOrden;

    @Column(name = "KeyLlvOrden")
    private String keyLlvOrden;

    @Column(name = "KeyCentro")
    private Integer keyCentro;

    @Column(name = "KeyFechaEntrega")
    private String keyFechaEntrega; // VARCHAR(8)

    @Column(name = "KeyHoraEntrega")
    private String keyHoraEntrega; // VARCHAR(6)

    @Column(name = "KeyNombre")
    private String keyNombre;

    @Column(name = "KeyApellidoUno")
    private String keyApellidoUno;

    @Column(name = "KeyApellidoDos")
    private String keyApellidoDos;

    @Column(name = "KeyFechaRecepcion")
    private String keyFechaRecepcion; // VARCHAR(8)

    @Column(name = "KeyHoraRecepcion")
    private String keyHoraRecepcion; // VARCHAR(6)
}