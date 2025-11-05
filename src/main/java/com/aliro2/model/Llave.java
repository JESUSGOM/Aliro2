package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Llaves")
public class Llave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LlvOrden")
    private Integer llvOrden;

    @Column(name = "LlvCodigo")
    private String llvCodigo;

    @Column(name = "LlvCentro")
    private Integer llvCentro;

    @Column(name = "LlvPuerta")
    private String llvPuerta;

    @Column(name = "LlvPlanta")
    private String llvPlanta;

    @Column(name = "LlvCajetin")
    private Integer llvCajetin;

    @Column(name = "LlvRestriccion")
    private String llvRestriccion;

    public Integer getLlvOrden() {
        return llvOrden;
    }

    public void setLlvOrden(Integer llvOrden) {
        this.llvOrden = llvOrden;
    }

    public String getLlvCodigo() {
        return llvCodigo;
    }

    public void setLlvCodigo(String llvCodigo) {
        this.llvCodigo = llvCodigo;
    }

    public Integer getLlvCentro() {
        return llvCentro;
    }

    public void setLlvCentro(Integer llvCentro) {
        this.llvCentro = llvCentro;
    }

    public String getLlvPuerta() {
        return llvPuerta;
    }

    public void setLlvPuerta(String llvPuerta) {
        this.llvPuerta = llvPuerta;
    }

    public String getLlvPlanta() {
        return llvPlanta;
    }

    public void setLlvPlanta(String llvPlanta) {
        this.llvPlanta = llvPlanta;
    }

    public Integer getLlvCajetin() {
        return llvCajetin;
    }

    public void setLlvCajetin(Integer llvCajetin) {
        this.llvCajetin = llvCajetin;
    }

    public String getLlvRestriccion() {
        return llvRestriccion;
    }

    public void setLlvRestriccion(String llvRestriccion) {
        this.llvRestriccion = llvRestriccion;
    }
}