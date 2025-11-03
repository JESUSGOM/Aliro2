package com.aliro2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "CCAA")
public class CCAA {

    @Id
    @Column(name = "idCCAA")
    private Integer idCCAA; // Es tinyint, pero Integer funciona bien

    @Column(name = "Nombre")
    private String nombre;
}
