package com.aliro2.service;

import com.aliro2.model.Movadoj;
import com.aliro2.repository.MovadojRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovadojService {

    private final MovadojRepository movadojRepository;

    @Autowired
    public MovadojService(MovadojRepository movadojRepository) {
        this.movadojRepository = movadojRepository;
    }

    // (Para /visitas/salida - Filtra por HOY y CENTRO)
    public Page<Movadoj> findVisitasActivas(String keyword, Integer movCentro, Pageable pageable) {
        // Define el rango de "Hoy" (desde 00:00 hasta 23:59:59)
        LocalDateTime inicioDelDia = LocalDate.now().atStartOfDay(); // Hoy a las 00:00
        LocalDateTime finDelDia = LocalDate.now().atTime(LocalTime.MAX); // Hoy a las 23:59:59.999...

        if (keyword != null && !keyword.trim().isEmpty()) {
            return movadojRepository.findByMovFechaHoraSalidaDtIsNullAndMovCentroEqualsAndMovFechaHoraEntradaDtBetweenAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(
                    movCentro, inicioDelDia, finDelDia, keyword, keyword, pageable);
        } else {
            return movadojRepository.findByMovFechaHoraSalidaDtIsNullAndMovCentroEqualsAndMovFechaHoraEntradaDtBetweenOrderByMovOrdenDesc(
                    movCentro, inicioDelDia, finDelDia, pageable);
        }
    }

    // (Para /visitas/informes/general - Filtra TODAS las ACTIVAS por CENTRO)
    public Page<Movadoj> findTodasVisitasActivasPorCentro(String keyword, Integer movCentro, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return movadojRepository.findByMovFechaHoraSalidaDtIsNullAndMovCentroEqualsAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(movCentro, keyword, keyword, pageable);
        } else {
            return movadojRepository.findByMovFechaHoraSalidaDtIsNullAndMovCentroEqualsOrderByMovOrdenDesc(movCentro, pageable);
        }
    }

    /**
     * AÑADE ESTE MÉTODO:
     * (Para /visitas/informes/movimientos)
     * Busca TODOS los movimientos (activos y cerrados) de un CENTRO.
     */
    public Page<Movadoj> findAllByCentro(String keyword, Integer movCentro, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return movadojRepository.findByMovCentroEqualsAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(movCentro, keyword, keyword, pageable);
        } else {
            return movadojRepository.findByMovCentroEqualsOrderByMovOrdenDesc(movCentro, pageable);
        }
    }

    // --- Métodos CRUD Estándar ---
    public List<Movadoj> findAllByCentro(Integer movCentro) {
        return movadojRepository.findByMovFechaSalidaIsNullAndMovCentroEqualsOrderByMovOrdenDesc(movCentro);
    }
    public Optional<Movadoj> findById(Integer id) { return movadojRepository.findById(id); }
    public Optional<Movadoj> findByIdAndMovCentro(Integer id, Integer movCentro) {
        return movadojRepository.findByMovOrdenAndMovCentro(id, movCentro);
    }
    public Movadoj save(Movadoj movadoj) { return movadojRepository.save(movadoj); }
    public void deleteById(Integer id) { movadojRepository.deleteById(id); }
}