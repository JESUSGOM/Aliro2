package com.aliro2.repository;

import com.aliro2.model.Mezua;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MezuaRepository extends JpaRepository<Mezua, Integer> {

    /**
     * Busca todos los registros, paginado y ordenado por ID descendente.
     */
    Page<Mezua> findAllByOrderByMezIdDesc(Pageable pageable);

    /**
     * Busca por Nombre, Apellidos o Email (ignorando mayúsculas/minúsculas).
     */
    Page<Mezua> findByMezNombreContainingIgnoreCaseOrMezApellidosContainingIgnoreCaseOrMezEmailContainingIgnoreCaseOrderByMezIdDesc(
            String nombreKeyword, String apellidosKeyword, String emailKeyword, Pageable pageable);

}