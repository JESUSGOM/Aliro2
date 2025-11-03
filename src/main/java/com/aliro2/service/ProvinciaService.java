package com.aliro2.service;

import com.aliro2.model.Provincia;
import com.aliro2.repository.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinciaService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    public List<Provincia> findAll() {
        return provinciaRepository.findAll();
    }

    public Optional<Provincia> findById(Integer id) {
        return provinciaRepository.findById(id);
    }

    public Provincia save(Provincia provincia) {
        return provinciaRepository.save(provincia);
    }

    public void deleteById(Integer id) {
        provinciaRepository.deleteById(id);
    }
}