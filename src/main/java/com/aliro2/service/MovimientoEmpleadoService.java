package com.aliro2.service;

import com.aliro2.model.MovimientoEmpleado;
import com.aliro2.repository.MovimientoEmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoEmpleadoService {

    @Autowired
    private MovimientoEmpleadoRepository movimientoEmpleadoRepository;

    public List<MovimientoEmpleado> findAll() {
        return movimientoEmpleadoRepository.findAll();
    }

    public Optional<MovimientoEmpleado> findById(Integer id) {
        return movimientoEmpleadoRepository.findById(id);
    }

    public MovimientoEmpleado save(MovimientoEmpleado movimientoEmpleado) {
        return movimientoEmpleadoRepository.save(movimientoEmpleado);
    }

    public void deleteById(Integer id) {
        movimientoEmpleadoRepository.deleteById(id);
    }
}