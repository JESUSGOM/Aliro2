package com.aliro2.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Proveedores")
public class Proveedor {

    @EmbeddedId
    private ProveedorId id;

    @Column(name = "PrdDenominacion")
    private String prdDenominacion;

    @Column(name = "PrdContacto")
    private String prdContacto;

    @Column(name = "PrdTelefono")
    private String prdTelefono;

    @Column(name = "PrdEmail")
    private String prdEmail;

    @Column(name = "PrdDireccion", columnDefinition = "text")
    private String prdDireccion;

    @Column(name = "PrdProvincia")
    private String prdProvincia;

    @Column(name = "PrdMunicipio")
    private String prdMunicipio;

    @Column(name = "PrdPais")
    private String prdPais;

    @Column(name = "PrdCodigopostal")
    private String prdCodigopostal;

    @Column(name = "PrdWeb")
    private String prdWeb;

    @Column(name = "PrdNotas", columnDefinition = "text")
    private String prdNotas;

    @Column(name = "PrdFechaAlta")
    private LocalDate prdFechaAlta;

    @Column(name = "PrdFechaExpiracion")
    private LocalDate prdFechaExpiracion;

    public ProveedorId getId() {
        return id;
    }

    public void setId(ProveedorId id) {
        this.id = id;
    }

    public String getPrdDenominacion() {
        return prdDenominacion;
    }

    public void setPrdDenominacion(String prdDenominacion) {
        this.prdDenominacion = prdDenominacion;
    }

    public String getPrdContacto() {
        return prdContacto;
    }

    public void setPrdContacto(String prdContacto) {
        this.prdContacto = prdContacto;
    }

    public String getPrdTelefono() {
        return prdTelefono;
    }

    public void setPrdTelefono(String prdTelefono) {
        this.prdTelefono = prdTelefono;
    }

    public String getPrdEmail() {
        return prdEmail;
    }

    public void setPrdEmail(String prdEmail) {
        this.prdEmail = prdEmail;
    }

    public String getPrdDireccion() {
        return prdDireccion;
    }

    public void setPrdDireccion(String prdDireccion) {
        this.prdDireccion = prdDireccion;
    }

    public String getPrdProvincia() {
        return prdProvincia;
    }

    public void setPrdProvincia(String prdProvincia) {
        this.prdProvincia = prdProvincia;
    }

    public String getPrdMunicipio() {
        return prdMunicipio;
    }

    public void setPrdMunicipio(String prdMunicipio) {
        this.prdMunicipio = prdMunicipio;
    }

    public String getPrdPais() {
        return prdPais;
    }

    public void setPrdPais(String prdPais) {
        this.prdPais = prdPais;
    }

    public String getPrdCodigopostal() {
        return prdCodigopostal;
    }

    public void setPrdCodigopostal(String prdCodigopostal) {
        this.prdCodigopostal = prdCodigopostal;
    }

    public String getPrdWeb() {
        return prdWeb;
    }

    public void setPrdWeb(String prdWeb) {
        this.prdWeb = prdWeb;
    }

    public String getPrdNotas() {
        return prdNotas;
    }

    public void setPrdNotas(String prdNotas) {
        this.prdNotas = prdNotas;
    }

    public LocalDate getPrdFechaAlta() {
        return prdFechaAlta;
    }

    public void setPrdFechaAlta(LocalDate prdFechaAlta) {
        this.prdFechaAlta = prdFechaAlta;
    }

    public LocalDate getPrdFechaExpiracion() {
        return prdFechaExpiracion;
    }

    public void setPrdFechaExpiracion(LocalDate prdFechaExpiracion) {
        this.prdFechaExpiracion = prdFechaExpiracion;
    }
}
