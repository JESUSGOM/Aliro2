package com.aliro2.repository;

import com.aliro2.model.Movadoj;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovadojRepository extends JpaRepository<Movadoj, Integer> {

    // --- MÉTODOS REQUERIDOS PARA LA VISTA "SALIDA VISITANTES" ---

    /**
     * 1. (Sin Búsqueda) Busca visitas activas (Salida IS NULL) de HOY (FechaEntrada) y POR CENTRO.
     */
    Page<Movadoj> findByMovFechaSalidaIsNullAndMovFechaEntradaEqualsAndMovCentroEqualsOrderByMovOrdenDesc(String fechaHoy, Integer movCentro, Pageable pageable);

    /**
     * 2. (Con Búsqueda) Busca visitas activas (Salida IS NULL) de HOY (FechaEntrada) y POR CENTRO,
     * que coincidan con el Nombre O el Apellido.
     */
    Page<Movadoj> findByMovFechaSalidaIsNullAndMovFechaEntradaEqualsAndMovCentroEqualsAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(String fechaHoy, Integer movCentro, String nombreKeyword, String apellidoKeyword, Pageable pageable);


    // --- MÉTODOS OPCIONALES (Para informes u otras vistas) ---

    // Busca TODAS las visitas activas (sin filtro de fecha) POR CENTRO (Paginado)
    Page<Movadoj> findByMovFechaSalidaIsNullAndMovCentroEqualsOrderByMovOrdenDesc(Integer movCentro, Pageable pageable);

    // Busca TODAS las visitas activas (sin filtro de fecha) POR CENTRO (Sin Paginación)
    List<Movadoj> findByMovFechaSalidaIsNullAndMovCentroEqualsOrderByMovOrdenDesc(Integer movCentro);
}