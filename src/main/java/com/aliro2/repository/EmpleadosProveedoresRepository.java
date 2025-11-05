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

    /**
     * Busca empleados de un centro específico, paginado y ordenado por apellido.
     */
    Page<EmpleadosProveedores> findByEmpCentroEqualsOrderByEmpApellido1Asc(Integer empCentro, Pageable pageable);

    /**
     * Busca empleados de un centro con un keyword (NIF, Nombre o Apellido1), paginado.
     */
    Page<EmpleadosProveedores> findByEmpCentroEqualsAndEmpNifContainingIgnoreCaseOrEmpNombreContainingIgnoreCaseOrEmpApellido1ContainingIgnoreCaseOrderByEmpApellido1Asc(
            Integer empCentro, String nifKeyword, String nombreKeyword, String apellidoKeyword, Pageable pageable);

    /**
     * Busca un empleado por su ID y su Centro (para seguridad en edición/eliminación).
     */
    Optional<EmpleadosProveedores> findByEmpIdAndEmpCentroEquals(Integer empId, Integer empCentro);

    /**
     * Busca todos los empleados de un centro (sin paginar).
     */
    List<EmpleadosProveedores> findByEmpCentroEquals(Integer empCentro);
}