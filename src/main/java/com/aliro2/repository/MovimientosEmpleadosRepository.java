package com.aliro2.repository;

import com.aliro2.model.MovimientosEmpleados;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // <- Importar Query
import org.springframework.data.repository.query.Param; // <- Importar Param
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovimientosEmpleadosRepository extends JpaRepository<MovimientosEmpleados, Integer> {

    /**
     * Busca TODOS los movimientos de un CENTRO, paginado.
     */
    Page<MovimientosEmpleados> findByMovCentroEqualsOrderByMovIdDesc(Integer movCentro, Pageable pageable);

    /**
     * Busca TODOS los movimientos de un CENTRO con un KEYWORD, paginado.
     * La consulta JOIN permite buscar en la tabla EmpleadosProveedores (por nombre/apellido)
     * usando el NIF y el Centro como claves de unión.
     */
    @Query("SELECT m FROM MovimientosEmpleados m JOIN EmpleadosProveedores e ON m.movEmpNif = e.empNif AND m.movCentro = e.empCentro " +
            "WHERE m.movCentro = :movCentro " +
            "AND (e.empNombre LIKE %:keyword% OR e.empApellido1 LIKE %:keyword% OR m.movEmpNif LIKE %:keyword%)" +
            "ORDER BY m.movId DESC")
    Page<MovimientosEmpleados> findByMovCentroWithSearch(@Param("movCentro") Integer movCentro, @Param("keyword") String keyword, Pageable pageable);


    /**
     * Busca movimientos ACTIVOS (sin fecha de salida) de un CENTRO, paginado.
     */
    Page<MovimientosEmpleados> findByMovCentroEqualsAndMovFechaHoraSalidaIsNullOrderByMovIdDesc(Integer movCentro, Pageable pageable);

    /**
     * Busca movimientos ACTIVOS de un CENTRO con un KEYWORD, paginado.
     */
    @Query("SELECT m FROM MovimientosEmpleados m JOIN EmpleadosProveedores e ON m.movEmpNif = e.empNif AND m.movCentro = e.empCentro " +
            "WHERE m.movCentro = :movCentro AND m.movFechaHoraSalida IS NULL " +
            "AND (e.empNombre LIKE %:keyword% OR e.empApellido1 LIKE %:keyword% OR m.movEmpNif LIKE %:keyword%)" +
            "ORDER BY m.movId DESC")
    Page<MovimientosEmpleados> findActivosByMovCentroWithSearch(@Param("movCentro") Integer movCentro, @Param("keyword") String keyword, Pageable pageable);


    /**
     * Busca un movimiento por su ID y su Centro (para seguridad).
     */
    Optional<MovimientosEmpleados> findByMovIdAndMovCentroEquals(Integer movId, Integer movCentro);
}