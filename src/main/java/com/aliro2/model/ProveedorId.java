package com.aliro2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor; // <- AÑADIDA
import lombok.EqualsAndHashCode; // <- AÑADIDA
import lombok.NoArgsConstructor; // <- AÑADIDA

import java.io.Serializable;
import java.util.Objects;


@Embeddable
@NoArgsConstructor      // <- AÑADIDA (Constructor sin argumentos)
@AllArgsConstructor   // <- AÑADIDA (Constructor con todos los argumentos)
@EqualsAndHashCode    // <- AÑADIDA (Implementa equals y hashCode)
public class ProveedorId implements Serializable {
	
	
	

    @Override
	public int hashCode() {
		return Objects.hash(prdCentro, prdCif);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProveedorId other = (ProveedorId) obj;
		return Objects.equals(prdCentro, other.prdCentro) && Objects.equals(prdCif, other.prdCif);
	}

	public ProveedorId() {
		super();
	}

	public ProveedorId(Integer prdCentro) {
		super();
		this.prdCentro = prdCentro;
	}

	public ProveedorId(String prdCif) {
		super();
		this.prdCif = prdCif;
	}

	public ProveedorId(String prdCif, Integer prdCentro) {
		super();
		this.prdCif = prdCif;
		this.prdCentro = prdCentro;
	}

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