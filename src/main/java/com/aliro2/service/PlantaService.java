package com.aliro2.service;

import com.aliro2.model.Planta;
import com.aliro2.repository.PlantaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantaService {

    @Autowired
    private PlantaRepository plantaRepository;

    public List<Planta> findAll() {
        return plantaRepository.findAll();
    }

    public Optional<Planta> findById(Integer id) {
        return plantaRepository.findById(id);
    }

    public Planta save(Planta planta) {
        return plantaRepository.save(planta);
    }

    public void deleteById(Integer id) {
        plantaRepository.deleteById(id);
    }
}