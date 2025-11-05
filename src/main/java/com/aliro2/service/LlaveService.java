package com.aliro2.service;

import com.aliro2.model.Llave;
import com.aliro2.repository.LlaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LlaveService {

    private final LlaveRepository llaveRepository;

    @Autowired
    public LlaveService(LlaveRepository llaveRepository) {
        this.llaveRepository = llaveRepository;
    }

    public List<Llave> findAll() {
        return llaveRepository.findAll();
    }

    /**
     * AÑADE ESTE MÉTODO:
     * Busca todas las llaves de un centro específico.
     */
    public List<Llave> findByCentro(Integer llvCentro) {
        return llaveRepository.findByLlvCentro(llvCentro);
    }

    public Optional<Llave> findById(Integer id) {
        return llaveRepository.findById(id);
    }

    public Llave save(Llave llave) {
        return llaveRepository.save(llave);
    }

    public void deleteById(Integer id) {
        llaveRepository.deleteById(id);
    }
}