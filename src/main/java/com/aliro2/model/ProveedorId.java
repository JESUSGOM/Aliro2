package com.aliro2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor; // <- AÑADIDA
import lombok.Data;
import lombok.EqualsAndHashCode; // <- AÑADIDA
import lombok.NoArgsConstructor; // <- AÑADIDA

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor      // <- AÑADIDA (Constructor sin argumentos)
@AllArgsConstructor   // <- AÑADIDA (Constructor con todos los argumentos)
@EqualsAndHashCode    // <- AÑADIDA (Implementa equals y hashCode)
public class ProveedorId implements Serializable {

    @Column(name = "PrdCif")
    private String prdCif;

    @Column(name = "PrdCentro")
    private Integer prdCentro;

    public String getPrdCif() {
        return prdCif;
    }

    public void setPrdCif(String prdCif) {
        this.prdCif = prdCif;
    }

    public Integer getPrdCentro() {
        return prdCentro;
    }

    public void setPrdCentro(Integer prdCentro) {
        this.prdCentro = prdCentro;
    }
}