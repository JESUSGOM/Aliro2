package com.aliro2.service;

import com.aliro2.model.MovimientosEmpleados;
import com.aliro2.repository.MovimientosEmpleadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientosEmpleadosService {

    private final MovimientosEmpleadosRepository movimientosRepository;

    @Autowired
    public MovimientosEmpleadosService(MovimientosEmpleadosRepository movimientosRepository) {
        this.movimientosRepository = movimientosRepository;
    }

    /**
     * Busca TODOS los movimientos por centro, con paginación y búsqueda opcional.
     */
    public Page<MovimientosEmpleados> findAllByCentro(Integer movCentro, String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return movimientosRepository.findByMovCentroWithSearch(movCentro, keyword, pageable);
        } else {
            return movimientosRepository.findByMovCentroEqualsOrderByMovIdDesc(movCentro, pageable);
        }
    }

    /**
     * Busca movimientos ACTIVOS por centro, con paginación y búsqueda opcional.
     */
    public Page<MovimientosEmpleados> findActivosByCentro(Integer movCentro, String keyword, Pageable pageable) {
        String queryKeyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword : "";

        if (!queryKeyword.isEmpty()) {
            return movimientosRepository.findActivosByMovCentroWithSearch(movCentro, queryKeyword, pageable);
        } else {
            return movimientosRepository.findByMovCentroEqualsAndMovFechaHoraSalidaIsNullOrderByMovIdDesc(movCentro, pageable);
        }
    }

    /**
     * Busca un movimiento por ID y Centro (para validación de seguridad).
     */
    public Optional<MovimientosEmpleados> findByIdAndCentro(Integer movId, Integer movCentro) {
        return movimientosRepository.findByMovIdAndMovCentroEquals(movId, movCentro);
    }

    // --- Métodos CRUD Estándar ---

    public MovimientosEmpleados save(MovimientosEmpleados movimiento) {
        return movimientosRepository.save(movimiento);
    }

    public void deleteById(Integer id) {
        movimientosRepository.deleteById(id);
    }

    public Optional<MovimientosEmpleados> findById(Integer id) {
        return movimientosRepository.findById(id);
    }

    public List<MovimientosEmpleados> findAll() {
        return movimientosRepository.findAll();
    }
}