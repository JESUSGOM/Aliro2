package com.aliro2.service;

import com.aliro2.model.EmpleadosProveedores;
import com.aliro2.repository.EmpleadosProveedoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadosProveedoresService {

    private final EmpleadosProveedoresRepository empleadosProveedoresRepository;

    @Autowired
    public EmpleadosProveedoresService(EmpleadosProveedoresRepository empleadosProveedoresRepository) {
        this.empleadosProveedoresRepository = empleadosProveedoresRepository;
    }

    // ... (métodos existentes: findByCentro, findByIdAndCentro, etc.) ...
    public Page<EmpleadosProveedores> findByCentro(Integer empCentro, String keyword, Pageable pageable) {
        // ... (código existente)
        if (keyword != null && !keyword.trim().isEmpty()) {
            return empleadosProveedoresRepository.findByEmpCentroEqualsAndEmpNifContainingIgnoreCaseOrEmpNombreContainingIgnoreCaseOrEmpApellido1ContainingIgnoreCaseOrderByEmpApellido1Asc(
                    empCentro, keyword, keyword, keyword, pageable);
        } else {
            return empleadosProveedoresRepository.findByEmpCentroEqualsOrderByEmpApellido1Asc(empCentro, pageable);
        }
    }
    public Optional<EmpleadosProveedores> findByIdAndCentro(Integer empId, Integer empCentro) {
        return empleadosProveedoresRepository.findByEmpIdAndEmpCentroEquals(empId, empCentro);
    }
    public List<EmpleadosProveedores> findByCentro(Integer empCentro) {
        return empleadosProveedoresRepository.findByEmpCentroEquals(empCentro);
    }

    /**
     * AÑADE ESTE MÉTODO:
     * Busca a un empleado por su NIF y Centro.
     */
    public Optional<EmpleadosProveedores> findByNifAndCentro(String empNif, Integer empCentro) {
        return empleadosProveedoresRepository.findByEmpNifAndEmpCentroEquals(empNif, empCentro);
    }

    // --- Métodos CRUD Estándar ---
    public EmpleadosProveedores save(EmpleadosProveedores empleado) {
        return empleadosProveedoresRepository.save(empleado);
    }
    public void deleteById(Integer id) {
        empleadosProveedoresRepository.deleteById(id);
    }
    public Optional<EmpleadosProveedores> findById(Integer id) {
        return empleadosProveedoresRepository.findById(id);
    }
    public List<EmpleadosProveedores> findAll() {
        return empleadosProveedoresRepository.findAll();
    }
}