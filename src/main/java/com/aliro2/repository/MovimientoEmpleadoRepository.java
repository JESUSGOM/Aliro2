package com.aliro2.repository;

import com.aliro2.model.MovimientoEmpleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovimientoEmpleadoRepository extends JpaRepository<MovimientoEmpleado, Integer> {

    /**
     * Busca TODOS los movimientos de un CENTRO, paginado.
     * (Para la vista de "Consultar" e "Informes")
     */
    // Usamos una @Query con JOIN para poder buscar por el nombre del empleado, no solo su NIF
    @Query("SELECT m FROM MovimientoEmpleado m JOIN EmpleadosProveedores e ON m.movEmpNif = e.empNif AND m.movCentro = e.empCentro " +
            "WHERE m.movCentro = :movCentro " +
            "AND (e.empNombre LIKE %:keyword% OR e.empApellido1 LIKE %:keyword% OR m.movEmpNif LIKE %:keyword%)")
    Page<MovimientoEmpleado> findByMovCentroWithSearch(Integer movCentro, String keyword, Pageable pageable);

    Page<MovimientoEmpleado> findByMovCentroEqualsOrderByMovIdDesc(Integer movCentro, Pageable pageable);

    /**
     * Busca movimientos ACTIVOS (sin fecha de salida) de un CENTRO, paginado.
     * (Para la vista de "Salida")
     */
    @Query("SELECT m FROM MovimientoEmpleado m JOIN EmpleadosProveedores e ON m.movEmpNif = e.empNif AND m.movCentro = e.empCentro " +
            "WHERE m.movCentro = :movCentro AND m.movFechaHoraSalida IS NULL " +
            "AND (e.empNombre LIKE %:keyword% OR e.empApellido1 LIKE %:keyword% OR m.movEmpNif LIKE %:keyword%)")
    Page<MovimientoEmpleado> findActivosByMovCentroWithSearch(Integer movCentro, String keyword, Pageable pageable);

    Page<MovimientoEmpleado> findByMovCentroEqualsAndMovFechaHoraSalidaIsNullOrderByMovIdDesc(Integer movCentro, Pageable pageable);

    /**
     * Busca un movimiento por su ID y su Centro (para seguridad).
     */
    Optional<MovimientoEmpleado> findByMovIdAndMovCentroEquals(Integer movId, Integer movCentro);
}