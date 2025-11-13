package com.aliro2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "EnvioEmail")
public class EnvioEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EnEmId")
    private Integer enEmId;

    @Column(name = "EnEmDestinatario")
    private String enEmDestinatario;

    @Column(name = "EnEmFecha")
    private String enEmFecha; // VARCHAR(8)

    @Column(name = "EnEmHora")
    private String enEmHora; // VARCHAR(6)

    @Column(name = "EnEmTexto", columnDefinition = "longtext")
    private String enEmTexto;

    @Column(name = "EnEmEmisor")
    private String enEmEmisor;

    // --- NUEVO CAMPO (DATETIME) ---
    @Column(name = "EnEmFechaHora_dt")
    private LocalDateTime enEmFechaHoraDt;

    public Integer getEnEmId() {
        return enEmId;
    }

    public void setEnEmId(Integer enEmId) {
        this.enEmId = enEmId;
    }

    public String getEnEmDestinatario() {
        return enEmDestinatario;
    }

    public void setEnEmDestinatario(String enEmDestinatario) {
        this.enEmDestinatario = enEmDestinatario;
    }

    public String getEnEmFecha() {
        return enEmFecha;
    }

    public void setEnEmFecha(String enEmFecha) {
        this.enEmFecha = enEmFecha;
    }

    public String getEnEmHora() {
        return enEmHora;
    }

    public void setEnEmHora(String enEmHora) {
        this.enEmHora = enEmHora;
    }

    public String getEnEmTexto() {
        return enEmTexto;
    }

    public void setEnEmTexto(String enEmTexto) {
        this.enEmTexto = enEmTexto;
    }

    public String getEnEmEmisor() {
        return enEmEmisor;
    }

    public LocalDateTime getEnEmFechaHoraDt() {
        return enEmFechaHoraDt;
    }

    public void setEnEmFechaHoraDt(LocalDateTime enEmFechaHoraDt) {
        this.enEmFechaHoraDt = enEmFechaHoraDt;
    }

    public void setEnEmEmisor(String enEmEmisor) {
        this.enEmEmisor = enEmEmisor;
    }


}
