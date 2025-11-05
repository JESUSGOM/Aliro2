package com.aliro2.repository;

import com.aliro2.model.Llave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Asegúrate de importar List

@Repository
public interface LlaveRepository extends JpaRepository<Llave, Integer> {

    /**
     * AÑADE ESTE MÉTODO:
     * Busca todas las llaves que pertenecen a un centro específico.
     */
    List<Llave> findByLlvCentro(Integer llvCentro);
}