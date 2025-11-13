package com.aliro2.service;

import com.aliro2.model.Garaje;
import com.aliro2.repository.GarajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GarajeService {

    private final GarajeRepository garajeRepository;

    @Autowired
    public GarajeService(GarajeRepository garajeRepository) {
        this.garajeRepository = garajeRepository;
    }

    /**
     * Busca TODOS los registros de garaje, con paginación y búsqueda opcional.
     * (Para "Informes")
     */
    public Page<Garaje> findAll(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return garajeRepository.findByGrjMatriculaContainingIgnoreCaseOrGrjNombreContainingIgnoreCaseOrGrjEmpresaContainingIgnoreCaseOrderByGrjIdDesc(
                    keyword, keyword, keyword, pageable);
        } else {
            return garajeRepository.findAllByOrderByGrjIdDesc(pageable);
        }
    }

    // --- Métodos CRUD Estándar ---

    public Garaje save(Garaje garaje) {
        return garajeRepository.save(garaje);
    }

    public List<Garaje> findAll() {
        return garajeRepository.findAll();
    }

    public Optional<Garaje> findById(Integer id) {
        return garajeRepository.findById(id);
    }

    public void deleteById(Integer id) {
        garajeRepository.deleteById(id);
    }
}