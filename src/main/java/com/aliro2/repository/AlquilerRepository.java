package com.aliro2.repository;

import com.aliro2.model.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // <- Asegúrate de importar List

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Integer> {

    /**
     * AÑADE ESTE MÉTODO:
     * Busca todas las empresas de alquiler que pertenecen a un centro (AlqCentro).
     */
    List<Alquiler> findByAlqCentro(Integer alqCentro);
}