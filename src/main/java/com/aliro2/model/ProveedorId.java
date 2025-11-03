package com.aliro2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class ProveedorId implements Serializable {

    @Column(name = "PrdCif")
    private String prdCif;

    @Column(name = "PrdCentro")
    private Integer prdCentro;
}
