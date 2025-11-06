package com.aliro2.repository;

import com.aliro2.model.Telefono;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelefonoRepository extends JpaRepository<Telefono, Integer> {

    /**
     * Busca TODAS las llamadas de un CENTRO, paginado.
     * (Para la vista de "Informes")
     */
    Page<Telefono> findByTelCentroEqualsOrderByTelIdDesc(Integer telCentro, Pageable pageable);

    /**
     * Busca TODAS las llamadas de un CENTRO con un KEYWORD (Emisor o Destinatario), paginado.
     */
    Page<Telefono> findByTelCentroEqualsAndTelEmisorContainingIgnoreCaseOrTelDestinatarioContainingIgnoreCaseOrderByTelIdDesc(
            Integer telCentro, String emisorKeyword, String destinatarioKeyword, Pageable pageable);

    /**
     * Busca llamadas PENDIENTES (TelComunicado IS NULL) de un CENTRO, paginado.
     * (Para la vista de "Comunicar llamadas")
     */
    Page<Telefono> findByTelCentroEqualsAndTelComunicadoIsNullOrderByTelIdDesc(Integer telCentro, Pageable pageable);

    /**
     * Busca llamadas PENDIENTES de un CENTRO con un KEYWORD, paginado.
     */
    Page<Telefono> findByTelCentroEqualsAndTelComunicadoIsNullAndTelEmisorContainingIgnoreCaseOrTelDestinatarioContainingIgnoreCaseOrderByTelIdDesc(
            Integer telCentro, String emisorKeyword, String destinatarioKeyword, Pageable pageable);

    /**
     * Busca una llamada por su ID y su Centro (para seguridad).
     */
    Optional<Telefono> findByTelIdAndTelCentroEquals(Integer telId, Integer telCentro);
}