package com.aliro2.repository;

import com.aliro2.model.EmpleadosProveedores;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadosProveedoresRepository extends JpaRepository<EmpleadosProveedores, Integer> {

    // ... (métodos existentes) ...
    Page<EmpleadosProveedores> findByEmpCentroEqualsOrderByEmpApellido1Asc(Integer empCentro, Pageable pageable);
    Page<EmpleadosProveedores> findByEmpCentroEqualsAndEmpNifContainingIgnoreCaseOrEmpNombreContainingIgnoreCaseOrEmpApellido1ContainingIgnoreCaseOrderByEmpApellido1Asc(
            Integer empCentro, String nifKeyword, String nombreKeyword, String apellidoKeyword, Pageable pageable);
    Optional<EmpleadosProveedores> findByEmpIdAndEmpCentroEquals(Integer empId, Integer empCentro);
    List<EmpleadosProveedores> findByEmpCentroEquals(Integer empCentro);

    /**
     * AÑADE ESTE MÉTODO:
     * Busca a un empleado por su NIF y su Centro.
     * Es crucial para vincular el Movimiento con el Proveedor correcto.
     */
    Optional<EmpleadosProveedores> findByEmpNifAndEmpCentroEquals(String empNif, Integer empCentro);
}