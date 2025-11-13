package com.aliro2.service;

import com.aliro2.model.KeyMove;
import com.aliro2.repository.KeyMoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime; // <- IMPORTADO
import java.time.LocalTime;   // <- IMPORTADO
import java.util.List;
import java.util.Optional;

@Service
public class KeyMoveService {

    private final KeyMoveRepository keyMoveRepository;
    // private final DateTimeFormatter dtfFecha = ... // <- ELIMINADO

    @Autowired
    public KeyMoveService(KeyMoveRepository keyMoveRepository) {
        this.keyMoveRepository = keyMoveRepository;
    }

    // (Para Informes - sin cambios)
    public Page<KeyMove> findByCentro(Integer keyCentro, Pageable pageable) {
        return keyMoveRepository.findByKeyCentroEqualsOrderByKeyOrdenDesc(keyCentro, pageable);
    }

    // (Para Seguridad - sin cambios)
    public Optional<KeyMove> findByIdAndCentro(Integer id, Integer centro) {
        return keyMoveRepository.findByKeyOrdenAndKeyCentro(id, centro);
    }

    /**
     * (Para /llaves/recogida - REFACTORIZADO)
     * Busca llaves PRESTADAS (para la lista de recogida) FILTRANDO SÓLO POR HOY.
     */
    public Page<KeyMove> findLlavesPrestadasPorCentro(Integer keyCentro, Pageable pageable) {

        // 1. Define el rango de "Hoy" (desde 00:00 hasta 23:59:59)
        LocalDateTime inicioDelDia = LocalDate.now().atStartOfDay(); // Hoy a las 00:00
        LocalDateTime finDelDia = LocalDate.now().atTime(LocalTime.MAX); // Hoy a las 23:59:59.999...

        // 2. Llamamos al nuevo método del repositorio que filtra por rango de fechas
        return keyMoveRepository.findByKeyCentroEqualsAndKeyFechaHoraRecepcionDtIsNullAndKeyFechaHoraEntregaDtBetweenOrderByKeyOrdenDesc(
                keyCentro, inicioDelDia, finDelDia, pageable);
    }

    // --- Métodos CRUD ---
    public KeyMove save(KeyMove keyMove) {
        return keyMoveRepository.save(keyMove);
    }
    public Optional<KeyMove> findById(Integer id) {
        return keyMoveRepository.findById(id);
    }
    public void deleteById(Integer id) {
        keyMoveRepository.deleteById(id);
    }
}