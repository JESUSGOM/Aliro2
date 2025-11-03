package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "EntreTurnos")
public class EntreTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EntId")
    private Integer entId;

    @Column(name = "EntCentro")
    private Integer entCentro;

    @Column(name = "EntOperario")
    private String entOperario;

    @Column(name = "EntFescrito")
    private String entFescrito; // VARCHAR(8)

    @Column(name = "EntHescrito")
    private String entHescrito; // VARCHAR(6)

    @Column(name = "EntUsuario")
    private String entUsuario;

    @Column(name = "EntTexto")
    private String entTexto;

    @Column(name = "EntFleido")
    private String entFleido; // VARCHAR(8)

    @Column(name = "EntHleido")
    private String entHleido; // VARCHAR(6)
}
