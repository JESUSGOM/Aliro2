package com.aliro2.service;

import com.aliro2.model.EntreTurno;
import com.aliro2.repository.EntreTurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntreTurnoService {

    @Autowired
    private EntreTurnoRepository entreTurnoRepository;

    public List<EntreTurno> findAll() {
        return entreTurnoRepository.findAll();
    }

    public Optional<EntreTurno> findById(Integer id) {
        return entreTurnoRepository.findById(id);
    }

    public EntreTurno save(EntreTurno entreTurno) {
        return entreTurnoRepository.save(entreTurno);
    }

    public void deleteById(Integer id) {
        entreTurnoRepository.deleteById(id);
    }
}