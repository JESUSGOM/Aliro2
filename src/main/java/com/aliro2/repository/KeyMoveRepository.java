package com.aliro2.repository;

import com.aliro2.model.KeyMove;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KeyMoveRepository extends JpaRepository<KeyMove, Integer> {

    // --- MÉTODOS REQUERIDOS PARA "LLAVES" ---

    /**
     * 1. (Para Informes) Busca TODOS los movimientos de un CENTRO, paginado.
     */
    Page<KeyMove> findByKeyCentroEqualsOrderByKeyOrdenDesc(Integer keyCentro, Pageable pageable);

    /**
     * 2. (Para Recogida) Busca llaves PRESTADAS (sin fecha recepción) de un CENTRO, paginado.
     */
    Page<KeyMove> findByKeyCentroEqualsAndKeyFechaRecepcionIsNullOrderByKeyOrdenDesc(Integer keyCentro, Pageable pageable);

    /**
     * 3. (Para Lógica de Entrega) Busca llaves PRESTADAS de un CENTRO (sin paginar).
     * Usado para saber qué llaves NO mostrar en el desplegable.
     */
    List<KeyMove> findByKeyCentroEqualsAndKeyFechaRecepcionIsNull(Integer keyCentro);

    /**
     * 4. (Para Seguridad) Busca un movimiento por su ID y su Centro.
     */
    Optional<KeyMove> findByKeyOrdenAndKeyCentro(Integer keyOrden, Integer keyCentro);

}