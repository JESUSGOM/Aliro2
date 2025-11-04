package com.aliro2.service;

import com.aliro2.model.Movadoj;
import com.aliro2.repository.MovadojRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate; // <- Importar LocalDate
import java.time.format.DateTimeFormatter; // <- Importar DateTimeFormatter
import java.util.List;
import java.util.Optional;

@Service
public class MovadojService {

    private final MovadojRepository movadojRepository;

    // Formateador para la fecha (tu BD usa dd/MM/yy)
    private final DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("dd/MM/yy");

    @Autowired
    public MovadojService(MovadojRepository movadojRepository) {
        this.movadojRepository = movadojRepository;
    }

    // ... (findAll, findById, save, deleteById se quedan igual) ...
    public List<Movadoj> findAll() { return movadojRepository.findAll(); }
    public Optional<Movadoj> findById(Integer id) { return movadojRepository.findById(id); }
    public Movadoj save(Movadoj movadoj) { return movadojRepository.save(movadoj); }
    public void deleteById(Integer id) { movadojRepository.deleteById(id); }


    /**
     * MÉTODO ACTUALIZADO:
     * Ahora este método filtra automáticamente las visitas activas
     * para que muestre SÓLO las del día actual.
     */
    public Page<Movadoj> findVisitasActivas(String keyword, Pageable pageable) {

        // 1. Obtenemos la fecha de hoy y la formateamos como "dd/MM/yy"
        String fechaHoy = LocalDate.now().format(dtfFecha);

        if (keyword != null && !keyword.trim().isEmpty()) {
            // 2. Si hay búsqueda, llama al NUEVO método de búsqueda CON FECHA
            return movadojRepository.findByMovFechaSalidaIsNullAndMovFechaEntradaEqualsAndMovNombreContainingIgnoreCaseOrMovApellidoUnoContainingIgnoreCaseOrderByMovOrdenDesc(fechaHoy, keyword, keyword, pageable);
        } else {
            // 3. Si no hay búsqueda, llama al NUEVO método simple CON FECHA
            return movadojRepository.findByMovFechaSalidaIsNullAndMovFechaEntradaEqualsOrderByMovOrdenDesc(fechaHoy, pageable);
        }
    }
}