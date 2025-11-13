package com.aliro2.repository;

import com.aliro2.model.Retposto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RetpostoRepository extends JpaRepository<Retposto, Integer> {

    /**
     * Busca en la BD todos los registros del centro dado,
     * que tengan un email no vacío, y los ordena por nombre.
     * Esto es lo que hacía tu consulta PHP.
     */
    List<Retposto> findByRptCentroAndRptEmailIsNotEmptyOrderByRptNombreAsc(Integer centro);
}