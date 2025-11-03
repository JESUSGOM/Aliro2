package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Telefonos")
public class Telefono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TelId")
    private Integer telId;

    @Column(name = "TelCentro")
    private Integer telCentro;

    @Column(name = "TelFecha")
    private String telFecha; // VARCHAR(8)

    @Column(name = "TelHora")
    private String telHora; // VARCHAR(6)

    @Column(name = "TelEmisor")
    private String telEmisor;

    @Column(name = "TelDestinatario")
    private String telDestinatario;

    @Column(name = "TelMensaje")
    private String telMensaje;

    @Column(name = "TelComunicado")
    private Integer telComunicado;

    @Column(name = "TelFechaEntrega")
    private String telFechaEntrega; // VARCHAR(8)

    @Column(name = "TelHoraEntrega")
    private String telHoraEntrega; // VARCHAR(6)
}