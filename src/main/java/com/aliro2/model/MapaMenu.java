package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "MapaMenu")
public class MapaMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MmId")
    private Integer mmId;

    @Column(name = "MmMnId")
    private Integer mmMnId;

    @Column(name = "MmUsuTipo")
    private String mmUsuTipo;

    @Column(name = "MmCentro")
    private Integer mmCentro;
}