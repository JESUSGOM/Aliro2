package com.aliro2.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "AperturasExtra")
public class AperturaExtra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AeId")
    private Integer aeId;

    @Column(name = "AeCentro")
    private Integer aeCentro;

    @Column(name = "AeFecha")
    private LocalDate aeFecha;

    @Column(name = "AeHoraInicio")
    private LocalTime aeHoraInicio;

    @Column(name = "AeHoraFinal")
    private LocalTime aeHoraFinal;

    @Column(name = "AeMotivo", columnDefinition = "longtext")
    private String aeMotivo;
}
