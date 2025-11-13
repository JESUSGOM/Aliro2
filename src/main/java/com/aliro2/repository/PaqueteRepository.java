package com.aliro2.repository;

import com.aliro2.model.Paquete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaqueteRepository extends JpaRepository<Paquete, Integer> {

    /**
     * 1. Para /informes (TODOS los paquetes)
     * Busca TODOS los paquetes de un CENTRO, paginado.
     */
    Page<Paquete> findByPktCentroEqualsOrderByPktIdDesc(Integer pktCentro, Pageable pageable);

    /**
     * 2. Para /informes (TODOS los paquetes con BÚSQUEDA)
     * Busca TODOS los paquetes de un CENTRO con un KEYWORD (Emisor o Destinatario), paginado.
     */
    Page<Paquete> findByPktCentroEqualsAndPktEmisorContainingIgnoreCaseOrPktDestinatarioContainingIgnoreCaseOrderByPktIdDesc(
            Integer pktCentro, String emisorKeyword, String destinatarioKeyword, Pageable pageable);

    /**
     * 3. Para /entrega (PENDIENTES)
     * Busca paquetes PENDIENTES (PktComunicado = "No") de un CENTRO, paginado.
     */
    Page<Paquete> findByPktCentroEqualsAndPktComunicadoEqualsOrderByPktIdDesc(Integer pktCentro, String comunicado, Pageable pageable);

    /**
     * 4. Para /entrega (PENDIENTES con BÚSQUEDA)
     * Busca paquetes PENDIENTES de un CENTRO con un KEYWORD, paginado.
     */
    Page<Paquete> findByPktCentroEqualsAndPktComunicadoEqualsAndPktEmisorContainingIgnoreCaseOrPktDestinatarioContainingIgnoreCaseOrderByPktIdDesc(
            Integer pktCentro, String comunicado, String emisorKeyword, String destinatarioKeyword, Pageable pageable);

    /**
     * 5. Para SEGURIDAD
     * Busca un paquete por su ID y su Centro.
     */
    Optional<Paquete> findByPktIdAndPktCentroEquals(Integer pktId, Integer pktCentro);
}