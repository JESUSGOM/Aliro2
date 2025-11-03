package com.aliro2.service;

import com.aliro2.model.Alquiler;
import com.aliro2.repository.AlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;

    public List<Alquiler> findAll() {
        return alquilerRepository.findAll();
    }

    public Optional<Alquiler> findById(Integer id) {
        return alquilerRepository.findById(id);
    }

    public Alquiler save(Alquiler alquiler) {
        return alquilerRepository.save(alquiler);
    }

    public void deleteById(Integer id) {
        alquilerRepository.deleteById(id);
    }
}