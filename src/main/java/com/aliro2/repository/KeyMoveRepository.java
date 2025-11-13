package com.aliro2.repository;

import com.aliro2.model.KeyMove;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface KeyMoveRepository extends JpaRepository<KeyMove, Integer> {

    // --- MÉTODOS REQUERIDOS PARA "LLAVES" ---

    // (Para Informes) Busca TODOS los movimientos de un CENTRO, paginado.
    Page<KeyMove> findByKeyCentroEqualsOrderByKeyOrdenDesc(Integer keyCentro, Pageable pageable);

    // (Para Lógica de Entrega) Busca llaves PRESTADAS (sin fecha recepción) de un CENTRO.
    List<KeyMove> findByKeyCentroEqualsAndKeyFechaHoraRecepcionDtIsNull(Integer keyCentro);

    /**
     * 2. (Para Recogida) Busca llaves PRESTADAS (sin fecha recepción) de un CENTRO, paginado.
     */
    Page<KeyMove> findByKeyCentroEqualsAndKeyFechaRecepcionIsNullOrderByKeyOrdenDesc(Integer keyCentro, Pageable pageable);

    /**
     * 3. (Para Lógica de Entrega) Busca llaves PRESTADAS de un CENTRO (sin paginar).
     * Usado para saber qué llaves NO mostrar en el desplegable.
     */
    List<KeyMove> findByKeyCentroEqualsAndKeyFechaRecepcionIsNull(Integer keyCentro);

    // (Para Seguridad) Busca un movimiento por su ID y su Centro.
    Optional<KeyMove> findByKeyOrdenAndKeyCentro(Integer keyOrden, Integer keyCentro);

    // (Para Recogida) Busca llaves PRESTADAS (sin recepción) de un CENTRO, entregadas HOY.
    Page<KeyMove> findByKeyCentroEqualsAndKeyFechaHoraRecepcionDtIsNullAndKeyFechaHoraEntregaDtBetweenOrderByKeyOrdenDesc(
            Integer keyCentro, LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable pageable);

    // (Sobrecarga para cuando no filtra por fecha - si decides quitar el filtro "hoy")
    Page<KeyMove> findByKeyCentroEqualsAndKeyFechaHoraRecepcionDtIsNullOrderByKeyOrdenDesc(Integer keyCentro, Pageable pageable);

    // --- MÉTODOS REQUERIDOS PARA /llaves/recogida ---


    /**
     * AÑADE ESTE NUEVO MÉTODO:
     * Busca llaves PRESTADAS (Salida IS NULL) + ENTREGADAS HOY (FechaEntrega) + POR CENTRO.
     */
    Page<KeyMove> findByKeyCentroEqualsAndKeyFechaRecepcionIsNullAndKeyFechaEntregaEqualsOrderByKeyOrdenDesc(Integer keyCentro, String fechaHoy, Pageable pageable);
}