package com.aliro2.service;

import com.aliro2.model.Incidencia;
import com.aliro2.repository.IncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidenciaService {

    @Autowired
    private IncidenciaRepository incidenciaRepository;

    public List<Incidencia> findAll() {
        return incidenciaRepository.findAll();
    }

    public Optional<Incidencia> findById(Integer id) {
        return incidenciaRepository.findById(id);
    }

    public Incidencia save(Incidencia incidencia) {
        return incidenciaRepository.save(incidencia);
    }

    public void deleteById(Integer id) {
        incidenciaRepository.deleteById(id);
    }
}