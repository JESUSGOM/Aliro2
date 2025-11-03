package com.aliro2.service;

import com.aliro2.model.Retposto;
import com.aliro2.repository.RetpostoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RetpostoService {

    @Autowired
    private RetpostoRepository retpostoRepository;

    public List<Retposto> findAll() {
        return retpostoRepository.findAll();
    }

    public Optional<Retposto> findById(Integer id) {
        return retpostoRepository.findById(id);
    }

    public Retposto save(Retposto retposto) {
        return retpostoRepository.save(retposto);
    }

    public void deleteById(Integer id) {
        retpostoRepository.deleteById(id);
    }
}