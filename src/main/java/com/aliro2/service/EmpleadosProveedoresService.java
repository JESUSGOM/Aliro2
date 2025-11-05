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

    /**
     * Busca empleados por centro, con paginación y búsqueda opcional.
     */
    public Page<EmpleadosProveedores> findByCentro(Integer empCentro, String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return empleadosProveedoresRepository.findByEmpCentroEqualsAndEmpNifContainingIgnoreCaseOrEmpNombreContainingIgnoreCaseOrEmpApellido1ContainingIgnoreCaseOrderByEmpApellido1Asc(
                    empCentro, keyword, keyword, keyword, pageable);
        } else {
            return empleadosProveedoresRepository.findByEmpCentroEqualsOrderByEmpApellido1Asc(empCentro, pageable);
        }
    }

    /**
     * Busca un empleado por ID y Centro (para validación de seguridad).
     */
    public Optional<EmpleadosProveedores> findByIdAndCentro(Integer empId, Integer empCentro) {
        return empleadosProveedoresRepository.findByEmpIdAndEmpCentroEquals(empId, empCentro);
    }

    public List<EmpleadosProveedores> findByCentro(Integer empCentro) {
        return empleadosProveedoresRepository.findByEmpCentroEquals(empCentro);
    }

    public EmpleadosProveedores save(EmpleadosProveedores empleado) {
        return empleadosProveedoresRepository.save(empleado);
    }

    public void deleteById(Integer id) {
        empleadosProveedoresRepository.deleteById(id);
    }

    public Optional<EmpleadosProveedores> findById(Integer id) {
        return empleadosProveedoresRepository.findById(id);
    }
}