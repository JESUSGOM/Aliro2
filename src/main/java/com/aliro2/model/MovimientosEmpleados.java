package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "MovimientosEmpleados")
public class MovimientosEmpleados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MovId")
    private Integer movId;

    @Column(name = "MovPrdCif")
    private String movPrdCif;

    @Column(name = "MovCentro")
    private Integer movCentro;

    @Column(name = "MovEmpNif")
    private String movEmpNif;

    @Column(name = "MovFechaHoraEntrada")
    private LocalDateTime movFechaHoraEntrada;

    @Column(name = "MovFechaHoraSalida")
    private LocalDateTime movFechaHoraSalida;

    public Integer getMovId() {
        return movId;
    }

    public void setMovId(Integer movId) {
        this.movId = movId;
    }

    public String getMovPrdCif() {
        return movPrdCif;
    }

    public void setMovPrdCif(String movPrdCif) {
        this.movPrdCif = movPrdCif;
    }

    public Integer getMovCentro() {
        return movCentro;
    }

    public void setMovCentro(Integer movCentro) {
        this.movCentro = movCentro;
    }

    public String getMovEmpNif() {
        return movEmpNif;
    }

    public void setMovEmpNif(String movEmpNif) {
        this.movEmpNif = movEmpNif;
    }

    public LocalDateTime getMovFechaHoraEntrada() {
        return movFechaHoraEntrada;
    }

    public void setMovFechaHoraEntrada(LocalDateTime movFechaHoraEntrada) {
        this.movFechaHoraEntrada = movFechaHoraEntrada;
    }

    public LocalDateTime getMovFechaHoraSalida() {
        return movFechaHoraSalida;
    }

    public void setMovFechaHoraSalida(LocalDateTime movFechaHoraSalida) {
        this.movFechaHoraSalida = movFechaHoraSalida;
    }
}
