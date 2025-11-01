package com.aliro2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id // Marca este campo como la clave primaria.
    @Column(name = "UsuDni") // Mapea el campo al nombre de la columna.
    private String usuDni;

    @Column(name = "UsuClave")
    private String usuClave;

    @Column(name = "UsuNombre")
    private String usuNombre;

    @Column(name = "UsuApellidoUno")
    private String usuApellidoUno;

    @Column(name = "UsuApellidoDos")
    private String usuApellidoDos;

    @Column(name = "UsuTipo")
    private String usuTipo;

    @Column(name = "UsuCargo")
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

    public String getUsuApellidoDos() {
        return usuApellidoDos;
    }
}
