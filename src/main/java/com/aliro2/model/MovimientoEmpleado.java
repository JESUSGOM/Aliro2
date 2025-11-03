package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "MovimientosEmpleados")
public class MovimientoEmpleado {

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
}
