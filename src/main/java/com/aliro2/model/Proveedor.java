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
}
