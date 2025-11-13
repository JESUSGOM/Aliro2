package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "KeyMoves")
public class KeyMove {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KeyOrden")
    private Integer keyOrden;

    @Column(name = "KeyLlvOrden")
    private String keyLlvOrden;

    @Column(name = "KeyCentro")
    private Integer keyCentro;

    @Column(name = "KeyFechaEntrega")
    private String keyFechaEntrega; // VARCHAR(8)

    @Column(name = "KeyHoraEntrega")
    private String keyHoraEntrega; // VARCHAR(6)

    @Column(name = "KeyFechaRecepcion")
    private String keyFechaRecepcion; // VARCHAR(8)

    @Column(name = "KeyHoraRecepcion")
    private String keyHoraRecepcion; // VARCHAR(6)

    // --- NUEVOS CAMPOS (DATETIME) ---
    @Column(name = "KeyFechaHoraEntrega_dt")
    private LocalDateTime keyFechaHoraEntregaDt;

    @Column(name = "KeyFechaHoraRecepcion_dt")
    private LocalDateTime keyFechaHoraRecepcionDt;

    @Column(name = "KeyNombre")
    private String keyNombre;

    @Column(name = "KeyApellidoUno")
    private String keyApellidoUno;

    @Column(name = "KeyApellidoDos")
    private String keyApellidoDos;

    public Integer getKeyOrden() {
        return keyOrden;
    }

    public void setKeyOrden(Integer keyOrden) {
        this.keyOrden = keyOrden;
    }

    public String getKeyLlvOrden() {
        return keyLlvOrden;
    }

    public void setKeyLlvOrden(String keyLlvOrden) {
        this.keyLlvOrden = keyLlvOrden;
    }

    public Integer getKeyCentro() {
        return keyCentro;
    }

    public void setKeyCentro(Integer keyCentro) {
        this.keyCentro = keyCentro;
    }

    public String getKeyFechaEntrega() {
        return keyFechaEntrega;
    }

    public void setKeyFechaEntrega(String keyFechaEntrega) {
        this.keyFechaEntrega = keyFechaEntrega;
    }

    public String getKeyHoraEntrega() {
        return keyHoraEntrega;
    }

    public void setKeyHoraEntrega(String keyHoraEntrega) {
        this.keyHoraEntrega = keyHoraEntrega;
    }

    public String getKeyNombre() {
        return keyNombre;
    }

    public void setKeyNombre(String keyNombre) {
        this.keyNombre = keyNombre;
    }

    public String getKeyApellidoUno() {
        return keyApellidoUno;
    }

    public void setKeyApellidoUno(String keyApellidoUno) {
        this.keyApellidoUno = keyApellidoUno;
    }

    public String getKeyApellidoDos() {
        return keyApellidoDos;
    }

    public void setKeyApellidoDos(String keyApellidoDos) {
        this.keyApellidoDos = keyApellidoDos;
    }

    public String getKeyFechaRecepcion() {
        return keyFechaRecepcion;
    }

    public void setKeyFechaRecepcion(String keyFechaRecepcion) {
        this.keyFechaRecepcion = keyFechaRecepcion;
    }

    public String getKeyHoraRecepcion() {
        return keyHoraRecepcion;
    }

    public void setKeyHoraRecepcion(String keyHoraRecepcion) {
        this.keyHoraRecepcion = keyHoraRecepcion;
    }

    public LocalDateTime getKeyFechaHoraEntregaDt() {return keyFechaHoraEntregaDt;}

    public void setKeyFechaHoraEntregaDt(LocalDateTime keyFechaHoraEntregaDt) {this.keyFechaHoraEntregaDt = keyFechaHoraEntregaDt;}

    public LocalDateTime getKeyFechaHoraRecepcionDt() {return keyFechaHoraRecepcionDt;}

    public void setKeyFechaHoraRecepcionDt(LocalDateTime keyFechaHoraRecepcionDt) {this.keyFechaHoraRecepcionDt = keyFechaHoraRecepcionDt;}


}