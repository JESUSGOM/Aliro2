package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "EnvioEmail")
public class EnvioEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EnEmId")
    private Integer enEmId;

    @Column(name = "EnEmDestinatario")
    private String enEmDestinatario;

    @Column(name = "EnEmFecha")
    private String enEmFecha; // VARCHAR(8)

    @Column(name = "EnEmHora")
    private String enEmHora; // VARCHAR(6)

    @Column(name = "EnEmTexto", columnDefinition = "longtext")
    private String enEmTexto;

    @Column(name = "EnEmEmisor")
    private String enEmEmisor;
}
