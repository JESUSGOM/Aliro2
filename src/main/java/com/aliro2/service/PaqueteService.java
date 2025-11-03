package com.aliro2.service;

import com.aliro2.model.Paquete;
import com.aliro2.repository.PaqueteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaqueteService {

    @Autowired
    private PaqueteRepository paqueteRepository;

    public List<Paquete> findAll() {
        return paqueteRepository.findAll();
    }

    public Optional<Paquete> findById(Integer id) {
        return paqueteRepository.findById(id);
    }

    public Paquete save(Paquete paquete) {
        return paqueteRepository.save(paquete);
    }

    public void deleteById(Integer id) {
        paqueteRepository.deleteById(id);
    }
}