package com.aliro2.repository;

import com.aliro2.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // <- Importar List

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    /**
     * Busca todos los menús activos cuyos IDs estén en la lista proporcionada.
     * Ordena por MnParentId y luego por MnId para asegurar un orden jerárquico.
     */
    List<Menu> findByMnIdInAndEstadoMenuOrderByMnParentIdAscMnIdAsc(List<Integer> ids, Integer estado);

}