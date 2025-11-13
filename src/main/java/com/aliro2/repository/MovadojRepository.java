package com.aliro2.repository;

import com.aliro2.model.Movadoj;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovadojRepository extends JpaRepository<Movadoj, Integer> {

    // --- Métodos existentes para /visitas/salida ---
    Page<Movadoj> findByMovFechaSalidaIsNullAndMovFechaEntradaEqualsAndMovCentroEqualsOrderByMovOrdenDesc(String fechaHoy, Integer movCentro, Pageable pageable);
    Page<Movadoj> findByMovFechaSalidaIsNullAndMovFechaEntradaEqualsAndMovCentroEqualsAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(String fechaHoy, Integer movCentro, String nombreKeyword, String apellidoKeyword, Pageable pageable);
    Page<Movadoj> findByMovFechaSalidaIsNullAndMovCentroEqualsOrderByMovOrdenDesc(Integer movCentro, Pageable pageable);
    Page<Movadoj> findByMovFechaSalidaIsNullAndMovCentroEqualsAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(Integer movCentro, String nombreKeyword, String apellidoKeyword, Pageable pageable);
    List<Movadoj> findByMovFechaSalidaIsNullAndMovCentroEqualsOrderByMovOrdenDesc(Integer movCentro);
    Optional<Movadoj> findByMovOrdenAndMovCentro(Integer movOrden, Integer movCentro);


    // (Para /visitas/salida - Filtra por HOY, ACTIVAS y CENTRO)
    Page<Movadoj> findByMovFechaHoraSalidaDtIsNullAndMovCentroEqualsAndMovFechaHoraEntradaDtBetweenOrderByMovOrdenDesc(
            Integer movCentro, LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable pageable);

    // (Para /visitas/salida - Con BÚSQUEDA)
    Page<Movadoj> findByMovFechaHoraSalidaDtIsNullAndMovCentroEqualsAndMovFechaHoraEntradaDtBetweenAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(
            Integer movCentro, LocalDateTime fechaInicio, LocalDateTime fechaFin, String nombreKeyword, String apellidoKeyword, Pageable pageable);

    // (Para /visitas/informes/general - Filtra TODAS las ACTIVAS por CENTRO)
    Page<Movadoj> findByMovFechaHoraSalidaDtIsNullAndMovCentroEqualsOrderByMovOrdenDesc(Integer movCentro, Pageable pageable);

    // (Para /visitas/informes/general - Con BÚSQUEDA)
    Page<Movadoj> findByMovFechaHoraSalidaDtIsNullAndMovCentroEqualsAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(
            Integer movCentro, String nombreKeyword, String apellidoKeyword, Pageable pageable);



    // --- NUEVOS MÉTODOS PARA /visitas/informes/movimientos ---

    /**
     * 1. (Sin Búsqueda) Busca TODOS los movimientos (activos y cerrados) de un CENTRO, paginado.
     */
    Page<Movadoj> findByMovCentroEqualsOrderByMovOrdenDesc(Integer movCentro, Pageable pageable);

    /**
     * 2. (Con Búsqueda) Busca TODOS los movimientos (activos y cerrados) de un CENTRO con un KEYWORD, paginado.
     */
    Page<Movadoj> findByMovCentroEqualsAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(Integer movCentro, String nombreKeyword, String apellidoKeyword, Pageable pageable);
}