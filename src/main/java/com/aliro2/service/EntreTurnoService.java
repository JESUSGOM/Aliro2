package com.aliro2.service;

import com.aliro2.model.EntreTurno;
import com.aliro2.repository.EntreTurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntreTurnoService {

    private final EntreTurnoRepository entreTurnoRepository;

    @Autowired
    public EntreTurnoService(EntreTurnoRepository entreTurnoRepository) {
        this.entreTurnoRepository = entreTurnoRepository;
    }

    /**
     * Busca TODOS los turnos por centro, con paginación y búsqueda opcional.
     * (Para "Informes")
     */
    public Page<EntreTurno> findAllByCentro(Integer entCentro, String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return entreTurnoRepository.findByEntCentroEqualsAndEntTextoContainingIgnoreCaseOrEntOperarioContainingIgnoreCaseOrderByEntIdDesc(
                    entCentro, keyword, keyword, pageable);
        } else {
            return entreTurnoRepository.findByEntCentroEqualsOrderByEntIdDesc(entCentro, pageable);
        }
    }

    /**
     * Busca turnos PENDIENTES por centro, con paginación.
     * (Para "Leer comunicación")
     */
    public Page<EntreTurno> findPendientesByCentro(Integer entCentro, Pageable pageable) {
        // Para esta vista, no implementamos búsqueda por keyword, solo paginación
        return entreTurnoRepository.findByEntCentroEqualsAndEntUsuarioIsNullOrderByEntIdDesc(entCentro, pageable);
    }

    /**
     * Busca un turno por ID y Centro (para validación de seguridad).
     */
    public Optional<EntreTurno> findByIdAndCentro(Integer entId, Integer entCentro) {
        return entreTurnoRepository.findByEntIdAndEntCentroEquals(entId, entCentro);
    }

    // --- Métodos CRUD Estándar ---

    public EntreTurno save(EntreTurno entreTurno) {
        return entreTurnoRepository.save(entreTurno);
    }

    public List<EntreTurno> findAll() {
        return entreTurnoRepository.findAll();
    }

    public Optional<EntreTurno> findById(Integer id) {
        return entreTurnoRepository.findById(id);
    }

    public void deleteById(Integer id) {
        entreTurnoRepository.deleteById(id);
    }
}