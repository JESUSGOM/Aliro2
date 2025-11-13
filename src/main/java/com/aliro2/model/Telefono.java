package com.aliro2.model; // <-- Corregido

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "Telefonos")
public class Telefono {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TelId")
	private Integer telId;

	@Column(name = "TelCentro", nullable = false)
	private Integer telCentro;

	@Column(name = "TelFecha", nullable = false, length = 8)
	private String telFecha;

	@Column(name = "TelHora", nullable = false, length = 6)
	private String telHora;

	@Column(name = "TelEmisor", nullable = false, length = 50)
	private String telEmisor;

	@Column(name = "TelDestinatario", nullable = false, length = 50)
	private String telDestinatario;

	@Column(name = "TelMensaje", nullable = false, length = 300)
	private String telMensaje;

	@Column(name = "TelComunicado")
	private Integer telComunicado;

	@Column(name = "TelFechaEntrega", length = 8)
	private String telFechaEntrega;

	@Column(name = "TelHoraEntrega", length = 6)
	private String telHoraEntrega;

	// --- NUEVOS CAMPOS (DATETIME) ---
	@Column(name = "TelFechaHoraRegistro_dt")
	private LocalDateTime telFechaHoraRegistroDt;

	@Column(name = "TelFechaHoraEntrega_dt")
	private LocalDateTime telFechaHoraEntregaDt;


	// --- Constructores, Getters y Setters ---

	public Telefono() {
		// Constructor vacío necesario para JPA
	}

	// (Aquí van todos los Getters y Setters... los omito por brevedad)

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

	public LocalDateTime getTelFechaHoraRegistroDt() {
		return telFechaHoraRegistroDt;
	}

	public void setTelFechaHoraRegistroDt(LocalDateTime telFechaHoraRegistroDt) {
		this.telFechaHoraRegistroDt = telFechaHoraRegistroDt;
	}

	public LocalDateTime getTelFechaHoraEntregaDt() {
		return telFechaHoraEntregaDt;
	}

	public void setTelFechaHoraEntregaDt(LocalDateTime telFechaHoraEntregaDt) {
		this.telFechaHoraEntregaDt = telFechaHoraEntregaDt;
	}
}