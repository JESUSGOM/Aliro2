package com.aliro2.repository;

import com.aliro2.model.Planta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Asegúrate de importar List

@Repository
public interface PlantaRepository extends JpaRepository<Planta, Integer> {

    /**
     * AÑADE ESTE MÉTODO:
     * Busca todas las plantas que pertenecen a un centro (PltCentro).
     */
    List<Planta> findByPltCentro(Integer pltCentro);
}