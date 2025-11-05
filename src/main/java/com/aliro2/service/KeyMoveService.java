package com.aliro2.service;

import com.aliro2.model.KeyMove;
import com.aliro2.repository.KeyMoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KeyMoveService {

    private final KeyMoveRepository keyMoveRepository;

    @Autowired
    public KeyMoveService(KeyMoveRepository keyMoveRepository) {
        this.keyMoveRepository = keyMoveRepository;
    }

    /**
     * Busca TODOS los movimientos de un CENTRO (para informes).
     */
    public Page<KeyMove> findByCentro(Integer keyCentro, Pageable pageable) {
        return keyMoveRepository.findByKeyCentroEqualsOrderByKeyOrdenDesc(keyCentro, pageable);
    }

    /**
     * Busca llaves PRESTADAS de un CENTRO (para la lista de recogida).
     */
    public Page<KeyMove> findLlavesPrestadasPorCentro(Integer keyCentro, Pageable pageable) {
        return keyMoveRepository.findByKeyCentroEqualsAndKeyFechaRecepcionIsNullOrderByKeyOrdenDesc(keyCentro, pageable);
    }

    /**
     * Busca llaves PRESTADAS de un CENTRO (para filtrar el dropdown de entrega).
     */
    public List<KeyMove> findLlavesPrestadasPorCentro(Integer keyCentro) {
        return keyMoveRepository.findByKeyCentroEqualsAndKeyFechaRecepcionIsNull(keyCentro);
    }

    /**
     * Busca un movimiento por ID y CENTRO (para seguridad).
     */
    public Optional<KeyMove> findByIdAndCentro(Integer id, Integer centro) {
        return keyMoveRepository.findByKeyOrdenAndKeyCentro(id, centro);
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
}