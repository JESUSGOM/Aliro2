package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Incidencias")
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IncId")
    private Integer incId;

    @Column(name = "IncCentro")
    private Integer incCentro;

    @Column(name = "IncFecha")
    private String incFecha; // VARCHAR(8)

    @Column(name = "IncHora")
    private String incHora; // VARCHAR(6)

    @Column(name = "IncTexto")
    private String incTexto;

    @Column(name = "IncComunicadoA")
    private String incComunicadoA;

    @Column(name = "IncModoComunica")
    private String incModoComunica;

    @Column(name = "IncEmailComunica")
    private String incEmailComunica;

    @Column(name = "IncUsuario")
    private String incUsuario;

    public Integer getIncId() {
        return incId;
    }

    public void setIncId(Integer incId) {
        this.incId = incId;
    }

    public Integer getIncCentro() {
        return incCentro;
    }

    public void setIncCentro(Integer incCentro) {
        this.incCentro = incCentro;
    }

    public String getIncFecha() {
        return incFecha;
    }

    public void setIncFecha(String incFecha) {
        this.incFecha = incFecha;
    }

    public String getIncHora() {
        return incHora;
    }

    public void setIncHora(String incHora) {
        this.incHora = incHora;
    }

    public String getIncTexto() {
        return incTexto;
    }

    public void setIncTexto(String incTexto) {
        this.incTexto = incTexto;
    }

    public String getIncComunicadoA() {
        return incComunicadoA;
    }

    public void setIncComunicadoA(String incComunicadoA) {
        this.incComunicadoA = incComunicadoA;
    }

    public String getIncModoComunica() {
        return incModoComunica;
    }

    public void setIncModoComunica(String incModoComunica) {
        this.incModoComunica = incModoComunica;
    }

    public String getIncEmailComunica() {
        return incEmailComunica;
    }

    public void setIncEmailComunica(String incEmailComunica) {
        this.incEmailComunica = incEmailComunica;
    }

    public String getIncUsuario() {
        return incUsuario;
    }

    public void setIncUsuario(String incUsuario) {
        this.incUsuario = incUsuario;
    }
}