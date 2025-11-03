package com.aliro2.service;

import com.aliro2.model.Telefono;
import com.aliro2.repository.TelefonoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelefonoService {

    @Autowired
    private TelefonoRepository telefonoRepository;

    public List<Telefono> findAll() {
        return telefonoRepository.findAll();
    }

    public Optional<Telefono> findById(Integer id) {
        return telefonoRepository.findById(id);
    }

    public Telefono save(Telefono telefono) {
        return telefonoRepository.save(telefono);
    }

    public void deleteById(Integer id) {
        telefonoRepository.deleteById(id);
    }
}