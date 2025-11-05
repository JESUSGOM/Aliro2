package com.aliro2.repository;

import com.aliro2.model.Incidencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {

    /**
     * Busca incidencias por CENTRO, paginadas.
     * (Para la lista principal sin búsqueda)
     */
    Page<Incidencia> findByIncCentroEqualsOrderByIncIdDesc(Integer incCentro, Pageable pageable);

    /**
     * Busca incidencias por CENTRO y con un KEYWORD, paginadas.
     * El keyword buscará en el texto de la incidencia O en el campo 'comunicado a'.
     */
    Page<Incidencia> findByIncCentroEqualsAndIncTextoContainingIgnoreCaseOrIncComunicadoAContainingIgnoreCaseOrderByIncIdDesc(
            Integer incCentro, String textoKeyword, String comunicadoKeyword, Pageable pageable);

    /**
     * Busca una incidencia por su ID y su Centro (para seguridad en edición/eliminación).
     */
    Optional<Incidencia> findByIncIdAndIncCentroEquals(Integer incId, Integer incCentro);

}