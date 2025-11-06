package com.aliro2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


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

	public Integer getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}

	public Integer getIdCCAA() {
		return idCCAA;
	}

	public void setIdCCAA(Integer idCCAA) {
		this.idCCAA = idCCAA;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
    
    
}