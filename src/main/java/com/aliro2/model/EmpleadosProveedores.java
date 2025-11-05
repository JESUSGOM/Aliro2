package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "EmpleadosProveedores")
public class EmpleadosProveedores {

    /**
     * Clave primaria autoincremental.
     * Es OBLIGATORIO que hayas añadido esta columna a tu base de datos
     * para que JPA pueda gestionar la entidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmpId")
    private Integer empId;

    /**
     * CIF del proveedor al que pertenece este empleado.
     * Relacionado con la tabla 'Proveedores'.
     */
    @Column(name = "EmpPrdCif")
    private String empPrdCif;

    /**
     * Centro al que está asociado.
     */
    @Column(name = "EmpCentro")
    private Integer empCentro;

    @Column(name = "EmpNif")
    private String empNif;

    @Column(name = "EmpNombre")
    private String empNombre;

    @Column(name = "EmpApellido1")
    private String empApellido1;

    @Column(name = "EmpApellido2")
    private String empApellido2;

    @Column(name = "EmpTelefono")
    private String empTelefono;

    @Column(name = "EmpEmail")
    private String empEmail;

    @Column(name = "EmpCargo")
    private String empCargo;

    @Column(name = "EmpFechaAcceso")
    private LocalDate empFechaAcceso;

    @Column(name = "EmpFechaFinAcceso")
    private LocalDate empFechaFinAcceso;

    @Column(name = "EmpNotas", columnDefinition = "text")
    private String empNotas;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpPrdCif() {
        return empPrdCif;
    }

    public void setEmpPrdCif(String empPrdCif) {
        this.empPrdCif = empPrdCif;
    }

    public Integer getEmpCentro() {
        return empCentro;
    }

    public void setEmpCentro(Integer empCentro) {
        this.empCentro = empCentro;
    }

    public String getEmpNif() {
        return empNif;
    }

    public void setEmpNif(String empNif) {
        this.empNif = empNif;
    }

    public String getEmpNombre() {
        return empNombre;
    }

    public void setEmpNombre(String empNombre) {
        this.empNombre = empNombre;
    }

    public String getEmpApellido1() {
        return empApellido1;
    }

    public void setEmpApellido1(String empApellido1) {
        this.empApellido1 = empApellido1;
    }

    public String getEmpApellido2() {
        return empApellido2;
    }

    public void setEmpApellido2(String empApellido2) {
        this.empApellido2 = empApellido2;
    }

    public String getEmpTelefono() {
        return empTelefono;
    }

    public void setEmpTelefono(String empTelefono) {
        this.empTelefono = empTelefono;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpCargo() {
        return empCargo;
    }

    public void setEmpCargo(String empCargo) {
        this.empCargo = empCargo;
    }

    public LocalDate getEmpFechaAcceso() {
        return empFechaAcceso;
    }

    public void setEmpFechaAcceso(LocalDate empFechaAcceso) {
        this.empFechaAcceso = empFechaAcceso;
    }

    public LocalDate getEmpFechaFinAcceso() {
        return empFechaFinAcceso;
    }

    public void setEmpFechaFinAcceso(LocalDate empFechaFinAcceso) {
        this.empFechaFinAcceso = empFechaFinAcceso;
    }

    public String getEmpNotas() {
        return empNotas;
    }

    public void setEmpNotas(String empNotas) {
        this.empNotas = empNotas;
    }
}