package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Movadoj")
public class Movadoj {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MovOrden")
    private Integer movOrden;

    @Column(name = "MovCentro")
    private Integer movCentro;

    @Column(name = "MovNombre")
    private String movNombre;

    @Column(name = "MovApellidoUno")
    private String movApellidoUno;

    @Column(name = "MovApellidoDos")
    private String movApellidoDos;

    @Column(name = "MovProcedencia")
    private String movProcedencia;

    @Column(name = "MovDestino")
    private String movDestino;

    @Column(name = "MovPlanta")
    private String movPlanta;

    @Column(name = "MovFechaEntrada")
    private String movFechaEntrada; // VARCHAR(8)

    @Column(name = "MovHoraEntrada")
    private String movHoraEntrada; // VARCHAR(6)

    @Column(name = "MovFechaSalida")
    private String movFechaSalida; // VARCHAR(8)

    @Column(name = "MovHoraSalida")
    private String movHoraSalida; // VARCHAR(6)

    @Column(name = "MovVehiculo")
    private String movVehiculo;

    @Column(name = "MovMotivo")
    private String movMotivo;
}