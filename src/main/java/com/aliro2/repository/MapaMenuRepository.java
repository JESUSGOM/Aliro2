package com.aliro2.repository;

import com.aliro2.model.MapaMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // <- Importar List

@Repository
public interface MapaMenuRepository extends JpaRepository<MapaMenu, Integer> {

    /**
     * Busca todas las asignaciones de menú para un tipo de usuario
     * y un centro específico.
     */
    List<MapaMenu> findByMmUsuTipoAndMmCentro(String usuTipo, Integer centro);

}