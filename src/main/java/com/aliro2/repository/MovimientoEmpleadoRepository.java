package com.aliro2.repository;

import com.aliro2.model.MovimientosEmpleados;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovimientosEmpleadosRepository extends JpaRepository<MovimientosEmpleados, Integer> {

    /**
     * Busca TODOS los movimientos de un CENTRO, paginado.
     * (Para la vista de "Consultar" e "Informes")
     */
    // Usamos una @Query con JOIN para poder buscar por el nombre del empleado, no solo su NIF
    @Query("SELECT m FROM MovimientosEmpleados m JOIN EmpleadosProveedores e ON m.movEmpNif = e.empNif AND m.movCentro = e.empCentro " +
            "WHERE m.movCentro = :movCentro " +
            "AND (e.empNombre LIKE %:keyword% OR e.empApellido1 LIKE %:keyword% OR m.movEmpNif LIKE %:keyword%)")
    Page<MovimientosEmpleados> findByMovCentroWithSearch(Integer movCentro, String keyword, Pageable pageable);

    Page<MovimientosEmpleados> findByMovCentroEqualsOrderByMovIdDesc(Integer movCentro, Pageable pageable);

    /**
     * Busca movimientos ACTIVOS (sin fecha de salida) de un CENTRO, paginado.
     * (Para la vista de "Salida")
     */
    @Query("SELECT m FROM MovimientosEmpleados m JOIN EmpleadosProveedores e ON m.movEmpNif = e.empNif AND m.movCentro = e.empCentro " +
            "WHERE m.movCentro = :movCentro AND m.movFechaHoraSalida IS NULL " +
            "AND (e.empNombre LIKE %:keyword% OR e.empApellido1 LIKE %:keyword% OR m.movEmpNif LIKE %:keyword%)")
    Page<MovimientosEmpleados> findActivosByMovCentroWithSearch(Integer movCentro, String keyword, Pageable pageable);

    Page<MovimientosEmpleados> findByMovCentroEqualsAndMovFechaHoraSalidaIsNullOrderByMovIdDesc(Integer movCentro, Pageable pageable);

    /**
     * Busca un movimiento por su ID y su Centro (para seguridad).
     */
    Optional<MovimientosEmpleados> findByMovIdAndMovCentroEquals(Integer movId, Integer movCentro);
}