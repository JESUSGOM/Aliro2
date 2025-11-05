package com.aliro2.service;

import com.aliro2.model.Incidencia;
import com.aliro2.repository.IncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;

    @Autowired
    public IncidenciaService(IncidenciaRepository incidenciaRepository) {
        this.incidenciaRepository = incidenciaRepository;
    }

    /**
     * Busca incidencias por centro, con paginación y búsqueda opcional.
     */
    public Page<Incidencia> findByCentro(Integer incCentro, String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Si hay búsqueda, llama al método complejo
            return incidenciaRepository.findByIncCentroEqualsAndIncTextoContainingIgnoreCaseOrIncComunicadoAContainingIgnoreCaseOrderByIncIdDesc(
                    incCentro, keyword, keyword, pageable);
        } else {
            // Si no, llama al método simple de paginación por centro
            return incidenciaRepository.findByIncCentroEqualsOrderByIncIdDesc(incCentro, pageable);
        }
    }

    /**
     * Busca una incidencia por ID y Centro (para validación de seguridad).
     */
    public Optional<Incidencia> findByIdAndCentro(Integer incId, Integer incCentro) {
        return incidenciaRepository.findByIncIdAndIncCentroEquals(incId, incCentro);
    }

    // --- Métodos CRUD Estándar ---

    public Incidencia save(Incidencia incidencia) {
        return incidenciaRepository.save(incidencia);
    }

    public void deleteById(Integer id) {
        incidenciaRepository.deleteById(id);
    }

    public Optional<Incidencia> findById(Integer id) {
        return incidenciaRepository.findById(id);
    }

    public List<Incidencia> findAll() {
        return incidenciaRepository.findAll();
    }
}