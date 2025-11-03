package com.aliro2.service;

import com.aliro2.model.Movadoj;
import com.aliro2.repository.MovadojRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovadojService {

    @Autowired
    private MovadojRepository movadojRepository;

    public List<Movadoj> findAll() {
        return movadojRepository.findAll();
    }

    public Optional<Movadoj> findById(Integer id) {
        return movadojRepository.findById(id);
    }

    public Movadoj save(Movadoj movadoj) {
        return movadojRepository.save(movadoj);
    }

    public void deleteById(Integer id) {
        movadojRepository.deleteById(id);
    }
}