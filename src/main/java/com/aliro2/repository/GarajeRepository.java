package com.aliro2.repository;

import com.aliro2.model.Garaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarajeRepository extends JpaRepository<Garaje, Integer> {

    /**
     * 1. Para /informes (TODOS los registros)
     * Busca TODOS los registros de garaje, ordenados por ID descendente (más nuevos primero).
     */
    Page<Garaje> findAllByOrderByGrjIdDesc(Pageable pageable);

    /**
     * 2. Para /informes (con BÚSQUEDA)
     * Busca registros que coincidan con la matrícula, nombre o empresa.
     */
    Page<Garaje> findByGrjMatriculaContainingIgnoreCaseOrGrjNombreContainingIgnoreCaseOrGrjEmpresaContainingIgnoreCaseOrderByGrjIdDesc(
            String matriculaKeyword, String nombreKeyword, String empresaKeyword, Pageable pageable);
}