package com.aliro2.service;

import com.aliro2.model.KeyMove;
import com.aliro2.repository.KeyMoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KeyMoveService {

    @Autowired
    private KeyMoveRepository keyMoveRepository;

    public List<KeyMove> findAll() {
        return keyMoveRepository.findAll();
    }

    public Optional<KeyMove> findById(Integer id) {
        return keyMoveRepository.findById(id);
    }

    public KeyMove save(KeyMove keyMove) {
        return keyMoveRepository.save(keyMove);
    }

    public void deleteById(Integer id) {
        keyMoveRepository.deleteById(id);
    }
}