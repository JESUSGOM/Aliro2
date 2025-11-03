package com.aliro2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entidad que representa la tabla 'Usuarios' de la base de datos Conlabac.
 * Mapea todos los campos de la tabla a propiedades de la clase.
 * La anotación @Data de Lombok genera automáticamente los getters, setters,
 * toString(), equals() y hashCode().
 */
@Data
@Entity
@Table(name = "Usuarios")
public class Usuario {

    /**
     * Clave primaria de la tabla. Es el DNI del usuario.
     */
    @Id
    @Column(name = "UsuDni", nullable = false, length = 9)
    private String usuDni;

    /**
     * Contraseña del usuario.
     */
    @Column(name = "UsuClave", nullable = false, length = 9)
    private String usuClave;

    /**
     * ID del centro al que pertenece el usuario.
     * Relacionado con la tabla 'Centros'.
     */
    @Column(name = "UsuCentro", nullable = false)
    private int usuCentro;

    /**
     * Nombre de pila del usuario.
     */
    @Column(name = "UsuNombre", nullable = false, length = 50)
    private String usuNombre;

    /**
     * Primer apellido del usuario.
     */
    @Column(name = "UsuApellidoUno", nullable = false, length = 60)
    private String usuApellidoUno;

    /**
     * Segundo apellido del usuario (puede ser nulo).
     */
    @Column(name = "UsuApellidoDos", length = 60)
    private String usuApellidoDos;

    /**
     * Tipo de usuario (U, A, T, Z, Y, etc.).
     * Determina los permisos y la vista del panel.
     */
    @Column(name = "UsuTipo", nullable = false, length = 1)
    private String usuTipo;

    /**
     * Cargo o puesto del usuario (ej: "Auxiliar de recepción").
     * Puede ser nulo.
     */
    @Column(name = "UsuCargo", length = 30)
    private String usuCargo;

    public String getUsuDni() {
        return usuDni;
    }

    public void setUsuDni(String usuDni) {
        this.usuDni = usuDni;
    }

    public String getUsuClave() {
        return usuClave;
    }

    public void setUsuClave(String usuClave) {
        this.usuClave = usuClave;
    }

    public int getUsuCentro() {
        return usuCentro;
    }

    public void setUsuCentro(int usuCentro) {
        this.usuCentro = usuCentro;
    }

    public String getUsuNombre() {
        return usuNombre;
    }

    public void setUsuNombre(String usuNombre) {
        this.usuNombre = usuNombre;
    }

    public String getUsuApellidoUno() {
        return usuApellidoUno;
    }

    public void setUsuApellidoUno(String usuApellidoUno) {
        this.usuApellidoUno = usuApellidoUno;
    }

    public String getUsuApellidoDos() {
        return usuApellidoDos;
    }

    public void setUsuApellidoDos(String usuApellidoDos) {
        this.usuApellidoDos = usuApellidoDos;
    }

    public String getUsuTipo() {
        return usuTipo;
    }

    public void setUsuTipo(String usuTipo) {
        this.usuTipo = usuTipo;
    }

    public String getUsuCargo() {
        return usuCargo;
    }

    public void setUsuCargo(String usuCargo) {
        this.usuCargo = usuCargo;
    }
}