package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Paqueteria")
public class Paquete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PktId")
    private Integer pktId;

    @Column(name = "PktCentro")
    private Integer pktCentro;

    @Column(name = "PktFecha")
    private String pktFecha; // VARCHAR(8)

    @Column(name = "PktHora")
    private String pktHora; // VARCHAR(6)

    @Column(name = "PktEmisor")
    private String pktEmisor;

    @Column(name = "PktDestinatario")
    private String pktDestinatario;

    @Column(name = "PktMensajeria")
    private String pktMensajeria;

    @Column(name = "PktBultos")
    private Integer pktBultos;

    @Column(name = "PktTipo")
    private String pktTipo;

    @Column(name = "PktComunicado")
    private String pktComunicado;

    @Column(name = "PktTipoComunicado")
    private String pktTipoComunicado;

    @Column(name = "PktFechaComunicacion")
    private String pktFechaComunicacion; // VARCHAR(8)

    @Column(name = "PktHoraComunicacion")
    private String pktHoraComunicacion; // VARCHAR(6)

    @Column(name = "PktOperario")
    private String pktOperario;

    @Column(name = "PktOperarioComunica")
    private String pktOperarioComunica;
}