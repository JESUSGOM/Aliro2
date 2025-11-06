package com.aliro2.model;

import jakarta.persistence.*;


@Entity
@Table(name = "Retposto")
public class Retposto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RptId")
    private Integer rptId;

    @Column(name = "RptCentro")
    private Integer rptCentro;

    @Column(name = "RptNombre")
    private String rptNombre;

    @Column(name = "RptApellidoUno")
    private String rptApellidoUno;

    @Column(name = "RptApellidoDos")
    private String rptApellidoDos;

    @Column(name = "RptEmail")
    private String rptEmail;

	public Integer getRptId() {
		return rptId;
	}

	public void setRptId(Integer rptId) {
		this.rptId = rptId;
	}

	public Integer getRptCentro() {
		return rptCentro;
	}

	public void setRptCentro(Integer rptCentro) {
		this.rptCentro = rptCentro;
	}

	public String getRptNombre() {
		return rptNombre;
	}

	public void setRptNombre(String rptNombre) {
		this.rptNombre = rptNombre;
	}

	public String getRptApellidoUno() {
		return rptApellidoUno;
	}

	public void setRptApellidoUno(String rptApellidoUno) {
		this.rptApellidoUno = rptApellidoUno;
	}

	public String getRptApellidoDos() {
		return rptApellidoDos;
	}

	public void setRptApellidoDos(String rptApellidoDos) {
		this.rptApellidoDos = rptApellidoDos;
	}

	public String getRptEmail() {
		return rptEmail;
	}

	public void setRptEmail(String rptEmail) {
		this.rptEmail = rptEmail;
	}
    
    
}