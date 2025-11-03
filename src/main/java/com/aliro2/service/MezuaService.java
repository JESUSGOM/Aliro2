package com.aliro2.service;

import com.aliro2.model.Mezua;
import com.aliro2.repository.MezuaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MezuaService {

    @Autowired
    private MezuaRepository mezuaRepository;

    public List<Mezua> findAll() {
        return mezuaRepository.findAll();
    }

    public Optional<Mezua> findById(Integer id) {
        return mezuaRepository.findById(id);
    }

    public Mezua save(Mezua mezua) {
        return mezuaRepository.save(mezua);
    }

    public void deleteById(Integer id) {
        mezuaRepository.deleteById(id);
    }
}