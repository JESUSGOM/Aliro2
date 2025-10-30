package com.aliro2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Centros")
public class Centro {

    @Id
    @Column(name = "CenId") // <-- Sé explícito
    private Long cenId;

    @Column(name = "CenDen")
    private String cenDen;

    @Column(name = "CenDireccion")
    private String cenDireccion;

    @Column(name = "CenCodigoPostal")
    private Integer cenCodigoPostal;

    @Column(name = "CenProvincia")
    private String cenProvincia;

    @Column(name = "CenTelefono")
    private String cenTelefono;

    @Column(name = "CenFax")
    private String cenFax;

    public Long getCenId() {
        return cenId;
    }

    public void setCenId(Long cenId) {
        this.cenId = cenId;
    }

    public String getCenDen() {
        return cenDen;
    }

    public void setCenDen(String cenDen) {
        this.cenDen = cenDen;
    }

    public String getCenDireccion() {
        return cenDireccion;
    }

    public void setCenDireccion(String cenDireccion) {
        this.cenDireccion = cenDireccion;
    }

    public Integer getCenCodigoPostal() {
        return cenCodigoPostal;
    }

    public void setCenCodigoPostal(Integer cenCodigoPostal) {
        this.cenCodigoPostal = cenCodigoPostal;
    }

    public String getCenProvincia() {
        return cenProvincia;
    }

    public void setCenProvincia(String cenProvincia) {
        this.cenProvincia = cenProvincia;
    }

    public String getCenTelefono() {
        return cenTelefono;
    }

    public void setCenTelefono(String cenTelefono) {
        this.cenTelefono = cenTelefono;
    }

    public String getCenFax() {
        return cenFax;
    }

    public void setCenFax(String cenFax) {
        this.cenFax = cenFax;
    }
}
