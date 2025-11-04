package com.aliro2.repository;

import com.aliro2.model.Proveedor;
import com.aliro2.model.ProveedorId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, ProveedorId> {

    /**
     * Busca todos los Proveedores que coincidan con un PrdCentro.
     * La sintaxis 'Id_PrdCentro' le dice a Spring que busque la propiedad 'prdCentro'
     * dentro del objeto 'id' (que es la clave compuesta).
     */
    Page<Proveedor> findById_PrdCentro(Integer prdCentro, Pageable pageable);

    /**
     * Busca por Centro Y por Denominación (nombre) o CIF.
     */
    Page<Proveedor> findById_PrdCentroAndPrdDenominacionContainingIgnoreCaseOrId_PrdCifContainingIgnoreCase(
            Integer prdCentro, String denominacionKeyword, String cifKeyword, Pageable pageable);

    // Método simple para listas no paginadas
    List<Proveedor> findById_PrdCentro(Integer prdCentro);
}