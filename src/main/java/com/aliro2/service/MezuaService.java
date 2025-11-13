package com.aliro2.service;

import com.aliro2.model.Mezua;
import com.aliro2.repository.MezuaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MezuaService {

    private final MezuaRepository mezuaRepository;

    @Autowired
    public MezuaService(MezuaRepository mezuaRepository) {
        this.mezuaRepository = mezuaRepository;
    }

    /**
     * Busca todos los registros de Mezua, con paginación y búsqueda opcional.
     */
    public Page<Mezua> findAll(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return mezuaRepository.findByMezNombreContainingIgnoreCaseOrMezApellidosContainingIgnoreCaseOrMezEmailContainingIgnoreCaseOrderByMezIdDesc(
                    keyword, keyword, keyword, pageable);
        } else {
            return mezuaRepository.findAllByOrderByMezIdDesc(pageable);
        }
    }

    // --- Métodos CRUD Estándar ---

    public Mezua save(Mezua mezua) {
        return mezuaRepository.save(mezua);
    }

    public List<Mezua> findAll() {
        return mezuaRepository.findAll();
    }

    public Optional<Mezua> findById(Integer id) {
        return mezuaRepository.findById(id);
    }

    public void deleteById(Integer id) {
        mezuaRepository.deleteById(id);
    }
}