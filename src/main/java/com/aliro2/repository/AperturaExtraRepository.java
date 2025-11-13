package com.aliro2.repository;

import com.aliro2.model.AperturaExtra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AperturaExtraRepository extends JpaRepository<AperturaExtra, Integer> {

    /**
     * 1. Para /informes (TODOS los registros)
     * Busca TODOS los registros de un CENTRO, paginado.
     */
    Page<AperturaExtra> findByAeCentroEqualsOrderByAeIdDesc(Integer aeCentro, Pageable pageable);

    /**
     * 2. Para /informes (con BÚSQUEDA)
     * Busca TODOS los registros de un CENTRO con un KEYWORD (en el motivo), paginado.
     */
    Page<AperturaExtra> findByAeCentroEqualsAndAeMotivoContainingIgnoreCaseOrderByAeIdDesc(
            Integer aeCentro, String motivoKeyword, Pageable pageable);

    /**
     * 3. Para SEGURIDAD
     * Busca un registro por su ID y su Centro.
     */
    Optional<AperturaExtra> findByAeIdAndAeCentroEquals(Integer aeId, Integer aeCentro);
}