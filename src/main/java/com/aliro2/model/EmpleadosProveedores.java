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
}