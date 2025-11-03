package com.aliro2.repository;

import com.aliro2.model.Movadoj;
import org.springframework.data.domain.Page; // <- 1. Importar Page
import org.springframework.data.domain.Pageable; // <- 2. Importar Pageable
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovadojRepository extends JpaRepository<Movadoj, Integer> {

    /**
     * Busca visitas activas (sin fecha de salida) - PAGINADO
     */
    Page<Movadoj> findByMovFechaSalidaIsNullOrderByMovOrdenDesc(Pageable pageable);

    /**
     * MÉTODO CORREGIDO:
     * Busca visitas activas (MovFechaSalida IsNull)
     * Y (And) donde el nombre (MovNombre) contenga el keyword
     * O (Or) el primer apellido (MovApellidoUno) contenga el keyword.
     * Ignora mayúsculas/minúsculas (ContainingIgnoreCase).
     */
    Page<Movadoj> findByMovFechaSalidaIsNullAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(String nombreKeyword, String apellidoKeyword, Pageable pageable);


    /**
     * Este método lo mantenemos por si es usado en otra parte del código
     * (Aunque para la paginación usaremos los métodos de arriba).
     */
    List<Movadoj> findByMovFechaSalidaIsNullOrderByMovOrdenDesc();

    // --- 1. NUEVO MÉTODO (Paginado, SÓLO de HOY) ---
    /**
     * Busca visitas activas (Salida IS NULL) Y (And) que la fecha de entrada
     * sea igual a la fecha de hoy que le pasamos.
     */
    Page<Movadoj> findByMovFechaSalidaIsNullAndMovFechaEntradaEqualsOrderByMovOrdenDesc(String fechaHoy, Pageable pageable);

    // --- 2. NUEVO MÉTODO (Paginado, de HOY y CON BÚSQUEDA) ---
    /**
     * Busca visitas activas (Salida IS NULL) Y que la fecha de entrada sea la de HOY
     * Y (And) que el nombre O el apellido coincidan con el keyword.
     */
    Page<Movadoj> findByMovFechaSalidaIsNullAndMovFechaEntradaEqualsAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(String fechaHoy, String nombreKeyword, String apellidoKeyword, Pageable pageable);
}