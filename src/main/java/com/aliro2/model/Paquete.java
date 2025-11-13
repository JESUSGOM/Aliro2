package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime; // <- IMPORTADO

@Data
@Entity
@Table(name = "Paqueteria")
public class Paquete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PktId")
    private Integer pktId;

    @Column(name = "PktCentro")
    private Integer pktCentro;

    @Column(name = "PktFecha")
    private String pktFecha; // VARCHAR(8)

    @Column(name = "PktHora")
    private String pktHora; // VARCHAR(6)

    @Column(name = "PktEmisor")
    private String pktEmisor;

    @Column(name = "PktDestinatario")
    private String pktDestinatario;

    @Column(name = "PktMensajeria")
    private String pktMensajeria;

    @Column(name = "PktBultos")
    private Integer pktBultos;

    @Column(name = "PktTipo")
    private String pktTipo;

    @Column(name = "PktComunicado")
    private String pktComunicado;

    @Column(name = "PktTipoComunicado")
    private String pktTipoComunicado;

    @Column(name = "PktFechaComunicacion")
    private String pktFechaComunicacion; // VARCHAR(8)

    @Column(name = "PktHoraComunicacion")
    private String pktHoraComunicacion; // VARCHAR(6)

    @Column(name = "PktOperario")
    private String pktOperario;

    @Column(name = "PktOperarioComunica")
    private String pktOperarioComunica;

    // --- NUEVOS CAMPOS (DATETIME) ---
    @Column(name = "PktFechaHoraRecepcion_dt")
    private LocalDateTime pktFechaHoraRecepcionDt;

    @Column(name = "PktFechaHoraComunica_dt")
    private LocalDateTime pktFechaHoraComunicaDt;

    public Integer getPktId() {
        return pktId;
    }

    public void setPktId(Integer pktId) {
        this.pktId = pktId;
    }

    public Integer getPktCentro() {
        return pktCentro;
    }

    public void setPktCentro(Integer pktCentro) {
        this.pktCentro = pktCentro;
    }

    public String getPktFecha() {
        return pktFecha;
    }

    public void setPktFecha(String pktFecha) {
        this.pktFecha = pktFecha;
    }

    public String getPktHora() {
        return pktHora;
    }

    public void setPktHora(String pktHora) {
        this.pktHora = pktHora;
    }

    public String getPktEmisor() {
        return pktEmisor;
    }

    public void setPktEmisor(String pktEmisor) {
        this.pktEmisor = pktEmisor;
    }

    public String getPktDestinatario() {
        return pktDestinatario;
    }

    public void setPktDestinatario(String pktDestinatario) {
        this.pktDestinatario = pktDestinatario;
    }

    public String getPktMensajeria() {
        return pktMensajeria;
    }

    public void setPktMensajeria(String pktMensajeria) {
        this.pktMensajeria = pktMensajeria;
    }

    public Integer getPktBultos() {
        return pktBultos;
    }

    public void setPktBultos(Integer pktBultos) {
        this.pktBultos = pktBultos;
    }

    public String getPktTipo() {
        return pktTipo;
    }

    public void setPktTipo(String pktTipo) {
        this.pktTipo = pktTipo;
    }

    public String getPktComunicado() {
        return pktComunicado;
    }

    public void setPktComunicado(String pktComunicado) {
        this.pktComunicado = pktComunicado;
    }

    public String getPktTipoComunicado() {
        return pktTipoComunicado;
    }

    public void setPktTipoComunicado(String pktTipoComunicado) {
        this.pktTipoComunicado = pktTipoComunicado;
    }

    public String getPktFechaComunicacion() {
        return pktFechaComunicacion;
    }

    public void setPktFechaComunicacion(String pktFechaComunicacion) {
        this.pktFechaComunicacion = pktFechaComunicacion;
    }

    public String getPktHoraComunicacion() {
        return pktHoraComunicacion;
    }

    public void setPktHoraComunicacion(String pktHoraComunicacion) {
        this.pktHoraComunicacion = pktHoraComunicacion;
    }

    public String getPktOperario() {
        return pktOperario;
    }

    public void setPktOperario(String pktOperario) {
        this.pktOperario = pktOperario;
    }

    public String getPktOperarioComunica() {
        return pktOperarioComunica;
    }

    public void setPktOperarioComunica(String pktOperarioComunica) {
        this.pktOperarioComunica = pktOperarioComunica;
    }

    public LocalDateTime getPktFechaHoraRecepcionDt() {
        return pktFechaHoraRecepcionDt;
    }

    public void setPktFechaHoraRecepcionDt(LocalDateTime pktFechaHoraRecepcionDt) {
        this.pktFechaHoraRecepcionDt = pktFechaHoraRecepcionDt;
    }

    public LocalDateTime getPktFechaHoraComunicaDt() {
        return pktFechaHoraComunicaDt;
    }

    public void setPktFechaHoraComunicaDt(LocalDateTime pktFechaHoraComunicaDt) {
        this.pktFechaHoraComunicaDt = pktFechaHoraComunicaDt;
    }
}