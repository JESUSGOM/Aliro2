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

    public Integer getMovOrden() {
        return movOrden;
    }

    public void setMovOrden(Integer movOrden) {
        this.movOrden = movOrden;
    }

    public Integer getMovCentro() {
        return movCentro;
    }

    public void setMovCentro(Integer movCentro) {
        this.movCentro = movCentro;
    }

    public String getMovNombre() {
        return movNombre;
    }

    public void setMovNombre(String movNombre) {
        this.movNombre = movNombre;
    }

    public String getMovApellidoUno() {
        return movApellidoUno;
    }

    public void setMovApellidoUno(String movApellidoUno) {
        this.movApellidoUno = movApellidoUno;
    }

    public String getMovApellidoDos() {
        return movApellidoDos;
    }

    public void setMovApellidoDos(String movApellidoDos) {
        this.movApellidoDos = movApellidoDos;
    }

    public String getMovProcedencia() {
        return movProcedencia;
    }

    public void setMovProcedencia(String movProcedencia) {
        this.movProcedencia = movProcedencia;
    }

    public String getMovDestino() {
        return movDestino;
    }

    public void setMovDestino(String movDestino) {
        this.movDestino = movDestino;
    }

    public String getMovPlanta() {
        return movPlanta;
    }

    public void setMovPlanta(String movPlanta) {
        this.movPlanta = movPlanta;
    }

    public String getMovFechaEntrada() {
        return movFechaEntrada;
    }

    public void setMovFechaEntrada(String movFechaEntrada) {
        this.movFechaEntrada = movFechaEntrada;
    }

    public String getMovHoraEntrada() {
        return movHoraEntrada;
    }

    public void setMovHoraEntrada(String movHoraEntrada) {
        this.movHoraEntrada = movHoraEntrada;
    }

    public String getMovFechaSalida() {
        return movFechaSalida;
    }

    public void setMovFechaSalida(String movFechaSalida) {
        this.movFechaSalida = movFechaSalida;
    }

    public String getMovHoraSalida() {
        return movHoraSalida;
    }

    public void setMovHoraSalida(String movHoraSalida) {
        this.movHoraSalida = movHoraSalida;
    }

    public String getMovVehiculo() {
        return movVehiculo;
    }

    public void setMovVehiculo(String movVehiculo) {
        this.movVehiculo = movVehiculo;
    }

    public String getMovMotivo() {
        return movMotivo;
    }

    public void setMovMotivo(String movMotivo) {
        this.movMotivo = movMotivo;
    }
}