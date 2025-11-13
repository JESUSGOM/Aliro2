package com.aliro2.repository;

import com.aliro2.model.EntreTurno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntreTurnoRepository extends JpaRepository<EntreTurno, Integer> {

    /**
     * 1. Para /informes (TODOS los turnos)
     * Busca TODOS los turnos de un CENTRO, paginado.
     */
    Page<EntreTurno> findByEntCentroEqualsOrderByEntIdDesc(Integer entCentro, Pageable pageable);

    /**
     * 2. Para /informes (TODOS los turnos con BÚSQUEDA)
     * Busca TODOS los turnos de un CENTRO con un KEYWORD (en el texto o por operario), paginado.
     */
    Page<EntreTurno> findByEntCentroEqualsAndEntTextoContainingIgnoreCaseOrEntOperarioContainingIgnoreCaseOrderByEntIdDesc(
            Integer entCentro, String textoKeyword, String operarioKeyword, Pageable pageable);

    /**
     * 3. Para /leer (PENDIENTES)
     * Busca turnos PENDIENTES (EntUsuario IS NULL) de un CENTRO, paginado.
     */
    Page<EntreTurno> findByEntCentroEqualsAndEntUsuarioIsNullOrderByEntIdDesc(Integer entCentro, Pageable pageable);

    /**
     * 4. Para SEGURIDAD
     * Busca un turno por su ID y su Centro.
     */
    Optional<EntreTurno> findByEntIdAndEntCentroEquals(Integer entId, Integer entCentro);
}