package com.aliro2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Provincias")
public class Provincia {

    @Id
    @Column(name = "idProvincia")
    private Integer idProvincia;

    @Column(name = "idCCAA")
    private Integer idCCAA;

    @Column(name = "Provincia")
    private String provincia;
}