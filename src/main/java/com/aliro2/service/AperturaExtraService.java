package com.aliro2.service;

import com.aliro2.model.AperturaExtra;
import com.aliro2.repository.AperturaExtraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AperturaExtraService {

    @Autowired
    private AperturaExtraRepository aperturaExtraRepository;

    public List<AperturaExtra> findAll() {
        return aperturaExtraRepository.findAll();
    }

    public Optional<AperturaExtra> findById(Integer id) {
        return aperturaExtraRepository.findById(id);
    }

    public AperturaExtra save(AperturaExtra aperturaExtra) {
        return aperturaExtraRepository.save(aperturaExtra);
    }

    public void deleteById(Integer id) {
        aperturaExtraRepository.deleteById(id);
    }
}