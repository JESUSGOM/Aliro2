package com.aliro2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor; // <- AÑADIR
import lombok.Data;
import lombok.EqualsAndHashCode; // <- AÑADIR
import lombok.NoArgsConstructor; // <- AÑADIR

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor      // <- AÑADIR (Constructor sin argumentos)
@AllArgsConstructor   // <- AÑADIR (Constructor con todos los argumentos)
@EqualsAndHashCode    // <- AÑADIR (Implementa equals y hashCode)
public class ProveedorId implements Serializable {

    @Column(name = "PrdCif")
    private String prdCif;

    @Column(name = "PrdCentro")
    private Integer prdCentro;
}