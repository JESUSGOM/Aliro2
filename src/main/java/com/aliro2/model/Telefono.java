package com.aliro2.model;

import jakarta.persistence.*;


@Entity
@Table(name = "Telefonos")
public class Telefono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TelId")
    private Integer telId;

    @Column(name = "TelCentro")
    private Integer telCentro;

    @Column(name = "TelFecha")
    private String telFecha; // VARCHAR(8)

    @Column(name = "TelHora")
    private String telHora; // VARCHAR(6)

    @Column(name = "TelEmisor")
    private String telEmisor;

    @Column(name = "TelDestinatario")
    private String telDestinatario;

    @Column(name = "TelMensaje")
    private String telMensaje;

    @Column(name = "TelComunicado")
    private Integer telComunicado;

    @Column(name = "TelFechaEntrega")
    private String telFechaEntrega; // VARCHAR(8)

    @Column(name = "TelHoraEntrega")
    private String telHoraEntrega; // VARCHAR(6)

	public Integer getTelId() {
		return telId;
	}

	public void setTelId(Integer telId) {
		this.telId = telId;
	}

	public Integer getTelCentro() {
		return telCentro;
	}

	public void setTelCentro(Integer telCentro) {
		this.telCentro = telCentro;
	}

	public String getTelFecha() {
		return telFecha;
	}

	public void setTelFecha(String telFecha) {
		this.telFecha = telFecha;
	}

	public String getTelHora() {
		return telHora;
	}

	public void setTelHora(String telHora) {
		this.telHora = telHora;
	}

	public String getTelEmisor() {
		return telEmisor;
	}

	public void setTelEmisor(String telEmisor) {
		this.telEmisor = telEmisor;
	}

	public String getTelDestinatario() {
		return telDestinatario;
	}

	public void setTelDestinatario(String telDestinatario) {
		this.telDestinatario = telDestinatario;
	}

	public String getTelMensaje() {
		return telMensaje;
	}

	public void setTelMensaje(String telMensaje) {
		this.telMensaje = telMensaje;
	}

	public Integer getTelComunicado() {
		return telComunicado;
	}

	public void setTelComunicado(Integer telComunicado) {
		this.telComunicado = telComunicado;
	}

	public String getTelFechaEntrega() {
		return telFechaEntrega;
	}

	public void setTelFechaEntrega(String telFechaEntrega) {
		this.telFechaEntrega = telFechaEntrega;
	}

	public String getTelHoraEntrega() {
		return telHoraEntrega;
	}

	public void setTelHoraEntrega(String telHoraEntrega) {
		this.telHoraEntrega = telHoraEntrega;
	}
    
    
}