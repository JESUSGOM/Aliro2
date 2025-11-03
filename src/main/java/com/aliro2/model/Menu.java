package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MnId")
    private Integer mnId;

    @Column(name = "MnNombre")
    private String mnNombre;

    @Column(name = "MnParentId")
    private Integer mnParentId;

    @Column(name = "MnUrl")
    private String mnUrl;

    @Column(name = "EstadoMenu")
    private Integer estadoMenu;

    @Column(name = "MnSvg")
    private String mnSvg;
}