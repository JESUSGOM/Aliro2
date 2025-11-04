package com.aliro2.repository;

import com.aliro2.model.Movadoj;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovadojRepository extends JpaRepository<Movadoj, Integer> {

    // --- MÉTODOS EXISTENTES (con filtro de centro añadido) ---

    // 1. Busca visitas activas (sin fecha de salida) POR CENTRO - PAGINADO
    Page<Movadoj> findByMovFechaSalidaIsNullAndMovCentroEqualsOrderByMovOrdenDesc(Integer movCentro, Pageable pageable);

    // 2. Busca visitas activas (sin fecha de salida) POR CENTRO y CON BÚSQUEDA - PAGINADO
    // (Añadimos el filtro MovCentroEquals después de IsNull)
    Page<Movadoj> findByMovFechaSalidaIsNullAndMovCentroEqualsAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(Integer movCentro, String nombreKeyword, String apellidoKeyword, Pageable pageable);

    // 3. Versión sin paginación (para findAll si aplica) POR CENTRO
    List<Movadoj> findByMovFechaSalidaIsNullAndMovCentroEqualsOrderByMovOrdenDesc(Integer movCentro);


    // --- MÉTODOS ANTES NUEVOS, AHORA CON FILTRO DE CENTRO (para "sólo de hoy") ---

    // 4. Paginado, SÓLO de HOY Y POR CENTRO
    Page<Movadoj> findByMovFechaSalidaIsNullAndMovFechaEntradaEqualsAndMovCentroEqualsOrderByMovOrdenDesc(String fechaHoy, Integer movCentro, Pageable pageable);

    // 5. Paginado, de HOY, CON BÚSQUEDA Y POR CENTRO
    Page<Movadoj> findByMovFechaSalidaIsNullAndMovFechaEntradaEqualsAndMovCentroEqualsAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(String fechaHoy, Integer movCentro, String nombreKeyword, String apellidoKeyword, Pageable pageable);

}