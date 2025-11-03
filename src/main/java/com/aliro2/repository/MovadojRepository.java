package com.aliro2.repository;

import com.aliro2.model.Movadoj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovadojRepository extends JpaRepository<Movadoj, Integer> {

    // Nuevo método para encontrar visitas sin fecha de salida
    List<Movadoj> findByMovFechaSalidaIsNullOrderByMovFechaEntradaDesc();

    /**
     * AÑADE ESTE MÉTODO:
     * Spring Data JPA creará automáticamente una consulta que busca todos los registros
     * donde el campo 'movFechaSalida' es nulo, ordenados por el ID (MovOrden)
     * de forma descendente (los más recientes primero).
     */
    List<Movadoj> findByMovFechaSalidaIsNullOrderByMovOrdenDesc();
}