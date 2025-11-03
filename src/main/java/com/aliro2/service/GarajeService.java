package com.aliro2.service;

import com.aliro2.model.Garaje;
import com.aliro2.repository.GarajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GarajeService {

    @Autowired
    private GarajeRepository garajeRepository;

    public List<Garaje> findAll() {
        return garajeRepository.findAll();
    }

    public Optional<Garaje> findById(Integer id) {
        return garajeRepository.findById(id);
    }

    public Garaje save(Garaje garaje) {
        return garajeRepository.save(garaje);
    }

    public void deleteById(Integer id) {
        garajeRepository.deleteById(id);
    }
}