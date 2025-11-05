package com.aliro2.repository;

import com.aliro2.model.Movadoj;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovadojRepository extends JpaRepository<Movadoj, Integer> {

    // --- MÉTODOS PARA /visitas/salida (HOY + CENTRO) ---

    /**
     * (Sin Búsqueda) Busca visitas activas (Salida IS NULL) + de HOY (FechaEntrada) + POR CENTRO.
     */
    Page<Movadoj> findByMovFechaSalidaIsNullAndMovFechaEntradaEqualsAndMovCentroEqualsOrderByMovOrdenDesc(String fechaHoy, Integer movCentro, Pageable pageable);

    /**
     * (Con Búsqueda) Busca visitas activas (Salida IS NULL) + de HOY (FechaEntrada) + POR CENTRO,
     * Y que coincidan con el Nombre O el Apellido.
     */
    Page<Movadoj> findByMovFechaSalidaIsNullAndMovFechaEntradaEqualsAndMovCentroEqualsAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(String fechaHoy, Integer movCentro, String nombreKeyword, String apellidoKeyword, Pageable pageable);


    // --- MÉTODOS PARA /visitas/informes (TODAS LAS ACTIVAS + CENTRO) ---

    /**
     * (Sin Búsqueda) Busca TODAS las visitas activas (sin filtro de fecha) POR CENTRO.
     */
    Page<Movadoj> findByMovFechaSalidaIsNullAndMovCentroEqualsOrderByMovOrdenDesc(Integer movCentro, Pageable pageable);

    /**
     * (Con Búsqueda) Busca TODAS las visitas activas (sin filtro de fecha) POR CENTRO,
     * Y que coincidan con el Nombre O el Apellido.
     */
    Page<Movadoj> findByMovFechaSalidaIsNullAndMovCentroEqualsAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(Integer movCentro, String nombreKeyword, String apellidoKeyword, Pageable pageable);


    /**
     * (Sin Paginación) Busca TODAS las visitas activas POR CENTRO.
     */
    List<Movadoj> findByMovFechaSalidaIsNullAndMovCentroEqualsOrderByMovOrdenDesc(Integer movCentro);

    /**
     * (Seguridad) Busca una visita por ID y Centro
     */
    Optional<Movadoj> findByMovOrdenAndMovCentro(Integer movOrden, Integer movCentro);
}