package com.aliro2.service;

import com.aliro2.model.EnvioEmail;
import com.aliro2.repository.EnvioEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnvioEmailService {

    @Autowired
    private EnvioEmailRepository envioEmailRepository;

    public List<EnvioEmail> findAll() {
        return envioEmailRepository.findAll();
    }

    public Optional<EnvioEmail> findById(Integer id) {
        return envioEmailRepository.findById(id);
    }

    public EnvioEmail save(EnvioEmail envioEmail) {
        return envioEmailRepository.save(envioEmail);
    }

    public void deleteById(Integer id) {
        envioEmailRepository.deleteById(id);
    }
}