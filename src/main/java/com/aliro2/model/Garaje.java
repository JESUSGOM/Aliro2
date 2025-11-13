package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Garaje")
public class Garaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GrjId")
    private Integer grjId;

    @Column(name = "GrjFecha")
    private String grjFecha; // VARCHAR(8)

    @Column(name = "GrjNombre")
    private String grjNombre;

    @Column(name = "GrjEmpresa")
    private String grjEmpresa;

    @Column(name = "GrjMarca")
    private String grjMarca;

    @Column(name = "GrjModelo")
    private String grjModelo;

    @Column(name = "GrjColor")
    private String grjColor;

    @Column(name = "GrjMatricula")
    private String grjMatricula;

    // --- NUEVO CAMPO (DATE) ---
    @Column(name = "GrjFecha_dt")
    private LocalDate grjFechaDt;

    public Integer getGrjId() {
        return grjId;
    }

    public void setGrjId(Integer grjId) {
        this.grjId = grjId;
    }

    public String getGrjFecha() {
        return grjFecha;
    }

    public void setGrjFecha(String grjFecha) {
        this.grjFecha = grjFecha;
    }

    public String getGrjNombre() {
        return grjNombre;
    }

    public void setGrjNombre(String grjNombre) {
        this.grjNombre = grjNombre;
    }

    public String getGrjEmpresa() {
        return grjEmpresa;
    }

    public void setGrjEmpresa(String grjEmpresa) {
        this.grjEmpresa = grjEmpresa;
    }

    public String getGrjMarca() {
        return grjMarca;
    }

    public void setGrjMarca(String grjMarca) {
        this.grjMarca = grjMarca;
    }

    public String getGrjModelo() {
        return grjModelo;
    }

    public void setGrjModelo(String grjModelo) {
        this.grjModelo = grjModelo;
    }

    public String getGrjColor() {
        return grjColor;
    }

    public void setGrjColor(String grjColor) {
        this.grjColor = grjColor;
    }

    public String getGrjMatricula() {
        return grjMatricula;
    }

    public void setGrjMatricula(String grjMatricula) {
        this.grjMatricula = grjMatricula;
    }

    public LocalDate getGrjFechaDt() {
        return grjFechaDt;
    }

    public void setGrjFechaDt(LocalDate grjFechaDt) {
        this.grjFechaDt = grjFechaDt;
    }
}