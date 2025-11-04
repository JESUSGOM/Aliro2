package com.aliro2.service;

import com.aliro2.model.Movadoj;
import com.aliro2.repository.MovadojRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class MovadojService {

    private final MovadojRepository movadojRepository;
    private final DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    public MovadojService(MovadojRepository movadojRepository) {
        this.movadojRepository = movadojRepository;
    }

    // --- CAMBIO AQUÍ: findAll ahora también recibe el centro ---
    public List<Movadoj> findAll(Integer movCentro) {
        // Asumiendo que findAll también debería filtrar por centro
        return movadojRepository.findByMovFechaSalidaIsNullAndMovCentroEqualsOrderByMovOrdenDesc(movCentro);
    }

    // --- CAMBIO AQUÍ: findById también debería comprobar el centro si lo usas en el flujo ---
    // Si findById se usa para editar o dar salida, debe asegurar que la visita pertenece al centro del usuario.
    public Optional<Movadoj> findByIdAndMovCentro(Integer id, Integer movCentro) {
        return movadojRepository.findById(id) // Primero buscamos por ID
                .filter(movadoj -> movadoj.getMovCentro().equals(movCentro)); // Luego filtramos por centro
    }

    public Movadoj save(Movadoj movadoj) {
        return movadojRepository.save(movadoj);
    }

    public void deleteById(Integer id) {
        movadojRepository.deleteById(id);
    }


    /**
     * MÉTODO ACTUALIZADO:
     * Ahora este método filtra las visitas activas por el día actual Y por el centro del usuario.
     */
    public Page<Movadoj> findVisitasActivas(String keyword, Integer movCentro, Pageable pageable) { // <- Recibe movCentro

        String fechaHoy = LocalDate.now().format(dtfFecha);

        if (keyword != null && !keyword.trim().isEmpty()) {
            // Llama al NUEVO método del repositorio con fecha, centro y búsqueda
            return movadojRepository.findByMovFechaSalidaIsNullAndMovFechaEntradaEqualsAndMovCentroEqualsAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(fechaHoy, movCentro, keyword, keyword, pageable);
        } else {
            // Llama al NUEVO método del repositorio con fecha y centro
            return movadojRepository.findByMovFechaSalidaIsNullAndMovFechaEntradaEqualsAndMovCentroEqualsOrderByMovOrdenDesc(fechaHoy, movCentro, pageable);
        }
    }
}