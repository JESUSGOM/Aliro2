package com.aliro2.repository;

import com.aliro2.model.Movadoj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovadojRepository extends JpaRepository<Movadoj, Integer> {

    // Nuevo método para encontrar visitas sin fecha de salida
    List<Movadoj> findByMovFechaSalidaIsNullOrderByMovFechaEntradaDesc();
}