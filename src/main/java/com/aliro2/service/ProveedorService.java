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
     * MÉTODO ACTUALIZADO:
     * Busca proveedores por centro, con paginación y búsqueda opcional.
     */
    public Page<Proveedor> findByCentro(Integer prdCentro, String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return proveedorRepository.findById_PrdCentroAndPrdDenominacionContainingIgnoreCaseOrId_PrdCifContainingIgnoreCase(
                    prdCentro, keyword, keyword, pageable);
        } else {
            return proveedorRepository.findById_PrdCentro(prdCentro, pageable);
        }
    }

    // Método para obtener una lista simple (usado en EmpleadoProveedor)
    public List<Proveedor> findByCentro(Integer prdCentro) {
        return proveedorRepository.findById_PrdCentro(prdCentro);
    }

    public Optional<Proveedor> findById(ProveedorId id) {
        return proveedorRepository.findById(id);
    }

    public Proveedor save(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public void deleteById(ProveedorId id) {
        proveedorRepository.deleteById(id);
    }
}