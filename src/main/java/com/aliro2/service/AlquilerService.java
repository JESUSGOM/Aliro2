package com.aliro2.service;

import com.aliro2.model.Alquiler;
import com.aliro2.repository.AlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List; // <- Importa List
import java.util.Optional;

@Service
public class AlquilerService {

    private final AlquilerRepository alquilerRepository;

    @Autowired
    public AlquilerService(AlquilerRepository alquilerRepository) {
        this.alquilerRepository = alquilerRepository;
    }

    /**
     * AÑADE ESTE MÉTODO:
     * Llama al repositorio para obtener los alquileres por centro.
     */
    public List<Alquiler> findByCentro(Integer alqCentro) {
        return alquilerRepository.findByAlqCentro(alqCentro);
    }

    // --- Métodos CRUD Estándar (que ya tenías) ---

    public List<Alquiler> findAll() {
        return alquilerRepository.findAll();
    }

    public Optional<Alquiler> findById(Integer id) {
        return alquilerRepository.findById(id);
    }

    public Alquiler save(Alquiler alquiler) {
        return alquilerRepository.save(alquiler);
    }

    public void deleteById(Integer id) {
        alquilerRepository.deleteById(id);
    }
}