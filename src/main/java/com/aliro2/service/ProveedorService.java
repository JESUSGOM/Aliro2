package com.aliro2.service;

import com.aliro2.model.Proveedor;
import com.aliro2.model.ProveedorId;
import com.aliro2.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
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