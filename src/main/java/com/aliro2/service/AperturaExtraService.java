package com.aliro2.service;

import com.aliro2.model.AperturaExtra;
import com.aliro2.repository.AperturaExtraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AperturaExtraService {

    private final AperturaExtraRepository aperturaExtraRepository;

    @Autowired
    public AperturaExtraService(AperturaExtraRepository aperturaExtraRepository) {
        this.aperturaExtraRepository = aperturaExtraRepository;
    }

    /**
     * Busca TODOS los registros por centro, con paginación y búsqueda opcional.
     * (Para "Informes")
     */
    public Page<AperturaExtra> findAllByCentro(Integer aeCentro, String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return aperturaExtraRepository.findByAeCentroEqualsAndAeMotivoContainingIgnoreCaseOrderByAeIdDesc(
                    aeCentro, keyword, pageable);
        } else {
            return aperturaExtraRepository.findByAeCentroEqualsOrderByAeIdDesc(aeCentro, pageable);
        }
    }

    /**
     * Busca un registro por ID y Centro (para validación de seguridad).
     */
    public Optional<AperturaExtra> findByIdAndCentro(Integer aeId, Integer aeCentro) {
        return aperturaExtraRepository.findByAeIdAndAeCentroEquals(aeId, aeCentro);
    }

    // --- Métodos CRUD Estándar ---

    public AperturaExtra save(AperturaExtra aperturaExtra) {
        return aperturaExtraRepository.save(aperturaExtra);
    }

    public List<AperturaExtra> findAll() {
        return aperturaExtraRepository.findAll();
    }

    public Optional<AperturaExtra> findById(Integer id) {
        return aperturaExtraRepository.findById(id);
    }

    public void deleteById(Integer id) {
        aperturaExtraRepository.deleteById(id);
    }
}