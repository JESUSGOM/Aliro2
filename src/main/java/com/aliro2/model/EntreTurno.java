package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "EntreTurnos")
public class EntreTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EntId")
    private Integer entId;

    @Column(name = "EntCentro")
    private Integer entCentro;

    @Column(name = "EntOperario")
    private String entOperario;

    @Column(name = "EntFescrito")
    private String entFescrito; // VARCHAR(8)

    @Column(name = "EntHescrito")
    private String entHescrito; // VARCHAR(6)

    @Column(name = "EntUsuario")
    private String entUsuario;

    @Column(name = "EntTexto")
    private String entTexto;

    @Column(name = "EntFleido")
    private String entFleido; // VARCHAR(8)

    @Column(name = "EntHleido")
    private String entHleido; // VARCHAR(6)

    // --- NUEVOS CAMPOS (DATETIME) ---
    @Column(name = "EntFechaHoraEscrito_dt")
    private LocalDateTime entFechaHoraEscritoDt;

    @Column(name = "EntFechaHoraLeido_dt")
    private LocalDateTime entFechaHoraLeidoDt;

    public Integer getEntId() {
        return entId;
    }

    public void setEntId(Integer entId) {
        this.entId = entId;
    }

    public Integer getEntCentro() {
        return entCentro;
    }

    public void setEntCentro(Integer entCentro) {
        this.entCentro = entCentro;
    }

    public String getEntOperario() {
        return entOperario;
    }

    public void setEntOperario(String entOperario) {
        this.entOperario = entOperario;
    }

    public String getEntFescrito() {
        return entFescrito;
    }

    public void setEntFescrito(String entFescrito) {
        this.entFescrito = entFescrito;
    }

    public String getEntHescrito() {
        return entHescrito;
    }

    public void setEntHescrito(String entHescrito) {
        this.entHescrito = entHescrito;
    }

    public String getEntUsuario() {
        return entUsuario;
    }

    public void setEntUsuario(String entUsuario) {
        this.entUsuario = entUsuario;
    }

    public String getEntTexto() {
        return entTexto;
    }

    public void setEntTexto(String entTexto) {
        this.entTexto = entTexto;
    }

    public String getEntFleido() {
        return entFleido;
    }

    public void setEntFleido(String entFleido) {
        this.entFleido = entFleido;
    }

    public String getEntHleido() {
        return entHleido;
    }

    public void setEntHleido(String entHleido) {
        this.entHleido = entHleido;
    }

    public LocalDateTime getEntFechaHoraEscritoDt() {
        return entFechaHoraEscritoDt;
    }

    public void setEntFechaHoraEscritoDt(LocalDateTime entFechaHoraEscritoDt) {
        this.entFechaHoraEscritoDt = entFechaHoraEscritoDt;
    }

    public LocalDateTime getEntFechaHoraLeidoDt() {
        return entFechaHoraLeidoDt;
    }

    public void setEntFechaHoraLeidoDt(LocalDateTime entFechaHoraLeidoDt) {
        this.entFechaHoraLeidoDt = entFechaHoraLeidoDt;
    }
}
