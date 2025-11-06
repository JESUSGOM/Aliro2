package com.aliro2.service;

import com.aliro2.model.Telefono;
import com.aliro2.repository.TelefonoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelefonoService {

    private final TelefonoRepository telefonoRepository;

    @Autowired
    public TelefonoService(TelefonoRepository telefonoRepository) {
        this.telefonoRepository = telefonoRepository;
    }

    /**
     * Busca TODAS las llamadas por centro, con paginación y búsqueda opcional.
     * (Para "Informes")
     */
    public Page<Telefono> findAllByCentro(Integer telCentro, String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return telefonoRepository.findByTelCentroEqualsAndTelEmisorContainingIgnoreCaseOrTelDestinatarioContainingIgnoreCaseOrderByTelIdDesc(
                    telCentro, keyword, keyword, pageable);
        } else {
            return telefonoRepository.findByTelCentroEqualsOrderByTelIdDesc(telCentro, pageable);
        }
    }

    /**
     * Busca llamadas PENDIENTES por centro, con paginación y búsqueda opcional.
     * (Para "Comunicar llamadas")
     */
    public Page<Telefono> findPendientesByCentro(Integer telCentro, String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return telefonoRepository.findByTelCentroEqualsAndTelComunicadoIsNullAndTelEmisorContainingIgnoreCaseOrTelDestinatarioContainingIgnoreCaseOrderByTelIdDesc(
                    telCentro, keyword, keyword, pageable);
        } else {
            return telefonoRepository.findByTelCentroEqualsAndTelComunicadoIsNullOrderByTelIdDesc(telCentro, pageable);
        }
    }

    /**
     * Busca una llamada por ID y Centro (para validación de seguridad).
     */
    public Optional<Telefono> findByIdAndCentro(Integer telId, Integer telCentro) {
        return telefonoRepository.findByTelIdAndTelCentroEquals(telId, telCentro);
    }

    // --- Métodos CRUD Estándar ---
    public Telefono save(Telefono telefono) {
        return telefonoRepository.save(telefono);
    }
    public Optional<Telefono> findById(Integer id) {
        return telefonoRepository.findById(id);
    }
    public List<Telefono> findAll() {
        return telefonoRepository.findAll();
    }
    public void deleteById(Integer id) {
        telefonoRepository.deleteById(id);
    }
}