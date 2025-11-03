package com.aliro2.service;

import com.aliro2.model.Movadoj;
import com.aliro2.repository.MovadojRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovadojService {

    // 1. El repositorio se declara como 'final'
    private final MovadojRepository movadojRepository;

    // 2. Se usa un constructor para la inyección de dependencias (buena práctica)
    @Autowired
    public MovadojService(MovadojRepository movadojRepository) {
        this.movadojRepository = movadojRepository;
    }

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

    // 3. Se deja solo UNA versión del método findVisitasActivas
    /**
     * Llama al nuevo método del repositorio para obtener solo las visitas activas.
     * (Busca visitas donde 'movFechaSalida' es nulo y las ordena).
     */
    public List<Movadoj> findVisitasActivas() {
        return movadojRepository.findByMovFechaSalidaIsNullOrderByMovOrdenDesc();
    }
}