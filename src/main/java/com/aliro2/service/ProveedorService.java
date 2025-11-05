package com.aliro2.service;

import com.aliro2.model.Proveedor;
import com.aliro2.model.ProveedorId;
import com.aliro2.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @Autowired
    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    /**
     * Busca proveedores por centro, con paginación y búsqueda opcional.
     */
    public Page<Proveedor> findByCentro(Integer prdCentro, String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Llama al método de búsqueda complejo
            return proveedorRepository.findById_PrdCentroAndPrdDenominacionContainingIgnoreCaseOrId_PrdCifContainingIgnoreCase(
                    prdCentro, keyword, keyword, pageable);
        } else {
            // Llama al método de búsqueda simple por centro
            return proveedorRepository.findById_PrdCentro(prdCentro, pageable);
        }
    }

    /**
     * Busca todos los proveedores de un centro (sin paginar).
     */
    public List<Proveedor> findByCentro(Integer prdCentro) {
        return proveedorRepository.findById_PrdCentro(prdCentro);
    }

    /**
     * Busca un proveedor por sus claves (ID compuesto).
     */
    public Optional<Proveedor> findById(ProveedorId id) {
        return proveedorRepository.findById(id);
    }

    /**
     * Busca un proveedor por sus claves separadas (para seguridad).
     */
    public Optional<Proveedor> findByIdAndCentro(String cif, Integer centro) {
        return proveedorRepository.findById_PrdCifAndId_PrdCentro(cif, centro);
    }

    public Proveedor save(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public void deleteById(ProveedorId id) {
        proveedorRepository.deleteById(id);
    }
}