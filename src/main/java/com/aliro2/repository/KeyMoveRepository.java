package com.aliro2.repository;

import com.aliro2.model.KeyMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface KeyMoveRepository extends JpaRepository<KeyMove, Integer> {

    /**
     * Nuevo método: Busca todos los movimientos de llaves (KeyMove)
     * donde la fecha de recepción (KeyFechaRecepcion) es nula.
     * Esto nos da la lista de llaves que están actualmente prestadas.
     */
    List<KeyMove> findByKeyFechaRecepcionIsNull();


}