package com.aliro2.service;

import com.aliro2.model.KeyMove;
import com.aliro2.repository.KeyMoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate; // <- Importar LocalDate
import java.time.format.DateTimeFormatter; // <- Importar DateTimeFormatter
import java.util.List;
import java.util.Optional;

@Service
public class KeyMoveService {

    private final KeyMoveRepository keyMoveRepository;

    // El formato de fecha de tu BD es AAAAMMDD
    private final DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    public KeyMoveService(KeyMoveRepository keyMoveRepository) {
        this.keyMoveRepository = keyMoveRepository;
    }

    // ... (Métodos existentes: findByCentro, findById, save, etc.) ...
    public Page<KeyMove> findByCentro(Integer keyCentro, Pageable pageable) {
        return keyMoveRepository.findByKeyCentroEqualsOrderByKeyOrdenDesc(keyCentro, pageable);
    }
    public Optional<KeyMove> findByIdAndCentro(Integer id, Integer centro) {
        return keyMoveRepository.findByKeyOrdenAndKeyCentro(id, centro);
    }
    public List<KeyMove> findLlavesPrestadasPorCentro(Integer keyCentro) {
        return keyMoveRepository.findByKeyCentroEqualsAndKeyFechaRecepcionIsNull(keyCentro);
    }
    public KeyMove save(KeyMove keyMove) {
        return keyMoveRepository.save(keyMove);
    }
    public Optional<KeyMove> findById(Integer id) {
        return keyMoveRepository.findById(id);
    }
    public void deleteById(Integer id) {
        keyMoveRepository.deleteById(id);
    }


    /**
     * MÉTODO CORREGIDO:
     * Busca llaves PRESTADAS (para la lista de recogida)
     * PERO AHORA FILTRA SÓLO POR LAS ENTREGADAS HOY.
     */
    public Page<KeyMove> findLlavesPrestadasPorCentro(Integer keyCentro, Pageable pageable) {

        // 1. Obtenemos la fecha de hoy en formato yyyyMMdd
        String fechaHoy = LocalDate.now().format(dtfFecha);

        // 2. Llamamos al nuevo método del repositorio que filtra por fecha
        return keyMoveRepository.findByKeyCentroEqualsAndKeyFechaRecepcionIsNullAndKeyFechaEntregaEqualsOrderByKeyOrdenDesc(keyCentro, fechaHoy, pageable);
    }
}